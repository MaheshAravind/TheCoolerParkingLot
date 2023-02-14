package ParkingLotImplementations

import entities.ParkingReceipt
import entities.ParkingTicket
import entities.VehicleType
import exceptions.ParkingLotFullException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*
import kotlin.test.assertIs

class MallParkingLotTest {
    @Test
    fun `cannot park vehicles if lot is full`() {
        val phoenixMarketcityParkingLot = MallParkingLot()

        assertThrows(ParkingLotFullException::class.java) {
            phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE)
        }
    }

    @Test
    fun `can get parking ticket`() {
        val phoenixMarketcityParkingLot = MallParkingLot(twoWheelerCount = 1u)

        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE)

        assertIs<ParkingTicket>(parkingTicket)
    }

    @Test
    fun `should get parking ticket with correct format`() {
        val phoenixMarketcityParkingLot = MallParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val expected = """Parking Ticket:
    Ticket Number: 1
    Spot Number: TWO_WHEELER - 1
    Entry Date-time: Wed Feb 15 03:06:07 IST 2023"""

        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE, entryDate)

        assertEquals(expected, parkingTicket.toString())
    }

    @Test
    fun `can get receipt`() {
        val phoenixMarketcityParkingLot = MallParkingLot(twoWheelerCount = 1u)
        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE)

        val parkingReceipt = phoenixMarketcityParkingLot.unpark(parkingTicket)

        assertIs<ParkingReceipt>(parkingReceipt)
    }

    @Test
    fun `should get parking receipt with correct format`() {
        val phoenixMarketcityParkingLot = MallParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676511414203)
        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val expected = """Parking Receipt:
    Receipt Number: R-1
    Entry Date-time: Wed Feb 15 03:06:07 IST 2023
    Exit Date-time: Thu Feb 16 07:06:54 IST 2023
    Fees: 280"""

        val parkingReceipt = phoenixMarketcityParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expected, parkingReceipt.toString())
    }

    @Test
    fun `should get 20 fee when motorcycle is parked for 2 hours`() {
        val phoenixMarketcityParkingLot = MallParkingLot(twoWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 2)
        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 20)

        val parkingReceipt = phoenixMarketcityParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should get 80 fee when suv is parked for 4 hours`() {
        val phoenixMarketcityParkingLot = MallParkingLot(fourWheelerCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 4)
        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.SUV, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 80)

        val parkingReceipt = phoenixMarketcityParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should get 50 fee when truck is parked for 1 hour`() {
        val phoenixMarketcityParkingLot = MallParkingLot(bigVehicleCount = 1u)
        val entryDate = Date(1676410567394)
        val exitDate = Date(1676410567394 + 1000 * 3600 * 1)
        val parkingTicket = phoenixMarketcityParkingLot.park(VehicleType.TRUCK, entryDate)
        val expectedReceipt = ParkingReceipt(1, entryDate, exitDate, 50)

        val parkingReceipt = phoenixMarketcityParkingLot.unpark(parkingTicket, exitDate)

        assertEquals(expectedReceipt, parkingReceipt)
    }

    @Test
    fun `should be able to park all valid types`() {
        val phoenixMarketcityParkingLot =
            MallParkingLot(twoWheelerCount = 2u, fourWheelerCount = 2u, bigVehicleCount = 2u)

        assertDoesNotThrow {
            phoenixMarketcityParkingLot.park(VehicleType.MOTORCYCLE)
            phoenixMarketcityParkingLot.park(VehicleType.SCOOTER)
            phoenixMarketcityParkingLot.park(VehicleType.CAR)
            phoenixMarketcityParkingLot.park(VehicleType.SUV)
            phoenixMarketcityParkingLot.park(VehicleType.BUS)
            phoenixMarketcityParkingLot.park(VehicleType.TRUCK)
        }
    }
}