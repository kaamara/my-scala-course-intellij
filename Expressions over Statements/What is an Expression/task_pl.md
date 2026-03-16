Podczas programowania w stylu imperatywnym mamy tendencję do budowania funkcji z instrukcji.  
Informujemy kompilator o dokładnych krokach i kolejności, w jakiej powinny zostać wykonane, aby osiągnąć  
zamierzony rezultat.  
Typowa funkcja podąża za tym podejściem: pobierz dane stąd, zmodyfikuj je, zapisz gdzie indziej.  
Manipuluje danymi, które znajdują się poza samą funkcją.  
Pomysł ten przeczy definicji funkcji, której uczyliśmy się w matematyce w szkole.  
Tam funkcje niczego nie modyfikowały.  
Zamiast tego przyjmowały argumenty i generowały nowy rezultat.  
Były wyrażeniami.  

Wyrażenie można postrzegać jako kombinację wartości, zmiennych, funkcji i operatorów, które są obliczane przez  
interpreter lub kompilator języka programowania w celu wygenerowania innych wartości.  
Na przykład, `1+2`, `x*3` i `f(42)` to wyrażenia.  
Typowo, wyrażenie oblicza się do *wartości*, która może być użyta w dalszych obliczeniach.  
Wyrażenia są również *komponowalne*, co oznacza, że jedno wyrażenie może być zagnieżdżone w innym, umożliwiając skomplikowane  
obliczenia.  
Często można zidentyfikować wyrażenie na podstawie kontekstu, w którym jest używane: najczęściej pojawiają się one w warunkach w `if`,  
jako argumenty funkcji oraz po prawej stronie przypisań.  

Głównym celem instrukcji jest wykonanie konkretnej *akcji*: zadeklarowanie zmiennej, uruchomienie pętli, wykonanie instrukcji warunkowej,  
itp.  
Na przykład, przykłady instrukcji to `val x = 13;`, `return 42` i `println("Hello world")`.  
Zazwyczaj nie zwracają one wartości; zamiast tego służą jako elementy budulcowe programu napisanego w stylu imperatywnym.  
Instrukcje definiują *przepływ kontroli* programu.  

Oczywiście, w rzeczywistych językach programowania różnice między wyrażeniami a instrukcjami mogą być mniej wyraźne  
niż w teorii.  
Wiele języków pozwala wyrażeniom powodować tzw. *efekty uboczne*: mogą one rzucać wyjątki, zapisywać do logów lub  
odczytywać z pamięci.  
Z drugiej strony instrukcje mogą zwracać wartości, a nawet być komponowalne.  
To, co jest ważne, to to, co uważamy za główny cel danego elementu języka.  

W językach programowania funkcyjnego mamy tendencję do preferowania wyrażeń z różnych powodów.  
Omówimy te powody w dalszych lekcjach.  
Na razie przyjrzyjmy się, jak używanie wyrażeń może wpłynąć na sposób, w jaki piszemy kod, korzystając z przykładu programu, który  
określa, czy liczba jest parzysta, czy nieparzysta.  
Najpierw przyjrzyjmy się implementacji opartej na instrukcjach.  

```scala 3
def even(number: Int): Unit = {
  if (number % 2 == 0)
    println("Liczba jest parzysta")
  else
    println("Liczba jest nieparzysta")
}

def main(): Unit = {
  val number = 42
  even(42)
}
```

Tutaj używamy instrukcji `if`, aby sprawdzić, czy liczba jest parzysta.  
W zależności od warunku wykonujemy jedno z dwóch instrukcji `println`.  
Zauważ, że żadna wartość nie jest zwracana. Zamiast tego, wszystko, co robi funkcja, to efekt uboczny drukowania na konsoli.  

Ten styl nie jest uważany za idiomatyczny w Scali.  
Zamiast tego preferuje się, by funkcja zwracała wartość w postaci ciągu znaków, który następnie jest drukowany, jak poniżej:  

```scala 3
def even(number: Int): String = { 
  if (number % 2 == 0) "parzysta" else "nieparzysta"
} 

@main
def main(): Unit = {
  val number = 42 
  val result = even(12)
  println(s"Liczba jest $result")
}
```

W ten sposób oddzielasz logikę obliczania wartości od jej wyświetlania.  
To sprawia również, że Twój kod staje się bardziej czytelny.  

## Ćwiczenie  

Przepisz funkcje `abs` i `concatStrings` jako wyrażenia, aby realizowały te same zadania co ich oryginalne implementacje.  
Zaimplementuj funkcje `sumOfAbsoluteDifferences` i `longestCommonPrefix`, używając stylu wyrażeń.  

`abs` oblicza wartość bezwzględną dla podanej liczby całkowitej.  

`concatStrings` konkatenuje listę ciągów znaków.  

`longestCommonPrefix` określa najdłuższy wspólny prefiks wśród ciągów znaków w podanej liście.  

`sumOfAbsoluteDifferences` najpierw oblicza różnice bezwzględne między liczbami na odpowiadających sobie pozycjach w dwóch tablicach, a  
następnie sumuje je.  
Na przykład, dla tablic `[1, 2]` i `[3, 4]` wynikiem jest `abs(1 - 3) + abs(2 - 4) == 4`.  
Zakłada się, że tablice zawsze mają tę samą długość.