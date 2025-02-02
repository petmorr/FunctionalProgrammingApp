/**
 * Main entry point for the F1 statistics application.
 */
object F1StatsApp {

  /**
   * Main method where the program initializes.
   *
   * @param args Command line arguments.
   */
  def main(args: Array[String]): Unit = {
    // Load and parse data from the file
    DataParser.parseData("data.txt") match {
      case Some(parsedData) => UserInterface.showMenu(parsedData)
      case None => println("Error reading data file. Exiting application.")
    }
  }
}