package ParkingLotImplementations

import ParkingLot
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import models.ParkingSpots
import models.VehicleClass
import models.VehicleClass.FOUR_WHEELER
import models.VehicleClass.TWO_WHEELER
import java.util.*
import java.util.concurrent.TimeUnit

class StadiumParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount)

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val timeDifferenceInMilliseconds = exitDateTime.time - entryDateTime.time
        val numberOfHours = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMilliseconds).toInt()

        return when (vehicleClass) {
            TWO_WHEELER -> findTwoWheelerCost(numberOfHours)
            FOUR_WHEELER -> findFourWheelerCost(numberOfHours)
            else -> throw InvalidVehicleTypeException()
        }
    }

    private fun findFourWheelerCost(numberOfHours: Int): Int {
        if (numberOfHours in 0 until 4) return 30
        if (numberOfHours in 4 until 12) return 30 + 60
        if (numberOfHours >= 12) return 30 + 60 + 100 * (numberOfHours - 12)

        throw InvalidDurationException()
    }

    private fun findTwoWheelerCost(numberOfHours: Int): Int {
        if (numberOfHours in 0 until 4) return 60
        if (numberOfHours in 4 until 12) return 60 + 120
        if (numberOfHours >= 12) return 60 + 120 + 200 * (numberOfHours - 12)

        throw InvalidDurationException()
    }
}