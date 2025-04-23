package com.podcastr2.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.podcastr2.data.model.Podcast
import com.podcastr2.data.model.PodcastWithEpisodes
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(podcast: Podcast)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(podcasts: List<Podcast>)
    
    @Update
    suspend fun update(podcast: Podcast)
    
    @Delete
    suspend fun delete(podcast: Podcast)
    
    @Query("DELETE FROM podcasts WHERE id = :podcastId")
    suspend fun deleteById(podcastId: String)
    
    @Query("SELECT * FROM podcasts ORDER BY title ASC")
    fun getAllPodcasts(): Flow<List<Podcast>>
    
    @Query("SELECT * FROM podcasts WHERE id = :podcastId")
    fun getPodcastById(podcastId: String): Flow<Podcast?>
    
    @Query("SELECT * FROM podcasts WHERE feedUrl = :feedUrl")
    suspend fun getPodcastByFeedUrl(feedUrl: String): Podcast?
    
    @Transaction
    @Query("SELECT * FROM podcasts WHERE id = :podcastId")
    fun getPodcastWithEpisodes(podcastId: String): Flow<PodcastWithEpisodes?>
    
    @Transaction
    @Query("SELECT * FROM podcasts ORDER BY title ASC")
    fun getAllPodcastsWithEpisodes(): Flow<List<PodcastWithEpisodes>>
    
    @Query("UPDATE podcasts SET lastChecked = :lastChecked WHERE id = :podcastId")
    suspend fun updateLastChecked(podcastId: String, lastChecked: Long)
    
    @Query("UPDATE podcasts SET lastUpdated = :lastUpdated WHERE id = :podcastId")
    suspend fun updateLastUpdated(podcastId: String, lastUpdated: Long)
}
