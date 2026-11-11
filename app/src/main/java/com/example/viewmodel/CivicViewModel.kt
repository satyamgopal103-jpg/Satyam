package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.CivicPreferences
import com.example.data.model.*
import com.example.data.repository.CivicRepository
import com.example.ui.theme.ThemePreset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("sign_up")
    object ForgotPassword : Screen("forgot_password")
    object NotificationPermission : Screen("notification_permission")
    object DashboardLoading : Screen("dashboard_loading")
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Complaints : Screen("complaints")
    object CreateComplaint : Screen("create_complaint")
    data class ComplaintDetail(val complaintId: String) : Screen("complaint_detail")
    object CivicMap : Screen("civic_map")
    object Representatives : Screen("representatives")
    object Projects : Screen("projects")
    object Communities : Screen("communities")
    object Messages : Screen("messages")
    object Polls : Screen("polls")
    object Volunteering : Screen("volunteering")
    object CivicAssistant : Screen("civic_assistant")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Themes : Screen("themes")
    object AdminCenter : Screen("admin_center")
}

class CivicViewModel(application: Application) : AndroidViewModel(application) {
    val repository = CivicRepository()
    private val preferences = CivicPreferences(application)

    // Current Screen Navigation Stack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Splash))
    val currentScreen: StateFlow<Screen> = _screenStack.map { it.lastOrNull() ?: Screen.Home }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Splash)

    // Theme and Preferences
    val currentThemePreset: StateFlow<ThemePreset> = preferences.selectedTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemePreset.BLUE_OCEAN)

    val currentThemeMode: StateFlow<com.example.ui.theme.ThemeMode> = preferences.themeMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, com.example.ui.theme.ThemeMode.DARK)

    val currentBackgroundPreset: StateFlow<com.example.ui.theme.BackgroundPreset> = preferences.selectedBackground
        .stateIn(viewModelScope, SharingStarted.Eagerly, com.example.ui.theme.BackgroundPreset.ANIMATED_SKYLINE)

    val currentFontPreset: StateFlow<com.example.ui.theme.FontPreset> = preferences.selectedFont
        .stateIn(viewModelScope, SharingStarted.Eagerly, com.example.ui.theme.FontPreset.DEFAULT_SANS)

    val isDarkMode: StateFlow<Boolean> = preferences.isDarkMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isNotificationsAllowed: StateFlow<Boolean> = preferences.isNotificationsAllowed
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isLocationServicesAllowed: StateFlow<Boolean> = preferences.isLocationServicesAllowed
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isHapticFeedbackEnabled: StateFlow<Boolean> = preferences.isHapticFeedbackEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isHighContrastEnabled: StateFlow<Boolean> = preferences.isHighContrastEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val isAutoSyncEnabled: StateFlow<Boolean> = preferences.isAutoSyncEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val preferredLanguage: StateFlow<String> = preferences.preferredLanguage
        .stateIn(viewModelScope, SharingStarted.Eagerly, "English")

    // Data from Repository
    val currentUser = repository.currentUser
    val complaints = repository.complaints
    val representatives = repository.representatives
    val projects = repository.projects
    val communities = repository.communities
    val polls = repository.polls
    val volunteerOpportunities = repository.volunteerOpportunities
    val news = repository.news
    val notifications = repository.notifications
    val conversations = repository.conversations
    val assistantChat = repository.assistantChat

    // UI state
    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen: StateFlow<Boolean> = _isDrawerOpen.asStateFlow()

    private val _selectedComplaint = MutableStateFlow<Complaint?>(null)
    val selectedComplaint: StateFlow<Complaint?> = _selectedComplaint.asStateFlow()

    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    private val _actionFeedback = MutableStateFlow<String?>(null)
    val actionFeedback: StateFlow<String?> = _actionFeedback.asStateFlow()

    fun navigateTo(screen: Screen) {
        _isDrawerOpen.value = false
        _screenStack.value = _screenStack.value + screen
    }

    fun navigateBack(): Boolean {
        if (_screenStack.value.size > 1) {
            _screenStack.value = _screenStack.value.dropLast(1)
            return true
        }
        return false
    }

    fun openDrawer() { _isDrawerOpen.value = true }
    fun closeDrawer() { _isDrawerOpen.value = false }

    fun selectComplaint(complaint: Complaint) {
        _selectedComplaint.value = complaint
        navigateTo(Screen.ComplaintDetail(complaint.id))
    }

    fun setTheme(theme: ThemePreset) {
        viewModelScope.launch {
            preferences.setThemePreset(theme)
            _actionFeedback.value = "Theme changed to ${theme.title}"
        }
    }

    fun setBackgroundPreset(bg: com.example.ui.theme.BackgroundPreset) {
        viewModelScope.launch {
            preferences.setBackgroundPreset(bg)
            _actionFeedback.value = "Background changed to ${bg.title}"
        }
    }

    fun setFontPreset(font: com.example.ui.theme.FontPreset) {
        viewModelScope.launch {
            preferences.setFontPreset(font)
            _actionFeedback.value = "Font style changed to ${font.title}"
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setDarkMode(enabled)
            _actionFeedback.value = if (enabled) "Switched to Dark Theme" else "Switched to Light Theme"
        }
    }

    fun setThemeMode(mode: com.example.ui.theme.ThemeMode) {
        viewModelScope.launch {
            preferences.setThemeMode(mode)
            _actionFeedback.value = "Appearance mode: ${mode.title}"
        }
    }

    fun setNotificationsAllowed(allowed: Boolean) {
        viewModelScope.launch {
            preferences.setNotificationsAllowed(allowed)
            _actionFeedback.value = if (allowed) "Push notifications enabled" else "Push notifications muted"
        }
    }

    fun setLocationServicesAllowed(allowed: Boolean) {
        viewModelScope.launch {
            preferences.setLocationServicesAllowed(allowed)
            _actionFeedback.value = if (allowed) "GPS Location tagging active" else "GPS Location services paused"
        }
    }

    fun setHapticFeedbackEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setHapticFeedbackEnabled(enabled)
        }
    }

    fun setHighContrastEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setHighContrastEnabled(enabled)
            _actionFeedback.value = if (enabled) "High contrast active" else "Standard contrast restored"
        }
    }

    fun setAutoSyncEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setAutoSyncEnabled(enabled)
        }
    }

    fun setPreferredLanguage(lang: String) {
        viewModelScope.launch {
            preferences.setPreferredLanguage(lang)
            _actionFeedback.value = "Language set to $lang"
        }
    }

    fun resetAllPreferences() {
        viewModelScope.launch {
            preferences.resetAllPreferences()
            _actionFeedback.value = "All preferences reset to factory defaults"
        }
    }

    fun clearCivicCache() {
        _actionFeedback.value = "Cache cleared: 24.8 MB local data refreshed"
    }

    fun clearFeedback() {
        _actionFeedback.value = null
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            _authError.value = "Please enter your email and password"
            return
        }
        if (!email.contains("@")) {
            _authError.value = "Please enter a valid email address"
            return
        }
        _authError.value = null
        onSuccess()
    }

    fun loginDemo(onSuccess: () -> Unit) {
        _authError.value = null
        onSuccess()
    }

    fun signUp(name: String, email: String, phone: String, pass: String, confirmPass: String, termsAccepted: Boolean, onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || phone.isBlank() || pass.isBlank()) {
            _authError.value = "All fields are required"
            return
        }
        if (!email.contains("@")) {
            _authError.value = "Please provide a valid email"
            return
        }
        if (pass.length < 6) {
            _authError.value = "Password must be at least 6 characters"
            return
        }
        if (pass != confirmPass) {
            _authError.value = "Passwords do not match"
            return
        }
        if (!termsAccepted) {
            _authError.value = "Please accept the Terms of Service & Privacy Policy"
            return
        }
        _authError.value = null
        onSuccess()
    }

    fun submitNewComplaint(
        title: String,
        description: String,
        category: ComplaintCategory,
        location: String
    ): Complaint? {
        if (title.isBlank() || description.isBlank() || location.isBlank()) {
            _actionFeedback.value = "Please complete all complaint fields"
            return null
        }
        val complaint = repository.submitComplaint(title, description, category, location)
        _actionFeedback.value = "Complaint ${complaint.trackingId} submitted successfully!"
        return complaint
    }

    fun upvoteComplaint(id: String) {
        repository.toggleUpvoteComplaint(id)
    }

    fun votePoll(pollId: String, optionId: String) {
        repository.votePoll(pollId, optionId)
        _actionFeedback.value = "Vote recorded! Thank you for participating."
    }

    fun toggleCommunity(communityId: String) {
        repository.toggleCommunityMembership(communityId)
    }

    fun toggleVolunteer(opportunityId: String) {
        repository.toggleVolunteerRegistration(opportunityId)
        _actionFeedback.value = "Volunteer status updated!"
    }

    fun askAssistant(prompt: String) {
        if (prompt.isNotBlank()) {
            repository.askCivicAssistant(prompt)
        }
    }

    fun updateComplaintStatusAdmin(complaintId: String, status: ComplaintStatus, note: String) {
        repository.updateComplaintStatusByAdmin(complaintId, status, note)
        _actionFeedback.value = "Status updated to ${status.label}"
        _selectedComplaint.value = repository.complaints.value.find { it.id == complaintId }
    }
}
