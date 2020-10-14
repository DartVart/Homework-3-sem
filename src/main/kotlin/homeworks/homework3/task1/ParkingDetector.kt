package homeworks.homework3.task1

class ParkingDetector(parkingStateHandler: ParkingStateHandler) {
    private val parkingRequester = ParkingRequester(parkingStateHandler)

    /**
     * @return false if there was an attempt to add the car in a full parking lot
     * */
    suspend fun tryToEnter() = parkingRequester.tryToAddCar()

    /**
     * @return false if there was an attempt to remove the car from an empty parking lot
     * */
    suspend fun tryToExit() = parkingRequester.tryToRemoveCar()
}
