abstract class AppUser(
    protected var userName: String,
    private val userId: Int,
    protected var email: String,
    protected val role: String,
    private val verified: Boolean,
    protected var bio: String,
    private val languages: String,
    protected var ratePerHour: Double
) {
    abstract fun setUserName(): String
    abstract fun setUserId(): Int
    abstract fun setUserEmail(): String
    abstract fun setUserRole(): String
    abstract fun setUserBio(): String
    abstract fun setUserVerified(): Boolean
    abstract fun setUserLanguage(): String
    abstract fun SetUserRatePerHour(): Double



    fun getUserName() {
        println("User Name: $userName")
    }
    fun getUserId() {
        println("User Id: $userId")
    }
    fun getUserEmail() {
        println("User Email: $email")
    }
    fun getUserRole() {
        println("User Role: $role")
    }
    fun getUserBio() {
        println("User bio: $bio")
    }
    fun getUserVerified() {
        println("User Verified: $verified")
    }
    fun getUserLanguage() {
        println("User Languages: $languages")
    }
    fun getUserRatePerHour() {
        println("User RatePerHour: $ratePerHour")
    }
}


