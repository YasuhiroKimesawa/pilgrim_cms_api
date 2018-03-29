package userinterfaces.article

import io.finch.Endpoint
import io.finch.Ok
import io.finch.jsonBody
import io.finch.syntax.get
import io.finch.syntax.post
import shapeless.:+:
import shapeless.CNil
import io.finch._
import io.finch.circe._
import models.article.Article
import models.article.ArticleId
import usecases.article.ArticleUseCase

class ArticleController(articleUseCase: ArticleUseCase) {

  val findAll: Endpoint[Article] = get("articles") {
    Ok(articleUseCase.findById(ArticleId(1)).right.get)
    //Ok(Article(1, "aaa", "bbb"))
  }

  val findById: Endpoint[Article] = get("articles" :: path[Long]) { id: Long =>
    Ok(Article(id, "aaa", "bbb"))
  }

  // 配列jsonも出来るかな
  val create: Endpoint[Article] = post("articlejson" :: jsonBody[Article]) {
    p: Article =>
      articleUseCase.register(article = p)
      Ok(Article(1, "aaa", "bbb"))
  }

  val articleService
    : Endpoint[:+:[Article, :+:[Article, :+:[Article, CNil]]]] = findById :+: create :+: findAll
}
