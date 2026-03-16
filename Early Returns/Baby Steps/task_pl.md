Najpierw rozważmy konkretny przykład programu, który wymaga wczesnych zwrotów (ang. early returns).  
Załóżmy, że mamy bazę danych wpisów użytkowników.  
Dostęp do tej bazy jest zasobożerny, a dane użytkownika są obszerne.  
W rezultacie operujemy jedynie na identyfikatorach użytkowników i pobieramy dane użytkownika z bazy danych tylko wtedy, gdy jest to konieczne.

Wyobraźmy sobie teraz, że wiele z tych wpisów użytkowników jest w jakiś sposób nieprawidłowych.  
Dla uproszczenia kodu przykładu, skupimy się wyłącznie na niepoprawnych adresach e-mail: tych, które zawierają znak spacji lub mają liczbę symboli `@` różną od `1`.  
W kolejnych zadaniach omówimy również przypadek, gdy użytkownik o danym identyfikatorze nie istnieje w bazie danych.

Zaczniemy od sekwencji identyfikatorów użytkowników.  
Dla podanego identyfikatora, najpierw pobieramy dane użytkownika z bazy danych.  
Ta operacja odpowiada *konwersji* omówionej w poprzedniej lekcji: konwertujemy liczbę całkowitą na instancję klasy `UserData`.  
W kolejnym kroku przeprowadzamy *walidację*, aby sprawdzić, czy e-mail jest prawidłowy.  
Po znalezieniu pierwszej prawidłowej instancji `UserData` powinniśmy ją natychmiast zwrócić, przerywając dalsze przetwarzanie pozostałej sekwencji.

```scala 3
object EarlyReturns:
  type UserId = Int
  type Email = String

  case class UserData(id: UserId, name: String, email: Email)

  private val database = Seq(
    UserData(1, "John Doe", "john@@gmail.com"),
    UserData(2, "Jane Smith", "jane smith@yahoo.com"),
    UserData(3, "Michael Brown", "michaeloutlook.com"),
    UserData(4, "Emily Johnson", "emily at icloud.com"),
    UserData(5, "Daniel Wilson", "daniel@hotmail.com"),
    UserData(6, "Sophia Martinez", "sophia@aol.com"),
    UserData(7, "Christopher Taylor", "christopher@mail.com"),
    UserData(8, "Olivia Anderson", "olivia@live.com"),
    UserData(9, "James Thomas", "james@protonmail.com"),
    UserData(10, "Isabella Jackson", "isabella@gmail.com"),
    UserData(11, "Alexander White", "alexander@yahoo.com")
  )

  private val identifiers = 1 to 11

  /**
   * To nasza metoda "złożonej konwersji". 
   * Zakładamy, że pobieranie danych użytkownika jest kosztowne, 
   * więc chcemy unikać wywoływania tej metody, o ile to nie jest absolutnie konieczne.
   *
   * Ta wersja metody zakłada, że dane użytkownika zawsze istnieją dla danego identyfikatora użytkownika.
   */
  def complexConversion(userId: UserId): UserData = 
    database.find(_.id == userId).get

  /**
   * Podobnie jak `complexConversion`, walidacja danych użytkownika jest kosztowna 
   * i nie powinna być wykonywana zbyt często.
   */
  def complexValidation(user: UserData): Boolean = 
    !user.email.contains(' ') && user.email.count(_ == '@') == 1
```

Typowe podejście imperatywne wykorzystuje wczesny zwrot w pętli `for`.  
Przeprowadzamy konwersję, następnie walidację, a jeśli dane są prawidłowe, zwracamy je opakowane w `Some`.  
Jeśli nie znajdziemy żadnych prawidłowych danych użytkownika, zwracamy `None` po przejściu przez całą sekwencję identyfikatorów.

```scala 3
 /**
  * Podejście imperatywne, które używa nieidiomatycznego `return`.
  */
  def findFirstValidUser1(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      val userData = complexConversion(userId)
      if (complexValidation(userData)) return Some(userData)
    None
```

To rozwiązanie jest niezadowalające, ponieważ używa `return`, co nie jest idiomatyczne w Scali.

Bardziej funkcjonalne podejście polega na użyciu funkcji wyższego rzędu na kolekcjach.  
Można użyć metody `find`, aby znaleźć `userId`, dla którego `userData` jest prawidłowe.  
Jednak to wymaga dwukrotnego wywołania `complexConversion`, ponieważ `find` zwraca oryginalny identyfikator, a nie `userData`.

```scala 3
 /**
  * Naiwne podejście funkcjonalne: wykonuje `complexConversion` dwukrotnie dla wybranego ID.
  */
  def findFirstValidUser2(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .find(userId => complexValidation(complexConversion(userId)))
      .map(complexConversion)
```

Oczywiście, możemy uruchomić `collectFirst` zamiast `find` i `map`.  
To rozwiązanie jest bardziej zwięzłe niż poprzednie, ale nadal nie pozwala uniknąć dwukrotnego wykonania konwersji.  
W następnej lekcji użyjemy niestandardowej metody `unapply`, aby wyeliminować potrzebę tych powtórnych obliczeń.

```scala 3
  /** 
   * Bardziej zwięzła implementacja, wykorzystująca `collectFirst`.
   */
  def findFirstValidUser3(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case userId if complexValidation(complexConversion(userId)) => complexConversion(userId)
    }
    
```

## Ćwiczenie

Przyjrzyjmy się ponownie jednemu z naszych przykładów z wcześniejszego modułu.  
Zarządzasz schroniskiem dla kotów i prowadzisz bazę danych, w której śledzisz koty, ich rasy oraz typy futra.  

Zauważasz liczne błędy w bazie danych, popełnione przez poprzedniego pracownika: znajdują się tam krótkowłose Maine Coony, długowłose Sfinksy i inne nieścisłości.  
Nie masz czasu naprawiać tej bazy w tej chwili, ponieważ widzisz potencjalnego adoptującego wchodzącego do schroniska.  
Twoim zadaniem jest znaleźć pierwsze prawidłowe dane w bazie i zaprezentować potencjalnemu adoptującemu kota.

Zaimplementuj metodę `catConversion`, która pobiera dane o kocie z bazy `catDatabase` w pliku `Database.scala` na podstawie jego identyfikatora.  
Aby to zrobić, musisz najpierw skonsultować się z inną "tabelą" bazy danych, `adoptionStatusDatabase`, aby znaleźć imię kota.  

Następnie zaimplementuj metodę `furCharacteristicValidation`, która sprawdza, czy cechy futra w danych bazy mają sens dla konkretnej rasy kota.  
Skonsultuj mapę `breedCharacteristics`, aby uzyskać odpowiednie cechy futra dla każdej rasy.  

Wreszcie zaimplementuj wyszukiwanie, używając metod konwersji i walidacji:  
* `imperativeFindFirstValidCat`, która działa w sposób imperatywny.  
* `functionalFindFirstValidCat`, wykorzystując styl funkcjonalny.  
* `collectFirstFindFirstValidCat`, używając metody `collectFirst`.  

Upewnij się, że twoje wyszukiwanie nie przechodzi przez całą bazę danych.  
Dodaliśmy proste logowanie wewnątrz metod konwersji i walidacji, abyś mógł to zweryfikować.