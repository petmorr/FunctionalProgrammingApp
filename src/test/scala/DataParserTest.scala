import org.scalatest.funsuite.AnyFunSuite
import java.io.{File, PrintWriter}
import scala.util.Using

class DataParserTest extends AnyFunSuite {
  def withTempFile(content: String)(testCode: String => Any): Unit = {
    val tempFile = File.createTempFile("testData", ".txt")
    tempFile.deleteOnExit()
    Using.resource(new PrintWriter(tempFile)) { writer =>
      writer.write(content)
    }
    testCode(tempFile.getAbsolutePath)
  }

  test("parseData should correctly parse valid data") {
    val content = """
                    |2023,Max Verstappen: 575 19,Sergio Perez: 285 2,Lewis Hamilton: 234 0
                    |2022,Max Verstappen: 454 15,Charles Leclerc: 308 3,Sergio Perez: 305 2
                    |""".stripMargin

    withTempFile(content) { filePath =>
      val result = DataParser.parseData(filePath)
      assert(result.isDefined)
      assert(result.get.size == 2)
    }
  }

  test("parseData should return None for an empty file") {
    withTempFile("") { filePath =>
      assert(DataParser.parseData(filePath).isEmpty)
    }
  }

  test("parseData should handle incorrect formatting gracefully") {
    val content = "Invalid line"
    withTempFile(content) { filePath =>
      assert(DataParser.parseData(filePath).isEmpty)
    }
  }
}