package com.podcastr2.data.repository

import com.podcastr2.data.db.PodcastDao
import com.podcastr2.data.model.Podcast
import com.podcastr2.data.model.PodcastWithEpisodes
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

/**
 * Repository for managing podcast data
 */
class PodcastRepository(private val podcastDao: PodcastDao) {
    
    /**
     * Get all podcasts
     */
    fun getAllPodcasts(): Flow<List<Podcast>> {
        return podcastDao.getAllPodcasts()
    }
    
    /**
     * Get podcast by ID
     */
    fun getPodcastById(podcastId: String): Flow<Podcast?> {
        return podcastDao.getPodcastById(podcastId)
    }
    
    /**
     * Get podcast with episodes by ID
     */
    fun getPodcastWithEpisodes(podcastId: String): Flow<PodcastWithEpisodes?> {
        return podcastDao.getPodcastWithEpisodes(podcastId)
    }
    
    /**
     * Get all podcasts with their episodes
     */
    fun getAllPodcastsWithEpisodes(): Flow<List<PodcastWithEpisodes>> {
        return podcastDao.getAllPodcastsWithEpisodes()
    }
    
    /**
     * Add a new podcast
     */
    suspend fun addPodcast(
        title: String,
        description: String,
        author: String,
        imageUrl: String,
        feedUrl: String,
        website: String,
        isYouTube: Boolean
    ): String {
        // Check if podcast with this feed URL already exists
        val existingPodcast = podcastDao.getPodcastByFeedUrl(feedUrl)
        if (existingPodcast != null) {
            return existingPodcast.id
        }
        
        // Create new podcast
        val now = Date()
        val podcast = Podcast(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            author = author,
            imageUrl = imageUrl,
            feedUrl = feedUrl,
            website = website,
            isYouTube = isYouTube,
            lastUpdated = now,
            lastChecked = now
        )
        
        podcastDao.insert(podcast)
        return podcast.id
    }
    
    /**
     * Update an existing podcast
     */
    suspend fun updatePodcast(podcast: Podcast) {
        podcastDao.update(podcast)
    }
    
    /**
     * Delete a podcast by ID
     */
    suspend fun deletePodcast(podcastId: String) {
        podcastDao.deleteById(podcastId)
    }
    
    /**
     * Update the last checked timestamp for a podcast
     */
    suspend fun updateLastChecked(podcastId: String) {
        podcastDao.updateLastChecked(podcastId, Date().time)
    }
    
    /**
     * Update the last updated timestamp for a podcast
     */
    suspend fun updateLastUpdated(podcastId: String) {
        podcastDao.updateLastUpdated(podcastId, Date().time)
    }
}
