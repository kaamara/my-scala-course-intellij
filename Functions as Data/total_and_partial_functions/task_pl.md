Omówiliśmy już, jak funkcję można sklasyfikować jako czystą lub nieczystą.  
Czysta funkcja nie powoduje efektów ubocznych; działa tylko na swoich argumentach i zwraca wynik.  
Natomiast funkcja nieczysta może wywoływać efekty uboczne i pobierać dane wejściowe z innych kontekstów niż jej argumenty.

Ten rozdział wprowadza kolejne ważne rozróżnienie: funkcja może być całkowita lub częściowa.  
Funkcja całkowita jest zdefiniowana dla wszystkich możliwych wartości wejściowych w swojej dziedzinie.  
Innymi słowy, zwraca wartość wyjściową dla każdej możliwej wartości wejściowej.  
Funkcja całkowita nigdy nie powoduje wyjątku ani błędu w czasie wykonania wynikającego z niezdefiniowanych wartości wejściowych.  
Z kolei funkcja częściowa jest zdefiniowana tylko dla podzbioru swojej dziedziny.  
Inaczej mówiąc, może nie zwrócić prawidłowej wartości wyjściowej dla pewnych wartości wejściowych.  
Jeśli funkcję częściową zastosujemy do niezdefiniowanej wartości wejściowej, może spowodować wyjątek, błąd w czasie wykonania lub pozostać niezewaluowana.

Przykładowo, rozważmy dwie funkcje:
```scala
def multiply(x: Int, y: Int): Int = x * y
def divide(x: Int, y: Int): Int = x / y
```
Pierwsza z nich, `multiply`, jest funkcją całkowitą: możemy podać jej dowolne dwie liczby całkowite, a ona zwróci prawidłowy wynik.  
Natomiast funkcja `divide` jest funkcją częściową: jeśli na drugi argument podamy 0, funkcja rzuci wyjątkiem `java.lang.ArithmeticException`, ponieważ właśnie próbowaliśmy podzielić przez zero.  
Dodatkowo, jeśli wynik dzielenia nie jest liczbą całkowitą, `divide` zaokrągli go w dół.

Możesz się teraz zastanawiać, dlaczego w ogóle używamy funkcji częściowych?  
Czy nie powinniśmy zawsze starać się pisać funkcje całkowite?  
Przecież w kodzie funkcji `divide` mogliśmy najpierw sprawdzić, czy `y` jest równe 0, i zwrócić jakąś wartość specjalną.  
I rzeczywiście, w wielu przypadkach byłoby to właściwe podejście.  
Jednak czasem funkcja częściowa może okazać się użyteczna.  
Rozważmy następujący przykład, który wykorzystuje metodę `collect` z kolekcji Scali:

```scala
enum Color:
 case Black, White, Ginger

import Color.*

trait Animal:
 def name: String
 def color: Color

case class Cat(name: String, color: Color) extends Animal
case class Dog(name: String, color: Color) extends Animal

// Tworzymy trzy instancje kotów
val felix = Cat("Felix", Black)
val garfield = Cat("Garfield", Ginger)
val shadow = Cat("Shadow", Black)

// oraz dwie instancje psów
val fido = Dog("Fido", Black)
val snowy = Dog("Snowy", White)

// Umieszczamy wszystkie koty i psy w sekwencji typu Seq[Animal]
val animals: Seq[Animal] = Seq(felix, garfield, shadow, fido, snowy)

// Korzystając z metody collect, tworzymy nową sekwencję zawierającą tylko czarne koty
val blackCats: Seq[Cat] = animals.collect {
 case cat: Cat if cat.color == Black => cat
}
```
W tym przykładzie najpierw tworzymy enum `Color` z trzema wartościami: `Black`, `White` i `Ginger`.  
Definiujemy trait `Animal` z dwoma metodami abstrakcyjnymi: `name` i `color`.  
Tworzymy klasy przypadków `Cat` i `Dog`, które rozszerzają trait `Animal` i nadpisują metody `name` oraz `color` odpowiednimi wartościami.  
Następnie tworzymy trzy instancje `Cat` (dwa czarne i jeden rudy) i dwie instancje `Dog` (jeden czarny i jeden biały).  
Łączymy te instancje w jedną sekwencję typu `Seq[Animal]`.

Ostatecznie używamy metody `collect` na tej sekwencji, aby stworzyć nową sekwencję zawierającą tylko czarne koty.  
Metoda `collect` stosuje funkcję częściową do pierwotnej kolekcji i tworzy nową kolekcję zawierającą tylko te elementy, dla których funkcja częściowa jest zdefiniowana.  
Można to postrzegać jako połączenie metod `filter` i `map`.  
W powyższym przykładzie podajemy metodzie `collect` następującą funkcję częściową:

```scala
case cat: Cat if cat.color == Black => cat
```
Słowo kluczowe `case` mówi nam, że funkcja zapewni prawidłowy wynik tylko w następującym przypadku:  
wartość wejściowa musi być typu `Cat` (nie dowolnym `Animal` z naszej pierwotnej sekwencji),  
a kolor tego kota musi być równy `Black`.  
Jeśli te warunki zostaną spełnione, funkcja zwróci kota, ale jako instancję typu `Cat`, a nie tylko `Animal`.  
W rezultacie możemy określić, że nowa kolekcja tworzona przez metodę `collect` jest sekwencją typu `Seq[Cat]`.

## Ćwiczenie

Zdefiniuj funkcję częściową `division`, aby uwzględniała dzielenie przez 0.  
Użyj słowa kluczowego `case` oraz odpowiedniego warunku, aby to osiągnąć.  
Następnie skorzystaj z metody `isDefinedAt`, aby sprawdzić, czy funkcja jest zdefiniowana dla danych argumentów.