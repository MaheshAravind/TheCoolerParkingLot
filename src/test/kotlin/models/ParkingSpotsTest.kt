package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParkingSpotsTest {
    @Test
    fun `should get spot number wherever there is a free spot`() {
        val vehicleClass = VehicleClass.BIG_VEHICLE
        val parkingSpots = ParkingSpots(vehicleClass to 3u)
        parkingSpots.findAndLockSpot(vehicleClass)
        parkingSpots.findAndLockSpot(vehicleClass)
        parkingSpots.findAndLockSpot(vehicleClass)
        parkingSpots.freeSpot(vehicleClass, 2)

        val freeSpot = parkingSpots.findAndLockSpot(vehicleClass)

        assertEquals(2, freeSpot)
    }
}