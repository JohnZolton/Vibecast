package com.podcastr2.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository for app settings
 */
class SettingsRepository(context: Context) {
    
    companion object {
        private const val PREFS_NAME = "podcastr_settings"
        private const val KEY_PLAYBACK_SPEED = "playback_speed"
        private const val DEFAULT_PLAYBACK_SPEED = 2.0f
    }
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _playbackSpeed = MutableStateFlow(
        sharedPreferences.getFloat(KEY_PLAYBACK_SPEED, DEFAULT_PLAYBACK_SPEED)
    )
    
    /**
     * Flow of the current playback speed
     */
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()
    
    /**
     * Set the playback speed
     * @param speed The new playback speed
     */
    fun setPlaybackSpeed(speed: Float) {
        sharedPreferences.edit().putFloat(KEY_PLAYBACK_SPEED, speed).apply()
        _playbackSpeed.value = speed
    }
    
    /**
     * Get the current playback speed
     * @return The current playback speed
     */
    fun getPlaybackSpeed(): Float {
        return sharedPreferences.getFloat(KEY_PLAYBACK_SPEED, DEFAULT_PLAYBACK_SPEED)
    }
}
