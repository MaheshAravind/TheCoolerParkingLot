import VehicleClass.FOUR_WHEELER
import VehicleClass.TWO_WHEELER
import exceptions.InvalidVehicleTypeException
import java.util.*
import java.util.concurrent.TimeUnit

class AirportParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
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
        return if (hourCount < 12) 60
        else if (hourCount < 24) 80
        else {
            val dayCount = TimeUnit.HOURS.toDays(hourCount.toLong()).toInt()
            dayCount * 100
        }
    }

    private fun findTwoWheelerCost(hourCount: Int): Int {
        return if (hourCount == 0) 0
        else if (hourCount < 8) 40
        else if (hourCount < 24) 60
        else {
            val dayCount = TimeUnit.HOURS.toDays(hourCount.toLong()).toInt()
            dayCount * 80
        }
    }
}
