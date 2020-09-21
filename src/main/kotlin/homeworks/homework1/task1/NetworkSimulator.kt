package homeworks.homework1.task1

class NetworkSimulator(val networkData: NetworkData) {
    private fun spreadViruses(probabilityGenerator: ProbabilityGenerator) {
        networkData.viruses.forEach { virus ->
            networkData.computersData.filter { it.computer.isInfected(virus) }.forEach { it ->
                it.connectedComputers.forEach {
                    it.tryToGetInfected(virus, probabilityGenerator)
                }
            }
        }
    }

    private fun printState() {
        networkData.computersData.forEachIndexed { index, computerData ->
            println("#${index + 1} - ${computerData.computer}")
        }
    }

    private fun printIntroduction() {
        println("================NETWORK=================")
        println("-------------Initial state-----------")
        printState()
        println("-------------------------------------")
    }

    private fun printConclusion() {
        println("========================================")
    }

    private fun printStateAfterMove(numberOfMove: Int) {
        println("----------------move №$numberOfMove--------------")
        printState()
        println("-------------------------------------")
    }

    /**
     * Prints its state after each move
     * @param probabilityGenerator the default is a [RandomProbabilityGenerator]
     * */
    fun run(
        numberOfMoves: Int,
        delay: Long,
        probabilityGenerator: ProbabilityGenerator = RandomProbabilityGenerator()
    ) {
        printIntroduction()
        Thread.sleep(delay)
        for (numberOfMove in 1..numberOfMoves) {
            spreadViruses(probabilityGenerator)
            printStateAfterMove(numberOfMove)
            Thread.sleep(delay)
        }
        printConclusion()
    }
}
