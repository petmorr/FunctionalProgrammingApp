# 🏎️ Formula 1 Statistics Analyser 🏁

## **Overview**
The **Formula 1 Statistics Analyser** is a Scala-based functional programming project that processes, analyses, and displays Formula 1 season data. It follows **functional programming principles**, ensuring **clean, composable, and efficient** code.

This project is an **excellent demonstration** of:
- **Pure functions and immutability**
- **Higher-order functions**
- **Function composition**
- **Pattern matching**
- **Recursion and map/filter/reduce transformations**
- **Modular and maintainable architecture**

## **Project Structure**

```
F1StatsAnalyser/
│── .idea/                              # Intellij files
│── project/                            # SBT project files
│── src/                                # Default code directory
    │── main/scala                      # Unit tests using ScalaTest
        │── Analyses.scala              # Functional data processing and calculations
        │── DataParser.scala            # Functional file reading and parsing logic
        │── UserInterface.scala         # Composable CLI user interface
        │── F1StatsApp.scala            # Entry point for running the application
    │── tests/                          # Unit tests using ScalaTest
    │   │── AnalysesTest.scala          # Tests for Analyses.scala
    │   │── DataParserTest.scala        # Tests for DataParser.scala
    │   │── UserInterfaceTest.scala     # Tests for UserInterface.scala
│── target/                             # SBT target files
│── README.md                           # Documentation (this file)
│── build.sbt                           # SBT build configuration
│── data.txt                            # Dataset used for this project
```

---

## **How It Works**
### 🏁 **1. Functional Data Parsing**
The **DataParser** reads the file and uses **pure functions** to transform it into a structured format.

```scala
def parseData(filePath: String): Option[Map[Int, List[(String, Float, Int)]]] = {
  Try(Source.fromFile(filePath).getLines().flatMap(parseLine).toMap) match {
    case Success(data) if data.nonEmpty => Some(data)
    case _ => None
  }
}
```
➡️ Uses pattern matching for safe file handling.

➡️ No mutations—returns a new immutable `Map`.

---

### 🏆 **2. Functional Data Analysis**
The **Analyses** object processes Formula 1 data **without side effects**, making it predictable and testable.

```scala 
def getSeasonWinners(data: Map[Int, List[(String, Float, Int)]], ascending: Boolean = true): Map[Int, (String, Float, Int)] = {
  data.toSeq.sortBy(_._1 * (if (ascending) 1 else -1))
    .collect { case (year, drivers) if drivers.nonEmpty => year -> drivers.head }
    .toMap
}
```

➡️ **Higher-order functions** (`sortBy`, `collect`) keep the code concise.

➡️ **Pure function**-doesn't mutate `data`, just transforms it.

---

### 🎮 **3. Functional User Interaction**
The **UserInterface** object follows a **functional approach to menu handling**. Instead of using `if` or `switch` statements, we use a **function map**.

```scala
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
```
➡️ Higher-order function mapping allows clean, scalable menu handling.

➡️ Avoids imperative `if-else` chains—pure functional lookup.

---

## Getting Started

### 💻 **1. Installation**
Make sure you have **Scala** and **sbt** installed.
```sh
# Clone this repository
git clone https://github.com/yourusername/FunctionalProgrammingApp.git

# Navigate to the project directory
cd FunctionalProgrammingApp
```

### 🚀 2. Running the Application
To start the Formula 1 Statistics Analyser, run:
```sh
sbt run
```

### **🧪 3. Running Tests**
This project includes **unit tests using ScalaTest**. Run all tests with:
```sh
sbt test
```

---

## Functional Programming Highlights 🚀
This project fully embraces functional programming in Scala:

- ✅ **Pure Functions** – No mutable state, every function is referentially transparent.
- ✅ **Higher-Order Functions** – Used in menu actions, sorting, and filtering operations.
- ✅ **Immutability** – Uses `Map`, `List`, and `Option` for data handling.
- ✅ **Function Composition** – Small, reusable functions composed into larger functionalities.
- ✅ **Pattern Matching** – Used extensively for safe and concise logic.
- ✅ **Type Safety** – Leveraging Scala's powerful type system to prevent runtime errors.

---

This was a project for my Programming Paradigms module at University that I thoroughly enjoyed and decided to work on further.