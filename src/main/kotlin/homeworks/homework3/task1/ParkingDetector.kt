package homeworks.homework3.task1

class ParkingDetector(private val parkingStateHandler: ParkingStateHandler) {
    suspend fun tryToEnter(): Boolean {
        // with a real server, there would be an asynchronous function that receives a response from it.
        return parkingStateHandler.tryToAddCar()
    }

    suspend fun tryToExit(): Boolean {
        // with a real server, there would be an asynchronous function that receives a response from it.
        return parkingStateHandler.tryToRemoveCar()
    }
}
