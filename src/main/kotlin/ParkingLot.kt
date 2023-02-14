import exceptions.InvalidVehicleTypeException
import java.util.*

abstract class ParkingLot {
    private var ticketNumber = 0
    private var receiptNumber = 0
    abstract val validVehicleClasses: Set<VehicleClass>
    abstract val freeSpots: FreeSpots

    fun park(vehicleType: VehicleType, entryTime: Date = Date()): ParkingTicket {
        val vehicleClass = vehicleType.vehicleClass
        if (vehicleClass !in validVehicleClasses) throw InvalidVehicleTypeException()
        val spotNumber = freeSpots.findAndLockSpot(vehicleClass)
        return generateTicket(spotNumber, vehicleClass)
    }

    fun unpark(parkingTicket: ParkingTicket, exitDateTime: Date = Date()): ParkingReceipt {
        val receiptNumber = getNextReceiptNumber()

        val vehicleClass = parkingTicket.getVehicleClass()
        val spotNumber = parkingTicket.getSpotNumber()
        freeSpots.freeSpot(vehicleClass, spotNumber)

        val entryDateTime = parkingTicket.getEntryDateTime()
        val totalFee = calculateCost(entryDateTime, exitDateTime, vehicleClass)

        return ParkingReceipt(receiptNumber, entryDateTime, exitDateTime, totalFee)
    }

    abstract fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int

    private fun generateTicket(spotNumber: Int, vehicleClass: VehicleClass): ParkingTicket {
        val ticketNumber = getNextTicketNumber()
        val currentDateTime = Date()
        return ParkingTicket(ticketNumber, spotNumber, currentDateTime, vehicleClass)
    }

    private fun getNextTicketNumber(): Int {
        return ticketNumber++
    }

    private fun getNextReceiptNumber(): Int {
        return receiptNumber++
    }
}