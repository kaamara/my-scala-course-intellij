Leniwa lista w Scali to kolekcja, która ocenia swoje elementy leniwie, przy czym każdy element jest obliczany tylko raz, w momencie, gdy jest potrzebny, a następnie przechowywany do późniejszego dostępu.  
Leniwe listy mogą być nieskończone, a ich elementy są obliczane na żądanie. W związku z tym, jeśli twój program stale pobiera kolejny element w pętli, leniwa lista będzie się nieuchronnie powiększać, aż program zakończy działanie błędem braku pamięci.  
W praktyce jednak zazwyczaj będziesz potrzebować tylko skończonej liczby elementów.  
Chociaż ta liczba może być duża i nieznana od początku, leniwa lista obliczy jedynie wyraźnie zażądane wartości, umożliwiając programistom pracę z dużymi zbiorami danych lub sekwencjami w sposób efektywny pod względem wykorzystania pamięci.  
W takich przypadkach leniwa lista zapewnia wygodną metodę implementacji logiki obliczania kolejnych elementów, aż zdecydujesz się zatrzymać.  
Możesz użyć jej w określonych przypadkach, w których w innym razie potrzebowałbyś zaimplementować skomplikowaną strukturę danych z polami mutowalnymi i metodą obliczającą nowe wartości dla tych pól.

Poniżej znajduje się przykład generowania ciągu Fibonacciego za pomocą leniwej listy w Scali:

```
lazy val fib: LazyList[BigInt] =
  BigInt(0) #::
    BigInt(1) #::
    fib.zip(fib.tail).map { case (a, b) => a + b }

// Pobierz i wypisz pierwsze 10 liczb Fibonacciego
fib.take(10).foreach(println)
```

W powyższym kodzie:  
* `#::` to operator, który tworzy nową leniwą listę z określoną głową (elementem przed operatorem) i ogonem (leniwą listą po operatorze).  
  Zaczynamy od `BigInt(0)` jako głowy, a wyrażenie po `#::` staje się ogonem.  
  Teraz ten ogon składa się z głowy (`BigInt(1)`) oraz kolejnego ogona połączonego operatorem `#::`.  
  Ten drugi "wewnętrzny" ogon jest skonstruowany za pomocą metody `zip`, działającej na oryginalnej leniwej liście `fib`.  
  Możliwe jest odwoływanie się do `fib` w tym miejscu kodu, ponieważ lista jest leniwa — wyrażenie nie zostanie ocenione natychmiast po konstrukcji leniwej listy, lecz dopiero później, gdy `fib` już istnieje i chcemy uzyskać dostęp do jednego z jego elementów.  
* `fib.zip(fib.tail)` bierze dwie sekwencje, `fib` oraz jej ogon (czyli `fib` bez jej pierwszego elementu), i łączy je w pary.  
  Ciąg Fibonacciego generowany jest poprzez sumowanie każdej pary `(a, b) => a + b` kolejnych liczb Fibonacciego.  
* `take(10)` służy do pobrania pierwszych 10 liczb Fibonacciego z leniwej listy, a `foreach(println)` je wypisuje.  
  Należy zauważyć, że ciąg Fibonacciego teoretycznie jest nieskończony, ale nie powoduje to żadnych problemów ani błędów braku pamięci  
  (przynajmniej na tym etapie), dzięki leniwemu przetwarzaniu.  
* Alternatywnie można użyć `takeWhile`, aby obliczać kolejne elementy leniwej listy, dopóki nie zostanie spełniony określony warunek.  
* Metody przeciwne do `take` oraz `takeWhile` — `drop` i `dropWhile` — mogą być używane do obliczania, a następnie pomijania określonej liczby elementów w leniwej liście lub do obliczania i pomijania elementów, dopóki nie zostanie spełniony określony warunek.  
  Metody te można łączyć w łańcuchy. Na przykład `fib.drop(5).take(5)` obliczy pierwsze 10 elementów ciągu Fibonacciego, ale zignoruje pierwsze 5.

Aby dowiedzieć się więcej o metodach `LazyList` w Scali, zajrzyj do [dokumentacji](https://www.scala-lang.org/api/current/scala/collection/immutable/LazyList.html).

## Zadanie 

Zaimplementuj funkcję generującą nieskończoną leniwą listę liczb pierwszych w porządku rosnącym.  
Użyj algorytmu Sita Eratostenesa.