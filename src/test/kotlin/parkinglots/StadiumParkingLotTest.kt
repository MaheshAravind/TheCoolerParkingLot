package parkinglots

import entities.ParkingReceipt
import entities.VehicleType
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class StadiumParkingLotTest {
    @Test
    fun `should not allow buses to be parked`() {
        val chepaukStadiumParkingLot =
            StadiumParkingLot()

        assertThrows(InvalidVehicleTypeException::class.java) {
            chepaukStadiumParkingLot.park(VehicleType.BUS)
        }
    }

    @Test
    fun `should not allow trucks to be parked`() {
        val chepaukStadiumParkingLot =
            StadiumParkingLot()

        assertThrows(InvalidVehicleTypeException::class.java) {
            chepaukStadiumParkingLot.park(VehicleType.TRUCK)
        }
    }

    @Test
    fun `should be able to park all valid types`() {
        val chepaukStadiumParkingLot =
            StadiumParkingLot(twoWheelerCount = 2u, fourWheelerCount = 2u)

        org.junit.jupiter.api.assertDoesNotThrow {
            chepaukStadiumParkingLot.park(VehicleType.MOTORCYCLE)
            chepaukStadiumParkingLot.park(VehicleType.SCOOTER)
            chepaukStadiumParkingLot.park(VehicleType.CAR)
            chepaukStadiumParkingLot.park(VehicleType.SUV)
        }
    }

    @Test
    fun `should not be allowed to have exit time before entry time`() {
        val chepaukStadiumParkingLot =
            StadiumParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val exitDate = Date(1676410567394 - 1000 * 3600 * 1)

        assertThrows(InvalidDurationException::class.java) {
            chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)
        }
    }

    @Test
    fun `should have to pay 30 as fee when motorcycle is parked for 2 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 2)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 30)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 90 as fee when scooter is parked for 4 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 4)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.SCOOTER, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 90)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 90 as fee when motorcycle is parked for 12 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 12)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 190)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 60 as fee when car is parked for 3 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 3)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.CAR, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 60)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 180 as fee when suv is parked for 4 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 4)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.SUV, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 180)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 580 as fee when suv is parked for 13 hours`() {
        val chepaukStadiumParkingLot = StadiumParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 13)
        val parkingTicket = chepaukStadiumParkingLot.park(VehicleType.SUV, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 580)

        val parkingReceipt = chepaukStadiumParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }
}