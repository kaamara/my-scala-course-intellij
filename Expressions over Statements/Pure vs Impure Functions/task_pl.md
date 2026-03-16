Nie wszystkie funkcje są sobie równe; niektóre z nich są lepsze od innych.  
Duża grupa takich funkcji o wyższej jakości jest określana jako *czyste*.  
Czysta funkcja zawsze zwraca tę samą wartość dla tych samych danych wejściowych.  
Na przykład matematyczna funkcja `double(x) = 2 * x`, służąca do podwajania liczby, zawsze zwraca `42`, gdy jako argument otrzyma `21`.  
Natomiast funkcja `g`, która jako argument przyjmuje liczbę, odczytuje kolejną liczbę ze standardowego wejścia, a następnie je mnoży,  
nie zawsze oblicza ten sam wynik, gdy zostaje wywołana z `21`.  

```scala 3
def g(x: Int): Int =
  val y = StdIn.readInt()
  x * y

println(g(21)) // Input: 1 => wypisano 21
println(g(21)) // Input: 3 => wypisano 63
```

Kolejną konsekwencją wymogu zawsze uzyskiwania tego samego rezultatu jest to, że czysta funkcja nie może wykonywać żadnych efektów ubocznych.  
Na przykład czysta funkcja nie może nic wypisywać, wchodzić w interakcje z bazą danych ani zapisywać do pliku.  
Nie może odczytywać danych z konsoli, bazy danych czy pliku, modyfikować swoich argumentów ani zgłaszać wyjątków.  
Wynik zależy wyłącznie od argumentów i samej implementacji funkcji.  
Jej działanie nie powinno ani być zależne od zewnętrznego świata, ani na niego wpływać.

Można argumentować, że czyste funkcje wydają się całkowicie bezużyteczne.  
Jeśli nie mogą wchodzić w interakcje ze światem zewnętrznym ani niczego modyfikować, jak można czerpać z nich jakąkolwiek wartość?  
Dlaczego w ogóle powinniśmy stosować czyste funkcje?  
Faktem jest, że są one znacznie bardziej niezawodne niż ich nieczyste odpowiedniki.  
Ponieważ nie zawierają ukrytych interakcji, znacznie łatwiej jest upewnić się, że twoja czysta funkcja robi to,  
co powinna, i nic więcej.  
Co więcej, są one znacznie łatwiejsze do przetestowania, ponieważ nie musisz budować atrap bazy danych, gdy funkcja nigdy z niej nie korzysta.

Niektóre języki programowania, takie jak Haskell, ograniczają "nieczystość" i odzwierciedlają wszelkie efekty uboczne w swoich typach.  
Może to jednak być dość restrykcyjne i nie jest podejściem stosowanym w Scalce.  
Idiomy Scali wskazują, aby pisać kod w taki sposób, by większość była czysta, a "nieczystość" była stosowana tylko tam,  
gdzie jest absolutnie konieczna, podobnie jak robiliśmy to z mutowalnymi danymi.  
Na przykład funkcję `g` można podzielić na dwie: jedną, która odczytuje liczbę ze standardowego wejścia,  
oraz tę odpowiedzialną za mnożenie:

```scala 3
def gPure(x: Int, y: Int): Int =
  x * y 
  
def g(x: Int): Int =
  val y = StdIn.readInt()
  gPure(x, y)
```

## Ćwiczenie

Zaimplementuj czystą funkcję `calculateAndLogPure`, która robi to samo, co `calculateAndLogImpure`, ale bez stosowania zmiennej globalnej.