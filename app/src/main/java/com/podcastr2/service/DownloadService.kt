package com.podcastr2.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.podcastr2.PodcastrApp
import com.podcastr2.R
import com.podcastr2.data.db.DownloadTaskDao
import com.podcastr2.data.db.EpisodeDao
import com.podcastr2.data.db.PodcastrDatabase
import com.podcastr2.data.repository.DirectDownloadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DownloadService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    
    private lateinit var directDownloadRepository: DirectDownloadRepository
    private lateinit var downloadTaskDao: DownloadTaskDao
    private lateinit var episodeDao: EpisodeDao
    
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "download_channel"
        
        // Intent action constants
        const val ACTION_EXTRACT_AND_DOWNLOAD = "com.podcastr2.action.EXTRACT_AND_DOWNLOAD"
        const val ACTION_DIRECT_DOWNLOAD = "com.podcastr2.action.DIRECT_DOWNLOAD"
        
        // Intent extra constants
        const val EXTRA_PAGE_URL = "com.podcastr2.extra.PAGE_URL"
        const val EXTRA_MP3_URL = "com.podcastr2.extra.MP3_URL"
        const val EXTRA_FILENAME = "com.podcastr2.extra.FILENAME"
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        // Initialize DAOs
        val database = PodcastrDatabase.getInstance(applicationContext)
        downloadTaskDao = database.downloadTaskDao()
        episodeDao = database.episodeDao()
        
        // Initialize repository
        directDownloadRepository = DirectDownloadRepository(
            applicationContext,
            downloadTaskDao,
            episodeDao
        )
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            val notification = createNotification("Downloading podcasts", "Download in progress")
            startForeground(NOTIFICATION_ID, notification)
            
            intent?.let { handleIntent(it) }
        } catch (e: Exception) {
            // Handle foreground service exception
            Log.e("DownloadService", "Error starting foreground service", e)
            
            // Try to continue without foreground service
            intent?.let { handleIntent(it) }
        }
        
        return START_STICKY
    }
    
    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            ACTION_EXTRACT_AND_DOWNLOAD -> {
                val pageUrl = intent.getStringExtra(EXTRA_PAGE_URL)
                val filename = intent.getStringExtra(EXTRA_FILENAME)
                
                if (pageUrl != null && filename != null) {
                    extractAndDownload(pageUrl, filename)
                }
            }
            ACTION_DIRECT_DOWNLOAD -> {
                val mp3Url = intent.getStringExtra(EXTRA_MP3_URL)
                val filename = intent.getStringExtra(EXTRA_FILENAME)
                
                if (mp3Url != null && filename != null) {
                    directDownload(mp3Url, filename)
                }
            }
        }
    }
    
    private fun extractAndDownload(pageUrl: String, filename: String) {
        serviceScope.launch {
            updateNotification("Extracting MP3 URL", "Processing $pageUrl")
            
            val mp3Url = directDownloadRepository.extractMp3Url(pageUrl)
            
            if (mp3Url != null) {
                updateNotification("Found MP3 URL", mp3Url)
                directDownload(mp3Url, filename)
            } else {
                updateNotification("Download Failed", "Could not extract MP3 URL from $pageUrl")
                stopSelf()
            }
        }
    }
    
    private fun directDownload(mp3Url: String, filename: String) {
        serviceScope.launch {
            updateNotification("Downloading", "Starting download of $filename")
            
            val episodeId = directDownloadRepository.downloadPodcastFromUrl(mp3Url, filename)
            
            if (episodeId != null) {
                updateNotification("Download Complete", "$filename downloaded successfully")
            } else {
                updateNotification("Download Failed", "Failed to download $filename")
            }
            
            // Stop the service after a delay to show the final notification
            serviceScope.launch(Dispatchers.Main) {
                kotlinx.coroutines.delay(3000)
                stopSelf()
            }
        }
    }
    
    private fun updateNotification(title: String, content: String) {
        val notification = createNotification(title, content)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Podcast Downloads",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows download progress for podcasts"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(title: String, content: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}
