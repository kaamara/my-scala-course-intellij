```def filter(pred: A => Boolean): Iterable[A]```

Metoda `filter` działa na dowolnej kolekcji w Scali, która implementuje `Iterable`. 
Przyjmuje predykat, który zwraca `true` lub `false` dla każdego elementu w kolekcji, i produkuje nową 
kolekcję, zawierającą jedynie te elementy, dla których predykat zwraca `true`.
Metoda `filter` zwraca pustą kolekcję, jeśli predykat nie spełni warunku dla żadnego elementu.

Już wcześniej używaliśmy `filter` w poprzednim przykładzie, ale dla spójności przyjrzyjmy się jeszcze raz przykładowi poniżej.

```scala
// Definiujemy enum Color
enum Color:
 case Black, White, Ginger

// Definiujemy klasę przypadków Cat z dwoma polami: name i color
case class Cat(name: String, color: Color)

// Importujemy wartości enum Color dla lepszej czytelności
import Color._

// Tworzymy cztery koty: dwa czarne, jednego białego i jednego rudego
val felix    = Cat("Felix", Black)
val snowball = Cat("Snowball", White)
val garfield = Cat("Garfield", Ginger)
val shadow   = Cat("Shadow", Black)

// Umieszczamy je wszystkie w zbiorze
val cats = Set(felix, snowball, garfield, shadow)

// Filtrujemy zbiór, aby pozostawić tylko czarne koty
val blackCats = cats.filter { cat => cat.color == Black }

```


## Ćwiczenie

W ćwiczeniach będziemy korzystać z bardziej szczegółowego przedstawienia kotów niż w lekcjach. 
Zapoznaj się z klasą `Cat` w pliku `src/Cat.scala`.
Kot posiada wiele cech: imię, rasę, wzór umaszczenia oraz zestaw dodatkowych cech sierści, takich jak 
`Fluffy` czy `SleekHaired`.
Zapoznaj się z odpowiednimi definicjami w innych plikach w `src/`.

Wyobraź sobie, że odwiedzasz schronisko dla zwierząt z zamiarem adopcji kota. 
Dostępnych jest wiele kotów, a Ty chcesz zaadoptować kota o jednej z poniższych cech:

* Kot jest trójkolorowy (calico).
* Kot jest puchaty.
* Kot należy do rasy abisyńskiej.

Aby uprościć proces podejmowania decyzji, najpierw zidentyfikuj wszystkie koty, które posiadają przynajmniej jedną z powyższych cech. Twoim zadaniem jest zaimplementowanie niezbędnych funkcji, a następnie zastosowanie filtru.