package com.podcastr2.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.podcastr2.data.db.PodcastrDatabase
import com.podcastr2.data.model.Episode
import com.podcastr2.data.repository.DirectDownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for the Direct Download screen
 */
class DirectDownloadViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = PodcastrDatabase.getInstance(application)
    private val episodeDao = database.episodeDao()
    private val downloadTaskDao = database.downloadTaskDao()
    
    private val directDownloadRepository = DirectDownloadRepository(
        application,
        downloadTaskDao,
        episodeDao
    )
    
    // Status message for UI feedback
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage
    
    /**
     * Flow of episodes downloaded directly from URLs
     */
    val directDownloadEpisodes: Flow<List<Episode>> = directDownloadRepository
        .getDirectDownloadEpisodes()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    /**
     * Start a direct download from a URL
     */
    fun downloadFromUrl(mp3Url: String, filename: String) {
        viewModelScope.launch {
            _statusMessage.value = "Starting download of $filename..."
            
            try {
                val episodeId = directDownloadRepository.downloadPodcastFromUrl(mp3Url, filename)
                
                if (episodeId != null) {
                    _statusMessage.value = "Download complete: $filename"
                    Log.d("DirectDownloadVM", "Download complete: $filename")
                } else {
                    _statusMessage.value = "Download failed: $filename"
                    Log.e("DirectDownloadVM", "Download failed: $filename")
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
                Log.e("DirectDownloadVM", "Error downloading", e)
            }
            
            // Clear status message after a delay
            launch {
                kotlinx.coroutines.delay(3000)
                _statusMessage.value = null
            }
        }
    }
    
    /**
     * Extract and download from a page URL
     */
    fun extractAndDownload(pageUrl: String, filename: String) {
        viewModelScope.launch {
            _statusMessage.value = "Extracting MP3 URL from page..."
            
            try {
                val mp3Url = directDownloadRepository.extractMp3Url(pageUrl)
                
                if (mp3Url != null) {
                    _statusMessage.value = "Found MP3 URL, starting download..."
                    downloadFromUrl(mp3Url, filename)
                } else {
                    _statusMessage.value = "Could not find MP3 URL in page"
                    Log.e("DirectDownloadVM", "Could not find MP3 URL in page: $pageUrl")
                }
            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
                Log.e("DirectDownloadVM", "Error extracting MP3 URL", e)
            }
        }
    }
    
    /**
     * Delete an episode and its file
     */
    fun deleteEpisode(episode: Episode) {
        viewModelScope.launch {
            try {
                directDownloadRepository.deleteEpisode(episode)
                _statusMessage.value = "Episode deleted: ${episode.title}"
            } catch (e: Exception) {
                _statusMessage.value = "Error deleting episode: ${e.message}"
                Log.e("DirectDownloadVM", "Error deleting episode", e)
            }
            
            // Clear status message after a delay
            launch {
                kotlinx.coroutines.delay(3000)
                _statusMessage.value = null
            }
        }
    }
}
