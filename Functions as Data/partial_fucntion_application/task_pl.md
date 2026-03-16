Zwracanie funkcji jako wyniku innych funkcji jest powiązane z, ale nie tożsame z, [częściową aplikacją](https://en.wikipedia.org/wiki/Partial_application).  
Pierwsza technika pozwala na tworzenie funkcji, które zachowują się tak, jakby miały „ukryte” argumenty, przekazywane w momencie tworzenia, a nie w momencie wywołania.  
Każda funkcja zwraca nową funkcję, która akceptuje kolejny argument, aż wszystkie argumenty zostaną przetworzone. Ostateczna funkcja zwraca wynik.

Z kolei częściowa aplikacja funkcji odnosi się do procesu przypisywania stałych wartości do części argumentów funkcji i zwracania nowej funkcji, która przyjmuje tylko pozostałe argumenty.  
Nowa funkcja jest wyspecjalizowaną wersją oryginalnej funkcji z już dostarczonymi pewnymi argumentami.  
Ta technika umożliwia ponowne wykorzystanie kodu — możemy napisać bardziej ogólną funkcję, a następnie skonstruować jej wyspecjalizowane wersje do użycia w różnych kontekstach.  
Poniżej znajduje się przykład:

```scala
// Zdefiniuj funkcję, która przyjmuje trzy argumenty
def addN(x: Int, y: Int, n: Int) = x + y + n
// Częściowo zastosuj funkcję 'addN', ustawiając ostatni argument na 3
val add3 = addN(_: Int, _: Int, 3)
// Wywołaj częściowo zastosowaną funkcję z pozostałymi argumentami
val result = add3(1, 2) // wynik to 6 (1 + 2 + 3)
```

W powyższym przykładzie definiujemy funkcję `addN`, która przyjmuje trzy argumenty: `x`, `y` i `n`, i zwraca ich sumę.  
Następnie częściowo stosujemy funkcję `addN`, aby ustawić ostatni argument na 3, używając symbolu `_` jako zastępstwa dla dwóch pierwszych.  
W ten sposób tworzymy nową funkcję, `add3`, która przyjmuje tylko dwa argumenty: `x` i `y`.  
Na końcu wywołujemy `add3` z dwoma argumentami, uzyskując ten sam wynik, co w przypadku funkcji zwracającej funkcję w poprzednim rozdziale oraz w przykładzie `CalculatorPlusN` z pierwszego zadania.

## Ćwiczenie 

Zaimplementuj funkcję `filterList`, która następnie będzie mogła zostać częściowo zastosowana.  
Możesz użyć metody `filter` w implementacji.