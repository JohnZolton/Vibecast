package com.podcastr2.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.podcastr2.data.model.Episode
import com.podcastr2.data.model.EpisodeWithAdSegments
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: Episode)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<Episode>)
    
    @Update
    suspend fun update(episode: Episode)
    
    @Delete
    suspend fun delete(episode: Episode)
    
    @Query("DELETE FROM episodes WHERE id = :episodeId")
    suspend fun deleteById(episodeId: String)
    
    @Query("DELETE FROM episodes WHERE podcastId = :podcastId")
    suspend fun deleteByPodcastId(podcastId: String)
    
    @Query("SELECT * FROM episodes ORDER BY publishDate DESC")
    fun getAllEpisodes(): Flow<List<Episode>>
    
    @Query("SELECT * FROM episodes WHERE podcastId = :podcastId ORDER BY publishDate DESC")
    fun getEpisodesByPodcastId(podcastId: String): Flow<List<Episode>>
    
    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    fun getEpisodeById(episodeId: String): Flow<Episode?>
    
    @Query("SELECT * FROM episodes WHERE isDownloaded = 1 ORDER BY publishDate DESC")
    fun getDownloadedEpisodes(): Flow<List<Episode>>
    
    @Query("SELECT * FROM episodes WHERE isTranscribed = 1 ORDER BY publishDate DESC")
    fun getTranscribedEpisodes(): Flow<List<Episode>>
    
    @Query("SELECT * FROM episodes WHERE isAnalyzed = 1 ORDER BY publishDate DESC")
    fun getAnalyzedEpisodes(): Flow<List<Episode>>
    
    @Transaction
    @Query("SELECT * FROM episodes WHERE id = :episodeId")
    fun getEpisodeWithAdSegments(episodeId: String): Flow<EpisodeWithAdSegments?>
    
    @Transaction
    @Query("SELECT * FROM episodes WHERE isAnalyzed = 1 ORDER BY publishDate DESC")
    fun getAnalyzedEpisodesWithAdSegments(): Flow<List<EpisodeWithAdSegments>>
    
    @Query("UPDATE episodes SET isDownloaded = :isDownloaded, downloadPath = :downloadPath WHERE id = :episodeId")
    suspend fun updateDownloadStatus(episodeId: String, isDownloaded: Boolean, downloadPath: String?)
    
    @Query("UPDATE episodes SET isTranscribed = :isTranscribed, transcriptionPath = :transcriptionPath WHERE id = :episodeId")
    suspend fun updateTranscriptionStatus(episodeId: String, isTranscribed: Boolean, transcriptionPath: String?)
    
    @Query("UPDATE episodes SET isAnalyzed = :isAnalyzed WHERE id = :episodeId")
    suspend fun updateAnalysisStatus(episodeId: String, isAnalyzed: Boolean)
    
    @Query("UPDATE episodes SET lastPlayedPosition = :position WHERE id = :episodeId")
    suspend fun updatePlaybackPosition(episodeId: String, position: Long)
    
    @Query("UPDATE episodes SET isCompleted = :isCompleted WHERE id = :episodeId")
    suspend fun updateCompletionStatus(episodeId: String, isCompleted: Boolean)
}
