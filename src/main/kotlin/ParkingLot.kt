import entities.ParkingReceipt
import entities.ParkingTicket
import entities.VehicleType
import exceptions.InvalidVehicleTypeException
import models.ParkingSpots
import models.VehicleClass
import java.util.*

abstract class ParkingLot {
    private var ticketNumber = 1
    private var receiptNumber = 1
    protected abstract val validVehicleClasses: Set<VehicleClass>
    protected abstract val parkingSpots: ParkingSpots

    fun park(vehicleType: VehicleType, entryDateTime: Date = Date()): ParkingTicket {
        val vehicleClass = vehicleType.vehicleClass
        if (vehicleClass !in validVehicleClasses)
            throw InvalidVehicleTypeException()

        val spotNumber = parkingSpots.findAndLockSpot(vehicleClass)

        return generateTicket(spotNumber, vehicleClass, entryDateTime)
    }

    fun unpark(parkingTicket: ParkingTicket, exitDateTime: Date = Date()): ParkingReceipt {
        val vehicleClass = parkingTicket.getVehicleClass()
        if (vehicleClass !in validVehicleClasses) throw InvalidVehicleTypeException()

        val spotNumber = parkingTicket.getSpotNumber()
        parkingSpots.freeSpot(vehicleClass, spotNumber)

        val entryDateTime = parkingTicket.getEntryDateTime()
        val totalFee = calculateCost(entryDateTime, exitDateTime, vehicleClass)

        return generateReceipt(entryDateTime, exitDateTime, totalFee)
    }

    protected abstract fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int

    private fun generateTicket(spotNumber: Int, vehicleClass: VehicleClass, entryDateTime: Date): ParkingTicket {
        val ticketNumber = getNextTicketNumber()
        return ParkingTicket(ticketNumber, spotNumber, entryDateTime, vehicleClass)
    }

    private fun generateReceipt(entryDateTime: Date, exitDateTime: Date, totalFee: Int): ParkingReceipt {
        val receiptNumber = getNextReceiptNumber()
        return ParkingReceipt(receiptNumber, entryDateTime, exitDateTime, totalFee)
    }

    private fun getNextTicketNumber(): Int {
        return ticketNumber++
    }

    private fun getNextReceiptNumber(): Int {
        return receiptNumber++
    }
}