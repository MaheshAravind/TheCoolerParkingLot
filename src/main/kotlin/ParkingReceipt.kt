import java.util.*

class ParkingReceipt(
    private val receiptNumber: Int,
    private val entryDateTime: Date,
    private val exitDateTime: Date,
    private val totalFee: Int
) {
    override fun toString(): String {
        return """
            Parking Receipt:
                Receipt Number: R-$receiptNumber
                Entry Date-time: $entryDateTime
                Exit Date-time: $exitDateTime
                Fees: $totalFee
        """.trimIndent()
    }
}
