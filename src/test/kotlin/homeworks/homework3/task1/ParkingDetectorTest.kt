package homeworks.homework3.task1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

internal class ParkingDetectorTest {
    @RepeatedTest(20)
    fun oneDetector_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100) * 4
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val detector = ParkingDetector(parkingStateHandler)

        runBlocking {
            launch {
                repeat(numberOfPlaces / 2) {
                    detector.tryToEnter()
                }
                repeat(numberOfPlaces / 4) {
                    detector.tryToExit()
                }
            }
        }

        assertEquals(numberOfPlaces / 4, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @RepeatedTest(20)
    fun manyDetectors_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100) * 8
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val firstDetector = ParkingDetector(parkingStateHandler)
        val secondDetector = ParkingDetector(parkingStateHandler)

        runBlocking {
            launch {
                repeat(numberOfPlaces / 4) {
                    firstDetector.tryToEnter()
                    secondDetector.tryToEnter()
                }
                repeat(numberOfPlaces / 8) {
                    firstDetector.tryToExit()
                    secondDetector.tryToExit()
                }
            }
        }

        assertEquals(numberOfPlaces / 4, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @RepeatedTest(20)
    fun manyDetectors_morePlacesThanCars_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100) * 32
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val firstDetector = ParkingDetector(parkingStateHandler)
        val secondDetector = ParkingDetector(parkingStateHandler)
        val thirdDetector = ParkingDetector(parkingStateHandler)

        runBlocking {
            launch {
                repeat(numberOfPlaces / 4) {
                    firstDetector.tryToEnter()
                    secondDetector.tryToEnter()
                    thirdDetector.tryToExit()
                    secondDetector.tryToEnter()
                }
            }
        }

        assertEquals(numberOfPlaces / 2, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @RepeatedTest(20)
    fun moreCarsThanPlaces_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100)
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val firstDetector = ParkingDetector(parkingStateHandler)
        val secondDetector = ParkingDetector(parkingStateHandler)
        val thirdDetector = ParkingDetector(parkingStateHandler)

        runBlocking {
            launch {
                repeat(Random.nextInt(numberOfPlaces, 1000)) {
                    firstDetector.tryToEnter()
                    secondDetector.tryToEnter()
                    thirdDetector.tryToEnter()
                }
            }
        }

        assertEquals(numberOfPlaces, parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @RepeatedTest(20)
    fun additionalCalculationOfOccupiedPlaces_mustWork() {
        val numberOfPlaces = Random.nextInt(0, 100)
        val numberOfOccupiedPlaces = AtomicInteger(0)
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val firstDetector = ParkingDetector(parkingStateHandler)
        val secondDetector = ParkingDetector(parkingStateHandler)
        val thirdDetector = ParkingDetector(parkingStateHandler)

        runBlocking {
            launch {
                repeat(Random.nextInt(0, 100)) {
                    if (withContext(Dispatchers.Default) { thirdDetector.tryToEnter() }) {
                        numberOfOccupiedPlaces.incrementAndGet()
                    }
                    if (withContext(Dispatchers.Default) { firstDetector.tryToExit() }) {
                        numberOfOccupiedPlaces.decrementAndGet()
                    }
                }
                repeat(Random.nextInt(0, 100)) {
                    if (withContext(Dispatchers.Default) { firstDetector.tryToExit() }) {
                        numberOfOccupiedPlaces.decrementAndGet()
                    }
                    if (withContext(Dispatchers.Default) { secondDetector.tryToEnter() }) {
                        numberOfOccupiedPlaces.incrementAndGet()
                    }
                }
            }
        }

        assertEquals(numberOfOccupiedPlaces.get(), parkingStateHandler.getNumberOfOccupiedPlaces())
    }

    @Test
    fun checkAddReturn_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val detector = ParkingDetector(parkingStateHandler)
        var result = true
        runBlocking {
            result = withContext(Dispatchers.Default) { detector.tryToEnter() }
        }
        assertTrue(result)
    }

    @Test
    fun checkRemoveReturn_mustWork() {
        val numberOfPlaces = 10
        val parkingStateHandler = ParkingStateHandler(numberOfPlaces)
        val detector = ParkingDetector(parkingStateHandler)
        var result = true
        runBlocking {
            repeat(2) {
                detector.tryToEnter()
            }
            result = withContext(Dispatchers.Default) { detector.tryToExit() }
        }
        assertTrue(result)
    }
}
