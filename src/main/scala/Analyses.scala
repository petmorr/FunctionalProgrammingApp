/**
 * Object containing analytical functions for Formula 1 statistics.
 */
object Analyses {

  /**
   * Retrieves the winner for each season in the dataset.
   *
   * @param data The map of seasons with lists of driver standings.
   * @param ascending If true, orders results in ascending order of years; otherwise, descending.
   * @return A map where each year maps to a tuple with the winner's name, points, and wins.
   */
  def getSeasonWinners(data: Map[Int, List[(String, Float, Int)]], ascending: Boolean = true): Map[Int, (String, Float, Int)] = {
    data.toSeq.sortBy(_._1 * (if (ascending) 1 else -1))
      .collect { case (year, drivers) if drivers.nonEmpty => year -> drivers.head }
      .toMap
  }

  /**
   * Retrieves the complete results for a specified season with numbered positions.
   *
   * @param year The year of the season to retrieve.
   * @param data The map of seasons with lists of driver standings.
   * @return An optional list of tuples with driver position, name, points, and wins.
   */
  def getSeasonResults(year: Int, data: Map[Int, List[(String, Float, Int)]]): Option[List[(Int, String, Float, Int)]] = {
    data.get(year).filter(_.nonEmpty)
      .map(_.zipWithIndex.map { case ((name, points, wins), idx) => (idx + 1, name, points, wins) })
  }

  /**
   * Calculates the total number of races for each season.
   *
   * @param data The map of seasons with lists of driver standings.
   * @param ascending If true, orders results in ascending order of years; otherwise, descending.
   * @return A map where each year maps to the total number of races.
   */
  def totalRacesPerSeason(data: Map[Int, List[(String, Float, Int)]], ascending: Boolean = true): Map[Int, Int] = {
    data.toSeq.sortBy(_._1 * (if (ascending) 1 else -1))
      .map { case (year, drivers) => year -> drivers.map(_._3).sum }
      .toMap
  }

  /**
   * Calculates the total points for a specified driver across all seasons.
   *
   * @param driverName The full or partial name of the driver to search for.
   * @param data The map of seasons with lists of driver standings.
   * @return The total points for the driver across all seasons, or None if no records exist.
   */
  def totalPointsForDriver(driverName: String, data: Map[Int, List[(String, Float, Int)]]): Option[Float] = {
    val totalPoints = data.values.flatten.collect {
      case (name, points, _) if name.toLowerCase.contains(driverName.toLowerCase) => points
    }.sum
    if (totalPoints > 0) Some(totalPoints) else None
  }
}