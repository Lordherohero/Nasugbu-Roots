package com.mygroup.nasugburoots.ui.auth

import android.util.Patterns

enum class AccountRole(val displayName: String) {
    TOURIST("Tourist"),
    LOCAL_GUIDE("Local Guide"),
    SITE_OWNER("Site Owner"),
    ADMIN("Administrator")
}

enum class AccountStatus {
    ACTIVE,
    PENDING_VERIFICATION,
    REJECTED,
    SUSPENDED
}

enum class AuthDestination {
    LOGIN,
    REGISTER,
    ROLE_SELECTION,
    GUIDE_REGISTRATION,
    OWNER_REGISTRATION,
    FORGOT_PASSWORD,
    REGISTRATION_SUCCESS,
    PENDING_VERIFICATION,
    HOME
}

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = true
)

data class RegistrationFormState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptedTerms: Boolean = false
)

data class ProfessionalFormState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val location: String = "",
    val biography: String = "",
    val establishmentName: String = "",
    val siteAddress: String = "",
    val documentUri: String? = null
)

data class AccountProfile(
    val userId: String,
    val fullName: String,
    val email: String,
    val role: AccountRole,
    val status: AccountStatus
)

private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(email.trim())
        .matches()
}

private fun isValidPhone(phone: String): Boolean {
    val digits = phone.filter { it.isDigit() }
    return digits.length in 10..13
}

fun validateLogin(form: LoginFormState): String? {
    if (!isValidEmail(form.email)) {
        return "Please enter a valid email address."
    }

    if (form.password.isBlank()) {
        return "Please enter your password."
    }

    return null
}

fun validateRegistration(
    form: RegistrationFormState
): String? {
    if (form.fullName.trim().length < 2) {
        return "Please enter your complete name."
    }

    if (!isValidEmail(form.email)) {
        return "Please enter a valid email address."
    }

    if (!isValidPhone(form.phoneNumber)) {
        return "Please enter a valid phone number."
    }

    if (form.password.length < 8) {
        return "Password must contain at least 8 characters."
    }

    if (form.confirmPassword != form.password) {
        return "Your passwords do not match."
    }

    if (!form.acceptedTerms) {
        return "Please accept the Terms and Conditions."
    }

    return null
}

fun validateProfessionalRegistration(
    form: ProfessionalFormState,
    role: AccountRole
): String? {
    if (form.fullName.trim().length < 2) {
        return "Please enter your complete name."
    }

    if (!isValidEmail(form.email)) {
        return "Please enter a valid email address."
    }

    if (!isValidPhone(form.phoneNumber)) {
        return "Please enter a valid phone number."
    }

    if (role == AccountRole.LOCAL_GUIDE) {
        if (form.location.isBlank()) {
            return "Please enter your location."
        }

        if (form.biography.isBlank()) {
            return "Please provide a short biography."
        }
    } else if (role == AccountRole.SITE_OWNER) {
        if (form.establishmentName.isBlank()) {
            return "Please enter your establishment name."
        }

        if (form.siteAddress.isBlank()) {
            return "Please enter your tourist site's address."
        }
    } else {
        return "Invalid registration role."
    }

    if (form.documentUri.isNullOrBlank()) {
        return "Please attach your supporting document."
    }

    return null
}
