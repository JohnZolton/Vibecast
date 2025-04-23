package com.podcastr2.data.repository

import com.podcastr2.data.db.EpisodeDao
import com.podcastr2.data.model.Episode
import com.podcastr2.data.model.EpisodeWithAdSegments
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

/**
 * Repository for managing episode data
 */
class EpisodeRepository(private val episodeDao: EpisodeDao) {
    
    /**
     * Get all episodes
     */
    fun getAllEpisodes(): Flow<List<Episode>> {
        return episodeDao.getAllEpisodes()
    }
    
    /**
     * Get episodes by podcast ID
     */
    fun getEpisodesByPodcastId(podcastId: String): Flow<List<Episode>> {
        return episodeDao.getEpisodesByPodcastId(podcastId)
    }
    
    /**
     * Get episode by ID
     */
    fun getEpisodeById(episodeId: String): Flow<Episode?> {
        return episodeDao.getEpisodeById(episodeId)
    }
    
    /**
     * Get downloaded episodes
     */
    fun getDownloadedEpisodes(): Flow<List<Episode>> {
        return episodeDao.getDownloadedEpisodes()
    }
    
    /**
     * Get transcribed episodes
     */
    fun getTranscribedEpisodes(): Flow<List<Episode>> {
        return episodeDao.getTranscribedEpisodes()
    }
    
    /**
     * Get analyzed episodes
     */
    fun getAnalyzedEpisodes(): Flow<List<Episode>> {
        return episodeDao.getAnalyzedEpisodes()
    }
    
    /**
     * Get episode with ad segments
     */
    fun getEpisodeWithAdSegments(episodeId: String): Flow<EpisodeWithAdSegments?> {
        return episodeDao.getEpisodeWithAdSegments(episodeId)
    }
    
    /**
     * Get analyzed episodes with ad segments
     */
    fun getAnalyzedEpisodesWithAdSegments(): Flow<List<EpisodeWithAdSegments>> {
        return episodeDao.getAnalyzedEpisodesWithAdSegments()
    }
    
    /**
     * Add a new episode
     */
    suspend fun addEpisode(
        podcastId: String,
        title: String,
        description: String,
        audioUrl: String,
        imageUrl: String,
        duration: Long,
        publishDate: Date
    ): String {
        val episode = Episode(
            id = UUID.randomUUID().toString(),
            podcastId = podcastId,
            title = title,
            description = description,
            audioUrl = audioUrl,
            imageUrl = imageUrl,
            duration = duration,
            publishDate = publishDate
        )
        
        episodeDao.insert(episode)
        return episode.id
    }
    
    /**
     * Update an existing episode
     */
    suspend fun updateEpisode(episode: Episode) {
        episodeDao.update(episode)
    }
    
    /**
     * Delete an episode by ID
     */
    suspend fun deleteEpisode(episodeId: String) {
        episodeDao.deleteById(episodeId)
    }
    
    /**
     * Delete episodes by podcast ID
     */
    suspend fun deleteEpisodesByPodcastId(podcastId: String) {
        episodeDao.deleteByPodcastId(podcastId)
    }
    
    /**
     * Update download status
     */
    suspend fun updateDownloadStatus(episodeId: String, isDownloaded: Boolean, downloadPath: String?) {
        episodeDao.updateDownloadStatus(episodeId, isDownloaded, downloadPath)
    }
    
    /**
     * Update transcription status
     */
    suspend fun updateTranscriptionStatus(episodeId: String, isTranscribed: Boolean, transcriptionPath: String?) {
        episodeDao.updateTranscriptionStatus(episodeId, isTranscribed, transcriptionPath)
    }
    
    /**
     * Update analysis status
     */
    suspend fun updateAnalysisStatus(episodeId: String, isAnalyzed: Boolean) {
        episodeDao.updateAnalysisStatus(episodeId, isAnalyzed)
    }
    
    /**
     * Update playback position
     */
    suspend fun updatePlaybackPosition(episodeId: String, position: Long) {
        episodeDao.updatePlaybackPosition(episodeId, position)
    }
    
    /**
     * Update completion status
     */
    suspend fun updateCompletionStatus(episodeId: String, isCompleted: Boolean) {
        episodeDao.updateCompletionStatus(episodeId, isCompleted)
    }
}
