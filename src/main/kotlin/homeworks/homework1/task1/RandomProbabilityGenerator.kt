package homeworks.homework1.task1

import kotlin.random.Random

class RandomProbabilityGenerator : ProbabilityGenerator {
    override fun getNextProbability() = Random.nextDouble(1.00)
}
