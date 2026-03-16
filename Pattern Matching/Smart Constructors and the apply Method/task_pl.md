W Scali metoda `apply` jest specjalną metodą, którą można wywoływać bez podawania jej nazwy.

```scala 3
class Cat:
  def apply(): String = "meow"

  val cat = Cat()
  cat() // zwraca "meow"
```

Technicznie rzecz biorąc, to wszystko na ten temat — możesz zaimplementować `apply` w dowolny sposób, do dowolnego celu.  
Jednak zgodnie z konwencją `apply` jest najczęściej używane jako inteligentny konstruktor.  
Ta konwencja jest bardzo istotna i zdecydowanie zalecamy jej przestrzeganie.  

Istnieje kilka innych zastosowań `apply`.  
Na przykład biblioteka kolekcji Scali często korzysta z tej metody do pobierania danych z kolekcji. To użycie może wyglądać tak,  
jakby Scala zastąpiła nawiasy kwadratowe, popularne w bardziej tradycyjnych językach, nawiasami:

```scala 3
val entry1 = listOfEntries(5) // listOfEntries: List[Entry]
val entry2 = listOfEntries.apply(5) // to jest to samo co powyżej
``` 

To zastosowanie jest na tyle popularne, że ludzie je rozumieją, gdy je widzą. Jeśli jednak spróbujesz użyć tej metody w zupełnie inny sposób,  
możesz sprawić, że Twój kod będzie trudniejszy do odczytania przez innych programistów Scali.  
Domyślnym oczekiwaniem jest, że para nawiasów po nazwie oznacza wywołanie inteligentnego konstruktora.  
Inteligentny konstruktor to wzorzec projektowy często stosowany w językach funkcyjnych.  
Jego głównym celem jest enkapsulowanie logiki tworzenia obiektu, co pozwala na wymuszenie pewnych ograniczeń lub reguł  
za każdym razem, gdy tworzona jest instancja klasy.  
Na przykład można go użyć, aby upewnić się, że obiekt jest zawsze tworzony w prawidłowym stanie.

Ten wzorzec jest szczególnie przydatny w sytuacjach, gdy:
* Konstrukcja obiektu jest złożona i wymaga abstrakcji.  
* Chcesz kontrolować sposób tworzenia obiektów i upewnić się, że zawsze znajdują się w prawidłowym stanie.  
* Musisz wymusić określony protokół przy tworzeniu obiektów, na przykład buforowanie obiektów,  
  tworzenie singletonów lub generowanie obiektów za pomocą fabryki.  

Idiomatyczny sposób wykorzystania `apply` jako inteligentnego konstruktora polega na umieszczeniu go w obiekcie towarzyszącym klasy  
i wywołaniu go, używając nazwy klasy, a następnie pary nawiasów.  
Na przykład rozważmy ponownie klasę `Cat`, która posiada obiekt towarzyszący z metodą `apply`:

```scala 3
class Cat private (val name: String, val age: Int)

object Cat:
  def apply(name: String, age: Int): Cat =
    if (age < 0) new Cat(name, 0)
    else new Cat(name, age)

  val fluffy = Cat("Fluffy", -5) // wiek Fluffy zostanie ustawiony na 0, a nie -5
```

Klasa `Cat` ma główny konstruktor, który przyjmuje `String` i `Int`, aby ustawić odpowiednio nazwę i wiek nowego kota.  
Dodatkowo tworzymy obiekt towarzyszący i definiujemy w nim metodę `apply`.  
W ten sposób, później wywołując `Cat("Fluffy", -5)`, zostanie wywołana metoda `apply`, a nie główny konstruktor.  
W metodzie `apply` sprawdzamy podany wiek kota i, jeśli jest on mniejszy od zera, tworzymy instancję kota  
z ustawionym wiekiem na zero, zamiast używać podanego wieku.

Zauważ również, jak odróżniamy wywołanie głównego konstruktora od metody `apply`.  
Gdy wywołujemy `Cat("Fluffy", -5)`, kompilator Scala 3 sprawdza, czy istnieje pasująca metoda `apply`.  
Jeśli tak, wywoływana jest metoda `apply`.  
W przeciwnym razie Scala 3 wywołuje główny konstruktor (jeśli podpis również pasuje).  
To sprawia, że metoda `apply` jest przejrzysta dla użytkownika.  
Jeśli musisz jawnie wywołać główny konstruktor, pomijając metodę `apply`, możesz użyć słowa kluczowego `new`,  
na przykład `new Cat(name, age)`.  
Stosujemy ten zabieg w podanym przykładzie, aby uniknąć nieskończonej rekurencji — gdybyśmy tego nie zrobili,  
wywołanie `Cat(name, age)` lub `Cat(name, 0)` ponownie uruchomiłoby metodę `apply`.

Możesz się zastanawiać, jak zapobiec obejściu przez użytkowników naszej metody `apply`, wywołując główny konstruktor `new Cat("Fluffy", -5)`.  
Zwróć uwagę, że w pierwszej linii przykładu, gdzie definiujemy klasę `Cat`,  
pomiędzy nazwą klasy a nawiasami znajduje się słowo kluczowe `private`.  
Słowo kluczowe `private` w tej pozycji oznacza, że główny konstruktor klasy `Cat` może być wywoływany wyłącznie przez  
metody zdefiniowane w tej klasie lub jej obiekcie towarzyszącym.  
Dzięki temu nadal możemy używać `new Cat(name, age)` w metodzie `apply`, ponieważ znajduje się ona w obiekcie towarzyszącym,  
ale jest niedostępna dla użytkownika.

## Ćwiczenie

Rozważ klasę `Dog`, która zawiera pola `name`, `breed` i `owner`.  
Czasami pies się zgubi, a osoba, która go znajduje, może znać jedynie jego imię z obroży.  
Dopóki nie zostanie odczytany mikroczip, nie ma sposobu na ustalenie, kto jest właścicielem psa ani jaka jest jego rasa.  
Aby umożliwić tworzenie instancji klasy `Dog` w takich sytuacjach, warto użyć inteligentnego konstruktora.  
Potencjalnie nieznane pola `breed` i `owner` reprezentujemy jako `Option[String]`.  
Zaimplementuj inteligentny konstruktor, który używa `defaultBreed` i `defaultOwner`, aby zainicjalizować odpowiednie pola.