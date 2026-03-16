```scala
def foreach[U](f: A => U): Unit
```

Metoda `foreach` działa na dowolnej kolekcji w Scali, która implementuje `Iterable`. 
Przyjmuje funkcję `f` i stosuje ją do każdego elementu w kolekcji. 
Zakładamy, że `f` wykonuje efekty uboczne (możemy zignorować typ wyniku `U` funkcji `f`), a to właśnie efekty uboczne są tym, czego szukamy. 
Możesz myśleć o metodzie `foreach` jako o prostym pętleniu for, które iteruje po kolekcji elementów, nie modyfikując ich.

Zauważ, że w programowaniu funkcyjnym staramy się unikać efektów ubocznych. 
W tym kursie nauczysz się, jak osiągać te same rezultaty w sposób funkcyjny, ale na początku `foreach` może być przydatne do wyświetlania wyników obliczeń, debugowania i eksperymentów.

W poniższym przykładzie użyjemy `foreach` do wyświetlenia nazw i kolorów naszych czterech kotów.

```scala
// Definiujemy enum Color
enum Color:
 case Black, White, Ginger

// Definiujemy klasę Cat jako case class z dwoma polami: name i color
case class Cat(name: String, color: Color)

// Importujemy wartości z enum Color dla lepszej czytelności
import Color._

// Tworzymy cztery koty: dwa czarne, jednego białego i jednego rudego
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// Umieszczamy je wszystkie w zbiorze
val cats = Set(felix, snowball, garfield, shadow)

// Dla każdego kota w zbiorze wyświetlamy jego kolor i nazwę
cats.foreach { cat =>
  println(s"Ten ${cat.color} kot nazywa się ${cat.name}")
}
```

## Ćwiczenie

Użyj `foreach`, aby wyświetlić krótkie informacje o każdym kocie. 
Uruchom funkcję `main`, aby zobaczyć wynik. 
Eksperymentuj do woli. 
Czy potrafisz zaimplementować funkcję, która wyświetla wszystkie cechy kota, w tym `FurCharacteristics`?