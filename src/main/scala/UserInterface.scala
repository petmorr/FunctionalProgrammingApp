/**
 * Object that manages user interaction with the application.
 */
object UserInterface {

  /**
   * Displays the main menu and handles user input.
   *
   * @param data The map of seasons with lists of driver standings.
   * @param readInput A function to read user input, defaulting to standard input.
   */
  def showMenu(data: Map[Int, List[(String, Float, Int)]], readInput: () => String = () => scala.io.StdIn.readLine()): Unit = {
    val menuActions = Map(
      "1" -> (() => showWinners(data)),
      "2" -> (() => showSeasonResultsPrompt(data, readInput)),
      "3" -> (() => showTotalRaces(data)),
      "4" -> (() => {
        println("Exiting the menu...")
        return
      }),
      "6" -> (() => showDriverPointsPrompt(data, readInput))
    )

    val continue = true
    while (continue) {
      println("\nChoose an analysis option:")
      println("1. Show season winners")
      println("2. Display results for a specific season")
      println("3. Total races per season")
      println("4. Quit")
      println("6. Total points for a specific driver")

      menuActions.get(readInput().trim) match {
        case Some(action) => action()
        case None => println("Invalid choice, please enter a number from 1 to 6.")
      }
    }
  }

  /**
   * Displays the winners for each season.
   */
  private def showWinners(data: Map[Int, List[(String, Float, Int)]]): Unit = {
    if (data.isEmpty) {
      println("No data available to display winners.")
    } else {
      val winners = Analyses.getSeasonWinners(data)
      if (winners.isEmpty) {
        println("No winners available for any season.")
      } else {
        winners.foreach { case (year, (name, points, wins)) =>
          println(s"$year: Winner - $name, Points: $points, Wins: $wins")
        }
      }
    }
  }

  /**
   * Prompts user to enter a year, then displays results for that season.
   */
  private def showSeasonResultsPrompt(data: Map[Int, List[(String, Float, Int)]], readInput: () => String): Unit = {
    println("Enter the year for which you want results:")
    readInput().trim.toIntOption match {
      case Some(year) => showSeasonResults(year, data)
      case None => println("Invalid input. Please enter a valid year.")
    }
  }

  /**
   * Displays the results for a specific season.
   *
   * @param year The year for which results are displayed.
   * @param data The map of seasons with lists of driver standings.
   */
  private def showSeasonResults(year: Int, data: Map[Int, List[(String, Float, Int)]]): Unit = {
    data.get(year) match {
      case Some(results) if results.nonEmpty =>
        results.zipWithIndex.foreach { case ((name, points, wins), index) =>
          println(s"Position ${index + 1}: Driver - $name, Points: $points, Wins: $wins")
        }
      case _ => println(s"No results available for the year $year.")
    }
  }

  /**
   * Displays the total number of races for each season.
   */
  private def showTotalRaces(data: Map[Int, List[(String, Float, Int)]]): Unit = {
    if (data.isEmpty) {
      println("No race data available.")
    } else {
      Analyses.totalRacesPerSeason(data).foreach { case (year, totalRaces) =>
        println(s"$year: Total Races - $totalRaces")
      }
    }
  }

  /**
   * Prompts user to enter a driver's name, then displays their total points.
   */
  private def showDriverPointsPrompt(data: Map[Int, List[(String, Float, Int)]], readInput: () => String): Unit = {
    println("Enter the driver's name:")
    val driverName = readInput().trim
    if (driverName.nonEmpty) showDriverTotalPoints(driverName, data)
    else println("Driver name cannot be empty.")
  }

  /**
   * Displays the total points for a specific driver.
   *
   * @param driverName The name of the driver.
   * @param data The map of seasons with lists of driver standings.
   */
  private def showDriverTotalPoints(driverName: String, data: Map[Int, List[(String, Float, Int)]]): Unit = {
    Analyses.totalPointsForDriver(driverName, data) match {
      case Some(points) => println(s"Total points for $driverName: $points")
      case None => println(s"No points found for driver $driverName.")
    }
  }
}