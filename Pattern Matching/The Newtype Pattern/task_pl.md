*Wzorzec newtype* w Scali jest sposobem na tworzenie nowych typów z istniejących, które są odrębne podczas kompilacji, 
ale mają tę samą reprezentację w czasie wykonywania. 
To podejście może być przydatne do dodania większego znaczenia prostym typom, wymuszania bezpieczeństwa typów oraz unikania błędów.

Na przykład, rozważmy scenariusz, w którym pracujesz z identyfikatorami użytkowników oraz produktów w swoim kodzie. 
Oba identyfikatory są typu `Int`, ale reprezentują całkowicie różne pojęcia. 
Używanie `Int` dla obu może prowadzić do błędów, takich jak przypadkowe przekazanie identyfikatora użytkownika tam, gdzie oczekiwano identyfikatora produktu, lub odwrotnie. 
Kompilator nie wyłapałby tych błędów, ponieważ oba identyfikatory są tego samego typu, `Int`.

Dzięki wzorcowi newtype możesz stworzyć odrębne typy dla `UserId` i `ProductId`, które opakowują `Int`, zapewniając większe bezpieczeństwo:

```scala 3
case class UserId(value: Int) extends AnyVal
case class ProductId(value: Int) extends AnyVal
```

Są to tak zwane klasy wartości (value classes) w Scali. `AnyVal` to specjalna cecha w Scali — gdy zostanie rozszerzona przez klasę typu case 
zawierającą tylko jedno pole, informujesz kompilator, że chcesz użyć wzorca newtype. 
Kompilator wykorzystuje tę informację do wychwycenia ewentualnych błędów, takich jak mylenie liczb całkowitych używanych 
dla identyfikatorów użytkowników z tymi używanymi do identyfikatorów produktów. Jednak w późniejszej fazie usuwa informacje o typie z danych, 
pozostawiając jedynie czyste `Int`, dzięki czemu Twój kod nie powoduje narzutu czasowego podczas wykonywania. 
Teraz, jeśli masz funkcję, która akceptuje `UserId`, nie możesz już przypadkowo przekazać do niej `ProductId`:

```scala 3
case class UserId(value: Int) extends AnyVal
case class ProductId(value: Int) extends AnyVal
case class User(id: UserId, name: String)

def getUser(id: UserId): User = ???
val userId = UserId(123)
val productId = ProductId(456)

// getUser(productId) to błąd kompilacji
val user = getUser(userId) // To jest poprawne
```

W Scali 3 wprowadzono nową składnię do tworzenia newtypes za pomocą *kryjących aliasów typów* (*opaque type aliases*), chociaż koncept pozostaje ten sam. 
Powyższy przykład w Scali 3 wyglądałby następująco:

```scala 3
object Ids:
  opaque type UserId = Int
  object UserId:
    def apply(id: Int): UserId = id

  opaque type ProductId = Int
  object ProductId:
    def apply(id: Int): ProductId = id

import Ids.*
case class User(id: UserId, name: String)

def getUser(id: UserId): User = ???
val userId = UserId(123)
val productId = ProductId(456)

// getUser(productId) to błąd kompilacji
val user = getUser(userId) // To jest poprawne
```

Jak widać, wymagana jest dodatkowa składnia. 
Ponieważ kryjący typ (*opaque type*) jest w zasadzie aliasem typu, a nie klasą typu case, musimy ręcznie zdefiniować metody `apply` 
dla zarówno `UserId`, jak i `ProductId`. 
Dodatkowo, konieczne jest definiowanie tych metod w obiekcie lub klasie — nie mogą one być definicjami na najwyższym poziomie. 
Z drugiej strony, kryjące typy dobrze integrują się z metodami rozszerzającymi (*extension methods*), inną nową funkcją w Scali 3. 
Omówimy to bardziej szczegółowo później.

### Ćwiczenie

Jednym z zastosowań kryjących typów jest wyrażanie jednostek miary. 
Na przykład, w aplikacji fitness, użytkownicy mogą wprowadzać dystans w stopach lub metrach, 
w zależności od preferowanego systemu jednostek.
Zaimplementuj funkcje do śledzenia dystansu w różnych jednostkach oraz funkcję `show` do wyświetlania 
śledzonego dystansu w preferowanych jednostkach.