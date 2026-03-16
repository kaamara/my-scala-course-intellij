Wzorzec budowniczego (builder pattern) to wzorzec projektowy często używany w programowaniu obiektowym, aby zapewnić elastyczne rozwiązanie do tworzenia złożonych obiektów.  
Jest szczególnie przydatny, gdy obiekt musi być utworzony z wieloma możliwymi opcjami konfiguracji.  
Wzorzec polega na oddzieleniu procesu tworzenia złożonego obiektu od jego reprezentacji,  
dzięki czemu ten sam proces konstrukcji może prowadzić do różnych reprezentacji.  

Oto, dlaczego wzorzec budowniczego jest używany:  
* Aby enkapsulować logikę konstrukcji złożonego obiektu.  
* Aby umożliwić konstruowanie obiektu krok po kroku, często za pomocą łańcuchowego wywołania metod.  
* Aby uniknąć konstruktorów z wieloma parametrami, co może być mylące i podatne na błędy (często określane jako antywzorzec „teleskopowego” konstruktora).  

Poniżej znajduje się przykład w Scali wykorzystujący wzorzec budowniczego do tworzenia instancji klasy case `User` z obowiązkowymi polami `firstName` i `lastName` oraz opcjonalnymi polami `email`, `twitterHandle` i `instagramHandle`.  

Należy zauważyć, że:  
* Klasa case `User` definiuje użytkownika z obowiązkowymi polami `firstName` i `lastName` oraz opcjonalnymi polami `email`, `twitterHandle` i `instagramHandle`.  
* `UserBuilder` ułatwia tworzenie obiektu `User` z obowiązkowymi parametrami określonymi w konstruktorze budowniczego. Metody takie jak `setEmail`, `setTwitterHandle` oraz `setInstagramHandle` są dostępne do ustawiania opcjonalnych parametrów.  
  Każda z tych metod zwraca samego budowniczego, umożliwiając łańcuchowe wywołanie metod.  
* Ostateczne wykonanie metody `build` używa wszystkich określonych parametrów (domyślnych lub ustawionych), aby skonstruować obiekt `User`.  

Ten wzorzec utrzymuje proces tworzenia obiektu jasnym i prostym, szczególnie w przypadku obiektów posiadających wiele opcjonalnych parametrów.  

```
case class User( firstName: String,
                 lastName: String,
                 email: Option[String] = None,
                 twitterHandle: Option[String] = None,
                 instagramHandle: Option[String] = None
               )
               
class UserBuilder(private val firstName: String, private val lastName: String):
  private var email: Option[String] = None
  private var twitterHandle: Option[String] = None
  private var instagramHandle: Option[String] = None
  
  def setEmail(e: String): UserBuilder =
    email = Some(e)
    this

  def setTwitterHandle(t: String): UserBuilder =
    twitterHandle = Some(t)
    this

  def setInstagramHandle(i: String): UserBuilder =
    instagramHandle = Some(i)
    this

  def build: User =
    User(firstName, lastName, email, twitterHandle, instagramHandle)

  // użycie
  val user: User = new UserBuilder("John", "Doe")
    .setEmail("john.doe@example.com")
    .setTwitterHandle("@johnDoe")
    .setInstagramHandle("@johnDoe_insta")
    .build
   
  println(user)
  // wypisuje User("John", "Doe", Some("john.doe@example.com"), Some("@johndoe"), Some("@johnDoe_insta"))
```

## Zadanie

Zaimplementuj wzorzec budowniczego dla konstruowania wiadomości, która posiada opcjonalne pola nadawca, odbiorca oraz treść.