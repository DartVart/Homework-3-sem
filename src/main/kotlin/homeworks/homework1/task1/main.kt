package homeworks.homework1.task1

import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException
import java.lang.NumberFormatException
import kotlin.NoSuchElementException

/**
 * Tries to read a positive integer from the console until the integer is entered correctly
 * */
fun scanPositiveInteger(): Int {
    var number: Int? = null
    while (number == null || number <= 0) {
        try {
            number = readLine()?.toInt()
        } catch (exception: NumberFormatException) {
            println("Please, enter a single integer.")
            continue
        }

        when {
            number == null -> {
                println("Please, try to enter an integer again.")
            }
            number <= 0 -> {
                println("Please, enter a positive integer.")
            }
        }
    }
    return number
}

/**
 * Tries to read a positive integer from the console until "yes" or "no" is entered
 * */
fun scanYesNoAnswer(): String {
    var answer = readLine()
    while (answer == null || (answer != "yes" && answer != "no")) {
        println(
            if (answer == null) {
                "An error occurred while entering a string. Please enter again."
            } else {
                "Please enter either \"yes\" or \"no\""
            }
        )
        answer = readLine()
    }
    return answer
}

fun printIntroduction(pathToBootFile: String, pathToExampleFile: String) {
    println("Hi! This is a simulation of a network with infections.")
    println("Network data will be loaded from a file: $pathToBootFile.")
    println("If you want to change the network settings,")
    println("this file contains an illustrative example of the configuration file: $pathToExampleFile")
    println("You only need to enter the number of moves and the delay between moves.")
}

fun onParsingException(exception: Exception) {
    println("Parsing error: ${exception.message}")
}

/**
 * @return null if network is not loaded
 * */
fun importNetworkFromFile(inputFile: File): NetworkData? {
    try {
        return NetworkFileParser().importNetwork(inputFile)
    } catch (exception: IllegalStateException) {
        onParsingException(exception)
    } catch (exception: IndexOutOfBoundsException) {
        onParsingException(exception)
    } catch (exception: NumberFormatException) {
        onParsingException(exception)
    } catch (exception: NoSuchElementException) {
        onParsingException(exception)
    } catch (exception: FileNotFoundException) {
        onParsingException(exception)
    }
    return null
}

fun main() {
    val pathToBootFile = "./src/main/resources/homeworks/homework1/task1/network.txt"
    val pathToExampleFile = "./src/main/resources/homeworks/homework1/task1/explanatoryFile.txt"

    printIntroduction(pathToBootFile, pathToExampleFile)
    println("Do you want to continue? (yes/no)")
    if (scanYesNoAnswer() == "yes") {
        val inputFile = File(pathToBootFile)
        val networkData = importNetworkFromFile(inputFile)
        if (networkData != null) {
            println("Network successfully loaded from file.")
            val network = NetworkSimulator(networkData)

            println("Enter the number of moves the network should make:")
            val numberOfMoves = scanPositiveInteger()

            println("Enter the delay that will occur between moves (millis):")
            val delay = scanPositiveInteger().toLong()
            println("Network started...")
            println()
            network.run(numberOfMoves, delay)
            println("Network took all steps.")
        } else {
            println("Failed to load network from file.")
        }
    }
}
