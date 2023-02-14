package ParkingLotImplementations

import ParkingLot
import exceptions.InvalidDurationException
import models.ParkingSpots
import models.VehicleClass
import models.VehicleClass.*
import java.util.*
import java.util.concurrent.TimeUnit

class MallParkingLot(twoWheelerCount: UInt = 0u, fourWheelerCount: UInt = 0u, bigVehicleCount: UInt = 0u) :
    ParkingLot() {
    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER, BIG_VEHICLE)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(
            TWO_WHEELER to twoWheelerCount,
            FOUR_WHEELER to fourWheelerCount,
            BIG_VEHICLE to bigVehicleCount
        )

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val timeDifferenceInMilliseconds = exitDateTime.time - entryDateTime.time
        val hourCount = TimeUnit.MILLISECONDS.toHours(timeDifferenceInMilliseconds).toInt()
        if (hourCount < 0) throw InvalidDurationException()

        val rate = ratePerHour(vehicleClass)
        val totalCost = rate * hourCount

        return totalCost
    }

    private fun ratePerHour(vehicleClass: VehicleClass): Int {
        return when (vehicleClass) {
            TWO_WHEELER -> 10
            FOUR_WHEELER -> 20
            BIG_VEHICLE -> 50
        }
    }
}