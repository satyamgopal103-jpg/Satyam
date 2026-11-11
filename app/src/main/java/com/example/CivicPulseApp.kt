package com.example

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Complaint
import com.example.ui.components.CivicBottomNav
import com.example.ui.components.CivicDrawer
import com.example.ui.screens.admin.AdminCenterScreen
import com.example.ui.screens.assistant.CivicAssistantScreen
import com.example.ui.screens.auth.ForgotPasswordScreen
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.auth.SignUpScreen
import com.example.ui.screens.communities.CommunitiesScreen
import com.example.ui.screens.complaints.ComplaintDetailScreen
import com.example.ui.screens.complaints.ComplaintsListScreen
import com.example.ui.screens.complaints.CreateComplaintScreen
import com.example.ui.screens.explore.ExploreScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.loading.DashboardLoadingScreen
import com.example.ui.screens.maps.CivicMapScreen
import com.example.ui.screens.messages.MessagesScreen
import com.example.ui.screens.notifications.NotificationsScreen
import com.example.ui.screens.onboarding.NotificationPermissionScreen
import com.example.ui.screens.polls.PollsScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.representatives.RepresentativesScreen
import com.example.ui.screens.projects.ProjectsScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.splash.SplashScreen
import com.example.ui.screens.themes.ThemesScreen
import com.example.ui.screens.volunteering.VolunteeringScreen
import com.example.ui.theme.CivicPulseTheme
import com.example.viewmodel.CivicViewModel
import com.example.viewmodel.Screen

@Composable
fun CivicPulseApp(viewModel: CivicViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val themePreset by viewModel.currentThemePreset.collectAsStateWithLifecycle()
    val themeMode by viewModel.currentThemeMode.collectAsStateWithLifecycle()
    val backgroundPreset by viewModel.currentBackgroundPreset.collectAsStateWithLifecycle()
    val fontPreset by viewModel.currentFontPreset.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val isDrawerOpen by viewModel.isDrawerOpen.collectAsStateWithLifecycle()

    val isNotificationsAllowed by viewModel.isNotificationsAllowed.collectAsStateWithLifecycle()
    val isLocationServicesAllowed by viewModel.isLocationServicesAllowed.collectAsStateWithLifecycle()
    val isHapticFeedbackEnabled by viewModel.isHapticFeedbackEnabled.collectAsStateWithLifecycle()
    val isHighContrastEnabled by viewModel.isHighContrastEnabled.collectAsStateWithLifecycle()
    val isAutoSyncEnabled by viewModel.isAutoSyncEnabled.collectAsStateWithLifecycle()
    val preferredLanguage by viewModel.preferredLanguage.collectAsStateWithLifecycle()

    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val complaints by viewModel.complaints.collectAsStateWithLifecycle()
    val representatives by viewModel.representatives.collectAsStateWithLifecycle()
    val projects by viewModel.projects.collectAsStateWithLifecycle()
    val communities by viewModel.communities.collectAsStateWithLifecycle()
    val polls by viewModel.polls.collectAsStateWithLifecycle()
    val volunteerOpportunities by viewModel.volunteerOpportunities.collectAsStateWithLifecycle()
    val news by viewModel.news.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val assistantChat by viewModel.assistantChat.collectAsStateWithLifecycle()
    val authError by viewModel.authError.collectAsStateWithLifecycle()
    val actionFeedback by viewModel.actionFeedback.collectAsStateWithLifecycle()
    val selectedComplaint by viewModel.selectedComplaint.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(actionFeedback) {
        if (actionFeedback != null) {
            snackbarHostState.showSnackbar(actionFeedback!!)
            viewModel.clearFeedback()
        }
    }

    // Handle system back navigation
    BackHandler(enabled = currentScreen !is Screen.Home && currentScreen !is Screen.Splash && currentScreen !is Screen.Login) {
        if (isDrawerOpen) {
            viewModel.closeDrawer()
        } else {
            viewModel.navigateBack()
        }
    }

    CivicPulseTheme(
        themePreset = themePreset,
        fontPreset = fontPreset,
        darkTheme = isDarkMode
    ) {
        val showBottomNav = currentScreen is Screen.Home ||
                currentScreen is Screen.Explore ||
                currentScreen is Screen.Notifications ||
                currentScreen is Screen.Profile

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                if (showBottomNav) {
                    CivicBottomNav(
                        currentScreen = currentScreen,
                        isDarkMode = isDarkMode,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        is Screen.Splash -> {
                            SplashScreen(
                                onSplashFinished = {
                                    viewModel.navigateTo(Screen.Login)
                                }
                            )
                        }

                        is Screen.Login -> {
                            LoginScreen(
                                onLoginSuccess = {
                                    viewModel.navigateTo(Screen.NotificationPermission)
                                },
                                onNavigateToSignUp = {
                                    viewModel.navigateTo(Screen.SignUp)
                                },
                                onNavigateToForgotPassword = {
                                    viewModel.navigateTo(Screen.ForgotPassword)
                                },
                                errorMessage = authError,
                                onPerformLogin = { email, pass, onSuccess ->
                                    viewModel.login(email, pass, onSuccess)
                                },
                                onDemoLogin = { onSuccess ->
                                    viewModel.loginDemo(onSuccess)
                                }
                            )
                        }

                        is Screen.SignUp -> {
                            SignUpScreen(
                                onSignUpSuccess = {
                                    viewModel.navigateTo(Screen.NotificationPermission)
                                },
                                onNavigateToLogin = {
                                    viewModel.navigateBack()
                                },
                                errorMessage = authError,
                                onPerformSignUp = { name, email, phone, pass, confirm, terms, onSuccess ->
                                    viewModel.signUp(name, email, phone, pass, confirm, terms, onSuccess)
                                }
                            )
                        }

                        is Screen.ForgotPassword -> {
                            ForgotPasswordScreen(
                                onNavigateBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.NotificationPermission -> {
                            NotificationPermissionScreen(
                                onContinue = { allowed ->
                                    viewModel.setNotificationsAllowed(allowed)
                                    viewModel.navigateTo(Screen.DashboardLoading)
                                }
                            )
                        }

                        is Screen.DashboardLoading -> {
                            DashboardLoadingScreen(
                                onLoadingComplete = {
                                    viewModel.navigateTo(Screen.Home)
                                }
                            )
                        }

                        is Screen.Home -> {
                            HomeScreen(
                                currentUser = currentUser,
                                complaints = complaints,
                                representatives = representatives,
                                projects = projects,
                                news = news,
                                unreadNotificationsCount = notifications.count { !it.isRead },
                                isDarkMode = isDarkMode,
                                onMenuClick = { viewModel.openDrawer() },
                                onNavigate = { viewModel.navigateTo(it) },
                                onSelectComplaint = { viewModel.selectComplaint(it) },
                                onUpvoteComplaint = { viewModel.upvoteComplaint(it) },
                                onToggleDarkMode = { viewModel.toggleDarkMode(!isDarkMode) }
                            )
                        }

                        is Screen.Explore -> {
                            ExploreScreen(
                                onNavigate = { viewModel.navigateTo(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Complaints -> {
                            ComplaintsListScreen(
                                complaints = complaints,
                                onSelectComplaint = { viewModel.selectComplaint(it) },
                                onUpvoteComplaint = { viewModel.upvoteComplaint(it) },
                                onCreateNewComplaint = { viewModel.navigateTo(Screen.CreateComplaint) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.CreateComplaint -> {
                            CreateComplaintScreen(
                                onSubmit = { title, desc, cat, loc ->
                                    viewModel.submitNewComplaint(title, desc, cat, loc)
                                },
                                onComplaintCreated = { newCmp ->
                                    viewModel.selectComplaint(newCmp)
                                },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.ComplaintDetail -> {
                            val cmp = selectedComplaint ?: complaints.firstOrNull()
                            if (cmp != null) {
                                ComplaintDetailScreen(
                                    complaint = cmp,
                                    onUpvote = { viewModel.upvoteComplaint(cmp.id) },
                                    onBack = { viewModel.navigateBack() }
                                )
                            }
                        }

                        is Screen.CivicMap -> {
                            CivicMapScreen(
                                complaints = complaints,
                                onSelectComplaint = { viewModel.selectComplaint(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Representatives -> {
                            RepresentativesScreen(
                                representatives = representatives,
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Projects -> {
                            ProjectsScreen(
                                projects = projects,
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Communities -> {
                            CommunitiesScreen(
                                communities = communities,
                                onToggleJoin = { viewModel.toggleCommunity(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Messages -> {
                            MessagesScreen(
                                conversations = conversations,
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Polls -> {
                            PollsScreen(
                                polls = polls,
                                onVote = { pId, oId -> viewModel.votePoll(pId, oId) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Volunteering -> {
                            VolunteeringScreen(
                                opportunities = volunteerOpportunities,
                                onToggleRegister = { viewModel.toggleVolunteer(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.CivicAssistant -> {
                            CivicAssistantScreen(
                                messages = assistantChat,
                                onSendMessage = { viewModel.askAssistant(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Notifications -> {
                            NotificationsScreen(
                                notifications = notifications,
                                onMarkAllRead = { viewModel.repository.markAllNotificationsRead() },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Profile -> {
                            ProfileScreen(
                                user = currentUser,
                                onNavigate = { viewModel.navigateTo(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Settings -> {
                            SettingsScreen(
                                user = currentUser,
                                isDarkMode = isDarkMode,
                                themeMode = themeMode,
                                currentThemePreset = themePreset,
                                currentBackgroundPreset = backgroundPreset,
                                currentFontPreset = fontPreset,
                                isNotificationsAllowed = isNotificationsAllowed,
                                isLocationServicesAllowed = isLocationServicesAllowed,
                                isHapticFeedbackEnabled = isHapticFeedbackEnabled,
                                isHighContrastEnabled = isHighContrastEnabled,
                                isAutoSyncEnabled = isAutoSyncEnabled,
                                preferredLanguage = preferredLanguage,
                                onToggleDarkMode = { viewModel.toggleDarkMode(it) },
                                onSelectThemeMode = { viewModel.setThemeMode(it) },
                                onSelectThemePreset = { viewModel.setTheme(it) },
                                onToggleNotifications = { viewModel.setNotificationsAllowed(it) },
                                onToggleLocationServices = { viewModel.setLocationServicesAllowed(it) },
                                onToggleHapticFeedback = { viewModel.setHapticFeedbackEnabled(it) },
                                onToggleHighContrast = { viewModel.setHighContrastEnabled(it) },
                                onToggleAutoSync = { viewModel.setAutoSyncEnabled(it) },
                                onSelectLanguage = { viewModel.setPreferredLanguage(it) },
                                onResetPreferences = { viewModel.resetAllPreferences() },
                                onClearCache = { viewModel.clearCivicCache() },
                                onNavigate = { viewModel.navigateTo(it) },
                                onLogout = { viewModel.navigateTo(Screen.Login) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.Themes -> {
                            ThemesScreen(
                                currentTheme = themePreset,
                                currentBackground = backgroundPreset,
                                currentFont = fontPreset,
                                isDarkMode = isDarkMode,
                                onSelectTheme = { viewModel.setTheme(it) },
                                onSelectBackground = { viewModel.setBackgroundPreset(it) },
                                onSelectFont = { viewModel.setFontPreset(it) },
                                onToggleDarkMode = { viewModel.toggleDarkMode(it) },
                                onBack = { viewModel.navigateBack() }
                            )
                        }

                        is Screen.AdminCenter -> {
                            AdminCenterScreen(
                                complaints = complaints,
                                onUpdateComplaintStatus = { id, st, note ->
                                    viewModel.updateComplaintStatusAdmin(id, st, note)
                                },
                                onBack = { viewModel.navigateBack() }
                            )
                        }
                    }
                }

                // Drawer modal overlay
                CivicDrawer(
                    isOpen = isDrawerOpen,
                    isDarkMode = isDarkMode,
                    user = currentUser,
                    onClose = { viewModel.closeDrawer() },
                    onNavigate = { screen ->
                        viewModel.closeDrawer()
                        viewModel.navigateTo(screen)
                    },
                    onToggleDarkMode = { viewModel.toggleDarkMode(!isDarkMode) },
                    onLogout = {
                        viewModel.closeDrawer()
                        viewModel.navigateTo(Screen.Login)
                    }
                )
            }
        }
    }
}
