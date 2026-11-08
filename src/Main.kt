fun main() {
    loadSampleData()
    var running = true

    while (running) {
        val user = chooseUser()
        if (user == null) {
            running = false
            println("Goodbye!")
        } else {
            currentUserId = user.getUserId()
            println()
            println("Welcome, ${user.getUserName()}")
            println()

            val role = user.getUserRole()
            if (role == "Tourist") {
                touristMenu()
            } else if (role == "Guide") {
                guideMenu()
            } else if (role == "Owner") {
                ownerMenu()
            } else if (role == "Manager") {
                managerMenu()
            }
        }
    }
}