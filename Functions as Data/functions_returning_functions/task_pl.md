W języku Scala możliwe jest dynamiczne tworzenie funkcji wewnątrz innych funkcji i metod oraz ich zwracanie.  
Technika ta pozwala nam tworzyć nowe funkcje na podstawie argumentów przekazanych do pierwotnej funkcji i zwracać je jako wynik tej funkcji.

Jest to szczególnie przydatne, gdy potrzebujemy tworzyć wyspecjalizowane funkcje oparte na wspólnym wzorcu lub zachowaniu.  
Na przykład, rozważmy klasę `CalculatorPlusN`, którą napisaliśmy w rozdziale „Co to jest funkcja?”.  
W tym przykładzie utworzyliśmy klasę, która przyjmuje liczbę `n` w swoim konstruktorze, a następnie używa tej liczby w metodzie `add(x: Int, y: Int)`, dodając ją do sumy `x` i `y`.

```scala
class CalculatorPlusN(n: Int) extends Calculator:
// Nadpisana metoda `add` dodaje `n` ze stanu wewnętrznego do wyniku dodawania.
override def add(x: Int, y: Int): Int = super.add(x, y) + n
// Instancja `CalculatorPlusN` ze stanem wewnętrznym `n` równym 3.
val calc = new CalculatorPlusN(3)
// Wywołujemy `add` na obiekcie `calc`. Zwraca 6 (1 + 2 + 3)
calc.add(1 , 2)
```

Teraz, zamiast tworzyć klasę przechowującą dodatkową liczbę `n`, możemy stworzyć i zwrócić funkcję `adder`, aby osiągnąć ten sam rezultat:

```scala
// Zdefiniuj funkcję, która przyjmuje stałą liczbę i zwraca nową funkcję, która dodaje ją do swoich argumentów
def addFixedNumber(n: Int): (Int, Int) => Int =
  def adder(x: Int, y: Int): Int = x + y + n
  adder
// Utwórz wyspecjalizowaną funkcję, która dodaje 3 do swoich argumentów
val add = addFixedNumber(3)
// Wywołujemy funkcję `add`. Zwraca 6 (1 + 2 + 3)
add(1, 2)
```

W powyższym przykładzie definiujemy funkcję `addFixedNumber`, która przyjmuje liczbę całkowitą `n` i zwraca nową funkcję, która przyjmuje dwie liczby całkowite, `x` i `y`, i zwraca sumę `n`, `x` i `y`.  
Zwróć uwagę na typ zwracany przez `addFixedNumber` — jest to typ funkcji `(Int, Int) => Int`.  
Następnie definiujemy nową funkcję `adder` wewnątrz `addFixedNumber`, która przechwytuje wartość `n` i dodaje ją do swoich dwóch argumentów, `x` i `y`.  
Funkcja `adder` jest następnie zwracana jako wynik funkcji `addFixedNumber`.

Tworzymy następnie wyspecjalizowaną funkcję `add`, wywołując `addFixedNumber(n: Int)` z `n` równym `3`.  
Teraz możemy wywoływać `add` na dowolnych dwóch liczbach całkowitych; wynikiem będzie suma tych liczb plus `3`.

Scala dostarcza specjalną składnię dla funkcji zwracających inne funkcje, co zostało pokazane poniżej:

``` 
def addFixedNumber(n: Int)(x: Int, y:Int) =
  x + y + n

val add = addFixedNumber(3)
```

Pierwszy argument funkcji `addFixedNumber` jest zamknięty we własnych nawiasach, podczas gdy drugi i trzeci argument są zamknięte w kolejnej parze nawiasów.  
Funkcji `addFixedNumber` można dostarczyć tylko pierwszy argument, co spowoduje utworzenie funkcji oczekującej na dwa kolejne argumenty: `x` i `y`.  
Możemy też wywołać funkcję, przekazując wszystkie trzy argumenty, ale muszą one być zamknięte w oddzielnych nawiasach: `addFixedNumber1(3)(4, 5)`, a nie `addFixedNumber(3,4,5)`.  
Zwróć uwagę, że nie można przekazać jedynie dwóch argumentów do funkcji zapisanej w tej składni: `addFixedNumber1(3)(4)` nie jest dozwolone.



## Ćwiczenie 

Zaimplementuj funkcję `filterList`, która będzie zwracać inną funkcję.  
W implementacji możesz użyć metody `filter`.