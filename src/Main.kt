fun main() {
    if (!loadAll()) {
        loadSampleData()
        saveAll()
    }
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
            if (role == Role.TOURIST) {
                touristMenu()
            } else if (role == Role.GUIDE) {
                guideMenu()
            } else if (role == Role.OWNER) {
                ownerMenu()
            } else if (role == Role.MANAGER) {
                managerMenu()
            }
        }
    }
}