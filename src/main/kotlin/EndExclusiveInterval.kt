class EndExclusiveInterval(private val start: Int, private val end: Int = -1) {
    fun contains(value: Int): Boolean {
        if (end == -1) return value > start //end being -1 means it is infinity
        return value in start until end
    }
}