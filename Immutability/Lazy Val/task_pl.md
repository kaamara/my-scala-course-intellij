**Lenistwo** odnosi się do opóźnienia wykonywania obliczeń aż do momentu, gdy są one naprawdę potrzebne.  
Ta strategia może zwiększyć wydajność i umożliwić programistom korzystanie z nieskończonych struktur danych, wraz z innymi korzyściami.  
Przy strategii leniwego obliczania wyrażenia nie są oceniane w momencie przypisania do zmiennej, lecz dopiero wtedy, gdy są używane po raz pierwszy.  
Jeśli nigdy nie zostaną użyte, nie zostaną również ocenione.  
W niektórych kontekstach leniwe obliczanie może także zapobiegać wyjątkom, unikając oceny błędnych obliczeń.  

W Scalę słowo kluczowe `lazy` służy do implementacji lenistwa.  
Kiedy `lazy` jest używane w deklaracji `val`, inicjalizacja tego `val` jest opóźniona do momentu jego pierwszego użycia.  
Oto podsumowanie tego, jak `lazy val` działa wewnętrznie:

* **Deklaracja**: Kiedy `lazy val` zostaje zadeklarowane, nie jest alokowana przestrzeń w pamięci na wartość i nie jest wykonywany żaden kod inicjalizacji.  
* **Pierwszy dostęp**: Przy pierwszym dostępie do `lazy val`, wyrażenie po prawej stronie operatora `=` jest oceniane,  
  a wynikowa wartość zostaje zapisana.  
  Obliczenie to odbywa się zazwyczaj w sposób bezpieczny wątkowo, aby unikać potencjalnych problemów w środowisku wielowątkowym.  
  Mechanizm sprawdzania i podwójnego sprawdzania zapewnia, że wartość jest obliczana tylko raz, nawet w środowisku współbieżnym.  
* **Kolejne dostępy**: Przy kolejnych dostępach wcześniej obliczona i zapisana wartość jest zwracana bez ponownego oceniania wyrażenia inicjalizującego.

Rozważ poniższy przykład:  

```
lazy val lazyComputedValue: Int = {
  println("Obliczanie...")
  // Jakieś kosztowne obliczenia
  Thread.sleep(1000)
  42  // Obliczona wartość
}

val timeOffset = System.currentTimeMillis

def now = System.currentTimeMillis - timeOffset

println(s"lazyComputedValue zadeklarowane o $now.")
// Wartość jest obliczana i drukowana tylko przy pierwszym dostępie.
println(s"Uzyskanie dostępu: $lazyComputedValue")
println(s"aktualny czas to $now") // zajmuje ponad 1000 milisekund
// Tym razem wartość nie jest obliczana, ale pobierana z pamięci.
println(s"Ponowny dostęp: $lazyComputedValue")
println(s"aktualny czas to $now") // powinno zająć co najwyżej kilka milisekund
```

W powyższym kodzie:  
* `lazy val lazyComputedValue` jest zadeklarowane, ale nie jest obliczane od razu w momencie deklaracji.  
* Gdy zostaje po raz pierwszy użyte w instrukcji `println`, obliczenie jest wykonywane, `"Obliczanie..."` zostaje wydrukowane na konsoli,  
  i obliczenie (tutaj symulowane przez `Thread.sleep(1000)`) odbywa się przed zwróceniem wartości `42`.  
* Wszelkie kolejne dostępy do `lazyComputedValue`, jak w drugim `println`, nie wyzwalają ponownego obliczenia.  
  Bezpośrednio używana jest przechowywana wartość (`42`).