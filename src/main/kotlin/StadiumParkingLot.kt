import INTERVALS.*
import VehicleClass.FOUR_WHEELER
import VehicleClass.TWO_WHEELER
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import java.util.*
import java.util.concurrent.TimeUnit

class StadiumParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
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
        val interval = findInterval(hourCount)
        return when (interval) {
            INTERVAL_ONE -> 60
            INTERVAL_TWO -> 60 + 120
            INTERVAL_THREE -> 60 + 120 + (hourCount - 12) * 200
        }
    }

    private fun findTwoWheelerCost(hourCount: Int): Int {
        val interval = findInterval(hourCount)
        return when (interval) {
            INTERVAL_ONE -> 30
            INTERVAL_TWO -> 30 + 60
            INTERVAL_THREE -> 30 + 60 + (hourCount - 12) * 100
        }
    }

    private fun findInterval(hourCount: Int): INTERVALS {
        return INTERVALS.values().find { it.contains(hourCount) } ?: throw InvalidDurationException()
    }
}

enum class INTERVALS(private val start: Int, val end: Int) {
    INTERVAL_ONE(0, 4),
    INTERVAL_TWO(4, 12),
    INTERVAL_THREE(12, -1);

    fun contains(value: Int): Boolean {
        if (end == -1) return value > start //end being -1 means it is infinity
        return value in start..end
    }
}