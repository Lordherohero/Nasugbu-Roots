enum class Role(val label: String) {
    TOURIST("Tourist"),
    GUIDE("Guide"),
    OWNER("Spot Owner"),
    MANAGER("Manager")
}

enum class BookingStatus(val label: String) {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled")
}