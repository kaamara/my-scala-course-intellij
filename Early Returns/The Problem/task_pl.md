Często zdarza się, że nie musimy przechodzić przez wszystkie elementy kolekcji, aby rozwiązać konkretny problem.  
Na przykład w rozdziale o Rekurencji w poprzednim module widzieliśmy funkcję służącą do szukania klucza w pudełku.  
Wystarczyło znaleźć jeden klucz i nie było sensu kontynuować poszukiwań w pudełku po jego znalezieniu.

Problem może się skomplikować, gdy dane stają się bardziej złożone.  
Rozważ aplikację zaprojektowaną do śledzenia członków zespołu, zawierającą szczegóły dotyczące projektów, nad którymi pracowali, oraz konkretnych dni, w których byli zaangażowani.  
Następnie menedżer zespołu mógłby używać aplikacji do uruchamiania skomplikowanych zapytań, takich jak:  
* Znajdź przypadek, gdy zespół pracował więcej godzin na osobę niż X w ciągu jednego dnia.  
* Znajdź przykład błędu, którego naprawienie zajęło więcej niż Y dni.  

Często przekształcamy element oryginalnej kolekcji danych w pochodną reprezentację, która lepiej opisuje dziedzinę problemu.  
Następnie ta przekształcona reprezentacja jest weryfikowana za pomocą predykatu, aby zdecydować, czy jest odpowiednim przykładem.  
Zarówno przekształcenie, jak i weryfikacja mogą być kosztowne, co sprawia, że naiwna implementacja, jak w naszym przykładzie wyszukiwania klucza, staje się nieefektywna.  
W językach takich jak Java można użyć `return`, aby zatrzymać przeszukiwanie kolekcji, gdy tylko znajdziemy odpowiedź.  
Implementacja może wyglądać mniej więcej tak:

```java
Bar complexConversion(Foo foo) {
  ...
}
 
bool complexValidation(Bar bar) {
  ...
}
 
Bar findFirstValidBar(Collection<Foo> foos) {
  for(Foo foo : foos) {
    Bar bar = complexConversion(foo);
    if (complexValidation(bar)) return bar;
  }
  return null;
}
```

Tutaj przechodzimy przez elementy kolekcji `foos` sekwencyjnie, wykonując na nich `complexConversion`, a następnie `complexValidation`.  
Jeśli znajdziemy element, dla którego `complexValidation(bar)` zakończy się sukcesem, przekształcony element jest natychmiast zwracany, a przeszukiwanie zostaje przerwane.  
Jeśli jednak taki element nie zostanie znaleziony, zwracane jest `null` po przeszukaniu całej kolekcji bez sukcesu.

Jak zastosować ten wzorzec w Scali?  
Kusi, aby przetłumaczyć ten kod linia po linii bezpośrednio na Scalę:

```scala 3
def complexConversion(foo: Foo): Bar = ...
def complexValidation(bar: Bar): Boolean = ...
 
def findFirstValidBar(seq: Seq[Foo]): Option[Bar] = {
  for (foo <- seq) {
    val bar = complexConversion(foo)
    if (complexValidation(bar)) return Some(bar)
  }
  None
}
```

Zastąpiliśmy `null` bardziej odpowiednią wartością `None`, ale poza tym kod pozostał taki sam.  
Jednakże nie jest to dobra praktyka w Scali, gdzie użycie `return` nie jest idiomatyczne.  
Ponieważ każdy blok kodu w Scali jest wyrażeniem, ostatnie wyrażenie w ramach bloku jest tym, co zostaje zwrócone.  
Można napisać `x` zamiast `return x` dla ostatniego wyrażenia, a semantyka byłaby taka sama.  
Kiedy `return` jest używane w środku bloku, programista nie może już polegać na ostatnim wyrażeniu jako na tym, które zwróci wynik z bloku.  
To sprawia, że kod jest mniej czytelny, trudniejszy do inline'owania i narusza referencyjną przejrzystość.  
Dlatego używanie `return` jest uznawane za błąd projektowy i powinno być unikane.

W tym module zbadamy bardziej idiomatyczne sposoby terminowania wykonania wcześniej w Scali.