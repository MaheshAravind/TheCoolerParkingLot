package ParkingLotImplementations

import ParkingLot
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import models.EndExclusiveInterval
import models.ParkingSpots
import models.VehicleClass
import models.VehicleClass.FOUR_WHEELER
import models.VehicleClass.TWO_WHEELER
import java.util.*
import java.util.concurrent.TimeUnit

class StadiumParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
    companion object {
        val INTERVAL_ONE = EndExclusiveInterval(0, 4)
        val INTERVAL_TWO = EndExclusiveInterval(4, 12)
        val INTERVAL_THREE = EndExclusiveInterval(12)
    }

    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount)

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val diffInMillis = exitDateTime.time - entryDateTime.time
        val numberOfHours = TimeUnit.MILLISECONDS.toHours(diffInMillis).toInt()

        return when (vehicleClass) {
            TWO_WHEELER -> findTwoWheelerCost(numberOfHours)
            FOUR_WHEELER -> findFourWheelerCost(numberOfHours)
            else -> throw InvalidVehicleTypeException()
        }
    }

    private fun findFourWheelerCost(numberOfHours: Int): Int {
        if (INTERVAL_ONE.contains(numberOfHours)) return 30
        if (INTERVAL_TWO.contains(numberOfHours)) return 30 + 60
        if (INTERVAL_THREE.contains(numberOfHours)) return 30 + 60 + 100 * (numberOfHours - 12)

        throw InvalidDurationException()
    }

    private fun findTwoWheelerCost(numberOfHours: Int): Int {
        if (INTERVAL_ONE.contains(numberOfHours)) return 60
        if (INTERVAL_TWO.contains(numberOfHours)) return 60 + 120
        if (INTERVAL_THREE.contains(numberOfHours)) return 60 + 120 + 200 * (numberOfHours - 12)

        throw InvalidDurationException()
    }
}