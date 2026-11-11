package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ui.theme.BackgroundPreset
import com.example.ui.theme.FontPreset
import com.example.ui.theme.ThemeMode
import com.example.ui.theme.ThemePreset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.civicDataStore by preferencesDataStore(name = "civic_pulse_preferences")

class CivicPreferences(private val context: Context) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("selected_theme_preset")
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        private val BACKGROUND_KEY = stringPreferencesKey("selected_background_preset")
        private val FONT_KEY = stringPreferencesKey("selected_font_preset")
        private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
        private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_allowed")
        private val LOCATION_KEY = booleanPreferencesKey("location_services_allowed")
        private val HAPTIC_KEY = booleanPreferencesKey("haptic_feedback_enabled")
        private val HIGH_CONTRAST_KEY = booleanPreferencesKey("high_contrast_enabled")
        private val AUTO_SYNC_KEY = booleanPreferencesKey("auto_sync_enabled")
        private val LANGUAGE_KEY = stringPreferencesKey("preferred_language")
        private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")
    }

    val selectedTheme: Flow<ThemePreset> = context.civicDataStore.data.map { prefs ->
        val name = prefs[THEME_KEY] ?: ThemePreset.BLUE_OCEAN.name
        try {
            ThemePreset.valueOf(name)
        } catch (e: Exception) {
            ThemePreset.BLUE_OCEAN
        }
    }

    val themeMode: Flow<ThemeMode> = context.civicDataStore.data.map { prefs ->
        val name = prefs[THEME_MODE_KEY] ?: ThemeMode.DARK.name
        try {
            ThemeMode.valueOf(name)
        } catch (e: Exception) {
            ThemeMode.DARK
        }
    }

    val selectedBackground: Flow<BackgroundPreset> = context.civicDataStore.data.map { prefs ->
        val name = prefs[BACKGROUND_KEY] ?: BackgroundPreset.ANIMATED_SKYLINE.name
        try {
            BackgroundPreset.valueOf(name)
        } catch (e: Exception) {
            BackgroundPreset.ANIMATED_SKYLINE
        }
    }

    val selectedFont: Flow<FontPreset> = context.civicDataStore.data.map { prefs ->
        val name = prefs[FONT_KEY] ?: FontPreset.DEFAULT_SANS.name
        try {
            FontPreset.valueOf(name)
        } catch (e: Exception) {
            FontPreset.DEFAULT_SANS
        }
    }

    val isDarkMode: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        val modeStr = prefs[THEME_MODE_KEY]
        if (modeStr != null) {
            when (modeStr) {
                ThemeMode.LIGHT.name -> false
                ThemeMode.DARK.name -> true
                else -> prefs[DARK_MODE_KEY] ?: true
            }
        } else {
            prefs[DARK_MODE_KEY] ?: true
        }
    }

    val isNotificationsAllowed: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[NOTIFICATIONS_KEY] ?: true
    }

    val isLocationServicesAllowed: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[LOCATION_KEY] ?: true
    }

    val isHapticFeedbackEnabled: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[HAPTIC_KEY] ?: true
    }

    val isHighContrastEnabled: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[HIGH_CONTRAST_KEY] ?: false
    }

    val isAutoSyncEnabled: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[AUTO_SYNC_KEY] ?: true
    }

    val preferredLanguage: Flow<String> = context.civicDataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: "English"
    }

    val isOnboardingCompleted: Flow<Boolean> = context.civicDataStore.data.map { prefs ->
        prefs[ONBOARDING_COMPLETED_KEY] ?: false
    }

    suspend fun setThemePreset(theme: ThemePreset) {
        context.civicDataStore.edit { it[THEME_KEY] = theme.name }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.civicDataStore.edit {
            it[THEME_MODE_KEY] = mode.name
            if (mode == ThemeMode.LIGHT) {
                it[DARK_MODE_KEY] = false
            } else if (mode == ThemeMode.DARK) {
                it[DARK_MODE_KEY] = true
            }
        }
    }

    suspend fun setBackgroundPreset(bg: BackgroundPreset) {
        context.civicDataStore.edit { it[BACKGROUND_KEY] = bg.name }
    }

    suspend fun setFontPreset(font: FontPreset) {
        context.civicDataStore.edit { it[FONT_KEY] = font.name }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.civicDataStore.edit {
            it[DARK_MODE_KEY] = enabled
            it[THEME_MODE_KEY] = if (enabled) ThemeMode.DARK.name else ThemeMode.LIGHT.name
        }
    }

    suspend fun setNotificationsAllowed(allowed: Boolean) {
        context.civicDataStore.edit { it[NOTIFICATIONS_KEY] = allowed }
    }

    suspend fun setLocationServicesAllowed(allowed: Boolean) {
        context.civicDataStore.edit { it[LOCATION_KEY] = allowed }
    }

    suspend fun setHapticFeedbackEnabled(enabled: Boolean) {
        context.civicDataStore.edit { it[HAPTIC_KEY] = enabled }
    }

    suspend fun setHighContrastEnabled(enabled: Boolean) {
        context.civicDataStore.edit { it[HIGH_CONTRAST_KEY] = enabled }
    }

    suspend fun setAutoSyncEnabled(enabled: Boolean) {
        context.civicDataStore.edit { it[AUTO_SYNC_KEY] = enabled }
    }

    suspend fun setPreferredLanguage(lang: String) {
        context.civicDataStore.edit { it[LANGUAGE_KEY] = lang }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.civicDataStore.edit { it[ONBOARDING_COMPLETED_KEY] = completed }
    }

    suspend fun resetAllPreferences() {
        context.civicDataStore.edit { prefs ->
            prefs[THEME_KEY] = ThemePreset.BLUE_OCEAN.name
            prefs[THEME_MODE_KEY] = ThemeMode.DARK.name
            prefs[DARK_MODE_KEY] = true
            prefs[BACKGROUND_KEY] = BackgroundPreset.ANIMATED_SKYLINE.name
            prefs[FONT_KEY] = FontPreset.DEFAULT_SANS.name
            prefs[NOTIFICATIONS_KEY] = true
            prefs[LOCATION_KEY] = true
            prefs[HAPTIC_KEY] = true
            prefs[HIGH_CONTRAST_KEY] = false
            prefs[AUTO_SYNC_KEY] = true
            prefs[LANGUAGE_KEY] = "English"
        }
    }
}
