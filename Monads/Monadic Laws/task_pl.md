Istnieje wiele monad, które nie zostały omówione w tym kursie.  
Monada to abstrakcyjny koncept, a każdy kod spełniający określone kryteria może być postrzegany jako monada.  
Jakie są te kryteria, o których mówimy?  
Nazywają się prawami monadycznymi: lewa tożsamość, prawa tożsamość i łączność.

## Prawa tożsamości

Pierwsze dwa właściwości dotyczą `tożsamości`, tj. konstruktora do tworzenia monad.  
Prawa tożsamości oznaczają, że istnieje specjalna wartość, która nie zmienia wyniku, gdy operator binarny zostaje do niej zastosowany.  
Na przykład, `0 + x == x + 0 == x` dla dowolnej liczby `x`.  
Taki element może nie istnieć dla niektórych operacji lub może działać tylko po jednej stronie operatora.  
Weźmy pod uwagę odejmowanie, gdzie `x - 0 == x`, ale `0 - x != x`.  
Jak się okazuje, `tożsamość` odnosi się do metody `flatMap`.  
Spójrzmy, co dokładnie to oznacza.

Prawo lewej tożsamości mówi, że jeśli stworzymy monadę z wartości `v` przy użyciu metody `identity` (`Monad`) i następnie użyjemy metody `flatMap` z funkcją `f`, jest to równoważne z przekazaniem wartości `v` bezpośrednio do funkcji `f`:

```scala 3
def f(value: V): Monad[V]

Monad(v).flatMap(f) == f(v)
```

Prawo prawej tożsamości stwierdza, że przekazanie metody `identity` do funkcji `flatMap` jest równoważne z nieprzekazywaniem jej wcale.  
Oddaje to ideę, że `identity` tylko opakowuje wartość, którą otrzymuje, i nie wywiera żadnego efektu.

```scala 3
val monad: Monad[_] = ...

monad.flatMap(Monad(_)) == monad
```

## Łączność

Łączność to właściwość, która mówi, że można ustawiać nawiasy w dowolny sposób w wyrażeniu i uzyskać ten sam rezultat.  
Na przykład, `(1 + 2) + (3 + 4)` jest równe `1 + (2 + 3) + 4` oraz `1 + 2 + 3 + 4`, ponieważ dodawanie jest łączne.  
Jednakże odejmowanie nie jest łączne, gdyż `(1 - 2) - (3 - 4)` różni się od `1 - (2 - 3) - 4` oraz `1 - 2 - 3 - 4`.

Łączność jest pożądana dla `flatMap`, ponieważ pozwala nam na rozpakowanie ich i bezpieczne użycie składniowych uproszczeń, takich jak for-comprehensions.  
Rozważmy dwie operacje monadyczne, `mA` i `mB`, a następnie funkcję `doSomething`, która działa na ich wynikach.  
Poniższy fragment kodu jest równoważny sytuacji, w której nawiasujemy fragment potoku zawierającego `mB` i `doSomething`.

```scala 3
mA.flatMap( a =>
  mB.flatMap( b =>
    doSomething(a, b)
  )
)
```

Można to przekształcić w poniższą postać, wykorzystując `tożsamość` odpowiedniej monady.  
Tutaj nawiasujemy połączenie pierwszych dwóch działań monadycznych, a dopiero później używamy `flatMap`, aby zastosować `doSomething` na rezultacie.

```scala 3
mA.flatMap { a => 
  mB.flatMap(b => Monad((a, b)))  
}.flatMap { case (a, b) =>  
  doSomething(a, b)
}
```

Możemy uprościć ten kod, korzystając z uproszczonej składni for-comprehensions.

```scala 3
for {
  a <- mA 
  b <- mB 
  res <- doSomething(a, b)
} yield res 
```

## Czy Option i Either przestrzegają praw? 

Teraz, gdy znamy reguły, możemy sprawdzić, czy monady, które są nam znane, przestrzegają ich.  
`Tożsamość` dla `Option` to `{ x => Some(x) }`, podczas gdy `flatMap` można zaimplementować w następujący sposób:

```scala 3
def flatMap[B](f: A => Option[B]): Option[B] = this match {
  case Some(x) => f(x)
  case _       => None
}
```

Prawo lewej tożsamości jest proste: `Some(x).flatMap(f)` po prostu wykonuje `f(x)`.

Aby udowodnić prawą tożsamość, rozważmy dwa przypadki dla `monad` w `monad.flatMap(Monad(_))`.  
Pierwszy przypadek to `None`, gdzie `monad.flatMap(Option(_)) == None.flatMap(Option(_)) == None`.  
Drugi przypadek to `Some(x)` dla jakiegoś `x`. Wtedy, `monad.flatMap(Option(_)) == Some(x).flatMap(Option(_)) == Some(x)`.  
W obu przypadkach otrzymaliśmy wartość równą tej, od której zaczynaliśmy.

Staranny przegląd przypadków to metoda dowodzenia łączności.  

1. Jeśli `mA == None`, oba wyrażenia natychmiast zwracają `None`.  
2. Jeśli `mA == Some(x)` i `mB == None`, oba wyrażenia ostatecznie zwracają `None`.  
3. Jeśli `mA == Some(x)` i `mB == Some(y)`, pierwsze wyrażenie daje w rezultacie `doSomething(x, y)`. Udowodnimy, że drugie wyrażenie również zwróci tę samą wartość.

```scala 3
Some(x).flatMap { a => 
  Some(y).flatMap(b => Some((a, b)))  
}.flatMap { case (a, b) => doSomething(a, b) }
```

To wyrażenie ocenia się do:

```scala
Some(b).flatMap(b => Some((x, b)))  
  .flatMap { case (a, b) => doSomething(a, b) }
```

Co ocenia się dalej do:

```scala 3
Some(x, y).flatMap { case (a, b) => doSomething(a, b) }
```

Ostatecznie otrzymujemy `doSomething(x, y)`, co dokładnie spełnia nasze oczekiwania.

Jeśli chcesz upewnić się, że rozumiesz zasady praw monadycznych, spróbuj udowodnić, że `Either` również jest monadą.  

## Poza porażkami

Omówiliśmy tylko monady zdolne do opisu porażek i niedeterministyczności.  
Istnieje wiele innych *efektów obliczeniowych*, które mogą być wyrażone za pomocą monad.  
Obejmują one logowanie, odczytywanie z globalnej pamięci, manipulację stanem, różne rodzaje niedeterministyczności i wiele więcej.  
Zachęcamy do samodzielnego eksplorowania tych monad.  
Gdy poczujesz się komfortowo z podstawami, zapoznaj się z bibliotekami [scalaz](https://scalaz.github.io/7/) oraz [cats](https://typelevel.org/cats/).