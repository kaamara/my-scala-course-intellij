Możemy przekazać nazwaną funkcję jako argument do innej funkcji, tak samo, jak przekazalibyśmy dowolną inną wartość. 
Jest to przydatne na przykład wtedy, gdy chcemy manipulować danymi w kolekcji. 
Scala oferuje wiele metod w klasach kolekcji, które operują poprzez akceptowanie funkcji jako argumentu i stosowanie jej w jakiś sposób do każdego elementu kolekcji. 
W poprzednim rozdziale zobaczyliśmy, jak można użyć metody `map` na sekwencji liczb, aby je podwoić. 
Teraz spróbujmy zrobić coś innego. 
Wyobraź sobie, że masz worek kotów w różnych kolorach i chcesz oddzielić jedynie czarne koty.

```scala
// Kolory modelujemy jako enumeracje.
enum Color:
 case Black
 case White
 case Ginger

// Kota modelujemy jako klasę. W tym przykładzie interesuje nas jedynie kolor kota.
class Cat(val color: Color)

// Tworzymy nasz worek (zbiór) kotów. Każdy kot ma inny kolor.
val bagOfCats = Set(Cat(Color.Black), Cat(Color.White), Cat(Color.Ginger))

// Używamy metody `filter`, aby stworzyć nowy worek czarnych kotów.  
val bagOfBlackCats = bagOfCats.filter(cat => cat.color == Color.Black)
```

W Scali 3 możemy użyć enumeracji, aby zdefiniować kolory. 
Następnie tworzymy klasę `Cat`, która zawiera wartość reprezentującą kolor kota. Kolejnym krokiem jest utworzenie "worka" kotów, który jest zbiorem zawierającym trzy koty: jednego czarnego, jednego białego i jednego rudego. 
Na końcu używamy metody `filter` i przekazujemy jej anonimową funkcję jako argument. Ta funkcja przyjmuje argument klasy `Cat` i zwraca `true`, jeśli kolor kota jest czarny. 
Metoda `filter` zastosuje tę funkcję do każdego kota w oryginalnym zbiorze i stworzy nowy zbiór zawierający tylko te koty, dla których funkcja zwróci `true`.

Jednak nasza funkcja sprawdzająca, czy kot jest czarny, nie musi być anonimowa. Metoda `filter` zadziała równie dobrze z nazwaną funkcją.

```scala
def isCatBlack(cat: Cat): Boolean = cat.color == Color.Black
val bagOfBlackCats = bagOfCats.filter(isCatBlack)
```

Przekazywanie funkcji jako argumentu do metody (lub innej funkcji) może być przydatne, gdy chcemy zastosować tę samą logikę do wszystkich elementów w kolekcji, strumieniu lub dowolnym innym obiekcie, na którym metoda operuje. Takie podejście pozwala na tworzenie bardziej wielokrotnego użytku i modularnego kodu.

Jak dotąd widziałeś przykłady wykorzystania tego z metodami `map` i `filter` — dwiema metodami z kolekcji Scali. W nadchodzących rozdziałach omówimy inne metody, które można wywoływać w podobny sposób, ale wykonujące różne operacje.

## Ćwiczenie 

Zaimplementuj funkcję sprawdzającą, czy kot jest biały lub rudy, i przekaż ją jako argument do metody `filter`, aby stworzyć worek zawierający jedynie białe lub rude koty.