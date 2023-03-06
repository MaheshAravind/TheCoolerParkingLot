package parkinglots

import entities.ParkingReceipt
import entities.VehicleType
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class AirportParkingLotTest {
    @Test
    fun `should not allow buses to be parked`() {
        val heathrowAirportParkingLot =
            AirportParkingLot()

        assertThrows(InvalidVehicleTypeException::class.java) {
            heathrowAirportParkingLot.park(VehicleType.BUS)
        }
    }

    @Test
    fun `should not allow trucks to be parked`() {
        val heathrowAirportParkingLot =
            AirportParkingLot()

        assertThrows(InvalidVehicleTypeException::class.java) {
            heathrowAirportParkingLot.park(VehicleType.TRUCK)
        }
    }

    @Test
    fun `should be able to park all valid types`() {
        val heathrowAirportParkingLot =
            AirportParkingLot(twoWheelerCount = 2u, fourWheelerCount = 2u)

        org.junit.jupiter.api.assertDoesNotThrow {
            heathrowAirportParkingLot.park(VehicleType.MOTORCYCLE)
            heathrowAirportParkingLot.park(VehicleType.SCOOTER)
            heathrowAirportParkingLot.park(VehicleType.CAR)
            heathrowAirportParkingLot.park(VehicleType.SUV)
        }
    }

    @Test
    fun `should not be allowed to have exit time before entry time`() {
        val heathrowAirportParkingLot =
            AirportParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val exitDate = Date(1676410567394 - 1000 * 3600 * 1)

        assertThrows(InvalidDurationException::class.java) {
            heathrowAirportParkingLot.unpark(parkingTicket, exitDate)
        }
    }

    @Test
    fun `should have to pay 40 as fee when motorcycle is parked for 2 hours`() {
        val heathrowAirportParkingLot = AirportParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 2)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 40)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should be able to park for free if scooter is parked for half an hour`() {
        val heathrowAirportParkingLot = AirportParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 / 2)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.SCOOTER, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 0)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 60 as fee when scooter is parked for 8 hours`() {
        val heathrowAirportParkingLot = AirportParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 8)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.SCOOTER, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 60)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 240 as fee when motorcycle is parked for 3 days`() {
        val heathrowAirportParkingLot = AirportParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 24 * 3)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.SCOOTER, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 240)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 60 as fee when car is parked for 9 hours`() {
        val heathrowAirportParkingLot = AirportParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 9)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.CAR, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 60)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 80 as fee when suv is parked for 14 hours`() {
        val heathrowAirportParkingLot = AirportParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 14)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.CAR, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 80)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should have to pay 500 as fee when car is parked for 5 days`() {
        val heathrowAirportParkingLot = AirportParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 24 * 5)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.CAR, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 500)

        val parkingReceipt = heathrowAirportParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should not allow exit time to be before entry time`() {
        val heathrowAirportParkingLot = AirportParkingLot(twoWheelerCount = 1u, fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 - 1000 * 3600 * 1)
        val parkingTicket = heathrowAirportParkingLot.park(VehicleType.CAR, entryDate)

        assertThrows(InvalidDurationException::class.java){heathrowAirportParkingLot.unpark(parkingTicket, exitDate)}
    }
}