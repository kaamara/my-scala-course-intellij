Monady mogą wyrażać różne efekty obliczeniowe, a porażka jest tylko jednym z nich.  
Innym jest niedeterministyczność, która pozwala programowi mieć wiele możliwych wyników.  
Jednym ze sposobów na enkapsulację różnych wyników jest użycie `List`.  

Rozważmy program, który oblicza dzielniki liczby.  
Dla liczb złożonych istnieje przynajmniej jeden dzielnik, który nie jest równy 1 ani samej liczbie, a wiele liczb ma wiele dzielników.  
Pytanie brzmi: który z dzielników powinniśmy zwrócić?  
Oczywiście, moglibyśmy zwrócić losowy dzielnik, ale bardziej funkcjonalnym podejściem jest zwrócenie ich wszystkich, zapakowanych w kolekcję taką jak `List`.  
W takim przypadku to wywołujący może zdecydować o odpowiednim traktowaniu.  

```scala
// Niedeterministyczna funkcja do obliczania wszystkich dzielników liczby 
def factors(n: Int): List[Int] = {
  (1 to n).filter(n % _ == 0).toList
}

// factors(42) == List(1, 2, 3, 6, 7, 14, 21, 42)
```

Porozmawiajmy teraz o monadzie List.  
Metoda `identity` po prostu tworzy listę jednoelementową z argumentem w środku, co wskazuje, że obliczenie zakończyło się jednym wynikiem.  
`flatMap` stosuje akcję monadyczną do każdego elementu listy, a następnie konkatenuje wyniki.  
Jeśli uruchomimy `factors(4).flatMap(factors)`, otrzymamy `List(1,2,4).flatMap(factors)`, co konkatenuje `List(1)`, `List(1,2)` oraz `List(1,2,4)` do ostatecznego wyniku `List(1,1,2,1,2,4)`.  

`List` nie jest jedyną kolekcją, która może opisywać niedeterministyczność; inną jest `Set`.  
Różnica między nimi polega na tym, że to drugie nie przejmuje się powtórzeniami, podczas gdy pierwsze zachowuje je wszystkie.  
Możesz wybrać odpowiednią kolekcję w zależności od analizowanego problemu.  
Dla `factors` może mieć sens użycie `Set`, ponieważ interesują nas tylko unikalne dzielniki.  

```scala
// Niedeterministyczna funkcja do obliczania wszystkich dzielników liczby 
def factors(n: Int): Set[Int] = {
  (1 to n).filter(n % _ == 0).toSet
}

// factors(42) == Set(1, 2, 3, 6, 7, 14, 21, 42)
// factors(6).flatMap(factors) == Set(1, 2, 4) 
```

## Ćwiczenie  

Aby nasz model użytkowników był nieco bardziej realistyczny, powinniśmy uwzględnić fakt, że użytkownik może mieć wiele dzieci.  
Czyni to naszą funkcję `getGrandchild` niedeterministyczną.  
Odbija się to w nazwach, typach oraz implementacjach.  

Teraz funkcja `getGrandchildren` agreguje wszystkie wnuki danego użytkownika.  
Ponieważ każda osoba jest unikalna, używamy `Set`.  
Jednakże może się zdarzyć, że niektóre wnuki będą miały ten sam wiek i nie chcemy utracić tej informacji.  
Z tego powodu dla funkcji `getGrandchildrenAges` używamy typu zwracanego `List`.  
Zauważ, że nie ma potrzeby jawnego zgłaszania błędów, ponieważ pusta kolekcja sama w sobie oznacza niepowodzenie.