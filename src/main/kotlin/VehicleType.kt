enum class VehicleType(val vehicleClass: VehicleClass) {
    MOTORCYCLE(VehicleClass.TWO_WHEELER),
    SCOOTER(VehicleClass.TWO_WHEELER),
    CAR(VehicleClass.FOUR_WHEELER),
    SUV(VehicleClass.FOUR_WHEELER),
    BUS(VehicleClass.BIG_VEHICLE),
    TRUCK(VehicleClass.BIG_VEHICLE)
}