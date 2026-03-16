Podobnie jak w Javie i innych popularnych językach, Scala zapewnia sposób na przerwanie pętli.  
Od wersji Scala 3.3 jest to osiągane przez kompozycję `boundary` i `break`, co stanowi czystsze rozwiązanie w porównaniu do nielokalnych zwrotów (`non-local returns`).  
Dzięki tej funkcji, kontekst obliczeniowy jest ustanawiany za pomocą `boundary:`, a `break` zwraca wartość z wnętrza otaczającego `boundary`.  
Sprawdź [implementację](https://github.com/scala/scala3/blob/3.3.0/library/src/scala/util/boundary.scala), jeśli chcesz dowiedzieć się, jak to działa „pod maską”.  

Jedną z ważnych rzeczy jest to, że zapewnia, iż użytkownicy nigdy nie wywołają `break` bez otaczającego `boundary`, co sprawia, że kod staje się znacznie bezpieczniejszy.  

Poniższy fragment kodu ilustruje użycie `boundary`/`break` w najprostszej formie.  
Jeśli nasze przekształcenie i walidacja zakończą się powodzeniem, `break(Some(userData))` wyskakuje z pętli oznaczonej `boundary:`.  
Ponieważ jest to koniec metody, natychmiast zwraca `Some(userData)`.  

```scala 3
  def findFirstValidUser10(userIds: Seq[UserId]): Option[UserData] =
    boundary:
      for userId <- userIds do
        safeComplexConversion(userId).foreach { userData =>
          if (complexValidation(userData)) break(Some(userData))
        }
      None
```

Czasami istnieje wiele `boundary` i w takich przypadkach można dodać etykiety do wywołań `break`.  
Jest to szczególnie ważne w przypadku zagnieżdżonych pętli.  
Przykład użycia etykiet znajdziesz [tutaj](https://gist.github.com/bishabosha/95880882ee9ba6c53681d21c93d24a97).

## Ćwiczenie 

Na koniec użyjmy `boundary`, aby osiągnąć ten sam rezultat.  

Spróbujmy użyć kolekcji leniwej (`lazy collection`), aby osiągnąć ten sam cel, co w poprzednich zadaniach.

* Użyj `boundary`, aby zaimplementować `findFirstValidCat`.  
* Skopiuj implementacje `furCharacteristicValidation` i `nonAdoptedCatConversion` z poprzedniego zadania.