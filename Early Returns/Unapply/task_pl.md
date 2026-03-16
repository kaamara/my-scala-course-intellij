Metody `unapply` stanowią podstawę mechanizmu dopasowywania wzorców.  
Ich celem jest wydobywanie danych zapisanych w obiektach.  
Możemy utworzyć własny obiekt ekstraktora do walidacji danych użytkownika z odpowiednią metodą `unapply`, na przykład:

```scala 3
  object ValidUser:
    def unapply(userId: UserId): Option[UserData] =
      val userData = complexConversion(userId)
      if complexValidation(userData) then Some(userData) else None
```

Podczas dopasowywania wzorców z użyciem `ValidUser` wywoływana jest metoda `unapply`.  
Wykonuje ona konwersję i walidację, zwracając jedynie poprawne dane użytkownika.  
W rezultacie otrzymujemy taką krótką definicję funkcji wyszukiwania:

```scala 3
  /**
   * Niestandardowa metoda `unapply` wykonuje konwersję i walidację, zwracając jedynie poprawne dane użytkownika.
   */
  def findFirstValidUser4(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser(user) => user
    }
```

W tym momencie spostrzegawczy czytelnik mógłby zaprotestować.  
To rozwiązanie jest dwukrotnie dłuższe niż imperatywne, od którego zaczęliśmy, i nie wydaje się, aby coś dodatkowego wykonywało!  
Jednak warto zauważyć, że implementacja imperatywna skupia się wyłącznie na „szczęśliwej” ścieżce.  
Ale co, jeśli żadne rekordy nie istnieją w bazie danych dla niektórych identyfikatorów użytkowników?  
Funkcja konwersji staje się częściowa i, zgodnie z podejściem funkcyjnym, musimy zwrócić opcjonalną wartość:

```scala 3
  /** 
   * Funkcja zakłada, że niektóre identyfikatory mogą nie występować w bazie danych
   */
  def safeComplexConversion(userId: UserId): Option[UserData] = database.find(_.id == userId)
```

Cząstkowość konwersji nieuchronnie skomplikuje imperatywną funkcję wyszukiwania.  
Kod zachowuje ten sam kształt, ale musi przejść przez dodatkowe iteracje, aby uwzględnić cząstkowość.  
Zauważ, że za każdym razem, gdy pojawia się nowe utrudnienie w logice biznesowej, musi ono być odzwierciedlone w pętli `for`.

```scala 3
  /**
   * Cząstkowość `safeComplexConversion` przenika do funkcji wyszukiwania.
   */
  def findFirstValidUser5(userIds: Seq[UserId]): Option[UserData] =
    for userId <- userIds do
      safeComplexConversion(userId) match
        case Some(user) if complexValidation(user) => return Some(user)
        case _ =>
    None
```

W przeciwieństwie do podejścia imperatywnego, implementacja funkcyjna oddziela logikę konwersji i walidacji  
od przetwarzania sekwencji, co prowadzi do czytelniejszego kodu.  
Rozwiązanie problemu potencjalnego braku rekordów w bazie danych sprowadza się do modyfikacji metody `unapply`,  
podczas gdy funkcja wyszukiwania pozostaje niezmieniona.

```scala 3
  /**
   * Ta niestandardowa metoda `unapply` wykonuje bezpieczną konwersję, a następnie walidację.
   */
  object ValidUser6:
    def unapply(userId: UserId): Option[UserData] =
      safeComplexConversion(userId).find(complexValidation)
  
  def findFirstValidUser6(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
    }
```

Ogólnie rzecz biorąc, dane użytkownika mogą być poprawne na różne sposoby.  
Wyobraźmy sobie użytkownika, który nie ma adresu e-mail.  
W takim przypadku `complexValidation` zwraca `false`, ale użytkownik nadal może być ważny.  
Na przykład może to być konto dziecka innego użytkownika.  
Nie musimy kontaktować się z dzieckiem, wystarczy skontaktować się z rodzicem.  
Chociaż ten przypadek jest rzadszy od podstawowego, musimy go uwzględnić.  
Aby to zrobić, możemy utworzyć inny obiekt ekstraktora z własną metodą `unapply`  
i użyć go w dopasowaniu wzorca, jeśli pierwsza walidacja zawiedzie.  
W takim przypadku wykonamy konwersję dwukrotnie, ale jej wpływ jest mniej znaczący z uwagi na rzadkość tej sytuacji.

```scala 3
  object ValidUserInADifferentWay:
    def otherValidation(userData: UserData): Boolean = false /* sprawdź, czy to użytkownik-dziecko */
    def unapply(userId: UserId): Option[UserData] = safeComplexConversion(userId).find(otherValidation)
  
  def findFirstValidUser7(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser6(user) => user
      case ValidUserInADifferentWay(user) => user
    }
```

Oba obiekty ekstraktorów działają w taki sam sposób.  
Wykonują metodę konwersji, która może zakończyć się sukcesem lub nie.  
Jeśli konwersja się powiedzie, jej wynik jest weryfikowany i zwrócony, jeśli jest poprawny.  
Całość odbywa się za pomocą metody `unapply`, której implementacja pozostaje stała bez względu na pozostałe metody.  
Tworzy to elegancką strukturę, którą można abstrahować jako cechę nazwaną `Deconstruct`.  
Zawiera ona metodę `unapply`, która wywołuje dwie metody abstrakcyjne: `convert` i `validate`,  
operujące na ogólnych typach `From` i `To`.

```scala 3
  /**
   * @tparam From Typ, na którym pierwotnie operujemy
   * @tparam To Typ danych, które chcemy uzyskać, jeśli są poprawne
   */
  trait Deconstruct[From, To]:
    def convert(from: From): Option[To]
    def validate(to: To): Boolean
    def unapply(from: From): Option[To] = convert(from).find(validate)
```

W naszym przypadku konkretna implementacja cechy `Deconstruct` działa na typach `From` = `UserId` i  
`To` = `UserData`.  
Korzysta z metod `safeComplexConversion` oraz `complexValidation`.

```scala 3
  object ValidUser8 extends Deconstruct[UserId, UserData]:
    override def convert(userId: UserId): Option[UserData] = safeComplexConversion(userId)
    override def validate(user: UserData): Boolean = complexValidation(user)
```

Na koniec funkcja wyszukiwania pozostaje niezmieniona, ale teraz korzysta z metody `unapply`  
zdefiniowanej w cechy `Deconstruct` podczas dopasowywania wzorców:

```scala 3
  def findFirstValidUser8(userIds: Seq[UserId]): Option[UserData] =
    userIds.collectFirst {
      case ValidUser8(user) => user
    }
```

## Ćwiczenie

Zauważyłeś, że pierwszy kot znaleziony z poprawnym wzorem sierści został już adoptowany.  
Teraz musisz uwzględnić w walidacji sprawdzenie, czy kot nadal przebywa w schronisku.

* Zaimplementuj `nonAdoptedCatConversion` tak, aby wybierała tylko koty, które wciąż mogą zostać adoptowane.  
* Skopiuj implementację funkcji `furCharacteristicValidation` z poprzedniego zadania.  
* Zaimplementuj własną metodę `unapply` dla obiektu `ValidCat` i użyj jej do napisania funkcji `unapplyFindFirstValidCat`. Walidacja cech sierści nie powinna być wykonywana dla kotów, które zostały adoptowane.

Następnie zauważasz, że wystąpiły pewne nieścisłości w wzorach sierści: żaden kot rasy bengalskiej nie może mieć jednolitego koloru!  

* Zaimplementuj walidację wzoru sierści, używając niestandardowej metody `unapply`.  
* Użyj obiektu `ValidPattern`, który rozszerza cechę `Deconstruct`.  
* Użyj niestandardowej metody `unapply` w funkcji `findFirstCatWithValidPattern`.