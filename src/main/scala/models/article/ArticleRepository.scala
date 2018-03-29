package models.article

import scala.util.Try

trait ArticleRepository {
  def findById(articleId: ArticleId): Try[Article]

  def register(article: Article): Try[Article]
}
