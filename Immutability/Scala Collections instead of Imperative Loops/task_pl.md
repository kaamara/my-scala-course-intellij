W stylu programowania imperatywnego często można spotkać następujący schemat: zmiennej początkowo przypisuje się jakąś domyślną wartość, taką jak pusta kolekcja, pusty ciąg tekstowy, zero lub null.  
Następnie kod inicjalizacyjny wykonuje się krok po kroku w pętli, aby stworzyć właściwą wartość.  
Po tym procesie wartość przypisana do zmiennej zazwyczaj nie ulega już zmianie — a jeśli tak się dzieje, odbywa się to w sposób, który można zastąpić zresetowaniem zmiennej do jej domyślnej wartości i ponownym uruchomieniem inicjalizacji.  
Jednak potencjał do modyfikacji pozostaje, mimo swojej zbędności.  
Przez cały czas działania programu wisi to jak luźny koniec kabla elektrycznego, kusząc, by ktoś go dotknął.

Programowanie funkcyjne, z kolei, pozwala na tworzenie użytecznych wartości bez potrzeby stosowania początkowych wartości domyślnych lub tymczasowej zmienności.  
Nawet bardzo złożona struktura danych może zostać obliczona w całości za pomocą funkcji wyższego rzędu, zanim zostanie przypisana do stałej, co zapobiega późniejszym modyfikacjom.  
Jeśli potrzebujemy zaktualizowanej wersji, możemy stworzyć nową strukturę danych zamiast modyfikować starą.

Scala oferuje bogatą bibliotekę kolekcji — `Array`, `List`, `Vector`, `Set`, `Map` i wiele innych — oraz metody do manipulacji tymi kolekcjami i ich elementami.  
Niektóre z tych metod poznałeś już w pierwszym rozdziale.  
W tym rozdziale dowiesz się więcej o tym, jak unikać zmienności i korzystać z niezmienności, aby pisać bezpieczniejszy, a czasem nawet bardziej wydajny kod.