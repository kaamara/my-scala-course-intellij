Widok w kolekcjach Scala to leniwe odwzorowanie standardowej kolekcji.  
Podczas gdy leniwa lista wymaga celowej konstrukcji, widok można utworzyć z dowolnej "nie-leniwej" kolekcji Scala, po prostu wywołując na niej `.view`.  
Widok przekształca swoje elementy (jak map, filter, itd.) w leniwy sposób,  
co oznacza, że te operacje nie są wykonywane natychmiast; zamiast tego są obliczane na bieżąco za każdym razem, gdy wymagany jest nowy element.  
Może to poprawić zarówno wydajność, jak i zużycie pamięci.  
Dodatkowo, dzięki widokowi, można łączyć wiele operacji bez potrzeby tworzenia pośrednich kolekcji —  
operacje są stosowane do elementów oryginalnej "nie-leniwej" kolekcji tylko wtedy, gdy są rzeczywiście potrzebne.  
Może to być szczególnie korzystne w scenariuszach, w których operacje takie jak map i filter są łączone, co pozwala na odfiltrowanie znaczącej liczby elementów, eliminując potrzebę dalszego przetwarzania takich elementów.

Rozważmy przykład, w którym używamy widoku, aby znaleźć pierwszą liczbę parzystą będącą kwadratem w liście, która jest większa niż 100.

```scala 3
val numbers = (1 to 100).toList

// Bez użycia widoku
val firstEvenSquareGreaterThan100_NoView = numbers
  .map(n => n * n)
  .filter(n => n > 100 && n % 2 == 0)
  .head
println(firstEvenSquareGreaterThan100_NoView)


// Z użyciem widoku
val firstEvenSquareGreaterThan100_View = numbers.view
  .map(n => {
    println(s"Obliczany jest kwadrat liczby $n.")  // Aby zaobserwować leniwą ewaluację
    n * n
  })
  .filter(n => n > 100 && n % 2 == 0)
  .head
println(firstEvenSquareGreaterThan100_View)
```

Bez użycia widoku wszystkie liczby w liście są najpierw podnoszone do kwadratu, a potem filtrowane, mimo że interesuje nas jedynie pierwszy kwadrat spełniający warunek.  
Z widokiem operacje transformacji są obliczane leniwie.  
Kwadraty są wyliczane i warunki sprawdzane sekwencyjnie dla każdego elementu, aż zostanie znaleziony pierwszy pasujący wynik.  
Unika to niepotrzebnych obliczeń i jest przez to bardziej efektywne w takim scenariuszu.

Aby dowiedzieć się więcej o metodach Scala View, przeczytaj jej [dokumentację](https://www.scala-lang.org/api/current/scala/collection/View.html).

## Ćwiczenie

Rozważ uproszczoną wiadomość logu: jest to ciąg rozdzielany przecinkami, gdzie pierwszy podciąg przed przecinkiem określa jego poziom (`severity`),  
drugi podciąg to numeryczny kod błędu, a ostatni to sama wiadomość.  
Zaimplementuj funkcję `findLogMessage`, która wyszukuje pierwszą wiadomość logu pasującą do podanego `severity` i `errorCode` na liście.  
Ponieważ lista jest założeniowo duża, użyj `view`, aby uniknąć tworzenia pośrednich struktur danych.