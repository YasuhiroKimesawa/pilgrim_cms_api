package models.article

import org.scalacheck.{Arbitrary, Gen}

object ArticleGenerator {
  val titleGen: Gen[Title] = for {
    size <- Gen.choose(1, 100)
    title <- Gen.listOfN(size, Gen.alphaChar).map(_.mkString)
  } yield Title(title)

  implicit val arbTitle: Arbitrary[Title] = Arbitrary(titleGen)
}
