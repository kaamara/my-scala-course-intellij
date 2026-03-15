object AnonymousFunctions:
  def multiplyAndOffsetList(multiplier: Int, offset: Int, numbers: List[Int]): List[Int] =
    numbers.map(x => x * multiplier + offset)
