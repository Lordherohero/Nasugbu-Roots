open class AppUser(
    val userId: Int,
    var userName: String,
    var email: String,
    val role: String,
    var bio: String = "",
    val verified: Boolean = false
) {
    init {
        require(userId > 0) { "User ID must be positive" }
        require(userName.isNotBlank()) { "User name cannot be blank" }
        require(isValidRole(role)) { "Role must be Tourist, Guide, Owner, or Manager" }
    }

    private fun isValidRole(role: String): Boolean {
        return role == "Tourist" || role == "Guide" || role == "Owner" || role == "Manager"
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
    val languages: String,
    var ratePerHour: Double,
    bio: String = "",
    verified: Boolean = false
) : AppUser(userId, userName, email, "Guide", bio, verified) {
    init {
        require(ratePerHour >= 0.0) { "Rate per hour cannot be negative" }
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