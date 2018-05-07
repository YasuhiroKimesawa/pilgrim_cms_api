import scoverage.ScoverageKeys.{coverageExcludedFiles, coverageExcludedPackages}

object Coverage {
  val Settings = Seq(

    coverageExcludedPackages := Seq(
      "datasources.*",
      "userinterfaces.*",
      ".*Error.*",
      ".*DiModule.*"
    ).mkString(";"),

    coverageExcludedFiles := Seq(
      ".*package.*",
      ".*Error.*",
      ".*Main.*"
    ).mkString(";")
  )
}
