Destrukturyzacja w Scala odnosi się do praktyki rozkładania instancji danego typu na jej składowe. 
Można o tym myśleć jak o odwróceniu procesu konstrukcji. 
W konstruktorze lub metodzie `apply` używamy zbioru parametrów, aby stworzyć nową instancję danego typu. 
Przy destrukturyzacji wychodzimy od instancji danego typu i rozkładamy ją na wartości, które – przynajmniej teoretycznie – 
mogłyby zostać ponownie użyte do utworzenia dokładnej kopii oryginalnej instancji. 
Dodatkowo, podobnie jak metoda `apply` może działać jako "inteligentny konstruktor" wykonujący pewne złożone operacje przed utworzeniem instancji, 
możemy zaimplementować niestandardową metodę, zwaną `unapply`, która inteligentnie rozkłada oryginalną instancję. 
Jest to bardzo potężna i wyrazista funkcjonalność języka Scala, często spotykana w idiomatycznym kodzie w Scala.

Metoda `unapply` powinna być zdefiniowana w obiekcie towarzyszącym (companion object). 
Zazwyczaj przyjmuje instancję powiązanej klasy jako jedyny argument i zwraca opcję (option) tego, co zawiera instancja. 
W najprostszych przypadkach będą to po prostu pola klasy: jedno, gdy jest tylko jedno pole, 
lub para, trójka, czwórka itd., gdy pól jest więcej. 
Scala automatycznie generuje proste metody `unapply` dla klas przypadków (case class). 
W takich sytuacjach `unapply` po prostu rozbija podaną instancję na kolekcję jej pól, jak pokazano w poniższym przykładzie:

```scala 3
case class Person(name: String, age: Int)
val john = Person("John", 25)
// ...
val Person(johnsName, johnsAge) = john
println(s"$johnsName ma $johnsAge lat.")
```

Jak możesz zauważyć, podobnie jak nie musimy jawnie pisać `apply`, aby utworzyć instancję klasy przypadku `Person`, 
tak samo nie musimy jawnie pisać `unapply`, aby rozłożyć instancję klasy przypadku `Person` z powrotem na jej pola: 
`johnsName` i `johnsAge`.

Jednak rzadko widuje się taki sposób użycia destrukturyzacji w Scala. 
W końcu, jeśli już dokładnie wiesz, z jaką klasą przypadku masz do czynienia i potrzebujesz tylko odczytać jej publiczne pola, 
możesz to zrobić bezpośrednio — w tym przykładzie za pomocą `john.name` i `john.age`. 
Natomiast `unapply` staje się znacznie bardziej wartościowe, gdy jest używane w połączeniu z dopasowaniem wzorców (pattern matching).

Zacznijmy od zdefiniowania wyliczenia (enum) `Color` oraz klasy przypadku `Cat`.

```scala 3
enum Color:
  case White, Ginger, Black

import Color.*
case class Cat(name: String, color: Color, age: Int)
```

Teraz utwórzmy pięć instancji klasy `Cat`:

```scala 3
val mittens  = Cat("Mittens", Black, 2)
val fluffy   = Cat("Fluffy", White, 1)
val ginger   = Cat("Ginger", Ginger, 3)
val snowy    = Cat("Snowy", White, 1)
val midnight = Cat("Midnight", Black, 4)
```

Mamy dwa koty (Fluffy i Snowy), które mają jeden rok, oraz trzy koty (Mittens, Ginger i Midnight), które są starsze niż rok.
Następnie umieśćmy te koty w sekwencji `Seq`:

```scala 3
val cats = Seq(mittens, fluffy, ginger, snowy, midnight)
```

Na koniec, możemy użyć dopasowywania wzorców i destrukturyzacji, aby sprawdzić wiek każdego kota i wypisać odpowiednią wiadomość:

```scala 3
cats.foreach {
  case Cat(name, color, age) if age > 1 =>
    println(s"To jest $color dorosły kot o imieniu $name")
  case Cat(name, color, _) =>
    println(s"To jest $color kociak o imieniu $name")
}
```

W tym kodzie używamy dopasowywania wzorców do destrukturyzacji każdego obiektu `Cat`. 
Używamy również strażnika `if age > 1`, aby sprawdzić wiek kota. 
Jeśli wiek jest większy niż jeden, wypisujemy wiadomość dla dorosłych kotów. 
Jeśli wiek wynosi jeden lub mniej, wypisujemy wiadomość dla kociąt. 
Zwróć uwagę, że w drugiej instrukcji dopasowania używamy symbolu podkreślenia `_`, aby zignorować wartość wieku, 
ponieważ nie musimy jej sprawdzać — jeśli instancja kota jest destrukturyzowana w drugiej instrukcji, 
oznacza to, że wiek kota został już sprawdzony w pierwszej instrukcji i nie spełnił tego testu.

Ponadto, jeśli musimy obsłużyć przypadek, w którym jedno z pól ma konkretną stałą wartość 
(w przeciwieństwie do pierwszego przypadku powyżej, gdzie dowolny wiek większy niż `1` jest odpowiedni), 
możemy bezpośrednio określić tę wartość zamiast pola:

```scala 3
cats.foreach {
  case Cat(name, _, 2) =>
    println(s"$name ma dokładnie dwa lata")
  case Cat(name, color, age) if age > 1 =>
    println(s"To jest $color dorosły kot o imieniu $name")
  case Cat(name, color, _) =>
    println(s"To jest $color kociak o imieniu $name")
}
```

### Ćwiczenie 

RGB oznacza Czerwony, Zielony i Niebieski. Jest to model kolorów używany w obrazowaniu cyfrowym, 
który reprezentuje kolory poprzez kombinację intensywności tych trzech kolorów podstawowych. Dzięki temu urządzenia elektroniczne 
mogą tworzyć szerokie spektrum kolorów. 
Czasami używany jest również czwarty komponent, zwany Alpha, który opisuje przezroczystość.
Każdy komponent może być dowolną liczbą całkowitą w zakresie `0 .. 255`, gdzie `0` oznacza brak koloru, 
a `255` oznacza maksymalną intensywność koloru.
Na przykład kolor czerwony jest reprezentowany, gdy Czerwony wynosi `255`, a Zielony i Niebieski wynoszą `0`.

W tym ćwiczeniu zaimplementuj funkcję `colorDescription`, która przekształca podany kolor RGB na tekst. 
Powinna destrukturyzować kolor, sprawdzać komponenty RGB i zwracać nazwę koloru, jeżeli jest jednym z 
następujących: `"Black", "Red", "Green", "Blue", "Yellow", "Cyan", "Magenta", "White"`. 
W przeciwnym razie powinna po prostu zwrócić wynik metody `toString()`. 
Proszę zignorować kanał alpha podczas określania nazwy koloru.