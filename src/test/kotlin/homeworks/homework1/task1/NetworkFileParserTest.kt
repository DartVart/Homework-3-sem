package homeworks.homework1.task1

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import java.io.File
import java.io.FileNotFoundException
import java.lang.NumberFormatException
import kotlin.NoSuchElementException

const val PATH_TO_DIRECTORY_WITH_ERRONEOUS_TEXTS = "./src/test/resources/homeworks/homework1/task1/filesWithMistakes"
const val PATH_TO_DIRECTORY_WITH_TEXTS_WITHOUT_MISTAKES =
    "./src/test/resources/homeworks/homework1/task1/filesWithoutMistakes"

internal class NetworkFileParserTest {
    private val networkFileParser = NetworkFileParser()

    private fun parseFile(fileName: String, pathToDirectory: String): NetworkData {
        val file = File("$pathToDirectory/$fileName")
        return networkFileParser.importNetwork(file)
    }

    private fun parseFileWithoutMistakes(fileName: String): NetworkData =
        parseFile(fileName, PATH_TO_DIRECTORY_WITH_TEXTS_WITHOUT_MISTAKES)

    private fun parseErroneousFile(fileName: String): NetworkData =
        parseFile(fileName, PATH_TO_DIRECTORY_WITH_ERRONEOUS_TEXTS)

    @Test
    fun importNetwork_checkNumberOfComputers_mustWork() {
        val networkData = parseFileWithoutMistakes("smallNetwork.txt")
        assertEquals(4, networkData.computersData.size)
    }

    @Test
    fun importNetwork_checkViruses_mustWork() {
        val networkData = parseFileWithoutMistakes("twoViruses.txt")
        assertTrue(networkData.viruses.contains(Virus("Lukavyj")) && networkData.viruses.contains(Virus("Insidious")))
    }

    @Test
    fun importNetwork_checkOs_mustWork() {
        val networkData = parseFileWithoutMistakes("smallNetwork.txt")
        val computers = networkData.computersData.map { it.computer }
        val windows = OperatingSystem("Windows", 1.0)
        val linux = OperatingSystem("Linux", 1.0)
        val macOs = OperatingSystem("MacOS", 1.0)
        println(computers[0].operatingSystem)
        assertTrue(
            computers[0].operatingSystem == windows &&
                    computers[1].operatingSystem == linux &&
                    computers[2].operatingSystem == linux &&
                    computers[3].operatingSystem == macOs
        )
    }

    @Test
    fun importNetwork_checkConnections_mustWork() {
        val networkData = parseFileWithoutMistakes("smallNetwork.txt")
        val computersData = networkData.computersData
        val computers = computersData.map { it.computer }
        assertTrue(
            computersData[0].connectedComputers.containsAll(listOf(computers[1], computers[3])) &&
                    computersData[1].connectedComputers.containsAll(listOf(computers[0], computers[2])) &&
                    computersData[2].connectedComputers.contains(computers[1]) &&
                    computersData[3].connectedComputers.contains(computers[0])
        )
    }

    @Nested
    inner class ThrowException {
        @Test
        fun fileNotFound_throwsException() {
            assertThrows(FileNotFoundException::class.java) { parseErroneousFile("nonexistentFile.txt") }
        }

        @Test
        fun lineNotFound_throwsException() {
            assertThrows(NoSuchElementException::class.java) { parseErroneousFile("lineNotFound.txt") }
        }

        @Test
        fun wrongNumberInComputerIndex_throwsException() {
            assertThrows(IllegalStateException::class.java) {
                parseErroneousFile("wrongNumberInComputerIndex.txt")
            }
        }

        @Test
        fun notNumberInPlaceOfComputerIndex_throwsException() {
            assertThrows(NumberFormatException::class.java) {
                parseErroneousFile("notNumberInPlaceOfComputerIndex.txt")
            }
        }

        @Test
        fun notNumberInMatrix_throwsException() {
            assertThrows(NumberFormatException::class.java) { parseErroneousFile("notNumberInMatrix.txt") }
        }

        @Test
        fun wrongNumberInMatrix_throwsException() {
            assertThrows(IllegalStateException::class.java) { parseErroneousFile("wrongNumberInMatrix.txt") }
        }

        @Test
        fun fewNumbersInMatrix_throwsException() {
            assertThrows(IllegalStateException::class.java) { parseErroneousFile("fewNumbersInMatrix.txt") }
        }

        @Test
        fun wrongOsOnComputer_throwsException() {
            assertThrows(IllegalStateException::class.java) { parseErroneousFile("wrongOsOnComputer.txt") }
        }

        @Test
        fun noProperlyDescribedOs_throwsException() {
            assertThrows(IllegalStateException::class.java) { parseErroneousFile("noProperlyDescribedOs.txt") }
        }

        @Test
        fun notNumberInPlaceOfInfectionProbability_throwsException() {
            assertThrows(NumberFormatException::class.java) {
                parseErroneousFile("notNumberInPlaceOfInfectionProbability.txt")
            }
        }

        @Test
        fun wrongNumberInInfectionProbability_throwsException() {
            assertThrows(IllegalStateException::class.java) {
                parseErroneousFile("wrongNumberInInfectionProbability.txt")
            }
        }

        @Test
        fun noProperlyDescribedVirus_throwsException() {
            assertThrows(IllegalStateException::class.java) {
                parseErroneousFile("noProperlyDescribedVirus.txt")
            }
        }
    }
}
