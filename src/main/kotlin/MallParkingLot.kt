import VehicleClass.*
import java.util.*
import java.util.concurrent.TimeUnit

class MallParkingLot(twoWheelerCount: Int = 0, fourWheelerCount: Int = 0, bigVehicleCount: Int = 0) : ParkingLot() {
    override val validVehicleClasses: Set<VehicleClass> = setOf(TWO_WHEELER, FOUR_WHEELER, BIG_VEHICLE)

    override val parkingSpots: ParkingSpots =
        ParkingSpots(TWO_WHEELER to twoWheelerCount, FOUR_WHEELER to fourWheelerCount, BIG_VEHICLE to bigVehicleCount)

    override fun calculateCost(entryDateTime: Date, exitDateTime: Date, vehicleClass: VehicleClass): Int {
        val diffInMillis = exitDateTime.time - entryDateTime.time
        val hourCount = TimeUnit.MILLISECONDS.toHours(diffInMillis).toInt()

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