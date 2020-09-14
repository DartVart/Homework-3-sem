package homeworks.homework1.task1

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

const val PATH_TO_DIRECTORY_WITH_TEXTS = "./src/test/resources/homeworks/homework1/task1/filesWithoutMistakes"

internal class NetworkSimulatorTest {
    private val networkFileParser = NetworkFileParser()

    /**
     * @param filePath path to file from directory from [PATH_TO_DIRECTORY_WITH_TEXTS]
     * */
    private fun getNetwork(filePath: String): NetworkSimulator {
        val file = File("$PATH_TO_DIRECTORY_WITH_TEXTS/$filePath")
        val networkData = networkFileParser.importNetwork(file)
        return NetworkSimulator(networkData)
    }

    private fun isComputerInfected(networkSimulator: NetworkSimulator, computerIndex: Int, virus: Virus) =
        networkSimulator.networkData.computersData[computerIndex].computer.isInfected(virus)

    private fun checkNetworkInfection(
        networkSimulator: NetworkSimulator,
        expectedIndexesOfInfectedComputers: List<Int>,
        expectedIndexesOfUninfectedComputers: List<Int>,
        virus: Virus
    ): Boolean {
        expectedIndexesOfInfectedComputers.forEach {
            if (!isComputerInfected(networkSimulator, it, virus)) {
                return false
            }
        }
        expectedIndexesOfUninfectedComputers.forEach {
            if (isComputerInfected(networkSimulator, it, virus)) {
                return false
            }
        }
        return true
    }

    @Nested
    inner class UnambiguousInfection {
        @Nested
        inner class SmallNetwork {
            private val virus = Virus("MrDeath")
            private val filePath = "unitProbability/smallNetwork.txt"

            @Test
            fun run_0move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(0, 0)
                val indexesOfInfectedComputers = listOf(0)
                val indexesOfUninfectedComputers = listOf(1, 2, 3)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_1move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(1, 0)
                val indexesOfInfectedComputers = listOf(0, 1, 3)
                val indexesOfUninfectedComputers = listOf(2)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_2move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(2, 0)
                val indexesOfInfectedComputers = listOf(0, 1, 2, 3)
                val indexesOfUninfectedComputers = listOf<Int>()

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }
        }

        @Nested
        inner class TwoViruses {
            private val filePath = "unitProbability/twoViruses.txt"
            private val firstVirus = Virus("Lukavyj")
            private val secondVirus = Virus("Insidious")

            @Test
            fun run_firstVirus_0move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(0, 0)
                val indexesOfInfectedComputers = listOf(0)
                val indexesOfUninfectedComputers = listOf(1, 2, 3, 4, 5, 6, 7)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        firstVirus
                    )
                )
            }

            @Test
            fun run_firstVirus_1move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(1, 0)
                val indexesOfInfectedComputers = listOf(0, 2)
                val indexesOfUninfectedComputers = listOf(1, 3, 4, 5, 6, 7)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        firstVirus
                    )
                )
            }

            @Test
            fun run_firstVirus_2move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(2, 0)
                val indexesOfInfectedComputers = listOf(0, 2, 1, 5, 3)
                val indexesOfUninfectedComputers = listOf(4, 6, 7)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        firstVirus
                    )
                )
            }

            @Test
            fun run_firstVirus_3move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(3, 0)
                val indexesOfInfectedComputers = listOf(0, 2, 1, 5, 3, 4, 6)
                val indexesOfUninfectedComputers = listOf(7)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        firstVirus
                    )
                )
            }

            @Test
            fun run_firstVirus_4move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(4, 0)
                val indexesOfInfectedComputers = listOf(0, 2, 1, 5, 3, 4, 6, 7)
                val indexesOfUninfectedComputers = listOf<Int>()

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        firstVirus
                    )
                )
            }

            @Test
            fun run_secondVirus_0move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(0, 0)
                val indexesOfInfectedComputers = listOf(7, 4)
                val indexesOfUninfectedComputers = listOf(0, 1, 2, 3, 5, 6)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        secondVirus
                    )
                )
            }

            @Test
            fun run_secondVirus_1move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(1, 0)
                val indexesOfInfectedComputers = listOf(7, 6, 4, 1)
                val indexesOfUninfectedComputers = listOf(0, 2, 3, 5)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        secondVirus
                    )
                )
            }

            @Test
            fun run_secondVirus_2move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(2, 0)
                val indexesOfInfectedComputers = listOf(7, 6, 3, 2, 1, 4)
                val indexesOfUninfectedComputers = listOf(0, 5)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        secondVirus
                    )
                )
            }

            @Test
            fun run_secondVirus_3move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                networkSimulator.run(3, 0)
                val indexesOfInfectedComputers = listOf(7, 6, 3, 2, 1, 4, 0, 5)
                val indexesOfUninfectedComputers = listOf<Int>()

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        secondVirus
                    )
                )
            }
        }
    }

    @Nested
    inner class ZeroProbabilityOfInfection {
        private val virus = Virus("ElectronicKiller")
        private val filePath = "zeroProbability/simpleNetwork.txt"

        @Test
        fun run_0move_mustWork() {
            val networkSimulator = getNetwork(filePath)
            networkSimulator.run(1, 0)
            val indexesOfInfectedComputers = listOf(0)
            val indexesOfUninfectedComputers = listOf(1, 2, 3, 4)

            assertTrue(
                checkNetworkInfection(
                    networkSimulator,
                    indexesOfInfectedComputers,
                    indexesOfUninfectedComputers,
                    virus
                )
            )
        }

        @Test
        fun run_1000move_mustWork() {
            val networkSimulator = getNetwork(filePath)
            networkSimulator.run(1000, 0)
            val indexesOfInfectedComputers = listOf(0)
            val indexesOfUninfectedComputers = listOf(1, 2, 3, 4)

            assertTrue(
                checkNetworkInfection(
                    networkSimulator,
                    indexesOfInfectedComputers,
                    indexesOfUninfectedComputers,
                    virus
                )
            )
        }
    }

    // The same probabilities are taken in each individual test.
    // This is done in order to avoid differences in the sequence
    // of applying the probabilities to the computers.
    @Nested
    inner class ProbabilityBetween0And1 {

        @Nested
        inner class TwoInitiallyInfectedComputers {
            private val filePath = "probabilityBetween0And1/twoInitiallyInfectedComputers.txt"
            private val virus = Virus("MyDoom")

            @Test
            fun run_2move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(14) { 0.6 }
                networkSimulator.run(2, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(0, 8, 2, 5, 1, 3)
                val indexesOfUninfectedComputers = listOf(4, 6, 7)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_1move_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(3) { 0.6 }
                networkSimulator.run(1, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(0, 2, 5, 8)
                val indexesOfUninfectedComputers = listOf(1, 4, 7, 3, 6)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }
        }

        @Nested
        inner class TwoViruses {
            private val filePath = "probabilityBetween0And1/twoViruses.txt"

            @Test
            fun run_firstVirus_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(10) { 0.4 }
                networkSimulator.run(2, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(0, 1, 3)
                val indexesOfUninfectedComputers = listOf(4, 2)
                val virus = Virus("Virus_1")

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_secondVirus_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(10) { 0.4 }
                networkSimulator.run(2, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(3, 4, 1)
                val indexesOfUninfectedComputers = listOf(0, 2)
                val virus = Virus("Virus_2")

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }
        }

        @Nested
        inner class CompleteGraph {
            private val filePath = "probabilityBetween0And1/completeGraph.txt"
            private val virus = Virus("Virus_1")

            @Test
            fun run_infectsImmediately_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(3) { 0.1 }
                networkSimulator.run(1, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(0, 1, 2, 3)
                val indexesOfUninfectedComputers = listOf<Int>()

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_withoutInfected_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(9) { 0.9 }
                networkSimulator.run(3, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(0)
                val indexesOfUninfectedComputers = listOf(1, 2, 3)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }
        }

        @Nested
        inner class BigNetwork {
            private val filePath = "probabilityBetween0And1/bigNetwork.txt"
            private val virus = Virus("Big")

            @Test
            fun run_firstProbability_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(37) { 0.7 }
                networkSimulator.run(3, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(6, 0, 2, 3, 14, 8, 5, 9, 10)
                val indexesOfUninfectedComputers = listOf(7, 1, 11, 12, 13)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }

            @Test
            fun run_secondProbability_mustWork() {
                val networkSimulator = getNetwork(filePath)
                val probabilities = DoubleArray(43) { 0.4 }
                networkSimulator.run(3, 0, CustomProbabilityGenerator(probabilities))
                val indexesOfInfectedComputers = listOf(6, 0, 2, 3, 14, 8, 5, 9, 10, 1, 13)
                val indexesOfUninfectedComputers = listOf(7, 11, 12)

                assertTrue(
                    checkNetworkInfection(
                        networkSimulator,
                        indexesOfInfectedComputers,
                        indexesOfUninfectedComputers,
                        virus
                    )
                )
            }
        }
    }
}
