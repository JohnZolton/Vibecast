package com.podcastr2.data.repository

import android.content.Context
import android.util.Log
import com.podcastr2.data.db.DownloadTaskDao
import com.podcastr2.data.db.EpisodeDao
import com.podcastr2.data.model.DownloadStatus
import com.podcastr2.data.model.DownloadTask
import com.podcastr2.data.model.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.util.Date
import java.util.UUID
import java.util.regex.Pattern

/**
 * Repository for handling direct podcast downloads from URLs
 */
class DirectDownloadRepository(
    private val context: Context,
    private val downloadTaskDao: DownloadTaskDao,
    private val episodeDao: EpisodeDao
) {
    private val client = OkHttpClient()
    private val TAG = "DirectDownloadRepo"
    
    /**
     * Extract MP3 URL from a podcast page
     * @param pageUrl The URL of the podcast page
     * @return The extracted MP3 URL or null if not found
     */
    suspend fun extractMp3Url(pageUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Extracting MP3 URL from $pageUrl")
            
            // For fountain.fm URLs, use a direct approach
            if (pageUrl.contains("fountain.fm")) {
                return@withContext extractMp3UrlFromFountainFm(pageUrl)
            }
            
            // For other URLs, use a more general approach
            val request = Request.Builder()
                .url(pageUrl)
                .build()
                
            val response = client.newCall(request).execute()
            
            // Use a buffered reader to process the HTML in chunks
            response.body?.let { body ->
                val reader = body.charStream().buffered()
                val buffer = StringBuilder()
                val charBuffer = CharArray(8192)
                var bytesRead: Int
                
                // Pattern to match MP3 URLs
                val pattern = Pattern.compile("https://[^\"]*\\.mp3")
                
                while (reader.read(charBuffer).also { bytesRead = it } != -1) {
                    buffer.append(charBuffer, 0, bytesRead)
                    
                    // Check if we have enough content to search for MP3 URLs
                    if (buffer.length > 8192) {
                        val matcher = pattern.matcher(buffer.toString())
                        if (matcher.find()) {
                            val mp3Url = matcher.group()
                            Log.d(TAG, "Found MP3 URL: $mp3Url")
                            return@withContext mp3Url
                        }
                        
                        // Keep the last 1000 characters in case an MP3 URL is split across chunks
                        if (buffer.length > 1000) {
                            buffer.delete(0, buffer.length - 1000)
                        }
                    }
                }
                
                // Check the remaining buffer
                val matcher = pattern.matcher(buffer.toString())
                if (matcher.find()) {
                    val mp3Url = matcher.group()
                    Log.d(TAG, "Found MP3 URL: $mp3Url")
                    return@withContext mp3Url
                }
            }
            
            Log.d(TAG, "No MP3 URL found in $pageUrl")
            return@withContext null
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting MP3 URL", e)
            return@withContext null
        }
    }
    
    /**
     * Extract MP3 URL specifically from fountain.fm URLs
     */
    private suspend fun extractMp3UrlFromFountainFm(pageUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Extracting MP3 URL from fountain.fm: $pageUrl")
            
            // Use OkHttp to fetch the page content
            val request = Request.Builder()
                .url(pageUrl)
                .build()
                
            val response = client.newCall(request).execute()
            
            // Use a buffered reader to process the HTML in chunks
            response.body?.let { body ->
                val reader = body.charStream().buffered()
                val buffer = StringBuilder()
                val charBuffer = CharArray(8192)
                var bytesRead: Int
                
                // Pattern to match MP3 URLs
                val pattern = Pattern.compile("https://[^\"]*\\.mp3")
                
                while (reader.read(charBuffer).also { bytesRead = it } != -1) {
                    buffer.append(charBuffer, 0, bytesRead)
                    
                    // Check if we have enough content to search for MP3 URLs
                    if (buffer.length > 8192) {
                        val matcher = pattern.matcher(buffer.toString())
                        if (matcher.find()) {
                            val mp3Url = matcher.group()
                            Log.d(TAG, "Found MP3 URL from fountain.fm: $mp3Url")
                            return@withContext mp3Url
                        }
                        
                        // Keep the last 1000 characters in case an MP3 URL is split across chunks
                        if (buffer.length > 1000) {
                            buffer.delete(0, buffer.length - 1000)
                        }
                    }
                }
                
                // Check the remaining buffer
                val matcher = pattern.matcher(buffer.toString())
                if (matcher.find()) {
                    val mp3Url = matcher.group()
                    Log.d(TAG, "Found MP3 URL from fountain.fm: $mp3Url")
                    return@withContext mp3Url
                }
            }
            
            Log.d(TAG, "No MP3 URL found in fountain.fm page")
            return@withContext null
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting MP3 URL from fountain.fm", e)
            return@withContext null
        }
    }
    
    /**
     * Download a podcast episode from a direct MP3 URL
     * @param mp3Url The URL of the MP3 file
     * @param filename The name to save the file as
     * @return The ID of the created episode or null if download failed
     */
    suspend fun downloadPodcastFromUrl(mp3Url: String, filename: String): String? = withContext(Dispatchers.IO) {
        Log.d(TAG, "Starting download of $filename from $mp3Url")
        try {
            // Create a unique episode ID
            val episodeId = UUID.randomUUID().toString()
            
            // Create download directory if it doesn't exist
            val downloadDir = File(context.getExternalFilesDir(null), "podcasts")
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }
            
            // Create the file
            val file = File(downloadDir, filename)
            
            // Create download task
            val now = Date()
            val downloadTask = DownloadTask(
                episodeId = episodeId,
                status = DownloadStatus.DOWNLOADING,
                progress = 0,
                createdAt = now,
                updatedAt = now
            )
            downloadTaskDao.insert(downloadTask)
            
            // Create episode entry
            val episode = Episode(
                id = episodeId,
                podcastId = "direct_downloads", // Special ID for direct downloads
                title = filename,
                description = "Downloaded from $mp3Url",
                audioUrl = mp3Url,
                imageUrl = "", // No image for direct downloads
                duration = 0, // Unknown duration initially
                publishDate = now,
                isDownloaded = false,
                downloadPath = file.absolutePath
            )
            episodeDao.insert(episode)
            
            // Start the download
            val request = Request.Builder()
                .url(mp3Url)
                .build()
                
            val response = client.newCall(request).execute()
            
            if (!response.isSuccessful) {
                // Update download task with error
                downloadTaskDao.updateStatusAndErrorMessage(
                    episodeId = episodeId,
                    status = DownloadStatus.FAILED,
                    errorMessage = "Failed to download: ${response.code}",
                    updatedAt = Date().time
                )
                return@withContext null
            }
            
            // Write the file
            response.body?.let { body ->
                val contentLength = body.contentLength()
                val inputStream = body.byteStream()
                val outputStream = file.outputStream()
                
                val buffer = ByteArray(8192)
                var bytesRead: Int
                var totalBytesRead = 0L
                
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    
                    // Update progress
                    if (contentLength > 0) {
                        val progress = (totalBytesRead * 100 / contentLength).toInt()
                        downloadTaskDao.updateProgress(
                            episodeId = episodeId,
                            progress = progress,
                            updatedAt = Date().time
                        )
                    }
                }
                
                outputStream.close()
                inputStream.close()
                
                // Update download task and episode
                downloadTaskDao.updateStatusAndProgress(
                    episodeId = episodeId,
                    status = DownloadStatus.COMPLETED,
                    progress = 100,
                    updatedAt = Date().time
                )
                
                episodeDao.updateDownloadStatus(
                    episodeId = episodeId,
                    isDownloaded = true,
                    downloadPath = file.absolutePath
                )
                
                return@withContext episodeId
            }
            
            // If we get here, the body was null
            downloadTaskDao.updateStatusAndErrorMessage(
                episodeId = episodeId,
                status = DownloadStatus.FAILED,
                errorMessage = "Empty response body",
                updatedAt = Date().time
            )
            return@withContext null
            
        } catch (e: IOException) {
            Log.e(TAG, "Error downloading podcast", e)
            return@withContext null
        }
    }
    
    /**
     * Get all direct download episodes
     */
    fun getDirectDownloadEpisodes(): Flow<List<Episode>> {
        // Use the new DAO method that joins with DownloadTask
        // The Episode objects emitted will have isDownloading, downloadProgress, 
        // and isDownloadComplete populated based on the DownloadTask.
        return episodeDao.getEpisodesByPodcastIdWithDownloadInfo("direct_downloads")
    }
    
    /**
     * Delete an episode and its file
     */
    suspend fun deleteEpisode(episode: Episode) = withContext(Dispatchers.IO) {
        // Delete the file
        episode.downloadPath?.let { path ->
            val file = File(path)
            if (file.exists()) {
                file.delete()
            }
        }
        
        // Delete from database
        episodeDao.delete(episode)
    }
}
