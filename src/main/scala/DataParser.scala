import scala.io.Source
import scala.util.{Try, Success, Failure}

/**
 * Object responsible for parsing Formula 1 data from a file.
 */
object DataParser {

  /**
   * Parses the data file to create a structured map of seasons with driver standings.
   *
   * @param filePath The path to the data file.
   * @return An optional map where each year is mapped to a list of drivers with their points and wins.
   */
  def parseData(filePath: String): Option[Map[Int, List[(String, Float, Int)]]] = {
    Try {
      val source = Source.fromFile(filePath)
      try {
        source.getLines().flatMap(parseLine).toMap
      } finally {
        source.close()
      }
    } match {
      case Success(data) if data.nonEmpty => Some(data)
      case Success(_) =>
        println("Data file is empty or incorrectly formatted. Please check the file.")
        None
      case Failure(e) =>
        println(s"Error reading file: ${e.getMessage}. Ensure the file exists and is correctly formatted.")
        None
    }
  }

  /**
   * Parses a single line of input into a season entry.
   *
   * @param line A line from the data file.
   * @return An optional tuple where the key is the season year and the value is a list of driver statistics.
   */
  private def parseLine(line: String): Option[(Int, List[(String, Float, Int)])] = {
    val parts = line.split(",", 2)
    for {
      year <- Try(parts(0).toInt).toOption
      drivers = parts.lift(1).map(parseDrivers).getOrElse(Nil)
    } yield year -> drivers
  }

  /**
   * Parses driver statistics from a comma-separated string.
   *
   * @param input The string containing driver details.
   * @return A list of tuples containing driver name, points, and wins.
   */
  private def parseDrivers(input: String): List[(String, Float, Int)] = {
    input.split(",").toList.flatMap { entry =>
      entry.split(":").toList match {
        case List(name, stats) =>
          stats.split(" ").toList match {
            case List(points, wins) =>
              for {
                p <- Try(points.toFloat).toOption
                w <- Try(wins.toInt).toOption
              } yield (name.trim, p, w)
            case _ => None
          }
        case _ => None
      }
    }
  }
}