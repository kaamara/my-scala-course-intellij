Możesz również zaimplementować własną metodę `unapply` zarówno dla zwykłej klasy, która nie posiada automatycznie wygenerowanej metody `unapply`, jak i w celu dostarczenia dodatkowego sposobu dekompozycji klasy case.  
Oto przykład niestandardowej metody `unapply` dla klasy case `Cat`, którą zdefiniowaliśmy w poprzednim rozdziale:

```scala 3
object Cat:
  def unapply(cat: Cat): (String, Int, String) =
    val ageDescription = if (cat.age < 2) "kociak" else "dorosły"
    (cat.name, cat.age, ageDescription)
```

Tutaj zdefiniowaliśmy `unapply`, który zwraca nie tylko imię i wiek `Cat`, ale także opis wieku kota (`"kociak"` lub `"dorosły"`).  
Teraz możemy używać tej niestandardowej metody `unapply` w dopasowywaniu wzorców:

```scala 3
val mittens = Cat("Mittens", 1)

mittens match
  case Cat(name, age, description) =>
    println(s"$name to $description.")
    // To wypisze "Mittens to kociak."
``` 

Zwróć uwagę, że nasza metoda `unapply` działa we wszystkich sytuacjach — niezależnie od wieku kota, dekompozycja daje prawidłowy wynik.  
Nazywa się to *Universal Apply Method*, nową funkcją w Scala 3.  
Wcześniej w Scala 2 każda metoda `unapply` musiała zwracać `Option` zbioru pól uzyskanych podczas dekompozycji.  
Ten obiekt `Option` zwracałby `Some(...)`, jeśli dekompozycja się powiodła, lub `None`, jeśli nie.  
Kiedy mogłaby się nie powieść?  
Wyobraźmy sobie, że tworzymy system zarządzania prawami jazdy.  
W Niemczech, aby prowadzić standardowy samochód, trzeba mieć co najmniej 18 lat.  
Natomiast prawo jazdy na mały motocykl można uzyskać w wieku 16 lat, a na motorower w wieku 15 lat.  
Dlatego stworzymy enum `VehicleType` oraz klasę `Applicant`, która zawiera nazwę osoby  
ubiegającej się o prawo jazdy, jej wiek i typ pojazdu, którym chce jeździć:

```scala 3
enum VehicleType:
  case Car
  case SmallMotorcycle
  case Moped

class Applicant(name: String, age: Int, vehicleType: VehicleType)
```

Teraz, w pewnym miejscu naszego kodu, mamy sekwencję wszystkich kandydatów i chcemy uzyskać imiona tych,  
którzy są uprawnieni do otrzymania prawa jazdy na podstawie wieku i typu pojazdu, na który aplikują.  
Analogicznie do tego, co zrobiliśmy w poprzednim rozdziale, szukając kotów starszych niż jeden rok, możemy zdefiniować Universal Apply Method  
i używać strażników (guards) w dopasowywaniu wzorców. Tym razem zamiast `foreach` użyjemy `collect`:

```scala 3
object Applicant:
  def unapply(applicant: Applicant): (String, Int, VehicleType) =
    (applicant.name, applicant.age, applicant.vehicleType)

  val applicants: Seq[Applicant] = ???
  val names = applicants.collect {
    case Applicant(name, age, VehicleType.Car) if age >= 18 => name
    case Applicant(name, age, VehicleType.SmallMotorcycle) if age >= 16 => name
    case Applicant(name, age, VehicleType.Moped) if age >= 15 => name
}
```

Jednakże skoro i tak definiujemy własną metodę `unapply` w tym przykładzie, możemy od razu w niej uwzględnić logikę, która sprawdza,  
czy kandydat spełnia wymagania do otrzymania prawa jazdy. Ta metoda zwróci `Option` zawierającą jego imię lub `None`:

```scala 3
object Applicant:
  def unapply(applicant: Applicant): Option[String] = applicant.vehicleType match
    case VehicleType.Car if age >= 18 => Some(applicant.name)
    case VehicleType.SmallMotorcycle if age >= 16 => Some(applicant.name)
    case VehicleType.Moped if age >= 15 => Some(applicant.name)
    case _ => None

  val applicants: Seq[Applicant] = ???
  val names = applicants.collect {
    case Applicant(name) => name
}
``` 

Jak widzisz, przenieśliśmy logikę z metody `collect` do metody `unapply`.  
Choć ten przykład niekoniecznie skraca kod lub poprawia jego czytelność,  
możliwość przeniesienia logiki sprawdzającej, czy dany obiekt spełnia wymagania określonej operacji,  
do osobnego miejsca w kodzie, może okazać się cenna w zależności od sytuacji.

### Ćwiczenie  

Ponieważ każdy komponent w zakresie RGB może przyjmować wartości między `0` a `255`, wykorzystuje on tylko 8 bitów.  
Cztery komponenty reprezentacji RGB doskonale mieszczą się w 32-bitowej liczbie całkowitej, co pozwala na lepsze wykorzystanie pamięci.  
Wiele operacji na kolorach można przeprowadzić bezpośrednio za pomocą operacji bitowych na tej reprezentacji liczbowej.  
Jednak czasami bardziej wygodne jest dostęp do każdego komponentu jako liczby,  
i tutaj przydaje się niestandardowa metoda `unapply`.  

Zaimplementuj metodę `unapply` dla reprezentacji RGB opartej na liczbach całkowitych.  
Kanał alfa znajduje się w najbardziej znaczących bitach, następnie czerwony, zielony, a na końcu niebieski.