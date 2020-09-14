package homeworks.homework1.task1

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

const val PATH_TO_DIRECTORY_WITH_TEXTS = "./src/test/resources/homeworks/homework1/task1/filesWithoutMistakes"

internal class NetworkSimulatorTest {
    private val networkFileParser = NetworkFileParser()
    private fun getNetwork(fileName: String): NetworkSimulator {
        val file = File("$PATH_TO_DIRECTORY_WITH_TEXTS/$fileName")
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
            private val fileName = "smallNetwork.txt"

            @Test
            fun run_0move_mustWork() {
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
            private val fileName = "twoViruses.txt"
            private val firstVirus = Virus("Lukavyj")
            private val secondVirus = Virus("Insidious")

            @Test
            fun run_firstVirus_0move_mustWork() {
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
                val networkSimulator = getNetwork(fileName)
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
    inner class ZeroChanceOfInfection {
        private val virus = Virus("ElectronicKiller")
        private val fileName = "zeroChanceOfInfection.txt"

        @Test
        fun run_0move_mustWork() {
            val networkSimulator = getNetwork(fileName)
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
            val networkSimulator = getNetwork(fileName)
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
}
