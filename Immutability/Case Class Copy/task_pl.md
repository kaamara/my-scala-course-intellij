W Scali klasy przypadków (ang. case classes) automatycznie otrzymują kilka przydatnych metod podczas deklaracji, jedną z nich jest metoda `copy`.  
Metoda `copy` służy do tworzenia nowej instancji klasy przypadków, która jest kopią oryginalnej; jednak można również  
zmodyfikować niektóre (lub żadne) pola w trakcie procesu kopiowania.  
Ta funkcja jest zgodna z zasadami programowania funkcyjnego, gdzie niezmienność jest często preferowana.  
Możesz tworzyć nowe instancje, jednocześnie zachowując niezmienność już istniejących. W rezultacie pomaga to  
zapobiegać błędom, które mogą wystąpić, gdy dwa wątki operują na tej samej strukturze danych, zakładając, że każdy z nich jest jednym modyfikującym.

Inną wartościową cechą metody `copy` jest to, że zapewnia wygodny i czytelny sposób tworzenia nowych instancji tej samej klasy przypadków.  
Zamiast budować jedną od podstaw, możesz wziąć istniejącą instancję i stworzyć jej kopię dostosowaną do swoich potrzeb.

Poniżej znajdziesz przykład w Scali wykorzystujący klasę przypadków User z obowiązkowymi polami `firstName` i `lastName`, a także opcjonalnymi   
polami `email`, `twitterHandle` i `instagramHandle`.  
Najpierw utworzymy użytkownika za pomocą domyślnego konstruktora, a następnie wygenerujemy innego użytkownika za pomocą metody `copy` na podstawie pierwszego.  

Zauważ, że:

* `originalUser` początkowo jest instancją klasy `User` z `firstName = "Jane"`, `lastName = "Doe"` i `email = "jane.doe@example.com"`.  
  Pozostałe pola korzystają ze swoich wartości domyślnych (tj. `None`).
* `updatedUser` jest tworzony przy użyciu metody `copy` na `originalUser`.  
  Tworzy to nową instancję z tymi samymi wartościami pól co `originalUser`, z wyjątkiem tych podanych jako parametry do `copy`:
   * `email` zostaje zaktualizowany na `"new.jane.doe@example.com"`
   * `twitterHandle` zostaje ustawiony na `"@newJaneDoe"`
* `originalUser` pozostaje niemodyfikowany po użyciu metody `copy`, co jest zgodne z zasadą niezmienności.

``` 
case class User( firstName: String,
                 lastName: String,
                 email: Option[String] = None,
                 twitterHandle: Option[String] = None,
                 instagramHandle: Option[String] = None
               )

// użycie
val originalUser = User("Jane", "Doe", Some("jane.doe@example.com"))

// Utwórz kopię originalUser, zmieniając email i dodając twitter handle
val updatedUser = originalUser.copy(
email = Some("new.jane.doe@example.com"),
twitterHandle = Some("@newJaneDoe")
)

println(s"Original user: $originalUser")
// wypisuje User("Jane", "Doe", Some("jane.doe@example.com"), None, None)

println(s"Updated user: $updatedUser")
// wypisuje User("Jane", "Doe", Some("new.jane.doe@example.com"), Some("@newJaneDoe"), None)
```

## Ćwiczenie  

Rozłóżmy funkcję `copy` na części pierwsze.  
Zaimplementuj własną funkcję `myCopy`, która będzie działać identycznie jak `copy`.  
Powinieneś móc przekazywać wartości tylko dla tych pól, które chcesz zmodyfikować.  
W rezultacie powinna zostać utworzona nowa kopia instancji.