package homeworks.homework3.task1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParkingRequester(private val parkingStateHandler: ParkingStateHandler) {
    suspend fun tryToAddCar() = withContext(Dispatchers.Default) {
        parkingStateHandler.tryToAddCar()
    }

    suspend fun tryToRemoveCar() = withContext(Dispatchers.Default) {
        parkingStateHandler.tryToRemoveCar()
    }
}
