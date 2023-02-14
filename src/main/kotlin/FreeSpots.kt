import exceptions.InvalidVehicleTypeException
import exceptions.ParkingLotFullException

class FreeSpots(vararg capacityForAllVehicleTypes: Pair<VehicleClass, Int>) {
    private val freeSpotsForAllVehicleFeeTypes = HashMap<VehicleClass, HashSet<Int>>()

    init {
        for (capacityPerType in capacityForAllVehicleTypes)
            freeSpotsForAllVehicleFeeTypes[capacityPerType.first] = Array(capacityPerType.second) { it }.toHashSet()
    }

    fun findAndLockSpot(vehicleClass: VehicleClass): Int {
        val spotsOfReqVehicleType = freeSpotsForAllVehicleFeeTypes[vehicleClass] ?: throw InvalidVehicleTypeException()
        try {
            return spotsOfReqVehicleType.first().also { spotsOfReqVehicleType.remove(it) }
        } catch (ex: NoSuchElementException) {
            throw ParkingLotFullException()
        }
    }

    fun freeSpot(vehicleClass: VehicleClass, spotNumber: Int) {
        val spotsOfReqVehicleType = freeSpotsForAllVehicleFeeTypes[vehicleClass] ?: throw InvalidVehicleTypeException()
        spotsOfReqVehicleType.add(spotNumber)
    }
}