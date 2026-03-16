W programowaniu funkcyjnym dane rzadko muszą być mutowalne.  
Teoretycznie możliwe jest całkowite unikanie mutowalności, zwłaszcza w takich językach programowania jak Haskell.  
Jednak dla wielu programistów takie podejście może wydawać się uciążliwe i niepotrzebnie skomplikowane.  
Na szczęście można połączyć zalety obu światów, korzystając z języków, które łączą programowanie funkcyjne i imperatywne.  
W szczególności Scala została zaprojektowana właśnie z myślą o takiej fuzji.  

Wzorzec Berliner to wzorzec architektoniczny, wprowadzony przez Billa Vennersa i Franka Sommersa podczas Scala Days 2018 w Berlinie.  
Jego celem jest ograniczenie mutowalności wyłącznie do tych części programu, w których jest to nieuniknione.  
Aplikację można podzielić na trzy warstwy:  

* Zewnętrzna warstwa, która musi współdziałać ze światem zewnętrznym,  
  umożliwiając aplikacji komunikację z innymi programami, usługami lub systemem operacyjnym.  
  W praktyce niemożliwe jest zaimplementowanie tej warstwy w sposób całkowicie funkcyjny,  
  jednak dobra wiadomość jest taka, że nie ma takiej potrzeby.  
* Wewnętrzna warstwa, gdzie łączymy się z bazami danych lub zapisujemy pliki.  
  Ta część aplikacji jest zwykle krytyczna pod względem wydajności,  
  więc naturalnym rozwiązaniem jest użycie mutowalnych struktur danych.  
* Środkowa warstwa, która łączy dwie poprzednie.  
  To tutaj mieści się nasza logika biznesowa i tu programowanie funkcyjne może błyszczeć.  

Ograniczanie mutowalności do cienkich warstw wewnętrznej i zewnętrznej oferuje wiele korzyści.  
Przede wszystkim, im bardziej ograniczamy dane, tym bardziej odporne na przyszłe zmiany staje się nasze oprogramowanie.  
Nie tylko dostarczamy więcej informacji do kompilatora, ale także sygnalizujemy przyszłym programistom, że pewne dane nie powinny być modyfikowane.  

Po drugie, upraszcza to pisanie kodu współbieżnego.  
Kiedy wiele wątków może modyfikować te same dane, szybko możemy znaleźć się w nieprawidłowym stanie,  
co utrudnia debugowanie.  
Nie ma potrzeby używania muteksów, monitorów czy innych podobnych wzorców, jeśli nie ma faktycznej możliwości modyfikowania danych.  

Na koniec, powszechnym wzorcem w programowaniu imperatywnym z użyciem mutowalnych danych  
jest najpierw przypisanie jakiejś domyślnej wartości do zmiennej, a następnie jej modyfikowanie.  
Na przykład możesz zacząć od pustej kolekcji, a następnie wypełnić ją określonymi wartościami.  
Jednak domyślne wartości są złe.  
Programiści często zapominają zmienić je na coś znaczącego, co prowadzi do wielu błędów, takich jak  
„błąd wart miliard dolarów” spowodowany użyciem `null`.  

Zachęcamy do zapoznania się z tym wzorcem, oglądając [oryginalne wideo](https://www.youtube.com/watch?v=DhNw60hxCeY).

## Ćwiczenie  

Udostępniamy przykład implementacji aplikacji obsługującej tworzenie, modyfikowanie i usuwanie użytkowników w bazie danych.  
Symulujemy warstwy bazy danych i HTTP, a Twoim zadaniem jest zaimplementowanie metod przetwarzania żądań zgodnie ze wzorcem Berliner.  

Rozpocznij od zaimplementowania metod `onNewUser` i `onVerification` w pliku `BerlinerPatternTask.scala`.  
Dostarczamy implementacje bazy danych i klienta dla tych metod, abyś mógł zapoznać się z aplikacją.  
Uruchom skrypt `run` w pliku `HttpClient.scala`, aby upewnić się, że Twoja implementacja działa poprawnie.  

Następnie zaimplementuj funkcjonalność związaną ze zmianą hasła i usuwaniem użytkowników.  
Będziesz musiał zaimplementować wszystkie warstwy dotyczące tych metod, więc sprawdź pliki  
`Database.scala` i `HttpClient.scala`.  
Nie zapomnij odkomentować kilku ostatnich linii w skrypcie `run` dla tego zadania.