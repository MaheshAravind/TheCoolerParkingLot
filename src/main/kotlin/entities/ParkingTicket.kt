package entities

import models.VehicleClass
import java.util.*

data class ParkingTicket(
    private val ticketNumber: Int,
    private val spotNumber: Int,
    private val entryDateTime: Date,
    private val vehicleClass: VehicleClass
) {
    override fun toString(): String {
        return """
            Parking Ticket:
                Ticket Number: $ticketNumber
                Spot Number: $vehicleClass - $spotNumber
                Entry Date-time: $entryDateTime
        """.trimIndent()
    }

    fun getVehicleClass() = vehicleClass
    fun getSpotNumber() = spotNumber
    fun getEntryDateTime() = entryDateTime
}