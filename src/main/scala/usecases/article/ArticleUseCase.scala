package usecases.article

import models.article.Article
import models.article.ArticleId
import models.article.ArticleRepository
import usecases.ApplicationError
import usecases.ServiceError
import usecases.ServiceErrorCodes
import usecases._

class ArticleUseCase(articleRepository: ArticleRepository) {

  // validationErrorはStringを引数にとりServiceErrorを返す関数
  // usecases.asServiceErrorの引数となる
  private implicit val validationError: String => ServiceError = (e) =>
    IllegalTaskOperationError(e)

  def register(article: Article): Either[ServiceError, Article] = {
    for {
      validatedArticle <- article.valid ifLeftThen asServiceError
      registeredArticle <- articleRepository.register(validatedArticle) ifFailureThen asServiceError
    } yield registeredArticle
  }

  def findById(articleId: ArticleId): Either[ServiceError, Article] = {
    for {
      foundArticle <- articleRepository.findById(articleId) ifFailureThen asServiceError
    } yield foundArticle
  }
}

case class IllegalTaskOperationError(error: String)
    extends ApplicationError(ServiceErrorCodes.InvalidTaskOperation)
