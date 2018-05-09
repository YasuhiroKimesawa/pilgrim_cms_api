import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._
import scala.language.postfixOps


class Sample extends Simulation {

  val httpConf: HttpProtocolBuilder = http
    .baseURL("http://pilgrim-lifestyle.com")
    .headers(Map(
      HttpHeaderNames.ContentType    -> HttpHeaderValues.ApplicationJson,
      HttpHeaderNames.Accept         -> HttpHeaderValues.ApplicationJson,
      HttpHeaderNames.AcceptEncoding -> "gzip,deflate"
    ))

  val scn: ScenarioBuilder = scenario("Basic sinario")
    .exec(http("request_1")
      .get("/"))
    .pause(500 milliseconds)
    .exec(http("request_2")
      .get("/"))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}