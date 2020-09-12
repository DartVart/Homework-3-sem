package homeworks.homework1.task1

import kotlin.random.Random

class Computer(val operatingSystem: OperatingSystem) {
    var viruses = mutableSetOf<Virus>()

    fun infectUnconditionally(virus: Virus) {
        viruses.add(virus)
    }

    fun isInfected(virus: Virus) = viruses.contains(virus)

    fun tryToGetInfected(virus: Virus) {
        val randomDouble = Random.nextDouble(1.00)
        if (!isInfected(virus) && randomDouble <= operatingSystem.infectionProbability) {
            viruses.add(virus)
        }
    }

    override fun toString(): String {
        val virusesDataAsString = if (viruses.isEmpty()) "-" else viruses.joinToString(", ") { it.name }
        return "${operatingSystem.name}: $virusesDataAsString"
    }
}
