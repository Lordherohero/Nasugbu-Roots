open class AppUser(
    private val userId: Int,
    private var userName: String,
    protected var email: String,
    protected val role: String,
    protected var bio: String,
    private var verified: Boolean
) {
    init {
        require(userId > 0) { "User ID must be positive" }
        require(userName.isNotBlank()) { "User name cannot be blank" }
        require(isValidRole(role)) { "Role must be Tourist, Guide, Owner, or Manager" }
    }

    private fun isValidRole(role: String): Boolean {
        return role == "Tourist" || role == "Guide" || role == "Owner" || role == "Manager"
    }

    fun getUserId(): Int {
        return userId
    }

    fun getUserName(): String {
        return userName
    }

    fun getUserEmail(): String {
        return email
    }

    fun getUserRole(): String {
        return role
    }

    fun getUserBio(): String {
        return bio
    }

    fun getUserVerified(): Boolean {
        return verified
    }

    fun setUserName(userName: String) {
        require(userName.isNotBlank()) { "User name cannot be blank" }
        this.userName = userName
        println("User name updated to: $userName")
    }

    fun setUserEmail(email: String) {
        this.email = email
        println("Email updated to: $email")
    }

    fun setUserBio(bio: String) {
        this.bio = bio
        println("Bio updated to: $bio")
    }

    fun setUserVerified(verified: Boolean) {
        this.verified = verified
        println("Verified updated to: $verified")
    }

    open fun displayUser() {
        println("User ID: $userId")
        println("User Name: $userName")
        println("Email: $email")
        println("Role: $role")
        println("Bio: $bio")
        println("Verified: $verified")
    }
}

class Tourist(
    userId: Int,
    userName: String,
    email: String,
    bio: String = "",
    verified: Boolean = false
) : AppUser(userId, userName, email, "Tourist", bio, verified)

class Guide(
    userId: Int,
    userName: String,
    email: String,
    private val languages: String,
    private var ratePerHour: Double,
    bio: String = "",
    verified: Boolean = false
) : AppUser(userId, userName, email, "Guide", bio, verified) {
    init {
        require(ratePerHour >= 0.0) { "Rate per hour cannot be negative" }
    }

    fun getUserLanguages(): String {
        return languages
    }

    fun getRatePerHour(): Double {
        return ratePerHour
    }

    fun setRatePerHour(ratePerHour: Double) {
        require(ratePerHour >= 0.0) { "Rate per hour cannot be negative" }
        this.ratePerHour = ratePerHour
        println("Rate per hour updated to: $ratePerHour")
    }

    override fun displayUser() {
        super.displayUser()
        println("Languages: $languages")
        println("Rate Per Hour: $ratePerHour")
    }
}

class SpotOwner(
    userId: Int,
    userName: String,
    email: String,
    bio: String = "",
    verified: Boolean = false
) : AppUser(userId, userName, email, "Owner", bio, verified)

class Manager(
    userId: Int,
    userName: String,
    email: String,
    bio: String = "",
    verified: Boolean = false
) : AppUser(userId, userName, email, "Manager", bio, verified)