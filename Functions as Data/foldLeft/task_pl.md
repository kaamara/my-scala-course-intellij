```scala
def foldLeft[B](acc: B)(f: (B, A) => B): B
```

Metoda `foldLeft` jest kolejną metodą w kolekcjach Scali, która może być postrzegana jako uogólniona wersja `map`, ale uogólniona inaczej niż `flatMap`.  
Powiedzmy, że wywołujemy `foldLeft` na kolekcji elementów typu `A`.  
`foldLeft` przyjmuje dwa argumenty: początkowy "akumulator" typu `B` (zazwyczaj innego niż `A`) oraz funkcję `f`, która również przyjmuje dwa argumenty: akumulator (typu `B`) i element z oryginalnej kolekcji (typu `A`).  
`foldLeft` zaczyna swoje działanie od wzięcia początkowego akumulatora i pierwszego elementu oryginalnej kolekcji, a następnie przekazuje je do `f`.  
Funkcja `f` wykorzystuje te dwie wartości, aby wygenerować nową wersję akumulatora — czyli nową wartość typu `B`.  
Ta nowa wartość, zaktualizowany akumulator, jest ponownie przekazywana do `f`, tym razem wraz z drugim elementem kolekcji.  
Proces ten jest powtarzany, aż wszystkie elementy oryginalnej kolekcji zostaną przetworzone.  
Końcowym wynikiem `foldLeft` jest wartość akumulatora po przetworzeniu ostatniego elementu oryginalnej kolekcji.

Część "fold" w nazwie metody `foldLeft` pochodzi od idei, że działanie `foldLeft` można postrzegać jako "składanie" kolekcji elementów, jeden w drugi, aż do momentu, gdy zostanie uzyskana jedna wartość — wynik końcowy.  
Sufiks "left" wskazuje, że w przypadku uporządkowanych kolekcji procesujemy od początku kolekcji (z lewej strony) do jej końca (z prawej strony).  
Istnieje również metoda `foldRight`, która działa w odwrotnym kierunku.

Zobaczmy, jak możemy zaimplementować popularne ćwiczenie programistyczne, *FizzBuzz*, używając `foldLeft`.  
W *FizzBuzz* powinniśmy wypisać sekwencję liczb od 1 do podanej liczby (powiedzmy do 100).  
Jednak za każdym razem, gdy liczba, którą chcemy wyświetlić, jest podzielna przez 3, wypisujemy "Fizz"; jeśli jest podzielna przez 5, wypisujemy "Buzz"; a jeśli jest podzielna przez 15, wypisujemy "FizzBuzz".  
Oto jak możemy to osiągnąć za pomocą `foldLeft` w Scali 3:

```scala
def fizzBuzz(n: Int): Int | String = n match
 case _ if n % 15 == 0 => "FizzBuzz"
 case _ if n % 3  == 0 => "Fizz"
 case _ if n % 5  == 0 => "Buzz"
 case _ => n

// Wygeneruj zakres liczb od 1 do 100
val numbers = 1 to 100

// Użyj foldLeft do iteracji po liczbach i zastosowania funkcji fizzBuzz
val fizzBuzzList = numbers.foldLeft[List[Int | String]](Nil) { (acc, n) => acc :+ fizzBuzz(n) }

println(fizzBuzzList)
```

Najpierw piszemy metodę `fizzBuzz`, która przyjmuje `Int` i zwraca albo `Int` (liczbę, którą otrzymała), albo `String`: "Fizz", "Buzz" lub "FizzBuzz".  
Dzięki wprowadzeniu typów unijnych w Scali 3 możemy zadeklarować, że nasza metoda może zwracać jeden z dwóch lub więcej różnych typów. Jednak jest zagwarantowane, że zwracana wartość będzie jednym z nich.

Następnie tworzymy zakres liczb od 1 do 100 za pomocą `1 to 100`.

Wywołujemy metodę `foldLeft` na zakresie liczb, określając, że akumulator będzie typu `List[String | Int]` i początkowo będzie pusty (`Nil`).

Drugim argumentem do `foldLeft` jest funkcja, która przyjmuje bieżącą wartość akumulatora (`acc`) i element z zakresu liczb (`n`).  
Funkcja ta wywołuje naszą metodę `fizzBuzz` z liczbą i dodaje wynik do listy akumulatora za pomocą operatora `:+`.

Po przetworzeniu wszystkich elementów `foldLeft` zwraca końcową wartość akumulatora, czyli kompletną listę liczb i stringów "Fizz", "Buzz" oraz "FizzBuzz", zastępując liczby, które były podzielne przez 3, 5 i 15, odpowiednimi stringami.

Na koniec wypisujemy wyniki.

## Zadanie

Zaimplementuj następujące funkcje, używając `foldLeft`:  
* Funkcję `computeAverage`, która oblicza średnią dla listy liczb;  
* Funkcję `maximum`, która znajduje największą liczbę w liście;  
* Funkcję `reverse`, która odwraca listę.