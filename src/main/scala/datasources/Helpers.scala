package datasources

import java.util.concurrent.TimeUnit

import org.mongodb.scala.Completed
import org.mongodb.scala.Document
import org.mongodb.scala.Observable

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Helpers {
  implicit class DocumentObservable[C](val observable: Observable[Document])
      extends ImplicitObservable[Document]

  implicit class CompletedObservable[C](val observable: Observable[Completed])
      extends ImplicitObservable[Completed]

  trait ImplicitObservable[C] {
    val observable: Observable[C]
    val timeOutCount = 10

    def results: Seq[C] =
      Await.result(observable.toFuture(), Duration(timeOutCount, TimeUnit.SECONDS))
  }
}
