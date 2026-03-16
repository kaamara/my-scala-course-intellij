Zamknięte (sealed) cechy (traits) w Scali są używane do reprezentowania ograniczonych hierarchii klas, umożliwiając wyczerpujące sprawdzanie typów.  
Kiedy cecha zostaje zadeklarowana jako sealed, może być rozszerzana tylko w obrębie tego samego pliku.  
To ograniczenie pozwala kompilatorowi zidentyfikować wszystkie podtypy, zapewniając dokładniejsze sprawdzanie w czasie kompilacji.

Wraz z wprowadzeniem enumeracji (enums) w Scalę 3, wiele przypadków użycia zamkniętych cech jest obecnie obsługiwanych przez enumeracje, a ich składnia jest bardziej zwięzła.  
Jednakże zamknięte cechy są bardziej elastyczne niż enumeracje — pozwalają na dodanie nowych zachowań w każdym podtypie.  
Na przykład możemy nadpisać domyślną implementację danej metody w odmienny sposób dla każdej klasy, która rozszerza nadrzędną cechę.  
W przeciwieństwie do tego w enumeracjach wszystkie przypadki dzielą te same metody i pola.

```scala 3 
sealed trait Tree[+A]:
  def whoAmI: String

case class Branch[A](left: Tree[A], value: A, right: Tree[A]) extends Tree[A]:
  override def whoAmI: String = "Jestem gałęzią!"

case class Leaf[A](value: A) extends Tree[A]:
  override def whoAmI: String = "Jestem liściem!"

case object Stump extends Tree[Nothing]:
  override def whoAmI: String = "Jestem pieńkiem!"
```

Kod tworzenia drzewa wygląda dokładnie tak samo:

```scala 3
import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )
```

## Ćwiczenie

Nasze drzewa są niemutowalne, więc możemy obliczać ich wysokość oraz sprawdzać, czy są zrównoważone w momencie ich tworzenia.  
Aby to zrobić, dodaliśmy członki `height` i `isBalanced` do deklaracji cechy `Tree`.  
Jedyną rzeczą, która pozostała do zrobienia, to nadpisanie tych członków we wszystkich klasach, które rozszerzają tę cechę w tym ćwiczeniu.  
Dzięki temu nie będą potrzebne dodatkowe przebiegi w celu określenia, czy drzewo jest zrównoważone.

Potraktuj to jako ćwiczenie.