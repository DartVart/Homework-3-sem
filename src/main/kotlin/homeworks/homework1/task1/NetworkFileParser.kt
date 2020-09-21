package homeworks.homework1.task1

import java.io.File
import java.io.FileNotFoundException
import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException
import java.util.Scanner
import kotlin.IllegalStateException

class NetworkFileParser {
    /**
     * @throws FileNotFoundException if file not found
     * @throws NoSuchElementException if the required line was not found
     * @throws IndexOutOfBoundsException if incorrect computer index on some virus
     * @throws NumberFormatException no number in the place where it should be
     *         (probability of infection, computer index, matrix number)
     * @throws IllegalStateException if no suitable data was found in the some line.
     *         For example, an empty line, or a line with data not according to the pattern,
     *         little data in the matrix, the wrong number
     *         or the operating system of the computer is incorrect
     * */
    fun importNetwork(inputFile: File): NetworkData {
        if (!inputFile.exists()) {
            throw FileNotFoundException("File \"${inputFile.path}\" not found.")
        }

        val scan = Scanner(inputFile)

        val operatingSystems = importOperatingSystems(scan.nextLine())
        val computers = importComputers(scan.nextLine(), operatingSystems)

        val unparsedConnectionsData = mutableListOf<String>()
        repeat(computers.size) {
            unparsedConnectionsData.add(scan.nextLine())
        }
        val computersNetworkData = getComputersNetworkData(unparsedConnectionsData, computers)

        val viruses = importAndInstallViruses(scan.nextLine(), computers)

        return NetworkData(computersNetworkData, viruses)
    }

    private fun importOperatingSystems(textLine: String): Set<OperatingSystem> {
        val operatingSystems = mutableSetOf<OperatingSystem>()
        getParsedPairs(textLine).forEach {
            try {
                val infectionProbability = it.second.toDouble()
                check(infectionProbability in 0.0..1.0) {
                    "The infection probability at ${it.first} must be between 0 and 1"
                }
                operatingSystems.add(OperatingSystem(it.first, infectionProbability))
            } catch (exception: NumberFormatException) {
                throw NumberFormatException("The infection probability at ${it.first} not a number")
            }
        }
        check(operatingSystems.isNotEmpty()) { "No operating systems found" }
        return operatingSystems
    }

    private fun importComputers(textLine: String, operatingSystems: Set<OperatingSystem>): MutableList<Computer> {
        check(textLine.isNotBlank()) { "The line with computers is blank" }
        val computers = mutableListOf<Computer>()
        parseItemsSeparatedByWhitespace(textLine).forEach { currentOperatingSystemName ->
            val operatingSystem = operatingSystems.find { it.name == currentOperatingSystemName }
                ?: throw IllegalStateException("Operating system \"$currentOperatingSystemName\" not defined")
            computers.add(Computer(operatingSystem))
        }
        return computers
    }

    private fun getComputersNetworkData(
        textLines: List<String>,
        computers: List<Computer>
    ): MutableList<ComputerNetworkData> {
        val computersData = mutableListOf<ComputerNetworkData>()
        textLines.forEachIndexed { rowIndex, textLine ->
            val connectedComputers = mutableSetOf<Computer>()
            val numbers = parseItemsSeparatedByWhitespace(textLine)
            check(numbers.size == computers.size) { "Few numbers in the adjacency matrix row" }
            numbers.forEachIndexed { columnIndex, numberAsString ->
                try {
                    val number = numberAsString.toInt()
                    check(number == 1 || number == 0) { "Not a suitable number in an adjacency matrix" }
                    if (number == 1) {
                        connectedComputers.add(computers[columnIndex])
                    }
                } catch (exception: NumberFormatException) {
                    throw NumberFormatException("The adjacency matrix must contain only numbers")
                }
            }
            computersData.add(ComputerNetworkData(computers[rowIndex], connectedComputers))
        }
        return computersData
    }

    private fun importAndInstallViruses(textLine: String, computers: List<Computer>): MutableSet<Virus> {
        val viruses = mutableSetOf<Virus>()
        getParsedPairs(textLine).forEach { it ->
            try {
                val virus = Virus(it.first)
                viruses.add(virus)
                val indexesOfInfectedComputers = parseItemsSeparatedByWhitespace(it.second).map { it.toInt() - 1 }

                indexesOfInfectedComputers.forEach { index ->
                    check(index in computers.indices) { "Incorrect computer index on virus ${it.first}" }
                    computers[index].infectUnconditionally(virus)
                }
            } catch (exception: NumberFormatException) {
                throw NumberFormatException("Unable to determine the computer index on virus ${it.first}")
            }
        }
        check(viruses.isNotEmpty()) { "No viruses found" }
        return viruses
    }

    /**
     * Reads entries of the form "name(value)" from [text]
     *
     * @return a sequence of pairs,
     *         where the first part of the pair contains the name,
     *         and the second contains the value.
     * */
    private fun getParsedPairs(text: String): Sequence<Pair<String, String>> {
        return Regex("""(\S+)\(([^()]*)\)""").findAll(text).map {
            val (name, value) = it.destructured
            Pair(name, value)
        }
    }

    private fun parseItemsSeparatedByWhitespace(text: String): List<String> = text.trim().split(Regex(" +"))
}
