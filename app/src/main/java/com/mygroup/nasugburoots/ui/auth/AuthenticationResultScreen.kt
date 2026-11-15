package com.mygroup.nasugburoots.ui.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Muted

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    onSendResetLink: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.Start),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Login", color = Forest)
        }

        Spacer(Modifier.height(35.dp))

        Box(
            modifier = Modifier
                .size(94.dp)
                .clip(CircleShape)
                .background(Color(0xFFE1EDE5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.LockReset,
                contentDescription = null,
                tint = Forest,
                modifier = Modifier.size(43.dp)
            )
        }

        Spacer(Modifier.height(25.dp))

        Text(
            "ACCOUNT RECOVERY",
            color = Color(0xFFB57B30),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )

        Text(
            "Forgot Your Password?",
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 7.dp)
        )

        Text(
            "Enter the email address associated with your account. We can use it to request a secure password reset.",
            color = Muted,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("you@example.com") },
            label = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    Icons.Outlined.MailOutline,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(14.dp)
        )

        if (errorMessage != null) {
            Text(
                errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                    errorMessage = "Please enter a valid email address."
                } else {
                    onSendResetLink(email.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .height(54.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Forest,
                contentColor = Color.White
            )
        ) {
            Text(
                "Send Reset Link",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RegistrationResultScreen(
    pending: Boolean,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 27.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(35.dp))

        Text(
            "🌴 Nasugbu Roots",
            color = Forest,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .size(108.dp)
                .clip(CircleShape)
                .background(Color(0xFFDCEBE1)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (pending) Gold else Forest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (pending) {
                        Icons.Outlined.VerifiedUser
                    } else {
                        Icons.Filled.Check
                    },
                    contentDescription = null,
                    tint = if (pending) DeepForest else Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }
        }

        Surface(
            modifier = Modifier.padding(top = 24.dp),
            shape = RoundedCornerShape(30.dp),
            color = Color(0xFFE5EFE8)
        ) {
            Text(
                if (pending) "PENDING VERIFICATION"
                else "REGISTRATION COMPLETE",
                color = Forest,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp,
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 7.dp
                )
            )
        }

        Text(
            if (pending) "Application Received!"
            else "Welcome to the Roots!",
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            if (pending) {
                "Thank you for joining Nasugbu Roots. Your application will be reviewed by an authorized administrator."
            } else {
                "Your account registration is complete. The stories, places, and Nasugbuenos are waiting for you."
            },
            color = Muted,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            lineHeight = 20.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        if (pending) {
            VerificationTimeline(
                modifier = Modifier.padding(top = 28.dp)
            )
        } else {
            Surface(
                shape = RoundedCornerShape(17.dp),
                color = Color(0xFFEDF3ED),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            ) {
                Row(
                    modifier = Modifier.padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Explore,
                        contentDescription = null,
                        tint = Forest,
                        modifier = Modifier.size(31.dp)
                    )

                    Column(
                        modifier = Modifier.padding(start = 11.dp)
                    ) {
                        Text(
                            "Your journey begins here",
                            color = DeepForest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )

                        Text(
                            "Discover heritage destinations and local experiences.",
                            color = Muted,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(54.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Forest,
                contentColor = Color.White
            )
        ) {
            Text(
                if (pending) "Return to Login"
                else "Start Exploring",
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun VerificationTimeline(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val steps = listOf(
            "Application submitted" to "Your information has been received.",
            "Administrator review" to "Your documents will be checked.",
            "Profile activation" to "Your account status will be updated."
        )

        steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(
                            when (index) {
                                0 -> Forest
                                1 -> Gold
                                else -> Color(0xFFE7EBE8)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (index == 0) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Text(
                            "${index + 1}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepForest
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        step.first,
                        color = DeepForest,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )

                    Text(
                        step.second,
                        color = Muted,
                        fontSize = 9.sp,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }
            }
        }
    }
}
