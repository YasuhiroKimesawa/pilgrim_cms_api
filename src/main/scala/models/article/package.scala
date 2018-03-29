package models

import io.circe.Decoder.Result
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json

package object article {
  implicit val encodeTodo: Encoder[Article] = new Encoder[Article] {
    final def apply(article: Article): Json = Json.obj(
      ("id", Json.fromBigInt(article.id.value)),
      ("title", Json.fromString(article.title.value)),
      ("content", Json.fromString(article.content.value))
    )
  }

  implicit val todoDecoder: Decoder[Article] = {
    new TodoDecode
  }

  class TodoDecode extends Decoder[Article] {
    override def apply(cursor: HCursor): Result[Article] = {
      for {
        id <- cursor.downField("id").as[Long]
        title <- cursor.downField("title").as[String]
        content <- cursor.downField("content").as[String]
      } yield {
        Article(id, title, content)
      }
    }
  }
}
