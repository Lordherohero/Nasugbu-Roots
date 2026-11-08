interface Bookable {
        fun checkAvailability(): Boolean

        fun bookTour(): Boolean

        fun cancelBooking(): Boolean
    }
