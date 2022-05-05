import cats.data.NonEmptyList
import cats.parse.Parser as P
import cats.parse.Rfc5234.digit

case class ParserError(expected: NonEmptyList[P.Expectation],
                       offsets: NonEmptyList[Int],
                       failedAtOffset: Int) extends Throwable

case class Semver(major: Int, minor: Int, patch: Int) extends Ordered[Semver] :
  override def compare(that: Semver): Int =
    val major = Integer.compare(this.major, that.major)
    if (major != 0)
      major
    else
      val minor = Integer.compare(this.minor, that.minor)
      if (minor != 0)
        minor
      else
        Integer.compare(this.patch, that.patch)

object Semver:
  def parse(string: String): Either[P.Error, Semver] =
    val p = (digit.rep ~ P.char('.').void) ~ (digit.rep ~ P.char('.').void) ~ digit.rep

    p.parse(string).map(_._2).map { x =>
      Semver(buildVersionInt(x._1._1._1),
        buildVersionInt(x._1._2._1),
        buildVersionInt(x._2))
    }

  def unsafeParse(string: String): Semver = {
    parse(string) match {
      case Left(ex) => throw ParserError(ex.expected, ex.offsets, ex.failedAtOffset)
      case Right(v) => v
    }
  }

  private def buildVersionInt(input: NonEmptyList[Char]): Int =
    input.toList.mkString.toInt
