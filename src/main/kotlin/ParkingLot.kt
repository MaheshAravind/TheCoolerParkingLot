import exceptions.InvalidVehicleTypeException
import java.util.*

abstract class ParkingLot {
    private var ticketNumber = 0
    private var receiptNumber = 0
    abstract val validVehicleClasses: Set<VehicleClass>
    abstract val parkingSpots: ParkingSpots

    fun park(vehicleType: VehicleType, entryDateTime: Date = Date()): ParkingTicket {
        val vehicleClass = vehicleType.vehicleClass
        if (vehicleClass !in validVehicleClasses) throw InvalidVehicleTypeException()

        val spotNumber = parkingSpots.findAndLockSpot(vehicleClass)

        return generateTicket(spotNumber, vehicleClass, entryDateTime)
    }

    fun unpark(parkingTicket: ParkingTicket, exitDateTime: Date = Date()): ParkingReceipt {
        val receiptNumber = getNextReceiptNumber()

        val vehicleClass = parkingTicket.getVehicleClass()
        val spotNumber = parkingTicket.getSpotNumber()
        parkingSpots.freeSpot(vehicleClass, spotNumber)

        val entryDateTime = parkingTicket.getEntryDateTime()
        val totalFee = calculateCost(entryDateTime, exitDateTime, vehicleClass)

        return ParkingReceipt(receiptNumber, entryDateTime, exitDateTime, totalFee)
    }

    abstract fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int

    private fun generateTicket(spotNumber: Int, vehicleClass: VehicleClass, entryDateTime: Date): ParkingTicket {
        val ticketNumber = getNextTicketNumber()
        return ParkingTicket(ticketNumber, spotNumber, entryDateTime, vehicleClass)
    }

    private fun getNextTicketNumber(): Int {
        return ticketNumber++
    }

    private fun getNextReceiptNumber(): Int {
        return receiptNumber++
    }
}