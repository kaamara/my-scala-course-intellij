W przypadku dowolnego monadu, czy to `Option`, `Either`, `Try`, czy innego, możliwe jest łączenie wielu funkcji za pomocą `flatMap`.  
Widzieliśmy wiele przykładów, w których poprawnie obliczony wynik jest bezpośrednio przekazywany do następnej funkcji: `foo(a).flatMap(bar).flatMap(baz)`.  
W wielu rzeczywistych sytuacjach pomiędzy wywołaniami jest wykonywana pewna dodatkowa logika.  
Rozważmy następujący realistyczny przykład:  

```scala 3
val res = client.getTeamMembers(teamId).flatMap { members =>
  storage.getUserData(members.map(_.userId)).flatMap { users =>
    log(s”members: $members, users: $users”)
    system.getPriorityLevels(teamId).flatMap {
      case levels if levels.size > 1 =>
        doSomeStuffOrFail(members, users, levels)
      case _ =>
        doSomeOtherStuffOrFail(members, users)
    }
  }
}
```

Nie wygląda to zbyt estetycznie, prawda?  
Każde kolejne wywołanie dodaje nowy poziom zagnieżdżenia, a gdy próbujemy przeanalizować, co się dzieje, jest to dość skomplikowane.  
Na szczęście Scala oferuje syntaktyczny skrót, zwany *for-comprehensions*, przypominający notację do w Haskellu.  
Ten sam kod można napisać bardziej zwięźle, używając `for/yield`:  

```scala 3
val res = for {
  members <- client.getTeamMembers(teamId)
  users   <- storage.getUserData(members.map(_.userId))
  _       =  log(s"members: $members, users: $users")
  levels  <- system.getPriorityLevels(teamId)
} yield
  if (levels.size > 1) 
    doSomeStuffOrFail(members, users, levels)
  else
    doSomeOtherStuffOrFail(members, users)
```

Każda linia z lewym strzałkiem odpowiada wywołaniu `flatMap`, gdzie nazwa zmiennej po lewej stronie strzałki reprezentuje nazwę zmiennej w funkcji lambda.  
Zaczynamy od powiązania poprawnych wyników pobrania członków zespołu za pomocą `members`, następnie pobieramy dane użytkowników na podstawie identyfikatorów członków i wiążemy je z `users`.  
Należy zauważyć, że pierwsza linia w strukturze for-comprehension musi zawierać lewy strzałek.  
To w ten sposób kompilator Scali rozumie, jaki typ ma monadyczna operacja.  

Następnie logowana jest wiadomość, a poziomy priorytetów są pobierane.  
Należy zauważyć, że nie używamy strzałki po lewej stronie funkcji `log`, ponieważ jest to zwykła funkcja, a nie operacja monadyczna, która w oryginalnym fragmencie kodu jest łączona za pomocą `flatMap`.  
Nie dbamy również o wartość zwracaną przez `log`, dlatego używamy podkreślenia po lewej stronie znaku równości.  
Gdy wszystko zostanie wykonane, blok `yield` oblicza końcowe wartości do zwrócenia.  
Jeśli jakakolwiek linia zakończy się niepowodzeniem, obliczenia zostają przerwane, a cały for-comprehension kończy się niepowodzeniem.  

## Ćwiczenie  

Użyj for-comprehensions, aby zaimplementować `getGrandchild` oraz `getGrandchildAge` z poprzedniego ćwiczenia.