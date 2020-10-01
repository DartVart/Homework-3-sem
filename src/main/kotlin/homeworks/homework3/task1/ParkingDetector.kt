package homeworks.homework3.task1

class ParkingDetector(private val parkingStateHandler: ParkingStateHandler) {
    /**
     * @return false if there was an attempt to add the car in a full parking lot
     * */
    suspend fun tryToEnter(): Boolean {
        // with a real server, there would be an asynchronous function that receives a response from it.
        return parkingStateHandler.tryToAddCar()
    }

    /**
     * @return false if there was an attempt to remove the car from an empty parking lot
     * */
    suspend fun tryToExit(): Boolean {
        // with a real server, there would be an asynchronous function that receives a response from it.
        return parkingStateHandler.tryToRemoveCar()
    }
}
