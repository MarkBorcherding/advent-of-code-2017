package adventofcode

object Day09 {

  // <>, empty garbage.
  // <random characters>, garbage containing random characters.
  // <<<<>, because the extra < are ignored.
  // <{!>}>, because the first > is canceled.
  // <!!>, because the second ! is canceled, allowing the > to terminate the garbage.
  // <!!!>>, because the second ! and the first > are canceled.
  // <{o"i!a,<{i<a>, which ends at the first >.


  val garbage = "<([^>]*)>".r
  val countGarbage = garbage.findAllIn(_:CharSequence).matchData.map(_.group(1)).mkString.length()
  val stripNegated = "!.".r.replaceAllIn(_:CharSequence, "")
  val stripGarbage = garbage.replaceAllIn(_:CharSequence, "")
  val stripCommas = ",".r.replaceAllIn(_:CharSequence, "")

  def lines(input:String) = input.split("\n").map(_.trim)

  def tryIt =
    lines(sampleInput)
      .map(go)

  val go =
    stripNegated andThen
    stripGarbage andThen
    stripCommas andThen
    score

  val count =
    stripNegated andThen
    countGarbage

  def score(s: String) =
    s.foldLeft(0 -> 1) {
      case ((score, level), '{') => score + level -> (level + 1)
      case ((score, level), '}') => score -> (level - 1)
      case ((score, level), s) => println(s"WHAT $s"); (score, level)
    }._1

  val sampleInput =
    """{}
      |{{{}}}
      |{{},{}}
      |{{{},{},{{}}}}
      |{<a>,<a>,<a>,<a>}
      |{{<ab>},{<ab>},{<ab>},{<ab>}}
      |{{<!!>},{<!!>},{<!!>},{<!!>}}
      |{{<a!>},{<a!>},{<a!>},{<ab>}}""".stripMargin
}
