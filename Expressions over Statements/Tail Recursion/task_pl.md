Częstym zarzutem wobec używania rekurencji jest to, że jest ona z natury wolna. 
Dlaczego tak się dzieje? 
Odpowiedź kryje się w tym, co jest znane jako stos wywołań (ang. call stack). 
Za każdym razem, gdy funkcja jest wywoływana, pewne informacje dotyczące tego wywołania są umieszczane na stosie wywołań — mówimy, że pewna przestrzeń stosu (ang. stack space) jest alokowana. 
Te informacje są tam przechowywane, dopóki wszystkie obliczenia w ramach tej funkcji nie zostaną zakończone, po czym stos jest dealokowany (informacje o wywołaniu funkcji są usuwane ze stosu), a obliczona wartość zostaje zwrócona. 
Jeśli funkcja wywoła inną funkcję, stos alokowany jest ponownie przed dealokacją wywołania poprzedniej funkcji. Co gorsze, musimy poczekać, aż wewnętrzne wywołanie zostanie zakończone, jego ramka stosu zostanie dealokowana, a jej wartość zostanie zwrócona, zanim będziemy mogli obliczyć wynik funkcji, która ją wywołała.
Ma to szczególne znaczenie dla funkcji rekurencyjnych, ponieważ głębokość stosu wywołań może być astronomiczna. 

Rozważmy przykład obliczania silni.

```scala 3
def factorial(n: BigInt): BigInt =
  if (n <= 0) 1
  else n * factorial(n - 1)
```

Wyliczenie `factorial(3)` powoduje następujące zdarzenia na stosie: 

```scala 3
factorial(3)
3 * factorial(2)
3 * 2 * factorial(1)
3 * 2 * 1 * factorial(0)
3 * 2 * 1 * 1 
3 * 2 * 1 
3 * 2 
6
```

Dopóki nie dotrzemy do przypadku bazowego, nie możemy rozpocząć mnożenia i musimy przechowywać wszystkie mnożniki na stosie. 
Wywołanie `factorial` z argumentem wystarczająco dużym (jak `10000` na moim komputerze) skutkuje błędem przepełnienia stosu (ang. stack overflow error), a obliczenie nie daje żadnego wyniku. 

Nie zniechęcaj się! 
Istnieje dobrze znana technika optymalizacji, która pozwala złagodzić ten problem. 
Polega ona na przepisaniu funkcji rekurencyjnej na formę ogonowo-rekurencyjną (ang. tail-recursive). 
W tej formie, wywołanie rekurencyjne powinno być ostatnią operacją wykonywaną przez funkcję. 
Na przykład, `factorial` można przepisać w następujący sposób: 

```scala 3
def factorial(n: BigInt): BigInt = 
  def go(n: BigInt, accumulator: BigInt): BigInt =
    if (n <= 0) accumulator
    else go(n - 1, n * accumulator)
  go(n, 1)
```

Dodajemy nowy parametr `accumulator` do funkcji rekurencyjnej, który śledzi częściowo obliczone mnożenie.
Zwróć uwagę, że wywołanie rekurencyjne funkcji `go` jest ostatnią operacją w gałęzi `else` instrukcji `if`. 
Jakakolwiek wartość zostanie zwrócona przez wywołanie rekurencyjne, zostaje po prostu zwrócona przez funkcję, która je wywołała. 
Nie ma potrzeby alokacji żadnych ramek stosu, ponieważ nic nie oczekuje na wynik wywołania rekurencyjnego do kontynuowania obliczeń. 
Wystarczająco sprytne kompilatory (Scala Compiler do nich należy) potrafią w takim przypadku zoptymalizować niepotrzebne alokacje stosu.
Śmiało, spróbuj znaleźć `n`, dla którego ogonowo-rekurencyjna funkcja `factorial` spowoduje przepełnienie stosu. 
O ile coś nie pójdzie bardzo źle, nie powinno Ci się to udać. 

Przy okazji, pamiętasz funkcję wyszukiwania klucza, którą zaimplementowaliśmy w poprzednim zadaniu? 
Zastanawiałeś się, jak to się stało, że nie musieliśmy śledzić kolekcji pudełek do przeszukania? 
Sztuczka polega na tym, że stos zastępuje tę kolekcję. 
Wszystkie pudełka do rozważenia są gdzieś na stosie, cierpliwie czekając na swoją kolej. 

Czy jest sposób na uczynienie tej funkcji ogonowo-rekurencyjną? 
Tak, oczywiście, że jest! 
Podobnie jak w przypadku funkcji `factorial`, możemy stworzyć funkcję pomocniczą `go` z dodatkowym parametrem `boxesToLookIn`, aby śledzić pudełka, które należy przeszukać w poszukiwaniu klucza.
Dzięki temu możemy upewnić się, że funkcja `go` jest ogonowo-rekurencyjna, tzn. albo zwraca wartość, albo wywołuje samą siebie jako ostatni krok. 

```scala 3
def lookForKey(box: Box): Option[Key] =
  def go(item: Item, boxesToLookIn: Set[Item]): Option[Key] =
    item match
      case key: Key => Some(key)
      case Box(content) =>
        if content.isEmpty
        then
          if boxesToLookIn.isEmpty
          then None
          else go(boxesToLookIn.head, boxesToLookIn.tail)
        else
          go(content.head, boxesToLookIn ++ content.tail)
  go(box, Set.empty)
```

W Scali istnieje sposób na upewnienie się, że Twoja funkcja jest ogonowo-rekurencyjna: adnotacja `@tailrec` z pakietu `scala.annotation.tailrec`. 
Sprawdza ona, czy implementacja jest ogonowo-rekurencyjna i generuje błąd kompilatora, jeśli taka nie jest. 
Zalecamy użycie tej adnotacji, aby upewnić się, że kompilator jest w stanie optymalizować Twój kod, nawet w razie przyszłych zmian.

## Ćwiczenie

Zaimplementuj funkcje ogonowo-rekurencyjne do odwracania listy oraz znajdowania sumy cyfr w liczbie nieujemnej. 
Dodaliśmy adnotację `@tailrec` do funkcji pomocniczych, aby kompilator mógł zweryfikować tę właściwość.