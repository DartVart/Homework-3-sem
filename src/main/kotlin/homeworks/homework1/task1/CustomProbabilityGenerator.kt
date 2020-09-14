package homeworks.homework1.task1

class CustomProbabilityGenerator(array: DoubleArray) : ProbabilityGenerator {
    val iterator = array.iterator()

    /**
     * @throws NoSuchElementException if ran out of available numbers
     * */
    override fun getNextProbability(): Double {
        return if (iterator.hasNext()) {
            iterator.next()
        } else {
            throw NoSuchElementException("Ran out of available numbers")
        }
    }
}
