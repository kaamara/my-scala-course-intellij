Monada to potężny koncept szeroko stosowany w programowaniu funkcyjnym. 
Jest to wzorzec projektowy zdolny do opisywania obliczeń, które mogą zakończyć się niepowodzeniem, zarządzania stanem oraz obsługi dowolnych efektów ubocznych. 
W przeciwieństwie do Haskella, standardowa biblioteka Scali nie zawiera konkretnego typu `Monad`. 
Zamiast tego, monada to klasa opakowująca `M[A]`, która implementuje metodę `flatMap`, służącą do łączenia kilku operacji w sekwencje.
Uproszczona wersja tej metody ma następujący typ: 

`def flatMap[B](f: A => M[B]): M[B]`

Metoda ta wykonuje obliczenie monadyczne, które zwraca wartość typu `A`, a następnie stosuje do niej funkcję `f`, co daje nowe obliczenie monadyczne.
Proces ten umożliwia przeprowadzanie sekwencyjnych obliczeń w zwięzły sposób. 

Oprócz tego powinna istnieć metoda tworzenia najprostszego przypadku monady.
W wielu poradnikach dotyczących monad w Scali funkcja ta nazywana jest `unit`, ale może to być mylące ze względu na istniejącą klasę `Unit`, która ma tylko jedną instancję.
Lepszymi nazwami dla tej metody są `identity`, `pure` lub `return`. 
Będziemy ją nazywać `identity` z powodów, które staną się jasne, gdy omówimy prawa monadyczne, zestaw zasad, które każda monada powinna spełniać.
Jej typ to `def identity[A](x: A): M[A]`, co oznacza, że po prostu opakowuje argument w monadę, a w większości przypadków jest to metoda `apply` odpowiadającej klasy. 
W tej lekcji przyjrzymy się naszej pierwszej monadzie, która powinna być już Wam znana. 

Jak pewnie już zauważyliście, wiele funkcji w rzeczywistych przypadkach jest częściowych. 
Na przykład dzielenie przez 0 powoduje błąd, co w pełni odpowiada naszemu postrzeganiu rzeczywistości.
Aby uczynić operację dzielenia funkcją całkowitą, możemy użyć wartości `Double.Infinity` lub `Double.NaN`, ale dotyczy to wyłącznie tego wąskiego przypadku. 
Częściej w funkcji częściowej zwracany jest `null` lub, co gorsza, zgłaszany jest wyjątek.
Używanie `null` określane jest mianem błędu wartego miliard dolarów z uzasadnionych powodów i należy tego unikać. 
Zgłaszanie wyjątków przypomina poddanie się i przekazanie problemu do rozwiązania komuś innemu. 
Praktyki te były kiedyś powszechne, ale teraz, gdy opracowano lepsze sposoby obsługi obliczeń zakończonych niepowodzeniem, warto z nich korzystać. 

`Option[A]` to najprostszy sposób na wyrażenie obliczeń, które mogą zakończyć się niepowodzeniem. 
Ma dwie podklasy: `None` i `Some[A]`. 
Pierwsza z nich oznacza brak wyniku lub niepowodzenie, podczas gdy druga opakowuje wynik udany.
Bezpierwotne, całkowite dzielenie można zaimplementować w następujący sposób:  

```scala 3
def div(x: Double, y: Double): Option[Double] =
  if (y == 0) None
  else Some(x / y)
```

Teraz załóżmy, że trzeba wykonać serię dzielenia w łańcuchu. 
Na przykład chcemy obliczyć, ile wizyt dziennie przypada na użytkownika naszej strony.
Najpierw musimy podzielić całkowitą liczbę wizyt przez liczbę użytkowników, a następnie przez liczbę dni, w których zbierano dane. 
To obliczenie może się nie udać dwa razy, a ręczne odwzorowanie każdego pośredniego wyniku szybko staje się nudne. 
Zamiast tego można łączyć operacje za pomocą `flatMap`. 
Jeśli którekolwiek z dzielenia zakończy się niepowodzeniem, cały łańcuch przerywa działanie.

```scala 3
div(totalVisits, numberOfUsers).flatMap { div(_, numberOfDays) }
```

Teraz przyjrzyjmy się, jak można zaimplementować `identity` i `flatMap`. 
Nie jest to dokładna implementacja z biblioteki standardowej, lecz odzwierciedla główny pomysł. 

```scala 3
def identity[A](x: A): Option[A] = Some(x)

def flatMap[B](f: A => Option[B]): Option[B] = this match {
  case Some(x) => f(x)  
  case _       => None 
}
```

W Scali istnieje jeszcze jeden szczególny przypadek: jeśli przekażesz `null` jako argument do konstruktora `Option`, otrzymasz `None`. 
Powinno się tego unikać, ale czasami trzeba odwołać się do zewnętrznej biblioteki Java, która może zwracać `null`:

```scala 3
val result = javaLib.getSomethingOrNull(bar)
Option(result).foreach { res => 
    // zostanie wykonane tylko, jeśli `result` nie jest nullem  
 }
```

W skrócie, `None` wskazuje, że coś poszło nie tak, a `flatMap` umożliwia łączenie wywołań funkcji, które się nie kończą niepowodzeniem. 

## Ćwiczenie

Rozważmy użytkowników reprezentowanych przez klasę `User`. 
Każdy użytkownik ma imię, wiek i, czasami, dziecko.
`UserService` reprezentuje bazę danych użytkowników wraz z funkcjami wyszukiwania. 

Twoim zadaniem jest zaimplementowanie metody `getGrandchild`, która pobiera wnuka użytkownika o podanym imieniu, jeśli taki istnieje. 
Tutaj już umieściliśmy dwa wywołania `flatMap`, aby połączyć niektóre funkcje; Twoim zadaniem jest uzupełnienie, jakie to funkcje. 

Następnie zaimplementuj metodę `getGrandchildAge`, która zwraca wiek wnuka, jeśli istnieje. 
Użyj tutaj `flatMap` i unikaj dopasowywania wzorców.