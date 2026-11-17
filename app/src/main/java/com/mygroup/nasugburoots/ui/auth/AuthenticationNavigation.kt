package com.mygroup.nasugburoots.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygroup.nasugburoots.data.UserRepository
import com.mygroup.nasugburoots.ui.HomeScreen
import kotlinx.coroutines.launch
import com.mygroup.nasugburoots.data.CurrentUser

@Composable
fun NasugbuRootsAuthApp() {

    var loggedInProfile by remember {
        mutableStateOf<AccountProfile?>(null)
    }

    var destination by remember {
        mutableStateOf(AuthDestination.LOGIN)
    }

    var selectedRole by remember {
        mutableStateOf(AccountRole.TOURIST)
    }

    var registrationForm by remember {
        mutableStateOf(RegistrationFormState())
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (destination) {

            AuthDestination.LOGIN -> {
                LoginScreen(
                    onLogin = { form ->
                        UserRepository.login(
                            email = form.email,
                            password = form.password
                        ) { result ->

                            result.onSuccess { profile ->
                                loggedInProfile = profile
                                CurrentUser.id = profile.userId

                                when (profile.status) {
                                    AccountStatus.ACTIVE -> {
                                        destination = AuthDestination.HOME
                                    }

                                    AccountStatus.PENDING_VERIFICATION -> {
                                        destination = AuthDestination.PENDING_VERIFICATION
                                    }

                                    AccountStatus.REJECTED -> {
                                        UserRepository.signOut()
                                        CurrentUser.reset()
                                        loggedInProfile = null

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Your application was rejected."
                                            )
                                        }
                                    }

                                    AccountStatus.SUSPENDED -> {
                                        UserRepository.signOut()
                                        CurrentUser.reset()
                                        loggedInProfile = null

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Your account has been suspended."
                                            )
                                        }
                                    }
                                }
                            }

                            result.onFailure { exception ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        exception.localizedMessage
                                            ?: "Login failed. Please try again."
                                    )
                                }
                            }
                        }
                    },
                    onRegister = {
                        destination = AuthDestination.REGISTER
                    },
                    onForgotPassword = {
                        destination = AuthDestination.FORGOT_PASSWORD
                    },
                    onSkip = {
                        destination = AuthDestination.HOME
                    }
                )
            }

            AuthDestination.REGISTER -> {
                RegistrationScreen(
                    onBack = {
                        destination = AuthDestination.LOGIN
                    },
                    onContinue = { form ->
                        registrationForm = form
                        destination = AuthDestination.ROLE_SELECTION
                    }
                )
            }

            AuthDestination.ROLE_SELECTION -> {
                RoleSelectionScreen(
                    onBack = {
                        destination = AuthDestination.REGISTER
                    },
                    onContinue = { role ->
                        selectedRole = role

                        when (role) {
                            AccountRole.TOURIST -> {
                                UserRepository.registerTourist(
                                    form = registrationForm
                                ) { result ->

                                    result.onSuccess { profile ->
                                        loggedInProfile = profile
                                        CurrentUser.id = profile.userId
                                        destination = AuthDestination.REGISTRATION_SUCCESS
                                    }

                                    result.onFailure { exception ->
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                exception.localizedMessage
                                                    ?: "Registration failed."
                                            )
                                        }
                                    }
                                }
                            }

                            AccountRole.LOCAL_GUIDE -> {
                                destination = AuthDestination.GUIDE_REGISTRATION
                            }

                            AccountRole.SITE_OWNER -> {
                                destination = AuthDestination.OWNER_REGISTRATION
                            }

                            AccountRole.ADMIN -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Administrator registration is not available."
                                    )
                                }
                            }
                        }
                    }
                )
            }

            AuthDestination.GUIDE_REGISTRATION -> {
                ProfessionalRegistrationScreen(
                    role = AccountRole.LOCAL_GUIDE,
                    onBack = {
                        destination = AuthDestination.ROLE_SELECTION
                    },
                    onSubmit = { form ->
                        UserRepository.registerProfessional(
                            registration = registrationForm,
                            professional = form,
                            role = AccountRole.LOCAL_GUIDE
                        ) { result ->

                            result.onSuccess { profile ->
                                CurrentUser.id = profile.userId
                                destination = AuthDestination.PENDING_VERIFICATION
                            }

                            result.onFailure { exception ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        exception.localizedMessage
                                            ?: "Registration failed."
                                    )
                                }
                            }
                        }
                    }
                )
            }

            AuthDestination.OWNER_REGISTRATION -> {
                ProfessionalRegistrationScreen(
                    role = AccountRole.SITE_OWNER,
                    onBack = {
                        destination = AuthDestination.ROLE_SELECTION
                    },
                    onSubmit = { form ->
                        UserRepository.registerProfessional(
                            registration = registrationForm,
                            professional = form,
                            role = AccountRole.SITE_OWNER
                        ) { result ->

                            result.onSuccess { profile ->
                                CurrentUser.id = profile.userId
                                destination = AuthDestination.PENDING_VERIFICATION
                            }

                            result.onFailure { exception ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        exception.localizedMessage
                                            ?: "Registration failed."
                                    )
                                }
                            }
                        }
                    }
                )
            }

            AuthDestination.FORGOT_PASSWORD -> {
                ForgotPasswordScreen(
                    onBack = {
                        destination = AuthDestination.LOGIN
                    },
                    onSendResetLink = { email ->
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Password reset is not connected yet."
                            )
                        }
                    }
                )
            }

            AuthDestination.REGISTRATION_SUCCESS -> {
                RegistrationResultScreen(
                    pending = false,
                    onContinue = {
                        destination = AuthDestination.HOME
                    }
                )
            }

            AuthDestination.PENDING_VERIFICATION -> {
                RegistrationResultScreen(
                    pending = true,
                    onContinue = {
                        UserRepository.signOut()
                        CurrentUser.reset()
                        loggedInProfile = null
                        destination = AuthDestination.LOGIN
                    }
                )
            }

            AuthDestination.HOME -> {
                HomeScreen(
                    userProfile = loggedInProfile,
                    onLogout = {
                        UserRepository.signOut()
                        CurrentUser.reset()
                        loggedInProfile = null
                        registrationForm = RegistrationFormState()
                        destination = AuthDestination.LOGIN
                    }
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}