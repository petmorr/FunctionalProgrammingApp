import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class UserInterfaceTest extends AnyFunSuite {
  val testData = Map(
    2023 -> List(("Max Verstappen", 575.0f, 19), ("Sergio Perez", 285.0f, 2), ("Lewis Hamilton", 234.0f, 0)),
    2022 -> List(("Max Verstappen", 454.0f, 15), ("Charles Leclerc", 308.0f, 3), ("George Russell", 275.0f, 1)),
    2021 -> List(("Lewis Hamilton", 387.5f, 8), ("Max Verstappen", 395.5f, 10), ("Valtteri Bottas", 226.0f, 1))
  )

  /**
   * Captures console output while executing a test block.
   */
  def withOutput(testCode: => Unit): String = {
    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)) {
      testCode
    }
    outputStream.toString
  }

  test("Show season winners should display correct winners") {
    val simulatedInput = List("1", "4").iterator // Exit menu after executing option 1
    val output = withOutput {
      UserInterface.showMenu(testData, () => simulatedInput.next())
    }

    assert(output.contains("2023: Winner - Max Verstappen, Points: 575.0, Wins: 19"))
    assert(output.contains("2022: Winner - Max Verstappen, Points: 454.0, Wins: 15"))
  }

  test("Display results for a specific year should show all drivers in that season with positions") {
    val simulatedInput = List("2", "2023", "4").iterator // Select year and exit
    val output = withOutput {
      UserInterface.showMenu(testData, () => simulatedInput.next())
    }

    assert(output.contains("Position 1: Driver - Max Verstappen, Points: 575.0, Wins: 19"))
    assert(output.contains("Position 2: Driver - Sergio Perez, Points: 285.0, Wins: 2"))
    assert(output.contains("Position 3: Driver - Lewis Hamilton, Points: 234.0, Wins: 0"))
  }

  test("Total points for a specific driver should display correct total") {
    val simulatedInput = List("6", "Max Verstappen", "4").iterator // Enter driver name and exit
    val output = withOutput {
      UserInterface.showMenu(testData, () => simulatedInput.next())
    }

    assert(output.contains("Total points for Max Verstappen: 1424.5"))
  }

  test("Invalid menu option should display an error message") {
    val simulatedInput = List("9", "4").iterator // Enter invalid option and exit
    val output = withOutput {
      UserInterface.showMenu(testData, () => simulatedInput.next())
    }

    assert(output.contains("Invalid choice, please enter a number from 1 to 6."))
  }
}