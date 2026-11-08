import java.time.LocalDate
import java.time.YearMonth

fun readText(prompt: String): String {
    print(prompt)
    return readln().trim()
}

fun readInt(prompt: String): Int {
    while (true) {
        val input = readText(prompt).toIntOrNull()
        if (input != null) {
            return input
        }
        println("Please enter a whole number")
    }
}

fun readDouble(prompt: String): Double {
    while (true) {
        val input = readText(prompt).toDoubleOrNull()
        if (input != null) {
            return input
        }
        println("Please enter a number")
    }
}

fun readFutureDate(prompt: String): String {
    while (true) {
        val input = readText(prompt)
        val parts = input.split("-")
        if (parts.size != 3) {
            println("Use the format YYYY-MM-DD, for example 2026-12-05")
            continue
        }
        val year = parts[0].toIntOrNull()
        val month = parts[1].toIntOrNull()
        val day = parts[2].toIntOrNull()
        if (year == null || month == null || day == null) {
            println("Year, month, and day must be numbers")
            continue
        }
        if (month < 1 || month > 12) {
            println("Month must be between 1 and 12")
            continue
        }
        if (day < 1 || day > YearMonth.of(year, month).lengthOfMonth()) {
            println("That day does not exist in that month")
            continue
        }
        val date = LocalDate.of(year, month, day)
        if (date.isBefore(LocalDate.now())) {
            println("The date cannot be in the past")
            continue
        }
        return date.toString()
    }
}