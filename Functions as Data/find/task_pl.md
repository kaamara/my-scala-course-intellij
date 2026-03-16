```scala
def find(pred: A => Boolean): Option[A]
```

Wyobraź sobie, że zamiast filtrowania wszystkich czarnych kotów, jesteśmy zadowoleni z uzyskania tylko jednego, niezależnie którego.  
Moglibyśmy użyć `filter` do tego celu, a następnie pobrać pierwszego kota z wynikowej kolekcji. Jednak `filter` przeiteruje całą oryginalną kolekcję kotów, bez względu na jej rozmiar.  
Istnieją lepsze rozwiązania.  
Na przykład możemy skorzystać z metody `find`, która działa dokładnie tak jak `filter`, ale zatrzymuje się na pierwszym pasującym elemencie.

```scala
// Znajdujemy pierwszego czarnego kota w worku, jeśli istnieje
val blackCat: Option[Cat] = bagOfCats.find { cat => cat.color == Black }

val felixTheCat: Option[Cat] = bagOfCats.find { cat => cat.name == "Felix" }
```

Zauważ, że metoda `find` zwraca `Option`.  
Na razie możesz traktować `Option` jako specjalny typ kolekcji, który przechowuje albo zero, albo jeden element.  
Jeśli użyty predykat zwrócił `false` dla każdego elementu w kolekcji, metoda `find` zwróci pusty `Option` (znany również jako `None`).  
Więcej o `Option` powiemy w jednym z kolejnych rozdziałów.  

Przy okazji, przejrzyj dokumentację Scali dotycząca metod takich jak `exists`, `forall` i `contains`.  
Mogą się one okazać przydatne, jeśli chcesz tylko sprawdzić, czy element w kolekcji spełnia określone wymagania  
(lub, w przypadku `forall`, czy wszystkie je spełniają), ale nie jesteś zainteresowany pobraniem pasującego elementu.  

## Ćwiczenie

Teraz wyobraź sobie, że jesteśmy gotowi przygarnąć dowolnego kota ze schroniska dla zwierząt, który jest biały i puszysty albo, powiedzmy, dowolnego kota perskiego z trójkolorową sierścią.  
Zaimplementuj odpowiadające funkcje.  

<div class="hint">
  Użyj metody <code>contains</code>, aby sprawdzić, czy kot jest puszysty.
</div>