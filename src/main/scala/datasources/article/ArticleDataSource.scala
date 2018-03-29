package datasources.article

import org.mongodb.scala.Document
import org.mongodb.scala.MongoClient
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.MongoDatabase
import datasources.Helpers._

import scala.util.Failure
import scala.util.Success
import scala.util.Try
import io.circe.parser._
import models.article.Article
import models.article.ArticleId
import models.article.ArticleRepository

class ArticleDataSource extends ArticleRepository {

  override def findById(articleId: ArticleId): Try[Article] = {
    // Localで稼働中のMongoDBに接続する
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")

    // Databaseを取得する
    val database: MongoDatabase = mongoClient.getDatabase("mydb")

    // Collectionを取得する
    val products: MongoCollection[Document] = database.getCollection("products")

    products.find.first.results
      .map(
        r =>
          decode[Article](r.toJson()) match {
            case Right(a) => Success(a)
            case Left(e)  => Failure(new RuntimeException(e.toString))
        }
      )
      .head
  }

  override def register(article: Article): Try[Article] = {
    import io.circe.syntax._

    // Localで稼働中のMongoDBに接続する
    val mongoClient: MongoClient = MongoClient("mongodb://localhost:27017")

    // Databaseを取得する
    val database: MongoDatabase = mongoClient.getDatabase("mydb")

    // Collectionを取得する
    val products: MongoCollection[Document] = database.getCollection("products")
    val doc2: Document = Document(article.asJson.toString())
    try {
      products.insertOne(doc2).results
      Success(article)
    } catch {
      case e: Exception => Failure(new RuntimeException(e.toString))
    }
  }
}
