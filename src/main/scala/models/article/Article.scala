package models.article

import models.Aggregate

import scalaz.ValidationNel
import scalaz.Scalaz._

class Article(val id: ArticleId, val title: Title, val content: Content)
    extends Aggregate[ArticleId] {

  def valid: ValidationNel[String, Article] = {
    val validTitle = title.valid()
    val validContent = content.valid()

    (validTitle |@| validContent)((_, _) => this)
  }

}

object Article {

  def apply(id: Long, title: String, content: String): Article = {
    new Article(ArticleId(id), Title(title), Content(content))
  }
}
