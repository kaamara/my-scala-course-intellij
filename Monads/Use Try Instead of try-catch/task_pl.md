Gdy cały kod jest pod naszą kontrolą, łatwo unikać rzucania wyjątków, korzystając z `Option` lub `Either`.  
Jednak często współpracujemy z bibliotekami Javy, w których wyjątki są wszechobecne, na przykład w kontekście pracy z bazami danych, plikami lub usługami internetowymi.  
Jednym ze sposobów na pokonanie tej różnicy jest użycie `try/catch` i przekształcenie kodu wyjątków na kod monadyczny:

```scala 3
def foo(data: Data): Either[Throwable, Result] =
  try {
    val res: Result = javaLib.getSomethingOrThrowException(data)
    Right(res)
  } catch {
    case NonFatal(err) => Left(err)
  }
```

Ten przypadek jest tak powszechny, że Scala udostępnia specjalną monadę `Try[A]`.  
`Try[A]` działa jak wersja `Either[Throwable, A]`, zaprojektowana specjalnie do obsługi błędów pochodzących z JVM.  
Można to traktować jako konieczne zło: w idealnym świecie nie byłoby wyjątków, ale ponieważ nie istnieje idealny świat, a wyjątki są wszędzie, mamy `Try`, by zniwelować tę różnicę.  
Użycie `Try` znacząco upraszcza konwersję:

```scala 3
def foo(data: Data): Try[Result] =
  Try(javaLib.getSomethingOrThrowException(data))
```

`Try` ma dwie podklasy: `Success[A]` i `Failure`, które przypominają `Right[A]` i `Left[Throwable]` w `Either[Throwable, A]`.  
Pierwsza zawija wynik udanej operacji, podczas gdy druga sygnalizuje błąd, otaczając wyrzucony wyjątek.  
Ponieważ `Try` jest monadą, można używać `flatMap`, aby łączyć funkcje w łańcuch – jeśli którakolwiek z nich rzuci wyjątek, dalsze obliczenia zostaną przerwane.  

Czasami wyjątek nie jest krytyczny, i wiesz, jak się z nim uporać.  
W takiej sytuacji możesz skorzystać z metod `recover` lub `recoverWith`.  
Metoda `recover` przyjmuje funkcję częściową, która w przypadku niektórych wyjątków produkuje wartość, otaczaną następnie przez `Success`, podczas gdy w innych przypadkach skutkuje `Failure`.  
Bardziej elastyczne podejście oferuje metoda `recoverWith`: jej argumentem jest funkcja, która może zdecydować, jak odpowiednio zareagować na konkretne błędy.

```scala 3
val t: Try[Result] =
  Try(javaLib.getSomethingOrThrowException(data))
  
t.recover {
  case ex: IOException => defaultResult
}
  
t.recoverWith {
  case ex: IOException =>
    if (ignoreErrors) Success(defaultResult)
    else Failure(ex)
}
```

Podsumowując, gorąco zalecamy używanie `Try` zamiast `try/catch`.