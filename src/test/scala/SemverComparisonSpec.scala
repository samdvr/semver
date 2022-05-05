import org.scalacheck.*

object SemverComparisonSpec extends Properties("Semver Comparison Spec") {
  val equalityCheck = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
  } yield (Semver(major, minor, patch), Semver(major, minor, patch))
  property("semver is equal when major,minor and patch is equal") = Prop.forAll(equalityCheck) { s =>
    s._1 == s._2
  }

  val majorCheck = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
    major2 <- Gen.choose(1, 1000).suchThat(_ < major)
    minor2 <- Gen.choose(1, 1000)
    patch2 <- Gen.choose(1, 1000)
  } yield (Semver(major, minor, patch), Semver(major2, minor2, patch2))
  property("semver is greater when major is greater") = Prop.forAll(majorCheck) { s =>
    s._1 > s._2
  }

  val majorCheck2 = for {
    major2 <- Gen.choose(1, 1000)
    major <- Gen.choose(1, 1000).suchThat(_ < major2)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
    minor2 <- Gen.choose(1, 1000)
    patch2 <- Gen.choose(1, 1000)
  } yield (Semver(major, minor, patch), Semver(major2, minor2, patch2))
  property("semver is smaller when major is smaller") = Prop.forAll(majorCheck2) { s =>
    s._1 < s._2
  }

  val minorCheck = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
    minor2 <- Gen.choose(1, 1000).suchThat(_ < minor)
    patch2 <- Gen.choose(1, 1000)
  } yield (Semver(major, minor, patch), Semver(major, minor2, patch2))
  property("semver is greater when minor is greater while major is the same") = Prop.forAll(minorCheck) { s =>
    s._1 > s._2
  }

  val minorCheck2 = for {
    minor2 <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000).suchThat(_ < minor2)
    major <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
    patch2 <- Gen.choose(1, 1000)
  } yield (Semver(major, minor, patch), Semver(major, minor2, patch2))
  property("semver is smaller when minor is smaller while major is the same") = Prop.forAll(minorCheck2) { s =>
    s._1 < s._2
  }

  val patchCheck = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000)
    patch2 <- Gen.choose(1, 1000).suchThat(_ < patch)
  } yield (Semver(major, minor, patch), Semver(major, minor, patch2))
  property("semver is greater when patch is greater while major and minor are the same") = Prop.forAll(patchCheck) { s =>
    s._1 > s._2
  }

  val patchCheck2 = for {
    major <- Gen.choose(1, 1000)
    minor <- Gen.choose(1, 1000)
    patch2 <- Gen.choose(1, 1000)
    patch <- Gen.choose(1, 1000).suchThat(_ < patch2)
  } yield (Semver(major, minor, patch), Semver(major, minor, patch2))
  property("semver is smaller when patch is smaller while major and minor are the same") = Prop.forAll(patchCheck2) { s =>
    s._1 < s._2
  }
}
