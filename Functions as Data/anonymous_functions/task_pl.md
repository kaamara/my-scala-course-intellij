Funkcja anonimowa to funkcja, która, dosłownie rzecz ujmując, nie ma nazwy. Jest definiowana wyłącznie przez listę argumentów oraz obliczenia.  
Funkcje anonimowe są również znane jako funkcje lambda, lub po prostu lambdy.

Funkcje anonimowe są szczególnie przydatne, gdy trzeba przekazać funkcję jako argument do innej funkcji, lub kiedy chcemy stworzyć funkcję, która jest używana tylko raz i nie warto jej definiować osobno.

Oto przykład:

```scala
// Tworzymy sekwencję liczb.
val numbers = Seq(1, 2, 3, 4, 5)

// Używamy metody Seq.map, aby podwoić każdą liczbę w sekwencji, korzystając z funkcji anonimowej.
val doubled = numbers.map(x => x * 2)
```

Tutaj tworzymy sekwencję `numbers` i chcemy podwoić każdą liczbę.  
Aby to zrobić, używamy metody `map`.  
Definiujemy funkcję anonimową `x => x * 2` i przekazujemy ją do metody `map` jako jedyny argument.  
Metoda `map` stosuje tę funkcję anonimową do każdego elementu `numbers` i zwraca nową listę, którą nazywamy `doubled`, zawierającą podwojone wartości.

Funkcje anonimowe mogą uzyskiwać dostęp do zmiennych, które są w zasięgu w momencie ich definiowania.  
Rozważ funkcję `multiplyList`, która mnoży każdą liczbę na liście przez `multiplier`.  
Parametr `multiplier` może być używany wewnątrz `map` bez żadnych problemów.  

```scala
def multiplyList(multiplier: Int, numbers: List[Int]): List[Int] =
  // Możemy używać multiplier wewnątrz map 
  numbers.map(x => multiplier * x)

```

Kiedy jakiś parametr jest używany tylko raz wewnątrz funkcji anonimowej, Scala pozwala pominąć nazwę argumentu i zastąpić ją symbolem `_`.  
Jednak należy pamiętać, że jeśli parametr jest używany wielokrotnie, należy użyć nazw, aby uniknąć niejasności.  
Kompilator Scali zgłosi błąd, jeśli nie zastosujemy się do tej zasady.  

```scala
// Mnożymy każdą parę liczb na liście, używając funkcji anonimowej.
def multiplyPairs(numbers: List[(Int, Int)]): List[Int] = numbers.map((x, y) => x * y)

// Tutaj również mnożymy każdą parę liczb na liście, ale pomijamy nazwy parametrów w funkcji anonimowej.
// Scala przypisuje symbol `_` do parametrów w kolejności ich przekazania.  
def multiplyPairs1(numbers: List[(Int, Int)]): List[Int] = numbers.map(_ * _)

// Obliczamy kwadrat każdego elementu na liście za pomocą funkcji anonimowej. 
def squareList(numbers: List[Int]): List[Int] = numbers.map(x => x * x)

// W tym przypadku pominięcie nazw parametrów jest niedozwolone.
// Można zauważyć, jak to może być mylące w porównaniu do `multiplyPairs1`. 
def squareList1(numbers: List[Int]): List[Int] = numbers.map(_ * _)
```

## Ćwiczenie

Zaimplementuj funkcję `multiplyAndOffsetList`, która mnoży i przesuwa każdy element na liście.  
Użyj `map` oraz funkcji anonimowej.