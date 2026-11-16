
package com.mygroup.nasugburoots.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mygroup.nasugburoots.ui.auth.AccountProfile
import com.mygroup.nasugburoots.ui.auth.AccountRole
import com.mygroup.nasugburoots.ui.auth.AccountStatus
import com.mygroup.nasugburoots.ui.auth.ProfessionalFormState
import com.mygroup.nasugburoots.ui.auth.RegistrationFormState

object UserRepository {

    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    fun registerTourist(
        form: RegistrationFormState,
        onComplete: (Result<AccountProfile>) -> Unit
    ) {
        createAccount(
            form = form,
            role = AccountRole.TOURIST,
            status = AccountStatus.ACTIVE,
            professionalForm = null,
            onComplete = onComplete
        )
    }

    fun registerProfessional(
        registration: RegistrationFormState,
        professional: ProfessionalFormState,
        role: AccountRole,
        onComplete: (Result<AccountProfile>) -> Unit
    ) {
        if (role != AccountRole.LOCAL_GUIDE &&
            role != AccountRole.SITE_OWNER
        ) {
            onComplete(
                Result.failure(
                    IllegalArgumentException("Invalid professional role.")
                )
            )
            return
        }

        createAccount(
            form = registration,
            role = role,
            status = AccountStatus.PENDING_VERIFICATION,
            professionalForm = professional,
            onComplete = onComplete
        )
    }

    private fun createAccount(
        form: RegistrationFormState,
        role: AccountRole,
        status: AccountStatus,
        professionalForm: ProfessionalFormState?,
        onComplete: (Result<AccountProfile>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(
            form.email.trim(),
            form.password
        ).addOnSuccessListener { authResult ->

            val uid = authResult.user?.uid

            if (uid.isNullOrBlank()) {
                onComplete(
                    Result.failure(
                        Exception("Account created but user ID was not found.")
                    )
                )
                return@addOnSuccessListener
            }

            val profileData = hashMapOf<String, Any>(
                "userId" to uid,
                "fullName" to form.fullName.trim(),
                "email" to form.email.trim(),
                "phoneNumber" to form.phoneNumber.trim(),
                "role" to role.name,
                "status" to status.name,
                "createdAt" to FieldValue.serverTimestamp()
            )

            professionalForm?.let { professional ->

                if (role == AccountRole.LOCAL_GUIDE) {
                    profileData["location"] = professional.location.trim()
                    profileData["biography"] = professional.biography.trim()
                }

                if (role == AccountRole.SITE_OWNER) {
                    profileData["establishmentName"] =
                        professional.establishmentName.trim()

                    profileData["siteAddress"] =
                        professional.siteAddress.trim()
                }

                professional.documentUri?.let {
                    profileData["documentUri"] = it
                }
            }

            db.collection("users")
                .document(uid)
                .set(profileData)
                .addOnSuccessListener {

                    val profile = AccountProfile(
                        userId = uid,
                        fullName = form.fullName.trim(),
                        email = form.email.trim(),
                        role = role,
                        status = status
                    )

                    onComplete(Result.success(profile))
                }
                .addOnFailureListener { exception ->
                    onComplete(
                        Result.failure(
                            Exception(
                                "Your Firebase account was created, but saving your profile failed. Please contact support or retry profile setup.",
                                exception
                            )
                        )
                    )
                }

        }.addOnFailureListener { exception ->
            onComplete(Result.failure(exception))
        }
    }

    fun login(
        email: String,
        password: String,
        onComplete: (Result<AccountProfile>) -> Unit
    ) {
        auth.signInWithEmailAndPassword(
            email.trim(),
            password
        ).addOnSuccessListener { authResult ->

            val uid = authResult.user?.uid

            if (uid.isNullOrBlank()) {
                onComplete(
                    Result.failure(Exception("Unable to retrieve user ID."))
                )
                return@addOnSuccessListener
            }

            db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->

                    if (!document.exists()) {
                        onComplete(
                            Result.failure(
                                Exception("User profile not found in Firestore.")
                            )
                        )
                        return@addOnSuccessListener
                    }

                    try {
                        val profile = AccountProfile(
                            userId = uid,
                            fullName = document.getString("fullName") ?: "",
                            email = document.getString("email") ?: "",
                            role = AccountRole.valueOf(
                                document.getString("role")
                                    ?: throw IllegalStateException("Missing user role.")
                            ),
                            status = AccountStatus.valueOf(
                                document.getString("status")
                                    ?: throw IllegalStateException("Missing account status.")
                            )
                        )

                        onComplete(Result.success(profile))

                    } catch (exception: Exception) {
                        onComplete(Result.failure(exception))
                    }
                }
                .addOnFailureListener { exception ->
                    onComplete(Result.failure(exception))
                }

        }.addOnFailureListener { exception ->
            onComplete(Result.failure(exception))
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
