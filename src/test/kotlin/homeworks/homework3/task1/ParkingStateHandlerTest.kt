package homeworks.homework3.task1

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class ParkingStateHandlerTest {
    @Test
    fun checkAddReturn_oneCar_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        assertTrue(parkingStateHandler.tryToAddCar())
    }

    @Test
    fun checkAdd_oneCar_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        parkingStateHandler.tryToAddCar()
        assertEquals(1, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @Test
    fun checkAdd_moreCarsThanPlaces_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100)
        val numberOfCars = numberOfPlaces + Random.nextInt(0, 100)
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        repeat(numberOfCars) {
            parkingStateHandler.tryToAddCar()
        }
        assertEquals(numberOfPlaces, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @Test
    fun checkAddReturn_moreCarsThanPlaces_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100)
        val numberOfCars = numberOfPlaces + Random.nextInt(0, 100)
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        repeat(numberOfCars) {
            parkingStateHandler.tryToAddCar()
        }

        assertFalse(parkingStateHandler.tryToAddCar())
    }

    @Test
    fun checkRemove_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        repeat(2) {
            parkingStateHandler.tryToAddCar()
        }
        parkingStateHandler.tryToRemoveCar()
        assertEquals(1, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @Test
    fun checkRemoveReturn_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        repeat(2) {
            parkingStateHandler.tryToAddCar()
        }
        parkingStateHandler.tryToRemoveCar()
        assertTrue(parkingStateHandler.tryToRemoveCar())
    }

    @Test
    fun checkRemoveReturn_AttemptToRemoveCarFromEmptyParkingLot_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        assertFalse(parkingStateHandler.tryToRemoveCar())
    }

    @Test
    fun checkAddAndRemove_removeAllCars_mustWork() {
        val numberOfPlaces = (Random.nextInt(0, 1000)) * 2
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        repeat(2 * numberOfPlaces) {
            parkingStateHandler.tryToAddCar()
        }
        repeat(numberOfPlaces) {
            parkingStateHandler.tryToRemoveCar()
        }
        assertEquals(0, parkingStateHandler.getNumberOfOccupiedPlaces())
    }
}
