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
import android.media.MediaPlayer
import android.media.PlaybackParams
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.webkit.URLUtil

/**
 * ViewModel for the Direct Download screen
 */
class DirectDownloadViewModel(private val app: Application) : AndroidViewModel(app) {

    private val database = PodcastrDatabase.getInstance(app)
    private val episodeDao = database.episodeDao()
    private val downloadTaskDao = database.downloadTaskDao()

    private val directDownloadRepository = DirectDownloadRepository(
        app,
        downloadTaskDao,
        episodeDao
    )

    // Status message for UI feedback
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage

    // MediaPlayer instance
    private var mediaPlayer: MediaPlayer? = null
    private var positionUpdateJob: Job? = null

    // State for Current Playback
    private val _currentlyPlayingEpisodeUri = MutableStateFlow<String?>(null)
    val currentlyPlayingEpisodeUri: StateFlow<String?> = _currentlyPlayingEpisodeUri.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _totalDuration = MutableStateFlow(0L)
    val totalDuration: StateFlow<Long> = _totalDuration.asStateFlow()

    private val _currentPlaybackSpeed = MutableStateFlow(1.0f)
    val currentPlaybackSpeed: StateFlow<Float> = _currentPlaybackSpeed.asStateFlow()
    
    private val _playbackError = MutableStateFlow<String?>(null)
    val playbackError: StateFlow<String?> = _playbackError.asStateFlow()


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

    private fun initMediaPlayer() {
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer().apply {
            setOnPreparedListener {
                _totalDuration.value = it.duration.toLong()
                it.start()
                _isPlaying.value = true
                startPositionUpdates()
                // Apply current playback speed if already set
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    try {
                        val params = PlaybackParams().apply { speed = _currentPlaybackSpeed.value }
                        it.playbackParams = params
                    } catch (e: Exception) {
                        Log.e("DirectDownloadVM", "Error setting playback speed on prepare", e)
                        _statusMessage.value = "Error setting playback speed."
                    }
                }
            }
            setOnCompletionListener {
                _isPlaying.value = false
                _currentPosition.value = _totalDuration.value // Or 0, depending on desired behavior
                // Optionally clear currently playing episode or keep it for replay
                // _currentlyPlayingEpisodeUri.value = null 
                stopPositionUpdates()
                _statusMessage.value = "Playback finished."
            }
            setOnErrorListener { mp, what, extra ->
                Log.e("DirectDownloadVM", "MediaPlayer Error: What: $what, Extra: $extra")
                _isPlaying.value = false
                _playbackError.value = "Playback error (Code: $what, $extra). Please try again."
                _statusMessage.value = "Error during playback."
                releaseMediaPlayer()
                true // Indicates the error was handled
            }
        }
    }

    private fun releaseMediaPlayer() {
        stopPositionUpdates()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
        // Don't clear URI, position, duration here to allow UI to reflect last state until new episode plays
    }

    private fun startPositionUpdates() {
        stopPositionUpdates() // Ensure only one updater runs
        positionUpdateJob = viewModelScope.launch {
            while (_isPlaying.value) {
                mediaPlayer?.let {
                    try {
                        if (it.isPlaying) {
                             _currentPosition.value = it.currentPosition.toLong()
                        }
                    } catch (e: IllegalStateException) {
                        // MediaPlayer might not be in a valid state
                        Log.w("DirectDownloadVM", "MediaPlayer not in valid state for position update.", e)
                        stopPositionUpdates() // Stop updates if player is in bad state
                    }
                }
                delay(1000) // Update every second
            }
        }
    }

    private fun stopPositionUpdates() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    fun playEpisode(episode: Episode) {
        val path = episode.downloadPath
        if (path == null) {
            _statusMessage.value = "Episode file not available."
            _playbackError.value = "Episode file not available for ${episode.title}."
            return
        }

        if (_currentlyPlayingEpisodeUri.value == path && mediaPlayer != null) {
            // If it's the same episode, decide whether to resume or restart
            if (!_isPlaying.value) {
                resumePlayback()
            } else { // Already playing this episode, possibly restart or do nothing
                mediaPlayer?.seekTo(0)
                _currentPosition.value = 0L
                if (!mediaPlayer!!.isPlaying) mediaPlayer?.start() // Ensure it starts if it was paused due to an issue
                 _isPlaying.value = true
                startPositionUpdates()
            }
        } else {
            _statusMessage.value = "Starting playback for ${episode.title}..."
            initMediaPlayer()
            _currentlyPlayingEpisodeUri.value = path
            _currentPosition.value = 0L // Reset position for new episode
            _totalDuration.value = 0L // Reset duration until known
            _playbackError.value = null // Clear previous errors

            try {
                mediaPlayer?.setDataSource(path)
                mediaPlayer?.prepareAsync() // Preferred for network/local files
            } catch (e: IOException) {
                Log.e("DirectDownloadVM", "Error setting data source", e)
                _statusMessage.value = "Error preparing playback."
                _playbackError.value = "Cannot play ${episode.title}. File error."
                releaseMediaPlayer()
            } catch (e: IllegalStateException) {
                Log.e("DirectDownloadVM", "Error setting data source - illegal state", e)
                _statusMessage.value = "Error preparing playback (player state)."
                 _playbackError.value = "Cannot play ${episode.title}. Player error."
                releaseMediaPlayer()
            }
        }
    }

    fun pausePlayback() {
        if (_isPlaying.value) {
            mediaPlayer?.pause()
            _isPlaying.value = false
            stopPositionUpdates()
            _statusMessage.value = "Playback paused."
        }
    }

    fun resumePlayback() {
        if (!_isPlaying.value && mediaPlayer != null && _currentlyPlayingEpisodeUri.value != null) {
            try {
                 if(mediaPlayer?.isPlaying == false) { // Check if it's actually paused
                    mediaPlayer?.start()
                    _isPlaying.value = true
                    startPositionUpdates()
                    _statusMessage.value = "Playback resumed."
                } else if (mediaPlayer == null && _currentlyPlayingEpisodeUri.value != null) {
                    // Edge case: player was released but URI is still set - try to replay current
                    // This requires finding the episode object again from the URI.
                    // For simplicity, this case might require user to click play on episode item again.
                    // Or, store current episode object. For now, let's assume user re-selects.
                     _statusMessage.value = "Player not ready. Select episode to play."
                }
            } catch (e: IllegalStateException) {
                 Log.e("DirectDownloadVM", "Error resuming playback", e)
                _statusMessage.value = "Error resuming playback."
                releaseMediaPlayer() // Release if error
            }
        } else if (mediaPlayer == null && _currentlyPlayingEpisodeUri.value != null) {
             // Attempt to re-initialize and play if URI is set but player is null
             // This would require fetching the Episode by URI. For now, prompt re-selection.
            _statusMessage.value = "Player not initialized. Please select the episode again."
        }
    }

    fun seekTo(position: Long) {
        mediaPlayer?.let {
            val newPosition = position.coerceIn(0, _totalDuration.value)
            it.seekTo(newPosition.toInt())
            _currentPosition.value = newPosition
        }
    }

    fun fastForward(seconds: Int = 30) {
        mediaPlayer?.let {
            val newPosition = (it.currentPosition + seconds * 1000).toLong()
            seekTo(newPosition.coerceAtMost(_totalDuration.value))
        }
    }

    fun rewind(seconds: Int = 10) { // Default to 10s for rewind
        mediaPlayer?.let {
            val newPosition = (it.currentPosition - seconds * 1000).toLong()
            seekTo(newPosition.coerceAtLeast(0L))
        }
    }

    fun setPlaybackSpeed(speed: Float) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mediaPlayer?.let {
                try {
                    if (it.isPlaying || _isPlaying.value) { // Check if player is in a state to change speed
                        val params = PlaybackParams().apply { this.speed = speed }
                        it.playbackParams = params
                        _currentPlaybackSpeed.value = speed
                        _statusMessage.value = "Playback speed set to ${speed}x"
                    } else {
                         // If not playing, just store the speed and it will be applied on next play/prepare
                        _currentPlaybackSpeed.value = speed
                        _statusMessage.value = "Playback speed will be ${speed}x on next play."
                    }
                } catch (e: IllegalStateException) {
                    Log.e("DirectDownloadVM", "Error setting playback speed", e)
                    _statusMessage.value = "Could not set playback speed."
                }
            } ?: run {
                 // If player is null, store the speed for when it's initialized
                _currentPlaybackSpeed.value = speed
                _statusMessage.value = "Playback speed will be ${speed}x when an episode is played."
            }
        } else {
            _statusMessage.value = "Playback speed adjustment requires Android M (API 23) or higher."
        }
    }
    
    fun clearPlaybackError() {
        _playbackError.value = null
    }

    override fun onCleared() {
        super.onCleared()
        releaseMediaPlayer()
    }

    internal fun generateFilenameFromUrl(url: String): String { // Changed visibility to internal
        var name = URLUtil.guessFileName(url, null, null) // Try to guess from content disposition or URL
        if (name.isBlank() || name == "audio" || name == "download" || !name.contains(".")) { // Basic check if guessed name is usable
            // Fallback to timestamp-based name
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            name = "podcast_$timestamp.mp3"
        }
        // Ensure it ends with .mp3, handling cases where it might have .mp3?query_params
        if (!name.endsWith(".mp3", ignoreCase = true)) {
            val queryIndex = name.indexOf('?')
            if (queryIndex != -1) {
                name = name.substring(0, queryIndex)
            }
            if (!name.endsWith(".mp3", ignoreCase = true)) {
               name = name.substringBeforeLast('.', missingDelimiterValue = name) + ".mp3"
            }
        }
        // Sanitize the filename (optional, but good practice)
        return name.replace(Regex("[^a-zA-Z0-9._-]"), "_").takeLast(100) // Limit length and sanitize
    }

    /**
     * Download an episode from a URL, attempting extraction first.
     */
    fun downloadEpisode(pageUrlOrMp3Url: String) { // Removed filename parameter
        viewModelScope.launch {
            val generatedFilename = generateFilenameFromUrl(pageUrlOrMp3Url)
            _statusMessage.value = "Attempting to extract MP3 URL from $pageUrlOrMp3Url for $generatedFilename..."
            Log.d("DirectDownloadVM", "Attempting to extract MP3 URL from $pageUrlOrMp3Url for $generatedFilename")

            try {
                // Try to extract MP3 URL first
                // Assuming extractMp3Url might still take filename for logging or other purposes,
                // or it might be changed later. For now, pass the generated one.
                val extractedMp3Url = directDownloadRepository.extractMp3Url(pageUrlOrMp3Url)

                if (extractedMp3Url != null) {
                    _statusMessage.value = "Found MP3 URL, starting download of $generatedFilename..."
                    Log.d("DirectDownloadVM", "Found MP3 URL: $extractedMp3Url, starting download of $generatedFilename")
                    downloadDirectly(extractedMp3Url, generatedFilename)
                } else {
                    // If extraction fails, try direct download
                    _statusMessage.value = "Extraction failed, trying direct download of $generatedFilename..."
                    Log.d("DirectDownloadVM", "Extraction failed for $pageUrlOrMp3Url, trying direct download of $generatedFilename")
                    downloadDirectly(pageUrlOrMp3Url, generatedFilename)
                }
            } catch (e: Exception) {
                // Handle any exceptions during extraction or download
                _statusMessage.value = "Error: ${e.message}"
                Log.e("DirectDownloadVM", "Error downloading episode $generatedFilename", e)
                // Also try direct download as a fallback in case of extraction error
                _statusMessage.value = "Extraction error, trying direct download of $generatedFilename..."
                Log.d("DirectDownloadVM", "Extraction error for $pageUrlOrMp3Url, trying direct download of $generatedFilename")
                downloadDirectly(pageUrlOrMp3Url, generatedFilename)
            } finally {
                // Clear status message after a delay
                launch {
                    kotlinx.coroutines.delay(5000) // Increased delay for potentially two messages
                    _statusMessage.value = null
                }
            }
        }
    }

    /**
     * Helper function to perform direct download
     */
    private suspend fun downloadDirectly(mp3Url: String, generatedFilename: String) { // Renamed parameter
        try {
            val episodeId = directDownloadRepository.downloadPodcastFromUrl(mp3Url, generatedFilename)

            if (episodeId != null) {
                _statusMessage.value = "Download complete: $generatedFilename"
                Log.d("DirectDownloadVM", "Download complete: $generatedFilename")
            } else {
                _statusMessage.value = "Download failed: $generatedFilename. Check if the URL is a direct MP3 link."
                Log.e("DirectDownloadVM", "Download failed: $generatedFilename. URL: $mp3Url")
            }
        } catch (e: Exception) {
            _statusMessage.value = "Error downloading $generatedFilename: ${e.message}"
            Log.e("DirectDownloadVM", "Error downloading $generatedFilename from $mp3Url", e)
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
