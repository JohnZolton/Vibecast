package com.podcastr2

import android.app.Application
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.podcastr2.data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PodcastrApp : Application() {
    
    // Application-wide coroutine scope
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    // Gemini model for ad detection
    var adDetectionModel: GenerativeModel? = null
    
    // Settings repository
    lateinit var settingsRepository: SettingsRepository
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize settings repository
        settingsRepository = SettingsRepository(this)
        
        try {
            // Initialize Gemini model for ad detection if API key is available
            initializeAdDetectionModel()
        } catch (e: Exception) {
            Log.e("PodcastrApp", "Failed to initialize Gemini model", e)
            // Continue without Gemini model
        }
    }
    
    private fun initializeAdDetectionModel() {
        // TODO: Replace with your actual API key in a secure way
        val apiKey = "YOUR_GEMINI_API_KEY"
        
        // Skip initialization if API key is not set
        if (apiKey == "YOUR_GEMINI_API_KEY") {
            Log.w("PodcastrApp", "Skipping Gemini model initialization: API key not set")
            return
        }
        
        try {
            adDetectionModel = GenerativeModel(
                modelName = "gemini-1.5-pro",
                apiKey = apiKey,
                generationConfig = generationConfig {
                    temperature = 0.2f
                    topK = 40
                    topP = 0.95f
                    maxOutputTokens = 1024
                }
            )
        } catch (e: Exception) {
            Log.e("PodcastrApp", "Error initializing Gemini model", e)
            // Continue without Gemini model
        }
    }
}
