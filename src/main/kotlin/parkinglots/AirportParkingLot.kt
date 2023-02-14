package parkinglots

import ParkingLot
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import models.ParkingSpots
import models.VehicleClass
import models.VehicleClass.FOUR_WHEELER
import models.VehicleClass.TWO_WHEELER
import java.util.*
import java.util.concurrent.TimeUnit

class AirportParkingLot(twoWheelerCount: UInt = 0u, fourWheelerCount: UInt = 0u) : ParkingLot() {
    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount)

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val timeDifferenceInMilliseconds = exitDateTime.time - entryDateTime.time
        val numberOfHours = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMilliseconds)

        return when (vehicleClass) {
            TWO_WHEELER -> findTwoWheelerCost(numberOfHours)
            FOUR_WHEELER -> findFourWheelerCost(numberOfHours)
            else -> throw InvalidVehicleTypeException()
        }
    }

    private fun findFourWheelerCost(numberOfHours: Long): Int {
        if (numberOfHours in 0 until 12)
            return 60
        if (numberOfHours in 12 until 24)
            return 80
        val numberOfDays = TimeUnit.HOURS.toDays(numberOfHours)
        if (numberOfDays > 0)
            return (numberOfDays * 100).toInt()

        throw InvalidDurationException()
    }

    private fun findTwoWheelerCost(numberOfHours: Long): Int {
        if (numberOfHours in 0 until 1)
            return 0
        if (numberOfHours in 1 until 8)
            return 40
        if (numberOfHours in 8 until 24)
            return 60
        val numberOfDays = TimeUnit.HOURS.toDays(numberOfHours)
        if (numberOfDays > 0)
            return (numberOfDays * 80).toInt()

        throw InvalidDurationException()
    }
}
