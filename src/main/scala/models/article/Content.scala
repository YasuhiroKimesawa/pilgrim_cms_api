package models.article

import models.Value

import scalaz.Failure
import scalaz.Success
import scalaz.ValidationNel
import scalaz.Scalaz._

private[article] case class Content(value: String)
    extends AnyVal
    with Value[String] {
  def isNotEmpty: Boolean = !value.isEmpty

  def valid(): ValidationNel[String, Content] = {
    val validRequire: ValidationNel[String, String] =
      if (!isNotEmpty) { "Error:Contentは必須です".failureNel[String] } else {
        value.successNel[String]
      }

    validRequire match {
      case Success(a) => this.successNel
      case Failure(e) => e.head.failureNel
    }
  }
}
