package models

import exceptions.InvalidVehicleTypeException
import exceptions.ParkingLotFullException

class ParkingSpots(vararg capacityForAllVehicleTypes: Pair<VehicleClass, UInt>) {
    private val freeSpotsForAllVehicleFeeTypes = HashMap<VehicleClass, HashSet<Int>>()

    init {
        for (capacityPerType in capacityForAllVehicleTypes)
            freeSpotsForAllVehicleFeeTypes[capacityPerType.first] =
                Array(capacityPerType.second.toInt()) { it + 1 }.toHashSet()
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