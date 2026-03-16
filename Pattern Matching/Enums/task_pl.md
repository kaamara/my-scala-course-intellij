Enumeracja (lub enum) to typ, który reprezentuje skończony zbiór unikalnych wartości.  
Enumeracje są często używane, aby ograniczyć zbiór możliwych wartości dla pola,  
co poprawia czytelność i niezawodność kodu.  
Ponieważ pole nie może przyjąć wartości spoza małego zbioru dobrze znanych wartości,  
możemy mieć pewność, że implementowana logika obsługuje wszystkie możliwe opcje  
i że nie istnieją nieprzewidziane scenariusze.  

W Scali 3 enumeracje są tworzone przy użyciu słowa kluczowego `enum`.  
Każda wartość enuma jest obiektem *enumerowanego typu*.  
Enumy w Scalę 3 mogą również mieć parametryzowane wartości i metody.  
Już widziałeś to w poprzednich przykładach, gdzie wykorzystaliśmy enumy do zdefiniowania kolorów sierści kota:

```scala 3
enum Color:
  case White, Black, Ginger
```  

Jednak enumy w Scali 3 są jeszcze potężniejsze.  
W rzeczywistości są bardziej wszechstronne niż ich odpowiedniki w wielu innych językach programowania.  
Enumy w Scali 3 mogą również być używane jako algebraiczne typy danych  
(znane również jako hierarchie sealed trait w Scali 2).  
Możesz mieć enum z przypadkami, które przenoszą różne typy danych.  
Oto przykład:

```scala 3
enum Tree[+A]:
  case Branch(left: Tree[A], value: A, right: Tree[A])
  case Leaf(value: A)
  case Stump

/*
    3
   / \
  2   5
 /   / \
1   4   6
*/

import Tree.*

val tree: Tree[Int] =
  Branch(
    Branch(Leaf(1), 2, Stump),
    3,
    Branch(Leaf(4), 5, Leaf(6))
  )
```  

W tym przykładzie `Tree` to enum modelujący strukturę danych drzewa binarnego.  
Drzewa binarne są używane w wielu dziedzinach informatyki, w tym do sortowania, wyszukiwania i efektywnego dostępu do danych.  
Składają się z węzłów, z których każdy może mieć co najwyżej dwa poddrzewa.  
Tutaj implementujemy drzewo binarne za pomocą enuma `Tree[A]`, który pozwala węzłom drzewa  
przyjąć jedną z trzech możliwych form:  
* `Branch`, który zawiera wartość typu `A` i dwa poddrzewa: `left` i `right`,  
* `Leaf`, który posiada wartość typu `A`, ale nie ma poddrzew, lub  
* `Stump`, który modeluje koniec gałęzi po danej stronie.  

Zwróć uwagę, że nasza implementacja drzewa binarnego różni się nieco od klasycznej.  
Możesz zauważyć, że jest trochę nadmiarowa:  
`Leaf` jest w praktyce tym samym, co `Branch`, gdzie oba poddrzewa są pniakami (`Stump`).  
Jednak posiadanie `Leaf` jako oddzielnego przypadku w enumie pozwala nam pisać kod budujący  
drzewo w bardziej zwięzły sposób.  

## Zadanie  

Zaimplementuj funkcję sprawdzającą, czy drzewo jest zbalansowane.  

Zbalansowane drzewo binarne spełnia następujące warunki:  
* Bezwzględna różnica między wysokościami lewego i prawego poddrzewa w dowolnym węźle nie przekracza 1.  
* Dla każdego węzła jego lewe poddrzewo jest zbalansowanym drzewem binarnym.  
* Dla każdego węzła jego prawe poddrzewo jest zbalansowanym drzewem binarnym.  

Dla dodatkowego wyzwania spróbuj osiągnąć to w jednej iteracji.