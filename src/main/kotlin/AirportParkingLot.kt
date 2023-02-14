import VehicleClass.FOUR_WHEELER
import VehicleClass.TWO_WHEELER
import exceptions.InvalidDurationException
import exceptions.InvalidVehicleTypeException
import java.util.*
import java.util.concurrent.TimeUnit

class AirportParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0) : ParkingLot() {
    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount)

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
        if (hourCount in 0 until 12) return 60
        if (hourCount in 12 until 24) return 80
        val dayCount = TimeUnit.HOURS.toDays(hourCount.toLong()).toInt()
        if (dayCount > 0) return dayCount * 100

        throw InvalidDurationException()
    }

    private fun findTwoWheelerCost(hourCount: Int): Int {
        if (hourCount in 0 until 1) return 0
        if (hourCount in 1 until 8) return 40
        if (hourCount in 8 until 24) return 60
        val dayCount = TimeUnit.HOURS.toDays(hourCount.toLong()).toInt()
        if (dayCount > 0) return dayCount * 80

        throw InvalidDurationException()
    }
}
