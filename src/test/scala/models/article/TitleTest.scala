package models.article

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.Matchers.{be, _}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.PropertyChecks

@RunWith(classOf[JUnitRunner])
class TitleTest extends FlatSpec with PropertyChecks {
  import models.article.ArticleGenerator._

  "Title" should "100文字以下かつ必須である" in {
    forAll { (title: Title) =>

      val validResult = title.valid()
      validResult.isSuccess should be(true)
    }
  }

}
