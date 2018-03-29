package usecases

class ServiceError(val errorCode: ErrorCode, val args: Any*) {
  protected val stackTrace: Array[StackTraceElement] = {
    val traces = Thread.currentThread().getStackTrace
    traces.drop(
      traces.lastIndexWhere(t => t.getClassName == getClass.getName) + 1
    )
  }

  override def toString: String = {
    s"""${getClass.getName}($errorCode, [${args.mkString(", ")}])
       |${stackTrace.map(s => s"  at $s").mkString("\n")}
    """.stripMargin
  }
}

case class SystemError(cause: Throwable)
    extends ServiceError(ServiceErrorCodes.SystemError)

abstract class ApplicationError(errorCode: ErrorCode, args: Any*)
    extends ServiceError(errorCode, args: _*)

object ServiceErrorCodes {
  val SystemError = "error.system"

  // in TaskService
  val InvalidTaskOperation = "error.invalidTaskOperation"
}
