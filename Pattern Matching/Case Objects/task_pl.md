Możesz zauważyć w przykładzie drzewa binarnego zaimplementowanego za pomocą hierarchii oznaczonych cech (*sealed trait*), że użyliśmy *obiektu case* (*case object*), aby wprowadzić typ `Stump`.  
W Scali obiekt case to specjalny rodzaj obiektu, który łączy cechy i zalety zarówno klasy case, jak i zwykłego obiektu.

Podobnie jak klasa case, obiekt case jest wyposażony w szereg automatycznie generowanych metod, takich jak `toString`, `hashCode` oraz `equals`, i można go bezpośrednio używać w dopasowywaniu wzorców (*pattern matching*).  
Z drugiej strony, tak jak każdy zwykły obiekt, obiekt case jest singletonem, tzn. istnieje dokładnie jedna jego instancja w całej maszynie wirtualnej JVM.  
Obiekty case są używane zamiast klas case, gdy nie ma potrzeby parametryzacji — kiedy nie trzeba przechowywać danych, a mimo to chce się korzystać z możliwości dopasowywania wzorców oferowanych przez klasy case.  
W Scali 2 zaimplementowanie wspólnej cechy za pomocą obiektów case było domyślnym sposobem realizacji funkcjonalności wyliczeń (*enum*).  
Nie jest to już konieczne w Scali 3, która wprowadziła wyliczenia (*enums*), ale obiekty case wciąż są przydatne w bardziej złożonych sytuacjach.

Na przykład można zauważyć, że aby używać obiektów case jako wyliczeń, rozszerzamy je o wspólną oznaczoną cechę (*sealed trait*).

```scala 3
sealed trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...
```

Tutaj `AuthorizationStatus` to oznaczona cecha (*sealed trait*), a `Authorized` i `Unauthorized` to dwa jedyne obiekty case, które ją rozszerzają.  
Oznacza to, że wynik wywołania metody `authorize` może być albo `Authorized`, albo `Unauthorized`.  
Nie jest możliwa żadna inna odpowiedź.

Wyobraźmy sobie jednak, że pracujesz nad kodem, który korzysta z biblioteki lub modułu, który nie chcesz już modyfikować.  
W takim przypadku pierwotny autor tej biblioteki lub modułu mógł użyć obiektów case rozszerzających nieoznaczoną cechę (*non-sealed trait*), aby ułatwić Ci dodanie własnej funkcjonalności:

```scala 3
// oryginalny kod
trait AuthorizationStatus

case object Authorized   extends AuthorizationStatus
case object Unauthorized extends AuthorizationStatus

def authorize(userId: UserId): AuthorizationStatus = ...

// nasze rozszerzenie
case object LoggedOut extends AuthorizationStatus

override def authorize(userId: UserId): AuthorizationStatus =
  if isLoggedOut(userId) then
    LoggedOut
  else
    super.authorize(userId)
```

Tutaj rozszerzamy funkcjonalność oryginalnego kodu, dodając możliwość, że użytkownik, mimo że jest uprawniony do wykonania danej operacji,  
napotyka problem i zostaje wylogowany.  
Teraz musi się ponownie zalogować, zanim będzie mógł kontynuować.  
Nie jest to to samo, co po prostu bycie `Unauthorized`, dlatego dodajemy trzeci obiekt case do zestawu tych rozszerzających `AuthorizationStatus`:  
nazywamy go `LoggedOut`.  
Gdyby pierwotny autor użył oznaczonej cechy (*sealed trait*) do zdefiniowania `AuthorizationStatus`,  
lub gdyby użył wyliczenia (*enum*), nie moglibyśmy tego zrobić.

### Ćwiczenie

Modelujemy boty poruszające się po płaszczyźnie 2D (patrz klasa case `Coordinates`).  
Istnieją różne rodzaje botów (patrz cecha `Bot`), z których każdy porusza się o określoną liczbę pól jednorazowo.  
Każdy bot porusza się w jednym z czterech kierunków (patrz cecha `Direction`).  
Zdecyduj, które cechy powinny być oznaczone (*sealed*), a które nie, i zmodyfikuj je odpowiednio.  
Zaimplementuj funkcję `move`.