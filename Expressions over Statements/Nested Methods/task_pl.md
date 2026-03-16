W języku Scala możliwe jest definiowanie metod wewnątrz innych metod. 
Jest to przydatne, gdy masz funkcję, która ma być używana jednorazowo. 
Na przykład możesz zaimplementować funkcję liczącą silnię z użyciem akumulatora, aby zwiększyć wydajność programu. 
Jednocześnie nie chciałbyś umożliwić użytkownikowi wywoływania tej funkcji z dowolnym parametrem akumulatora. 
W takiej sytuacji możesz udostępnić standardową funkcję jednoparametrową `factorial`, która wywołuje zagnieżdżoną implementację 
rekurencyjną w ogonie `fact` z odpowiednim akumulatorem: 

```scala 3
def factorial(x: Int): Int =
  def fact(x: Int, accumulator: Int): Int =
    if x <= 1 then accumulator
    else fact(x - 1, x * accumulator)
  fact(x, 1)
```

Alternatywną opcją jest umieszczenie funkcji `fact` na tym samym poziomie co `factorial` i oznaczenie jej jako `private`. 
Pozwala to innym funkcjom w tym samym module na dostęp do `fact`, natomiast jej zagnieżdżenie sprawia, że funkcja jest dostępna 
wyłącznie w ramach funkcji `factorial`. 
Możesz również zagnieżdżać metody wewnątrz innych zagnieżdżonych metod, przy czym zasady dotyczące zakresów pozostają 
spójne: zagnieżdżona funkcja jest dostępna jedynie w ramach swojej zewnętrznej funkcji: 

```scala 3
def foo() = {
  def bar() = {
    def baz() = { }
    baz()
  }
  def qux() = {
    def corge() = { }
    corge() // Zagnieżdżona funkcja może być wywołana
    bar() // Funkcja na tym samym poziomie może być wywołana
    // Funkcja zagnieżdżona wewnątrz innej funkcji nie może:
    // baz() // nie znaleziono: baz
  }
  // Można wywoływać funkcje na tym poziomie...
  bar()
  qux()
  // ... ale ich zagnieżdżonych funkcji już nie: 
  // baz() // nie znaleziono: baz
  // corge() // nie znaleziono: corge
}
```

Zauważ, że użyliśmy nawiasów klamrowych, aby wyraźniej zaznaczyć zakresy; jednak w Scali 3 nie są one konieczne. 

Zagnieżdżone funkcje mają dostęp do parametrów swoich rodziców, dzięki czemu można uniknąć przekazywania niezmiennych parametrów: 

```scala 3
def f(x: Int, y: Int): Int =
  def g(z: Int): Int =
    def h(): Int =
      x + y + z
    h()
  //  def i(): Int = z // z nie jest widoczne poza g
  g(42)
```

Kolejnym przypadkiem, w którym zagnieżdżone metody okazują się szczególnie przydatne, jest tworzenie łańcucha wywołań 
funkcji wyższego rzędu, gdzie zagnieżdżone metody przypisują znaczące nazwy ich argumentom. 
Rozważmy przykład, w którym liczymy liczbę kociąt, które są białe lub rude. 

```scala 3
enum Color:
  case Black
  case White
  case Ginger

// Mamy model, w którym każdy kot ma kolor i wiek (w latach)
class Cat(val color: Color, val age: Int)

val bagOfCats = Set(Cat(Color.Black, 0), Cat(Color.White, 1), Cat(Color.Ginger, 3))

// Liczba białych lub rudych kociąt (kotów, które mają maksymalnie 1 rok)
val numberOfWhiteOrGingerKittens =
  def isWhiteOrGinger(cat: Cat): Boolean = cat.color == Color.White || cat.color == Color.Ginger
  def isKitten(cat: Cat): Boolean = cat.age <= 1
  bagOfCats.filter(isWhiteOrGinger).count(isKitten)
```

Możemy napisać powyższą funkcję w sposób pokazany poniżej, lecz jest on zdecydowanie mniej czytelny: 

```scala 3
val numberOfWhiteOrGingerKittens =
  bagOfCats
    .filter(cat => cat.color == Color.White || cat.color == Color.Ginger)
    .count(cat => cat.age <= 1)
```

## Ćwiczenie 

Zbadaj zakresy zagnieżdżonych metod. Spraw, aby kod w pliku `NestedTask.scala` się kompilował.