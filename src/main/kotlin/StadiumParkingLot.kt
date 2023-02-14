import VehicleClass.FOUR_WHEELER
import VehicleClass.TWO_WHEELER
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import java.util.*
import java.util.concurrent.TimeUnit

class StadiumParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
    companion object {
        val INTERVAL_ONE = EndExclusiveInterval(0, 4)
        val INTERVAL_TWO = EndExclusiveInterval(4, 12)
        val INTERVAL_THREE = EndExclusiveInterval(12)
    }

    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER)

    override val freeSpots: FreeSpots = FreeSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount)

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val diffInMillis = exitDateTime.time - entryDateTime.time
        val hourCount = TimeUnit.MILLISECONDS.toHours(diffInMillis).toInt()

        return when (vehicleClass) {
            TWO_WHEELER -> findTwoWheelerCost(hourCount)
            FOUR_WHEELER -> findFourWheelerCost(hourCount)
            else -> throw InvalidVehicleTypeException()
        }
    }

    private fun findFourWheelerCost(hourCount: Int): Int {
        if (INTERVAL_ONE.contains(hourCount)) return 30
        if (INTERVAL_TWO.contains(hourCount)) return 30 + 60
        if (INTERVAL_THREE.contains(hourCount)) return 30 + 60 + 100 * (hourCount - 12)

        throw InvalidDurationException()
    }

    private fun findTwoWheelerCost(hourCount: Int): Int {
        if (INTERVAL_ONE.contains(hourCount)) return 60
        if (INTERVAL_TWO.contains(hourCount)) return 60 + 120
        if (INTERVAL_THREE.contains(hourCount)) return 60 + 120 + 200 * (hourCount - 12)

        throw InvalidDurationException()
    }
}