fun emailExists(email: String): Boolean {
    for (user in users) {
        if (user.getUserEmail().equals(email, ignoreCase = true)) {
            return true
        }
    }
    return false
}

fun registerUser(): AppUser? {
    val name = readText("Name: ")
    if (name.isBlank()) {
        println("Name cannot be blank")
        return null
    }
    val email = readText("Email: ")
    if (!email.contains("@") || !email.contains(".")) {
        println("Please enter a valid email")
        return null
    }
    if (emailExists(email)) {
        println("That email is already registered")
        return null
    }

    println("1. Tourist")
    println("2. Guide")
    println("3. Spot Owner")
    val choice = readText("Register as: ")
    if (choice != "1" && choice != "2" && choice != "3") {
        println("Invalid choice")
        return null
    }

    val bio = readText("Short bio (optional): ")
    val id = users.size + 1
    val user: AppUser

    if (choice == "1") {
        user = Tourist(id, name, email, bio)
    } else if (choice == "2") {
        val languages = readText("Languages you speak: ")
        if (languages.isBlank()) {
            println("Languages cannot be blank")
            return null
        }
        val rate = readDouble("Your rate per hour: ")
        if (rate < 0.0) {
            println("Rate cannot be negative")
            return null
        }
        val guide = Guide(id, name, email, languages, rate, bio)
        guides.add(guide)
        user = guide
    } else {
        user = SpotOwner(id, name, email, bio)
    }

    users.add(user)
    println("Account created for $name. Your user ID is $id")
    if (choice == "2") {
        println("A manager needs to verify your guide account")
    }
    return user
}

fun chooseUser(): AppUser? {
    while (true) {
        println("=== Nasugbu Roots ===")
        println("Who is using the app?")
        for (user in users) {
            println("ID: ${user.getUserId()} | ${user.getUserName()} | ${user.getUserRole()}")
        }
        println("R. Register a new account")
        println("0. Exit")

        val input = readText("Enter your user ID, R, or 0: ")
        if (input == "0") {
            return null
        }
        if (input.equals("r", ignoreCase = true)) {
            val newUser = registerUser()
            if (newUser != null) {
                return newUser
            }
            println()
            continue
        }

        val id = input.toIntOrNull()
        if (id == null) {
            println("Please enter an ID, R, or 0")
            println()
            continue
        }
        for (user in users) {
            if (user.getUserId() == id) {
                return user
            }
        }
        println("No user with that ID")
        println()
    }
}