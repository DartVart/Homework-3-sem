package homeworks.homework3.task1

import java.util.concurrent.atomic.AtomicInteger

class ParkingStateHandler(private val numberOfPlaces: Int) {
    private val numberOfOccupiedPlaces = AtomicInteger(0)

    fun getNumberOfOccupiedPlaces() = numberOfOccupiedPlaces.get()

    /**
     * @return false if there was an attempt to add the car in a full parking lot
     * */
    fun tryToAddCar() = numberOfOccupiedPlaces.getAndUpdate { previousNumber ->
        if (previousNumber == numberOfPlaces) previousNumber else previousNumber + 1
    } < numberOfPlaces

    /**
     * @return false if there was an attempt to remove the car from an empty parking lot
     * */
    fun tryToRemoveCar() = numberOfOccupiedPlaces.getAndUpdate { previousNumber ->
        if (previousNumber == 0) previousNumber else previousNumber - 1
    } > 0
}
