Kolekcje Scala obejmują rozległy zestaw struktur danych, które oferują potężne i elastyczne sposoby manipulacji i organizacji danych. 
Framework kolekcji Scala został zaprojektowany tak, aby był zarówno przyjazny dla użytkownika, jak i ekspresyjny, umożliwiając wykonywanie złożonych operacji za pomocą zwięzłego i czytelnego kodu. 
Aby to osiągnąć, wiele metod przyjmuje funkcje jako argumenty.

Kolekcje Scala są podzielone na dwie główne kategorie: kolekcje mutowalne i niemutowalne. 
Kolekcje niemutowalne nie mogą być zmieniane po stworzeniu, ale można je duplikować z modyfikacjami, podczas gdy kolekcje mutowalne pozwalają na aktualizacje w miejscu. 
Scala domyślnie zachęca do używania kolekcji niemutowalnych, ponieważ są one prostsze do zrozumienia i mogą zapobiegać błędom spowodowanym nieoczekiwanymi efektami ubocznymi.

Oto omówienie głównych cech i klas:
1. `Iterable`: Wszystkie kolekcje, które można przechodzić w liniowej kolejności, rozszerzają `Iterable`. Zawiera metody takie jak `iterator`, `map`, `flatMap`, `filter` i inne, które omówimy wkrótce.
2. `Seq`: Ta cecha reprezentuje sekwencje, tj. uporządkowane kolekcje elementów. Rozszerza `Iterable` i udostępnia metody takie jak `apply(index: Int): T` (która pozwala na dostęp do elementu pod określonym indeksem) oraz `indexOf(element: T): Int` (która zwraca indeks pierwszego wystąpienia w sekwencji, które odpowiada podanemu elementowi, lub -1, jeśli element nie został znaleziony). Niektóre istotne klasy implementujące cechę `Seq` to `List`, `Array`, `Vector` i `Queue`.
3. `Set`: Zbiory to nieuporządkowane kolekcje unikatowych elementów. Rozszerzają `Iterable`, ale nie `Seq`, co oznacza, że nie można przypisać stałych indeksów do ich elementów. Najczęściej stosowaną implementacją `Set` jest `HashSet`.
4. `Map`: Mapa to kolekcja par klucz-wartość. Rozszerza `Iterable` i udostępnia metody takie jak `get`, `keys`, `values`, `updated` i inne. Jest nieuporządkowana, podobnie jak `Set`. Najczęściej stosowaną implementacją `Map` jest `HashMap`.

Teraz szybko przejrzymy niektóre z najczęściej używanych metod kolekcji Scala: `filter`, `find`, `foreach`, `map`, `flatMap` i `foldLeft`. 
W każdym przypadku zobaczysz przykład kodu i zostaniesz poproszony o wykonanie ćwiczenia z użyciem danej metody. 
Należy zauważyć, że dostępnych jest wiele innych metod. Zachęcamy do zapoznania się z [dokumentacją kolekcji Scala](https://www.scala-lang.org/api/current/scala/collection/index.html) i przejrzenia ich. Świadomość ich istnienia i zrozumienie, że można ich użyć zamiast tworzenia własnej logiki, może zaoszczędzić znaczną ilość wysiłku.