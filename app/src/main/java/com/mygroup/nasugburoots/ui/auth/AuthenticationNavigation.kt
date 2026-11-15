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
import com.mygroup.nasugburoots.ui.HomeScreen
import kotlinx.coroutines.launch

@Composable
fun NasugbuRootsAuthApp() {

    var destination by remember {
        mutableStateOf(AuthDestination.LOGIN)
    }

    var selectedRole by remember {
        mutableStateOf(AccountRole.TOURIST)
    }

    // Temporary in-memory form data for future backend integration.
    var registrationForm by remember {
        mutableStateOf(RegistrationFormState())
    }

    var professionalForm by remember {
        mutableStateOf(ProfessionalFormState())
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
                    onLogin = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Form validated. Real login requires backend setup."
                            )
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

                        destination = when (role) {
                            AccountRole.TOURIST ->
                                AuthDestination.REGISTRATION_SUCCESS

                            AccountRole.LOCAL_GUIDE ->
                                AuthDestination.GUIDE_REGISTRATION

                            AccountRole.SITE_OWNER ->
                                AuthDestination.OWNER_REGISTRATION

                            AccountRole.ADMIN ->
                                AuthDestination.LOGIN
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
                        professionalForm = form
                        destination = AuthDestination.PENDING_VERIFICATION
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
                        professionalForm = form
                        destination = AuthDestination.PENDING_VERIFICATION
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
                                "Email validated. Password reset requires backend setup."
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
                        destination = AuthDestination.LOGIN
                    }
                )
            }

            AuthDestination.HOME -> {
                HomeScreen()
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
