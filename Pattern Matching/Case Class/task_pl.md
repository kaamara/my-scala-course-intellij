W języku Scala klasa przypadku (case class) to szczególny rodzaj klasy, który jest wyposażony w kilka przydatnych domyślnych zachowań i metod, korzystnych przy modelowaniu niezmiennych danych.  
Chociaż kompilator Scali nakłada na nie pewne ograniczenia, jednocześnie wzbogaca je o funkcje, które w innym przypadku musielibyśmy zaimplementować ręcznie:

1. Pola klasy przypadku są domyślnie niemodyfikowalne (immutable).  
   Aby to zmienić, muszą być jawnie oznaczone jako `var`, jednak taka praktyka jest postrzegana jako wysoce nieidiomatyczna w Scali.  
   Instancje klas przypadku powinny pełnić funkcję struktur danych niemodyfikowalnych,  
   ponieważ ich modyfikowanie może prowadzić do mniej intuicyjnego i czytelnego kodu.  
2. Klasa przypadku udostępnia domyślny konstruktor z publicznymi, tylko do odczytu parametrami, co redukuje kod szablonowy związany z jej tworzeniem.  
3. Scala automatycznie definiuje kilka przydatnych metod dla klas przypadków, takich jak `toString`, `hashCode` i `equals`.  
   Metoda `toString` zwraca tekstową reprezentację obiektu,  
   `hashCode` jest wykorzystywana przy kolekcjach takich jak `HashSet` i `HashMap`,  
   a `equals` sprawdza równość strukturalną zamiast równości referencyjnej.  
   Innymi słowy, metoda ta weryfikuje równość odpowiednich pól klasy przypadków,  
   zamiast sprawdzać, czy oba odwołania wskazują ten sam obiekt.  
4. Klasy przypadku mają metodę `copy`, którą można użyć do stworzenia kopii instancji klasy przypadków.  
   Kopia ta może być identyczna z oryginałem lub z niektórymi zmodyfikowanymi parametrami  
   (sygnatura metody `copy` odzwierciedla sygnaturę domyślnego konstruktora).  
5. Scala automatycznie tworzy obiekt towarzyszący (companion object) dla klasy przypadków,  
   który zawiera metody fabryczne `apply` i `unapply`.  
   Metoda `apply` odpowiada domyślnemu konstruktorowi i umożliwia tworzenie instancji klasy bez użycia słowa kluczowego `new`.  
   Z kolei metoda `unapply` jest wykorzystywana w dopasowywaniu wzorców.  
6. Klasy przypadków można wygodnie używać w dopasowaniu wzorców, ponieważ mają domyślną metodę `unapply`,  
   co pozwala na rozkładanie instancji klasy przypadków.  
7. Poza tym klasy przypadków nie są zwykle rozszerzane.  
   Mogą rozszerzać cechy (traits) i inne klasy, ale nie powinny być używane jako klasy nadrzędne dla innych klas.  
   Technicznie jednak rozszerzanie klas przypadków nie jest domyślnie zabronione.  
   Jeśli chcesz mieć pewność, że klasa przypadków nie będzie rozszerzana, oznacz ją słowem kluczowym `final`.  

Powinieneś być już zaznajomiony z niektórymi z tych funkcji, ponieważ korzystaliśmy z nich w poprzednim module.  
Tym razem jednak chcemy, abyś skupił się na wyróżnionych aspektach, które zobaczysz w przykładach i ćwiczeniach.

Poniżej znajduje się prosty przykład klasy przypadków modelującej koty.  
Tworzymy instancję `Cat` o nazwie `myCat`, a następnie używamy dopasowywania wzorców na `Cat`, aby uzyskać dostęp do jej nazwy i koloru.  

```scala 3
case class Cat(name: String, color: String)

val myCat = Cat("Whiskers", "white")
myCat match {
  case Cat(name, color) => println(s"I have a $color cat named $name.")
}
```

## Ćwiczenie 

Utwórz klasę przypadku, która reprezentuje psa.  
Każdy pies powinien mieć imię, rasę i ulubioną zabawkę.  
Na chwilę obecną modeluj te cechy jako ciągi znaków (String).  
Użyj dopasowywania wzorców, aby przedstawić psa.