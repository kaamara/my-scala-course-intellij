*Aby zrozumieć rekurencję, trzeba najpierw zrozumieć rekurencję*  

Ten temat powinien być znany każdemu, kto zgłębił programowanie funkcyjne, ale chcielibyśmy go raz jeszcze omówić.  
Istota rekurencji polega na tym, że funkcja wywołuje samą siebie.  
Na pierwszy rzut oka takie podejście może wydawać się nietypowe, ale z czasem i ćwiczeniami staje się ono coraz bardziej naturalne.  
Rozważmy problem znalezienia klucza w pudełku. Jednak pudełko może zawierać inne pudełka, które również mogą zawierać jeszcze inne pudełka i tak dalej, a klucz znajduje się w jednym z tych pudełek, ale nie wiadomo konkretnie w którym.  
Aby rozwiązać taki problem bez użycia rekurencji, zazwyczaj używa się pętli:

```scala 3
// Modelujemy zawartość pudełka jako zbiór elementów (Item), które mogą być innymi pudełkami lub kluczami
sealed trait Item
case class Box(content: Set[Item]) extends Item
case class Key(id: String) extends Item

def lookForKey(box: Box): Option[Key] =
  // tworzymy zmienną mutowalną do przechowywania stosu elementów do przeszukiwania
  var pile = box.content
  while pile.nonEmpty do
    // wybieramy jeden element ze stosu
    val item = pile.head
    item match
      case key: Key => 
        // znaleziono klucz
        return Some(key)
      case box: Box => 
        // usuwamy obecny element ze stosu i dodajemy jego zawartość do dalszego przeszukiwania
        pile = pile - box ++ box.content
  // klucza nie znaleziono
  None 

@main
def main() =
  val box = Box(Set(Box(Set(Box(Set.empty), Box(Set.empty))), Box(Set(Key(), Box(Set.empty)))))
  println(lookForKey(box))
```

To rozwiązanie jest poprawne, ale wydaje się niewygodne.  
Musimy stworzyć zmienną mutowalną, aby przechowywać stos elementów wewnątrz pudełka.  
Musimy również pamiętać o usunięciu przeszukiwanego pudełka ze stosu przed dodaniem jego zawartości.  
Dodatkowo musimy zwrócić `None` na końcu, po pętli `while`: na szczęście kompilator zgłosi błąd, jeśli o tym zapomnimy.  
Ogólnie rzecz biorąc, łatwo jest popełnić błąd przy pisaniu tej funkcji.

Alternatywnie możemy rozważyć, co oznacza znalezienie klucza w pudełku wypełnionym innymi pudełkami i kluczami.  
Zaglądamy do pudełka, wybieramy jeden element wewnątrz, i jeśli jest to pudełko, po prostu kontynuujemy poszukiwanie klucza w jego zawartości — dokładnie tak jak to robimy teraz.  
Jest to właśnie przypadek, w którym funkcja wywołuje samą siebie.  
Porównajmy implementację rekurencyjną z nierekurencyjną:

```scala 3
def lookForKey(box: Box): Option[Key] =
  def go(item: Any): Option[Key] =
    item match
      case key: Key => Some(key)
      case box: Box =>
        // procesujemy każdy element w pudełku rekurencyjnie i wybieramy pierwszy klucz w wyniku, jeśli istnieje
        box.content.flatMap(go).headOption
  go(box)
```

W tym przypadku po prostu przechodzimy przez każdy element w pudełku, stosujemy funkcję rekurencyjną `go` na każdym z nich, a potem wybieramy pierwszy klucz wynikowy.  
Zauważ, że nie mamy już żadnych zmiennych mutowalnych, nasz program jest znacznie bardziej czytelny i istnieje mniej możliwości popełnienia błędu.  
Ktoś mógłby zauważyć, że ten kod nie jest optymalny, ponieważ `flatMap` przejdzie przez całe pudełko, nawet jeśli klucz zostanie znaleziony wcześniej — i to jest uzasadnione zastrzeżenie.  
Istnieje wiele sposobów na rozwiązanie tego typu problemu wydajnościowego.  
Jednym z nich byłoby użycie leniwych kolekcji lub tzw. "views", a inny poznamy w jednym z kolejnych modułów, gdy będziemy omawiać wczesne wyjścia z funkcji.  

Jeśli trudno Ci myśleć rekurencyjnie, rozważ następujące podejście w dwóch krokach:  
1. Jeśli dany przypadek problemu da się rozwiązać bezpośrednio, rozwiąż go bezpośrednio.  
2. W przeciwnym razie zredukuj go do jednej lub więcej *prostszego przypadku tego samego problemu*.  

W naszym przykładzie z pudełkami, jeśli obecny element to klucz, rozwiązaliśmy problem i wystarczy zwrócić klucz.  
To jest przypadek bazowy naszego problemu, najmniejszy przypadek problemu.  
W przeciwnym razie mamy *mniejsze* przypadki tego samego problemu: inne pudełka, w których możemy spróbować znaleźć klucz.  
Gdy przeszukamy pudełka rekurencyjnie, możemy być pewni, że jeśli klucz istnieje, został znaleziony.  
Ostatecznie pozostaje postprzetwarzanie wyników rekurencyjnych wywołań — w naszym przypadku jest to uzyskanie pierwszego znalezionego klucza za pomocą `headOption`.  

Rekurencja najlepiej nadaje się do pracy z rekursywnymi strukturami danych, które są bardzo powszechne w programowaniu funkcyjnym.  
Listy, drzewa i inne struktury algebraiczne to obszary, w których rekurencja znajduje naturalne zastosowanie.  
Przyjrzyjmy się rekurencyjnej funkcji, która oblicza sumę wartości w węzłach drzewa binarnego:

```scala 3
enum Tree:
  case Node(val payload: Int, val left: Tree, val right: Tree)
  case Leaf(val payload: Int)

import Tree.*

def sumTree(tree: Tree): Int =
  tree match
    case Leaf(payload) => payload
    case Node(payload, left, right) =>
      payload + sumTree(left) + sumTree(right)

@main
def main() =
  /**
   *     4
   *    / \
   *   2  5
   *  / \
   * 1  3
   */
  val tree = Node(4, Node(2, Leaf(1), Leaf(3)), Leaf(5))
  println(sumTree(tree))
```

Zauważ, że wywołujemy `sumTree` rekurencyjnie za każdym razem, gdy `Tree` znajduje się wewnątrz węzła `Tree`.  
Rekurencja w typie danych wskazuje nam *mniejszy* przypadek problemu do rozwiązania.  
Następnie sumujemy wartości zwrócone z rekurencyjnych wywołań, uzyskując ostateczny wynik.  
W liściu (`Leaf`) nie ma `Tree`, więc wiemy, że jest to przypadek bazowy i problem można rozwiązać od razu.

## Ćwiczenie  

Zaimplementuj mały kalkulator `eval` oraz printer `prefixPrinter` dla wyrażeń arytmetycznych.  
Wyrażenie przedstawiamy jako abstrakcyjne drzewo składniowe, gdzie liście zawierają liczby, a węzły odpowiadają operatorom binarnym stosowanym do podwyrażeń.  
Na przykład drzewo `Node(Mult, Node(Plus, Leaf(1), Leaf(3)), Leaf(5))` odpowiada wyrażeniu `(1+3)*5`.  
Printer powinien przekształcić wyrażenie w formę prefiksową, w której operator pojawia się jako pierwszy, następnie lewy operand, a potem prawy operand.  
Nasze drzewo powinno zostać wydrukowane jako `* + 1 3 5`.