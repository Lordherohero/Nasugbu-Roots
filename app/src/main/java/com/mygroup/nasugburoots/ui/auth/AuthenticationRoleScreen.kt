package com.mygroup.nasugburoots.ui.auth

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Muted

@Composable
fun RoleSelectionScreen(
    onBack: () -> Unit,
    onContinue: (AccountRole) -> Unit
) {
    var selectedRole by remember {
        mutableStateOf(AccountRole.TOURIST)
    }

    val availableRoles = listOf(
        AccountRole.TOURIST,
        AccountRole.LOCAL_GUIDE,
        AccountRole.SITE_OWNER
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        RoleHeader(
            onBack = onBack,
            step = "2 OF 2"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 25.dp)
        ) {
            Text(
                "CHOOSE YOUR PATH",
                color = Color(0xFFB57B30),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.5.sp
            )

            Text(
                "How will you use\nNasugbu Roots?",
                fontFamily = FontFamily.Serif,
                fontSize = 29.sp,
                lineHeight = 34.sp,
                fontWeight = FontWeight.Bold,
                color = DeepForest,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                "Select your role please.",
                color = Muted,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 7.dp)
            )

            Spacer(Modifier.height(22.dp))

            availableRoles.forEach { role ->
                RoleOptionCard(
                    role = role,
                    selected = selectedRole == role,
                    onClick = {
                        selectedRole = role
                    }
                )

                Spacer(Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    onContinue(selectedRole)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Forest,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Continue as ${selectedRole.displayName}",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 17.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Outlined.VerifiedUser,
                    contentDescription = null,
                    tint = Muted,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(Modifier.width(7.dp))

                Text(
                    "Your information is protected and used to personalize your experience.",
                    color = Muted,
                    fontSize = 9.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun RoleOptionCard(
    role: AccountRole,
    selected: Boolean,
    onClick: () -> Unit
) {
    val icon = when (role) {
        AccountRole.TOURIST -> Icons.Outlined.LocationOn
        AccountRole.LOCAL_GUIDE -> Icons.Outlined.Explore
        AccountRole.SITE_OWNER -> Icons.Outlined.AccountBalance
        AccountRole.ADMIN -> Icons.Outlined.AdminPanelSettings
    }

    val iconBackground = when (role) {
        AccountRole.TOURIST -> Color(0xFFE4EEE7)
        AccountRole.LOCAL_GUIDE -> Color(0xFFFBECD1)
        AccountRole.SITE_OWNER -> Color(0xFFEAE8F3)
        AccountRole.ADMIN -> Color.LightGray
    }

    val iconColor = when (role) {
        AccountRole.TOURIST -> Forest
        AccountRole.LOCAL_GUIDE -> Color(0xFF99601D)
        AccountRole.SITE_OWNER -> Color(0xFF5B5686)
        AccountRole.ADMIN -> DeepForest
    }

    val subtitle = when (role) {
        AccountRole.TOURIST -> "EXPLORE & DISCOVER"
        AccountRole.LOCAL_GUIDE -> "SHARE LOCAL KNOWLEDGE"
        AccountRole.SITE_OWNER -> "WELCOME MORE VISITORS"
        AccountRole.ADMIN -> "SYSTEM MANAGEMENT"
    }

    val description = when (role) {
        AccountRole.TOURIST ->
            "Explore destinations, discover heritage stories, find local guides, and book tours."

        AccountRole.LOCAL_GUIDE ->
            "Offer guided tours, share your knowledge, and manage your bookings."

        AccountRole.SITE_OWNER ->
            "Manage your destination, update information, and monitor availability."

        AccountRole.ADMIN ->
            "Manage and verify the tourism platform."
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        color = if (selected) Color(0xFFF0F6F1) else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 1.5.dp else 1.dp,
            color = if (selected) Forest else Color(0xFFE0E5DF)
        ),
        shadowElevation = if (selected) 3.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = role.displayName,
                    tint = iconColor,
                    modifier = Modifier.size(23.dp)
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 13.dp)
                    .weight(1f)
            ) {
                Text(
                    subtitle,
                    color = Color(0xFF9A6C31),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.7.sp
                )

                Text(
                    role.displayName,
                    color = DeepForest,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )

                Text(
                    description,
                    color = Muted,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            if (selected) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .background(Forest, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfessionalRegistrationScreen(
    role: AccountRole,
    onBack: () -> Unit,
    onSubmit: (ProfessionalFormState) -> Unit
) {
    val context = LocalContext.current

    var form by remember {
        mutableStateOf(ProfessionalFormState())
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val isGuide = role == AccountRole.LOCAL_GUIDE
    val isOwner = role == AccountRole.SITE_OWNER

    val documentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // The selected URI can still be used during this session.
            }

            form = form.copy(
                documentUri = uri.toString()
            )

            errorMessage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        RoleHeader(
            onBack = onBack,
            step = "VERIFICATION"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 22.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFE4EEE7)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isGuide) {
                            Icons.Outlined.Explore
                        } else {
                            Icons.Outlined.AccountBalance
                        },
                        contentDescription = null,
                        tint = Forest,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ) {
                    Text(
                        if (isGuide) {
                            "BECOME A STORYTELLER"
                        } else {
                            "JOIN OUR DESTINATIONS"
                        },
                        color = Color(0xFFB57B30),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )

                    Text(
                        if (isGuide) {
                            "Local Guide Profile"
                        } else {
                            "Site Owner Profile"
                        },
                        color = DeepForest,
                        fontFamily = FontFamily.Serif,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            VerificationNotice()

            ProfessionalInput(
                label = "Complete Name",
                value = form.fullName,
                onValueChange = {
                    form = form.copy(fullName = it)
                    errorMessage = null
                },
                placeholder = "Your legal name",
                icon = Icons.Outlined.Person
            )

            ProfessionalInput(
                label = "Email Address",
                value = form.email,
                onValueChange = {
                    form = form.copy(email = it)
                    errorMessage = null
                },
                placeholder = "you@example.com",
                icon = Icons.Outlined.Email
            )

            ProfessionalInput(
                label = "Phone Number",
                value = form.phoneNumber,
                onValueChange = {
                    form = form.copy(phoneNumber = it)
                    errorMessage = null
                },
                placeholder = "+63 912 345 6789",
                icon = Icons.Outlined.Phone
            )

            if (isGuide) {
                ProfessionalInput(
                    label = "Location",
                    value = form.location,
                    onValueChange = {
                        form = form.copy(location = it)
                        errorMessage = null
                    },
                    placeholder = "Barangay, Nasugbu",
                    icon = Icons.Outlined.LocationOn
                )

                ProfessionalInput(
                    label = "Short Biography",
                    value = form.biography,
                    onValueChange = {
                        form = form.copy(biography = it)
                        errorMessage = null
                    },
                    placeholder = "Tell visitors about your local knowledge...",
                    icon = Icons.Outlined.Person,
                    multiline = true
                )

                DocumentPickerCard(
                    title = "Guide credentials or supporting document",
                    selected = !form.documentUri.isNullOrBlank(),
                    onClick = {
                        documentPicker.launch(
                            arrayOf(
                                "application/pdf",
                                "image/jpeg",
                                "image/png"
                            )
                        )
                    }
                )
            }

            if (isOwner) {
                ProfessionalInput(
                    label = "Establishment Name",
                    value = form.establishmentName,
                    onValueChange = {
                        form = form.copy(establishmentName = it)
                        errorMessage = null
                    },
                    placeholder = "Name of destination or establishment",
                    icon = Icons.Outlined.AccountBalance
                )

                ProfessionalInput(
                    label = "Tourist Site Address",
                    value = form.siteAddress,
                    onValueChange = {
                        form = form.copy(siteAddress = it)
                        errorMessage = null
                    },
                    placeholder = "Complete site address",
                    icon = Icons.Outlined.LocationOn,
                    multiline = true
                )

                DocumentPickerCard(
                    title = "Ownership or authorization document",
                    selected = !form.documentUri.isNullOrBlank(),
                    onClick = {
                        documentPicker.launch(
                            arrayOf(
                                "application/pdf",
                                "image/jpeg",
                                "image/png"
                            )
                        )
                    }
                )
            }

            if (!isGuide && !isOwner) {
                Text(
                    "Invalid professional registration role.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            Button(
                onClick = {
                    val error = validateProfessionalRegistration(
                        form,
                        role
                    )

                    if (error != null) {
                        errorMessage = error
                    } else {
                        onSubmit(form)
                    }
                },
                enabled = isGuide || isOwner,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Forest,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Submit for Verification",
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(25.dp))
        }
    }
}

@Composable
private fun ProfessionalInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    multiline: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp)
    ) {
        Text(
            label,
            color = DeepForest,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 3.dp, bottom = 7.dp)
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
            minLines = if (multiline) 3 else 1,
            maxLines = if (multiline) 4 else 1,
            singleLine = !multiline,
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
private fun DocumentPickerCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp, bottom = 18.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF4F8F4),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (selected) Forest else Color(0xFF9DB5AA)
        )
    ) {
        Column(
            modifier = Modifier.padding(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE0ECE4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (selected) {
                        Icons.Filled.Check
                    } else {
                        Icons.Outlined.UploadFile
                    },
                    contentDescription = null,
                    tint = Forest,
                    modifier = Modifier.size(22.dp)
                )
            }

            Text(
                if (selected) "Document selected" else title,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = DeepForest,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                if (selected) {
                    "Tap to replace your document"
                } else {
                    "PDF, JPG or PNG"
                },
                color = Muted,
                fontSize = 9.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun VerificationNotice() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFFFF8E5),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFFE8D197)
        )
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Outlined.VerifiedUser,
                contentDescription = null,
                tint = Color(0xFF805921),
                modifier = Modifier.size(21.dp)
            )

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    "Identity verification required",
                    color = Color(0xFF805921),
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )

                Text(
                    "Your application will be reviewed by the authorized tourism administrator.",
                    color = Color(0xFF8A7455),
                    fontSize = 9.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }
    }
}

@Composable
private fun RoleHeader(
    onBack: () -> Unit,
    step: String
) {
    Surface(color = Cream) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
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
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            Text(
                step,
                color = Muted,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    HorizontalDivider(color = Color(0xFFE8EBE5))
}
