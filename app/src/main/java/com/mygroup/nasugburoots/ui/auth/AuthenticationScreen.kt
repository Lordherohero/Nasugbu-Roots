package com.mygroup.nasugburoots.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.Muted

@Composable
fun LoginScreen(
    onLogin: (LoginFormState) -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit,
    onSkip: () -> Unit
) {
    var form by remember { mutableStateOf(LoginFormState()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
    ) {
        // Scenic header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Forest)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1739365414685-2da55bc82113?auto=format&fit=crop&w=1200&q=88",
                contentDescription = "Nasugbu coastal scenery",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0x55362F22),
                                Color(0xCC19362D)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {
                Text(
                    "NASUGBU ROOTS",
                    color = Gold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Text(
                    "Find your way\nback to the stories.",
                    color = Color.White,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .background(
                    Cream,
                    RoundedCornerShape(
                        topStart = 26.dp,
                        topEnd = 26.dp
                    )
                )
                .padding(horizontal = 24.dp, vertical = 25.dp)
        ) {
            AuthHeading(
                eyebrow = "MAGANDANG ARAW",
                title = "Welcome Back!",
                description = "Sign in to continue your Nasugbu trip."
            )

            Spacer(Modifier.height(18.dp))

            AuthInputField(
                label = "Email Address",
                value = form.email,
                onValueChange = {
                    form = form.copy(email = it)
                    errorMessage = null
                },
                placeholder = "NasugbuRoots@gmail.com",
                icon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(14.dp))

            AuthInputField(
                label = "Password",
                value = form.password,
                onValueChange = {
                    form = form.copy(password = it)
                    errorMessage = null
                },
                placeholder = "Enter your password",
                icon = Icons.Outlined.Lock,
                password = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = form.rememberMe,
                    onCheckedChange = {
                        form = form.copy(rememberMe = it)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Forest
                    )
                )

                Text(
                    "Remember Me",
                    color = Muted,
                    fontSize = 11.sp
                )

                Spacer(Modifier.weight(1f))

                TextButton(onClick = onForgotPassword) {
                    Text(
                        "Forgot Password?",
                        color = Forest,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            AuthPrimaryButton(
                text = "Login",
                onClick = {
                    val error = validateLogin(form)

                    if (error == null) {
                        onLogin(form)
                    } else {
                        errorMessage = error
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = onSkip,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Skip for Testing",
                    color = Forest,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Don't have an account?",
                    color = Muted,
                    fontSize = 11.sp
                )

                TextButton(onClick = onRegister) {
                    Text(
                        "Sign Up",
                        color = Forest,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationScreen(
    onBack: () -> Unit,
    onContinue: (RegistrationFormState) -> Unit
) {
    var form by remember {
        mutableStateOf(RegistrationFormState())
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        AuthBackHeader(
            onBack = onBack,
            step = "1 OF 2"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 25.dp)
        ) {
            AuthHeading(
                eyebrow = "JOIN THE COMMUNITY",
                title = "Create Your Account",
                description = "Your next local story starts here."
            )

            Spacer(Modifier.height(12.dp))

            AuthInputField(
                label = "Full Name",
                value = form.fullName,
                onValueChange = {
                    form = form.copy(fullName = it)
                    errorMessage = null
                },
                placeholder = "Juan Dela Cruz",
                icon = Icons.Outlined.Person
            )

            Spacer(Modifier.height(13.dp))

            AuthInputField(
                label = "Email Address",
                value = form.email,
                onValueChange = {
                    form = form.copy(email = it)
                    errorMessage = null
                },
                placeholder = "juan@example.com",
                icon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(13.dp))

            AuthInputField(
                label = "Phone Number",
                value = form.phoneNumber,
                onValueChange = {
                    form = form.copy(phoneNumber = it)
                    errorMessage = null
                },
                placeholder = "+63 912 345 6789",
                icon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone
            )

            Spacer(Modifier.height(13.dp))

            AuthInputField(
                label = "Password",
                value = form.password,
                onValueChange = {
                    form = form.copy(password = it)
                    errorMessage = null
                },
                placeholder = "At least 8 characters",
                icon = Icons.Outlined.Lock,
                password = true
            )

            Spacer(Modifier.height(13.dp))

            AuthInputField(
                label = "Confirm Password",
                value = form.confirmPassword,
                onValueChange = {
                    form = form.copy(confirmPassword = it)
                    errorMessage = null
                },
                placeholder = "Re-enter your password",
                icon = Icons.Outlined.Lock,
                password = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = form.acceptedTerms,
                    onCheckedChange = {
                        form = form.copy(acceptedTerms = it)
                        errorMessage = null
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Forest
                    )
                )

                Text(
                    "I agree to the Terms & Conditions and Privacy Policy.",
                    color = Muted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            AuthPrimaryButton(
                text = "Create Account",
                gold = true,
                onClick = {
                    val error = validateRegistration(form)

                    if (error == null) {
                        onContinue(form)
                    } else {
                        errorMessage = error
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    color = Muted,
                    fontSize = 11.sp
                )

                TextButton(onClick = onBack) {
                    Text(
                        "Log In",
                        color = Forest,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun AuthHeading(
    eyebrow: String,
    title: String,
    description: String
) {
    Column {
        Text(
            eyebrow,
            color = Color(0xFFB57B30),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )

        Text(
            title,
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 35.sp,
            modifier = Modifier.padding(top = 5.dp)
        )

        Text(
            description,
            color = Muted,
            fontSize = 13.sp,
            lineHeight = 19.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}

@Composable
private fun AuthInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    password: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    Column {
        Text(
            label,
            color = DeepForest,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(
                start = 3.dp,
                bottom = 7.dp
            )
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFFA2AEA9),
                    fontSize = 13.sp
                )
            },
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Muted,
                    modifier = Modifier.size(19.dp)
                )
            },
            trailingIcon = if (password) {
                {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) {
                                Icons.Outlined.VisibilityOff
                            } else {
                                Icons.Outlined.Visibility
                            },
                            contentDescription = if (passwordVisible) {
                                "Hide password"
                            } else {
                                "Show password"
                            },
                            tint = Muted
                        )
                    }
                }
            } else null,
            visualTransformation = if (
                password && !passwordVisible
            ) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (password) {
                    KeyboardType.Password
                } else {
                    keyboardType
                }
            ),
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Forest,
                unfocusedBorderColor = Color(0xFFDFE5DF)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun AuthPrimaryButton(
    text: String,
    onClick: () -> Unit,
    gold: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (gold) Gold else Forest,
            contentColor = if (gold) DeepForest else Color.White
        )
    ) {
        Text(
            text,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.width(8.dp))

        Icon(
            Icons.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun AuthBackHeader(
    onBack: () -> Unit,
    step: String
) {
    Surface(color = Cream) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedIconButton(
                onClick = onBack,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Go back",
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "🌴 NASUGBU ROOTS",
                color = Forest,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )

            Spacer(Modifier.weight(1f))

            Text(
                step,
                color = Muted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    HorizontalDivider(
        color = Color(0xFFE8EBE5)
    )
}
