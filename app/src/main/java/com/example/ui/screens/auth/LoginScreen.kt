package com.example.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

enum class AuthMode {
    EMAIL_PASSWORD,
    GOOGLE_CREDENTIALS,
    PHONE_OTP,
    DEMO_CITIZEN
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    errorMessage: String? = null,
    onPerformLogin: (email: String, pass: String, onSuccess: () -> Unit) -> Unit,
    onDemoLogin: (onSuccess: () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var selectedAuthMode by remember { mutableStateOf(AuthMode.EMAIL_PASSWORD) }

    // Email / Password Form State
    var email by remember { mutableStateOf("alex.morgan@civicpulse.org") }
    var password by remember { mutableStateOf("civicpass123") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }

    // Phone OTP Form State
    var phoneNumber by remember { mutableStateOf("+1 (555) 019-2834") }
    var otpCode by remember { mutableStateOf("") }
    var isOtpDispatched by remember { mutableStateOf(false) }

    // Status State
    var isAuthenticating by remember { mutableStateOf(false) }
    var localError by remember { mutableStateOf<String?>(null) }
    var authStatusMessage by remember { mutableStateOf<String?>(null) }

    AnimatedCityBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                CivicPulseLogo(size = 68.dp)

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Welcome to Civic Pulse",
                    color = CivicWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Secure Citizen Portal & Representative Access",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Auth Mode Selector Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF0A142E))
                        .border(1.dp, Color(0x3338BDF8), RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val tabs = listOf(
                        Triple(AuthMode.EMAIL_PASSWORD, "Email", Icons.Default.Email),
                        Triple(AuthMode.GOOGLE_CREDENTIALS, "Google", Icons.Default.AccountCircle),
                        Triple(AuthMode.PHONE_OTP, "Phone", Icons.Default.Phone),
                        Triple(AuthMode.DEMO_CITIZEN, "Demo", Icons.Default.PlayCircle)
                    )

                    tabs.forEach { (mode, label, icon) ->
                        val isSelected = selectedAuthMode == mode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) CivicRoyalBlue else Color.Transparent)
                                .clickable {
                                    selectedAuthMode = mode
                                    localError = null
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    tint = if (isSelected) Color.White else Color(0xFF94A3B8),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = label,
                                    color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Main Authentication Card Shell
                CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                    val displayError = localError ?: errorMessage
                    if (displayError != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0x2EEF4444),
                            border = BorderStroke(1.dp, Color(0xFFEF4444)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = displayError,
                                color = Color(0xFFF87171),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }

                    if (authStatusMessage != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0x2E10B981),
                            border = BorderStroke(1.dp, Color(0xFF10B981)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Text(
                                text = authStatusMessage!!,
                                color = Color(0xFF34D399),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }

                    // 1. Email & Password Mode
                    if (selectedAuthMode == AuthMode.EMAIL_PASSWORD) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it; localError = null },
                            label = { Text("Citizen Email", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = CivicCyan) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CivicCyan,
                                unfocusedBorderColor = Color(0x6638BDF8),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_email_input")
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it; localError = null },
                            label = { Text("Password", color = Color(0xFF94A3B8)) },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = CivicCyan) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = null,
                                        tint = Color(0xFF94A3B8)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CivicCyan,
                                unfocusedBorderColor = Color(0x6638BDF8),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_password_input")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(checkedColor = CivicCyan)
                                )
                                Text("Remember me", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                            }

                            Text(
                                text = "Forgot password?",
                                color = CivicCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable { onNavigateToForgotPassword() }
                                    .padding(4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        CivicButton(
                            text = "SIGN IN",
                            isLoading = isAuthenticating,
                            testTag = "sign_in_button",
                            onClick = {
                                isAuthenticating = true
                                onPerformLogin(email, password) {
                                    isAuthenticating = false
                                    onLoginSuccess()
                                }
                                isAuthenticating = false
                            }
                        )
                    }

                    // 2. Google Identity & Credential Manager Shell
                    if (selectedAuthMode == AuthMode.GOOGLE_CREDENTIALS) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E3A8A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.VpnKey,
                                    contentDescription = "Credential Manager",
                                    tint = CivicCyan,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Google Credential Manager",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Sign in securely using Android Jetpack Credential Manager and passkeys.",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Google Sign-In Button Shell
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .clickable {
                                        isAuthenticating = true
                                        authStatusMessage = "Connecting with Google Identity Service..."
                                        onDemoLogin {
                                            isAuthenticating = false
                                            onLoginSuccess()
                                        }
                                    }
                                    .testTag("google_credential_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text("G", color = Color(0xFF4285F4), fontSize = 20.sp, fontWeight = FontWeight.Black)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Continue with Google Account",
                                        color = Color(0xFF1F2937),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // 3. Phone OTP Verification Shell
                    if (selectedAuthMode == AuthMode.PHONE_OTP) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Instant Phone Verification",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "A one-time security passcode will be sent via SMS.",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                label = { Text("Mobile Number", color = Color(0xFF94A3B8)) },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = CivicCyan) },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CivicCyan,
                                    unfocusedBorderColor = Color(0x6638BDF8),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (isOtpDispatched) {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = otpCode,
                                    onValueChange = { otpCode = it },
                                    label = { Text("6-Digit OTP Code", color = Color(0xFF94A3B8)) },
                                    leadingIcon = { Icon(Icons.Default.Pin, contentDescription = null, tint = CivicCyan) },
                                    placeholder = { Text("e.g. 849201", color = Color(0xFF64748B)) },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = CivicCyan,
                                        unfocusedBorderColor = Color(0x6638BDF8),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            CivicButton(
                                text = if (isOtpDispatched) "VERIFY & SIGN IN" else "SEND OTP CODE",
                                testTag = "otp_action_button",
                                onClick = {
                                    if (!isOtpDispatched) {
                                        isOtpDispatched = true
                                        authStatusMessage = "SMS Code dispatched to $phoneNumber (Demo code: 849201)"
                                    } else {
                                        onDemoLogin { onLoginSuccess() }
                                    }
                                }
                            )
                        }
                    }

                    // 4. One-Click Citizen Demo Login
                    if (selectedAuthMode == AuthMode.DEMO_CITIZEN) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Citizen Alex Morgan",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Ward 4 — Central District • Metro North",
                                color = CivicCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "One-click pre-configured citizen credentials for fast evaluation.",
                                color = Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CivicButton(
                                text = "LAUNCH DEMO SESSION",
                                testTag = "launch_demo_button",
                                onClick = {
                                    onDemoLogin { onLoginSuccess() }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Biometric Authentication Shortcut Shell
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F1A3A))
                            .border(1.dp, Color(0x3338BDF8), RoundedCornerShape(10.dp))
                            .clickable {
                                authStatusMessage = "Biometric credential verified."
                                onDemoLogin { onLoginSuccess() }
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Fingerprint,
                            contentDescription = "Biometric Sign In",
                            tint = CivicCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Quick Biometric / Device Key Sign-In",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Navigation to Sign Up
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "New to Civic Pulse? ",
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Create an account",
                        color = CivicCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onNavigateToSignUp() }
                            .padding(4.dp)
                            .testTag("navigate_signup_button")
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Civic Pulse verifies citizen data in accordance with open governance charter.",
                    color = Color(0xFF64748B),
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
