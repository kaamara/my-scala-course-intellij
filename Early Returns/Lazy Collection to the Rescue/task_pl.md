Jeszcze jednym sposobem osiągnięcia tego samego efektu, co wcześniejszy zwrot wyjścia, jest użycie koncepcji leniwej kolekcji.  
Leniwa kolekcja nie przechowuje wszystkich swoich elementów jako obliczonych i gotowych do dostępu.  
Zamiast tego przechowuje sposób na obliczenie elementu dopiero wtedy, gdy jest on potrzebny.  
Dzięki temu możliwe jest po prostu przeglądanie kolekcji aż do momentu, gdy napotkamy element spełniający określone warunki.  
Ponieważ nie jesteśmy zainteresowani resztą kolekcji, te elementy nie zostaną obliczone.  

Jak już widzieliśmy kilka modułów temu, istnieje kilka sposobów na przekonwertowanie kolekcji na leniwą.  
Pierwszym z nich jest użycie [iteratorów](https://www.scala-lang.org/api/current/scala/collection/Iterator.html): możemy wywołać metodę `iterator` na naszej sekwencji identyfikatorów.  
Innym sposobem jest użycie [widoków](https://www.scala-lang.org/api/current/scala/collection/View.html), co zrobiliśmy w jednym z poprzednich modułów.  
Spróbuj samodzielnie porównać te dwa podejścia.

```scala 3
  def findFirstValidUser9(userIds: Seq[UserId]): Option[UserData] =
    userIds
      .iterator
      .map(safeComplexConversion)
      .find(_.exists(complexValidation))
      .flatten
```

## Ćwiczenie

Spróbujmy użyć leniwej kolekcji, aby osiągnąć ten sam cel, co w poprzednich zadaniach.

* Użyj leniwej kolekcji do zaimplementowania `findFirstValidCat`.
* Skopiuj implementacje `furCharacteristicValidation` oraz `nonAdoptedCatConversion` z poprzedniego zadania.