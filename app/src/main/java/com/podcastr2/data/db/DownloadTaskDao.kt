package com.podcastr2.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.podcastr2.data.model.DownloadStatus
import com.podcastr2.data.model.DownloadTask
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadTaskDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(downloadTask: DownloadTask)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(downloadTasks: List<DownloadTask>)
    
    @Update
    suspend fun update(downloadTask: DownloadTask)
    
    @Delete
    suspend fun delete(downloadTask: DownloadTask)
    
    @Query("DELETE FROM download_tasks WHERE episodeId = :episodeId")
    suspend fun deleteByEpisodeId(episodeId: String)
    
    @Query("SELECT * FROM download_tasks ORDER BY createdAt DESC")
    fun getAllDownloadTasks(): Flow<List<DownloadTask>>
    
    @Query("SELECT * FROM download_tasks WHERE episodeId = :episodeId")
    fun getDownloadTaskByEpisodeId(episodeId: String): Flow<DownloadTask?>
    
    @Query("SELECT * FROM download_tasks WHERE status = :status ORDER BY createdAt ASC")
    fun getDownloadTasksByStatus(status: DownloadStatus): Flow<List<DownloadTask>>
    
    @Query("SELECT * FROM download_tasks WHERE status IN (:statuses) ORDER BY createdAt ASC")
    fun getDownloadTasksByStatuses(statuses: List<DownloadStatus>): Flow<List<DownloadTask>>
    
    @Query("UPDATE download_tasks SET status = :status, updatedAt = :updatedAt WHERE episodeId = :episodeId")
    suspend fun updateStatus(episodeId: String, status: DownloadStatus, updatedAt: Long)
    
    @Query("UPDATE download_tasks SET progress = :progress, updatedAt = :updatedAt WHERE episodeId = :episodeId")
    suspend fun updateProgress(episodeId: String, progress: Int, updatedAt: Long)
    
    @Query("UPDATE download_tasks SET errorMessage = :errorMessage, updatedAt = :updatedAt WHERE episodeId = :episodeId")
    suspend fun updateErrorMessage(episodeId: String, errorMessage: String?, updatedAt: Long)
    
    @Query("UPDATE download_tasks SET status = :status, progress = :progress, updatedAt = :updatedAt WHERE episodeId = :episodeId")
    suspend fun updateStatusAndProgress(episodeId: String, status: DownloadStatus, progress: Int, updatedAt: Long)
    
    @Query("UPDATE download_tasks SET status = :status, errorMessage = :errorMessage, updatedAt = :updatedAt WHERE episodeId = :episodeId")
    suspend fun updateStatusAndErrorMessage(episodeId: String, status: DownloadStatus, errorMessage: String?, updatedAt: Long)
}
