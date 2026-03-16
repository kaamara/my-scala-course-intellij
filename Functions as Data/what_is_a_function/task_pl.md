Funkcja to niezależny blok kodu, który przyjmuje argumenty, wykonuje pewne obliczenia i zwraca wynik.  
Może, ale nie musi mieć efektów ubocznych; oznacza to, że może uzyskiwać dostęp do danych w programie, a jeśli dane są modyfikowalne, funkcja może je zmieniać.  
Jeśli tego nie robi — to znaczy, jeśli funkcja działa wyłącznie na swoich argumentach — stwierdzamy, że funkcja jest czysta (pure).  
W programowaniu funkcyjnym używamy czystych funkcji, kiedy tylko jest to możliwe, chociaż zasada ta ma ważne wyjątki, które omówimy później.  

Główna różnica między funkcją a metodą polega na tym, że metoda jest powiązana z klasą lub obiektem.  
Z kolei funkcja jest traktowana dokładnie tak samo jak każda inna wartość w programie: może być tworzona w dowolnym miejscu w kodzie, przekazywana jako argument, zwracana przez inną funkcję lub metodę itd.

Rozważmy następujący kod:
```Scala

// Funkcja zdefiniowana z użyciem `def`
def addAsFunction(x: Int, y: Int): Int = x + y

// Funkcja zdefiniowana jako wartość
val addAsValue: (Int, Int) => Int = (x, y) => x + y

// Metoda powiązana z klasą
class Calculator:
  def add(x: Int, y: Int): Int = x + y

```

Obie funkcje `add` przyjmują dwa parametry wejściowe, `x` i `y`, wykonują czyste obliczenie dodania ich do siebie, a następnie zwracają wynik.  
Nie zmieniają one żadnego zewnętrznego stanu.  
W pierwszym przypadku definiujemy funkcję za pomocą słowa kluczowego `def`.  
Po `def` mamy nazwę funkcji, następnie listę argumentów wraz z ich typami, potem typ wyniku funkcji, a na końcu obliczenia wykonywane przez funkcję, czyli `x + y`.

Porównaj to z drugim podejściem do definiowania funkcji za pomocą słowa kluczowego `val`, którego używamy również dla wszystkich innych rodzajów danych.  
Tutaj, po `val`, mamy nazwę funkcji, następnie jej typ, `(Int, Int) => Int`.  
Zawiera on zarówno typy argumentów, jak i typ wyniku. Następnie pojawiają się argumenty (tym razem bez typów), a na końcu implementacja.  
Prawdopodobnie uznasz pierwszy sposób definiowania funkcji za bardziej czytelny i będziesz go używał częściej.  
Jednak ważne jest, aby pamiętać, że w Scali funkcja jest traktowana jako dane, podobnie jak liczby całkowite, ciągi znaków i instancje klasy przypadków (case classes) — i może być zdefiniowana jako dane, jeśli jest taka potrzeba.

Trzeci przykład ilustruje metodę,  
którą nazywamy po prostu `add`.  
Jej definicja naśladuje definicję `addAsFunction`, jednak nazywamy `add` metodą, ponieważ jest powiązana z klasą `Calculator`.  
W ten sposób, tworząc instancję klasy `Calculator`, możemy wywołać `add` na niej, uzyskując dostęp do wewnętrznego stanu instancji.  
Możliwe jest również, na przykład, jej nadpisanie w podklasie `Calculator`.

```scala
// Instancja klasy Calculator. Instancja nie ma wewnętrznego stanu.
val calc = new Calculator
// Wywołujemy add(1, 2) na calc. Zwraca 3 (1 + 2).
calc.add(1, 2)

// Podklasa Calculator, która posiada wewnętrzny stan: liczbę całkowitą n.
class CalculatorPlusN(n: Int) extends Calculator:
  // Nadpisana metoda `add`, która dodaje n z wewnętrznego stanu do wyniku dodawania.
  override def add(x: Int, y: Int): Int = super.add(x, y) + n

// Instancja CalculatorPlusN z wewnętrznym stanem, n == 3.
val calc3 = new CalculatorPlusN(3)
// Wywołujemy add na calc3. Zwraca 6 (1 + 2 + 3)
calc3.add(1 , 2)

```
<div class="hint" title="Zobacz dodatkowe materiały dotyczące tematu">

Wpis na blogu <a href="https://makingthematrix.wordpress.com/2020/12/15/programming-with-functions-2-functions-as-data">tutaj</a>.  

Wideo: 

<iframe width="560" height="315" src="https://www.youtube.com/embed/RX1_EJp9Vxk" title="Odtwarzacz wideo YouTube" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>

## Zadanie

Zaimplementuj mnożenie zarówno jako funkcję, jak i wartość; dodatkowo zaimplementuj mnożenie jako metodę w klasie.