```scala
def map[B](f: A => B): Iterable[B]
```

Metoda `map` działa na dowolnej kolekcji w Scali, która implementuje `Iterable`. 
Przyjmuje funkcję `f` i stosuje ją do każdego elementu w kolekcji, podobnie jak `foreach`. Jednak w przypadku `map` bardziej interesują nas wyniki funkcji `f` niż jej efekty uboczne. 
Jak widać w deklaracji funkcji `f`, przyjmuje ona element oryginalnej kolekcji typu `A` i zwraca nowy element typu `B`. 
Na końcu metoda `map` zwraca nową kolekcję elementów typu `B`.
W szczególnym przypadku typ `B` może być taki sam jak typ `A`. Na przykład, moglibyśmy użyć metody `map`, aby wziąć kolekcję kotów w określonych kolorach i stworzyć nową kolekcję kotów w innych kolorach. 
Ale moglibyśmy również wziąć kolekcję kotów i stworzyć kolekcję samochodów o kolorach zgodnych z kolorami naszych kotów.

```scala
// Definiujemy wyliczenie Color (kolor)
enum Color:
 case Black, White, Ginger

// Importujemy wartości z wyliczenia Color dla lepszej czytelności
import Color._

// Definiujemy klasę przypadków Cat (kot) z dwoma polami: name (imię) i color (kolor)
case class Cat(name: String, color: Color)

// Definiujemy klasę przypadków Car (samochód) z jednym polem: color (kolor)
case class Car(color: Color)

// Tworzymy cztery koty: dwa czarne, jednego białego i jednego rudego
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// i umieszczamy je wszystkie na liście
val cats: List[Cat] = Set(felix, snowball, garfield, shadow)

// Definiujemy funkcję catToCar, która dla danego kota tworzy samochód w tym samym kolorze
def catToCar(cat: Cat): Car = Car(color = cat.color)

// Korzystając z metody map i funkcji catToCar, tworzymy nową listę samochodów w tych samych kolorach co nasze koty
val cars: List[Car] = cats.map(catToCar)
```

Należy zauważyć, że w tym przykładzie umieściliśmy nasze koty w `List`, a nie w `Set`. 
Robimy to, aby uniknąć nieporozumień — dla każdego kota chcemy samochód w dopasowanym kolorze. 
Ponieważ jednak mamy dwa czarne koty o tej samej barwie, `Felix` i `Shadow`, nasza funkcja `catToCar` stworzy dwa identyczne czarne samochody. Ponieważ `Set` to kolekcja przechowująca jedynie unikalne elementy, jeden z nich musiałby zostać usunięty. 
Dlatego zamiast `Set` potrzebujemy kolekcji, która pozwala na wiele identycznych elementów, takich jak `List`.

## Ćwiczenie

W programowaniu funkcyjnym zwykle oddzielamy efekty uboczne od obliczeń. 
Na przykład, jeśli chcemy wydrukować wszystkie cechy futra kota, najpierw przekształcamy każdą cechę w `String`, a następnie wypisujemy każdą z nich w osobnym kroku. 
Zaimplementuj funkcję `furCharacteristicsDescription`, która wykona to przekształcenie za pomocą metody `map`.