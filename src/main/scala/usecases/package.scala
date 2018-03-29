import scala.util.Try
import scalaz.ValidationNel

package object usecases {

  type ErrorCode = String

  // ValidationNelにifLeftThenを追加する
  implicit class DomainErrorOperations[E, R](
    domainResult: ValidationNel[E, R]
  ) {
    def ifLeftThen(f: E => ServiceError): Either[ServiceError, R] = {
      domainResult match {
        case scalaz.Failure(e) => Left(f(e.head))
        case scalaz.Success(r) => Right(r)
      }
    }
  }

  implicit class InfraErrorOperations[S](infraResult: Try[S]) {
    def ifFailureThen(f: String => ServiceError): Either[ServiceError, S] = {
      infraResult match {
        case scala.util.Failure(e) => Left(f(e.getMessage))
        case scala.util.Success(s) => Right(s)
      }
    }
  }

  // implicit usecases.TodoUseCase.validationError関数が引数になる。
  // 中身は暗黙のパラメータで渡ってきた関数をそのまま返す
  def asServiceError[E](implicit f: E => ServiceError): E => ServiceError = f
}
