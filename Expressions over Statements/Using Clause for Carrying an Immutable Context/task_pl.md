Podczas pisania czystych funkcji często przenosimy jakiś niezmienny kontekst, taki jak konfiguracje, jako dodatkowe argumenty. 
Typowym przykładem jest sytuacja, gdy funkcja oczekuje określonego komparatora obiektów, na przykład przy obliczaniu maksymalnej wartości lub sortowaniu: 

```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

class IntComparator extends Comparator[Int]:
  def compare(x: Int, y: Int): Int = x - y

def max[A](x: A, y: A, comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0 then x
  else y

@main
def main() =
  println(s"${max(13, 42, IntComparator())}")
```

Tutaj mamy cechę `Comparator`, aby określić metodę porównywania wartości. 
Za każdym razem, gdy chcemy wywołać funkcję `max`, musimy dostarczyć określony `Comparator`. 
Jednak zazwyczaj dla danego typu istnieje tylko jeden sensowny komparator, a przekazywanie dodatkowego argumentu przeszkadza
w czytelności. 
Scala rozwiązuje ten problem za pomocą klauzuli `using`, znanej również jako parametr kontekstowy, wraz z tzw. `given`s. 

```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

object Comparator:
  given Comparator[Int] with
    def compare(x: Int, y: Int): Int =
      x - y
end Comparator

def max[A](x: A, y: A)(using comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0 then x
  else y

@main
def main() =
  println(s"${max(13, 42)}")
```

Oznaczając argument za pomocą `using` w Scala 3 lub `implicit` w Scala 2, mówimy kompilatorowi, aby znalazł odpowiednią 
wartość na podstawie jej typu spośród tych oznaczonych jako `given`. 
To oznaczenie stosuje się, gdy istnieje jedna kanoniczna wartość dla danego typu, jak np. w przypadku porównywania liczb całkowitych. 
Jeśli kompilator nie znajdzie wartości `given` dla potrzebnego typu, zgłosi błąd. 
Więcej na temat tego, jak kompilator szuka `given`, możesz przeczytać [w tej odpowiedzi na StackOverflow](https://stackoverflow.com/questions/5598085/where-does-scala-look-for-implicits/5598107#5598107).

W niektórych przypadkach istnieją dwie lub więcej sensownych implementacji `Comparator`.  
Na przykład `String`i można porównywać leksykograficznie (`"aa"` jest przed `"b"`, które jest przed `"bb"`) lub przede wszystkim 
na podstawie ich długości, a następnie leksykograficznie (`"b"` jest przed `"aa"`, które jest przed `"bb"`). 
Nawet gdy używany jest parametr kontekstowy, nadal możesz użyć swojej własnej wartości zamiast `given`. Jedyną rzeczą,
którą musisz zrobić, jest jawne przekazanie jej jako argumentu i oznaczenie go słowem kluczowym `using` podczas wywoływania funkcji: 

```scala 3
trait Comparator[A]:
  def compare(x: A, y: A): Int

object Comparator:
  given Comparator[Int] with
    def compare(x: Int, y: Int): Int =
      x - y
  given Comparator[String] with
    def compare(x: String, y: String): Int =
      x.compareTo(y)
end Comparator

object StringLengthComparator extends Comparator[String]:
  def compare(x: String, y: String): Int =
    if x.length != y.length then y.length - x.length
    else x.compareTo(y)

def max[A](x: A, y: A)(using comparator: Comparator[A]): A =
  if comparator.compare(x,y) >= 0
    then x
  else y

@main
def main() =
  println(s"${max("b", "aa")}")  // wypisuje b
  println(s"${max("b", "aa")(using StringLengthComparator)}") // wypisuje aa
```

## Ćwiczenie 

Zaimplementuj funkcję `sort`, aby posortować tablicę wartości na podstawie przekazanego `Comparator`. Użyj parametru kontekstowego, 
aby nie przenosić niezmiennego kontekstu. Możesz użyć dowolnego algorytmu sortowania.