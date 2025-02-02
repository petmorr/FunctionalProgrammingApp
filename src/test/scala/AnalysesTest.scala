import org.scalatest.funsuite.AnyFunSuite

class AnalysesTest extends AnyFunSuite {
  val testData = Map(
    2023 -> List(("Max Verstappen", 575.0f, 19), ("Sergio Perez", 285.0f, 2), ("Lewis Hamilton", 234.0f, 0)),
    2022 -> List(("Max Verstappen", 454.0f, 15), ("Charles Leclerc", 308.0f, 3), ("George Russell", 275.0f, 1)),
    2021 -> List(("Lewis Hamilton", 387.5f, 8), ("Max Verstappen", 395.5f, 10), ("Valtteri Bottas", 226.0f, 1))
  )

  val emptyData: Map[Int, List[(String, Float, Int)]] = Map()

  test("getSeasonWinners should retrieve winners for each season in ascending order") {
    val result = Analyses.getSeasonWinners(testData, ascending = true)
    val expected = Map(
      2021 -> ("Lewis Hamilton", 387.5f, 8),
      2022 -> ("Max Verstappen", 454.0f, 15),
      2023 -> ("Max Verstappen", 575.0f, 19)
    )
    assert(result == expected)
  }

  test("getSeasonWinners should retrieve winners for each season in descending order") {
    val result = Analyses.getSeasonWinners(testData, ascending = false)
    val expected = Map(
      2023 -> ("Max Verstappen", 575.0f, 19),
      2022 -> ("Max Verstappen", 454.0f, 15),
      2021 -> ("Lewis Hamilton", 387.5f, 8)
    )
    assert(result == expected)
  }

  test("getSeasonResults should return results with numbered positions") {
    val result = Analyses.getSeasonResults(2023, testData)
    val expected = Some(List(
      (1, "Max Verstappen", 575.0f, 19),
      (2, "Sergio Perez", 285.0f, 2),
      (3, "Lewis Hamilton", 234.0f, 0)
    ))
    assert(result == expected)
  }

  test("totalPointsForDriver should return correct points or None for non-existing drivers") {
    assert(Analyses.totalPointsForDriver("Max Verstappen", testData).contains(1424.5f))
    assert(Analyses.totalPointsForDriver("Unknown Driver", testData).isEmpty)
  }
}