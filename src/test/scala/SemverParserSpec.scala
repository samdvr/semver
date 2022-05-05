import org.scalacheck.*

import scala.util.Try

object SemverParserSpec extends Properties("Semver Parser Spec") {
  val stringParseCheck = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
  } yield (Semver.parse(s"$major.$minor.$patch"), Semver(major, minor, patch))
  property("parser parses string correctly") = Prop.forAll(stringParseCheck) { s =>
    s._1 == Right(s._2)
  }

  val stringParseInvalidSemverCheck = for {
    major <- Gen.alphaChar.suchThat(x => !('0' to '9').contains(x))
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
  } yield Semver.parse(s"$major.$minor.$patch")
  property("parser parses string correctly") = Prop.forAll(stringParseInvalidSemverCheck) { s =>
    s.isLeft
  }

  val stringParseInvalidSemverCheck2 = for {
    str <- Gen.alphaNumStr
  } yield Semver.parse(str)
  property("parser parses string correctly") = Prop.forAll(stringParseInvalidSemverCheck2) { s =>
    s.isLeft
  }

  val unsafeParseError = for {
    str <- Gen.alphaNumStr
  } yield Try(Semver.unsafeParse(str)).toEither
  property("unsafe parse throws exception") = Prop.forAll(unsafeParseError) { s =>
    s.isLeft
  }

  val unsafeParseSuccess = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
  } yield Try(Semver.unsafeParse(s"$major.$minor.$patch")).toEither
  property("unsafe parse does not throw exception when version is valid") = Prop.forAll(unsafeParseSuccess) { s =>
    s.isRight
  }
}
