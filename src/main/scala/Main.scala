import com.twitter.finagle.http.Request
import com.twitter.finagle.http.Response
import com.twitter.finagle.Http
import com.twitter.finagle.Service
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import scaldi.Injectable
import scaldi.Injector
import userinterfaces.user.UserController
import userinterfaces.article.ArticleController

object Main {
  def main(args: Array[String]): Unit = {
    val appliation =
      new Application()(new DiModule())

    Await.ready(Http.serve(":8082", appliation.applicationService))
  }
}

class Application(implicit injector: Injector) extends Injectable {
  def applicationService: Service[Request, Response] =
    (inject[ArticleController].articleService :+: inject[UserController].userService)
      .handle {
        case fe: io.finch.Error =>
          BadRequest(new Exception(fe.getMessage)) // validation Error が起きた場合のcase
        case e: Exception => InternalServerError(new Exception(e.getMessage))
      }
      .toServiceAs[Application.Json]
}
