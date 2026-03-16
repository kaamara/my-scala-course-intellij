```scala
def flatMap[B](f: A => IterableOnce[B]): Iterable[B]
```

Można uznać, że `flatMap` jest uogólnioną wersją metody `map`. Funkcja `f`, używana przez `flatMap`, przyjmuje każdy element oryginalnej kolekcji i zamiast zwracać tylko jeden nowy element innego (lub tego samego) typu, generuje całą nową kolekcję nowych elementów. Te kolekcje są następnie "spłaszczane" przez metodę `flatMap`, co skutkuje zwróceniem jednej dużej kolekcji.

W zasadzie ten sam efekt można osiągnąć za pomocą `map`, a następnie `flatten`. `flatten` to inna metoda z kolekcji Scala. Jeśli oryginalna kolekcja jest kolekcją kolekcji — to znaczy, jeśli każdy jej element jest sam w sobie kolekcją — metoda `flatten` połączy je w jedną nową kolekcję.

Jednak zastosowanie `flatMap` wykracza daleko poza ten prosty przypadek użycia. Jest to prawdopodobnie najbardziej kluczowa metoda w programowaniu funkcyjnym w Scali. Będziemy mówić więcej o tym w późniejszych rozdziałach dotyczących monad i łańcuchów wykonywania. Na razie skupmy się na prostym przykładzie, aby zaprezentować, jak dokładnie działa `flatMap`.

Podobnie jak w przykładzie z `map`, użyjemy listy czterech kotów. Ale tym razem, dla każdego kota, utworzymy listę samochodów różnych marek, ale w tym samym kolorze co kot. Na końcu użyjemy `flatMap`, aby połączyć cztery listy samochodów różnych marek i kolorów w jedną listę.

```scala
// Definiujemy wyliczenie Color
enum Color:
 case Black, White, Ginger

// Definiujemy wyliczenie CarBrand
enum CarBrand:
 case Volkswagen, Mercedes, Toyota

// Importujemy wartości wyliczeń Color i CarBrand dla lepszej czytelności
import Color._
import CarBrand._

// Definiujemy klasę domknięcia (case class) Cat z dwoma polami: name i color
case class Cat(name: String, color: Color)

// Definiujemy klasę domknięcia (case class) Car z dwoma polami: brand i color
case class Car(brand: CarBrand, color: Color)

// Tworzymy cztery koty: dwóch czarnych, jednego białego i jednego rudego
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// Umieszczamy wszystkie w liście
val cats = List(felix, snowball, garfield, shadow)

// Definiujemy funkcję, która przyjmuje kota i generuje listę samochodów
def catToCars(cat: Cat): List[Car] =
 List(
   Car(Volkswagen, cat.color),
   Car(Mercedes, cat.color),
   Car(Toyota, cat.color)
 )

// Używamy metody flatMap i funkcji catToCars, aby utworzyć nową listę wszystkich samochodów we wszystkich kolorach
val cars: List[Car] = cats.flatMap(catToCars)
```

## Ćwiczenie

Użyj `flatMap`, aby zaimplementować `collectFurCharacteristics`, która zbiera wszystkie cechy futra kotów w jednym zbiorze.