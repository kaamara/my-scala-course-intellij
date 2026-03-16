Czasami możesz potrzebować dodatkowych informacji, aby zrozumieć, dlaczego dana funkcja się nie powiodła.  
Dlatego mamy różne typy wyjątków: oprócz wysyłania sygnału paniki, wyjaśniamy również, co się stało.  
`Option` nie nadaje się do przekazywania tych informacji, dlatego zamiast tego używa się `Either[A, B]`.  
Instancja `Either[A, B]` może zawierać tylko wartość typu `A` lub typu `B`, ale nigdy jednocześnie.  
Jest to osiągane dzięki temu, że `Either` posiada dwie klasy podrzędne: `Left[A]` oraz `Right[B]`.  
Kiedy istnieje funkcja częściowa `def partialFoo(...): B`, która rzuca wyjątki i zwraca typ `B`, możemy ją zastąpić funkcją totalną `def totalFoo(...): Either[A, B]`, gdzie `A` opisuje możliwe błędy.  

Podobnie jak `Option`, `Either` jest monadą, która pozwala łączyć kolejne obliczenia.  
Konwencja jest taka, że niepowodzenie jest reprezentowane przez `Left`, podczas gdy `Right` zawiera wartość obliczoną w przypadku sukcesu.  
Którą klasę podrzędną użyć w danej sytuacji jest arbitralną decyzją, i wszystko działałoby tak samo, gdybyśmy dokonali innego wyboru i odzwierciedlili go w implementacji `flatMap`.  
Przydatnym mnemotechnicznym sposobem zapamiętania jest to, że `Right` oznacza przypadki, kiedy wszystko poszło *prawidłowo*.  
Dlatego `identity` opakowuje wartość w konstruktorze `Right`, a `flatMap` uruchamia drugą funkcję tylko wtedy, gdy pierwsza funkcja zwraca `Right`.  
Jeśli wystąpi błąd i w dowolnym momencie pojawi się `Left`, wykonanie zatrzymuje się, a błąd zostaje zgłoszony.  
Poświęć chwilę, aby samodzielnie napisać implementacje tych dwóch metod.  

Rozważ przypadek, w którym odczytujesz dwie liczby z wejścia i dzielisz jedną przez drugą.  
Funkcja ta może zawieść na dwa sposoby: jeśli użytkownik poda nie-numeryczne dane wejściowe lub jeśli wystąpi błąd dzielenia przez zero.  
Możemy zaimplementować to jako sekwencję dwóch funkcji:  

```scala 3
def readNumbers(x: String, y: String): Either[String, (Double, Double)] = 
  (x.toDoubleOption, y.toDoubleOption) match
    case (Some(x), Some(y)) => Right (x, y)
    case (None, Some(y)) => Left("Pierwszy ciąg znaków nie jest liczbą")
    case (Some(x), None) => Left("Drugi ciąg znaków nie jest liczbą")
    case (None, None) => Left("Oba ciągi znaków nie są liczbami")

def safeDiv(x: Double, y: Double): Either[String, Double] =
  if (y == 0) Left("Dzielenie przez zero")
  else Right(x / y)

@main
def main() =
  val x = readLine()
  val y = readLine()
  print(readNumbers(x, y).flatMap(safeDiv))
```

Zauważ, że tutaj użyliśmy `String` do reprezentowania błędów, ale mogliśmy użyć własnego typu danych.  
Mogliśmy nawet stworzyć całą hierarchię błędów, jeśli byśmy tego chcieli.  
Na przykład moglibyśmy zrobić z `Error` interfejs (trait), a następnie zaimplementować klasy dla błędów IO, błędów sieciowych, błędów nieprawidłowego stanu itd.  
Inną opcją jest użycie standardowej hierarchii wyjątków w Javie, jak w poniższej implementacji `safeDiv`.  
Zwracamy uwagę, że tutaj żaden wyjątek nie jest faktycznie rzucony; zamiast tego możesz uzyskać rodzaj błędu, stosując dopasowanie wzorca do wyniku.  

```scala 3
def safeDiv(x: Double, y: Double): Either[Throwable, Double] =
    if (y == 0) Left(new IllegalArgumentException("Dzielenie przez zero"))
    else Right(x / y)
```

## Ćwiczenie

Wróćmy teraz do naszego `UserService` z poprzedniej lekcji.  
Istnieją trzy możliwe powody, dla których `getGrandchild` może się nie powieść:  

* Użytkownik o podanej nazwie nie może zostać znaleziony.  
* Użytkownik nie ma dziecka.  
* Dziecko użytkownika nie ma dziecka.  

Aby wyjaśnić niepowodzenie wywołującemu, stworzyliśmy enum `SearchError` i zmieniliśmy typy funkcji `findUser`, `getGrandchild`, `getGrandchildAge` na `Either[SearchError, _]`.  

Twoim zadaniem jest zaimplementowanie tych funkcji, dostarczając odpowiedni komunikat o błędzie.  
Istnieje funkcja pomocnicza, `getChild`, którą należy zaimplementować, aby `getGrandchild` mogło naturalnie używać `flatMap`.