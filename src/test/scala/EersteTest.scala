import io.gatling.core.Predef._
import io.gatling.http.Predef._

class EersteTest extends Simulation {

  val httpConf = http.baseUrl("http://bap-generiek-fat.web-lsz.ota.duo.nl/bap-referentie-rest/api/")
    .header("Accept", "application/json")

  def getAlleGemeenten() = {
    exec(http("Alle gemeentes ophalen")
      .get("gemeenten"))
  }

  def getAlleLanden() = {
    exec(http("Alle landen ophalen")
      .get("landen"))
  }

  val scn = scenario("Ophalen van referentiegegevens")
    .exec(getAlleGemeenten())
    .exec(getAlleLanden())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
    .assertions(global.responseTime.mean.lt(1000))
}
