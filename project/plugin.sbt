logLevel := Level.Warn

addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.6")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.4.0")
// sbt scalafmt

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")
// sbt scalastyleGenerateConfig ...configファイル作成
// sbt scalastyle