package com.podcastr2.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.podcastr2.data.model.AdSegment
import kotlinx.coroutines.flow.Flow

@Dao
interface AdSegmentDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(adSegment: AdSegment): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(adSegments: List<AdSegment>): List<Long>
    
    @Update
    suspend fun update(adSegment: AdSegment)
    
    @Delete
    suspend fun delete(adSegment: AdSegment)
    
    @Query("DELETE FROM ad_segments WHERE id = :adSegmentId")
    suspend fun deleteById(adSegmentId: Long)
    
    @Query("DELETE FROM ad_segments WHERE episodeId = :episodeId")
    suspend fun deleteByEpisodeId(episodeId: String)
    
    @Query("SELECT * FROM ad_segments WHERE episodeId = :episodeId ORDER BY startTime ASC")
    fun getAdSegmentsByEpisodeId(episodeId: String): Flow<List<AdSegment>>
    
    @Query("SELECT * FROM ad_segments WHERE id = :adSegmentId")
    fun getAdSegmentById(adSegmentId: Long): Flow<AdSegment?>
    
    @Query("SELECT * FROM ad_segments WHERE episodeId = :episodeId AND :position BETWEEN startTime AND endTime LIMIT 1")
    suspend fun getAdSegmentAtPosition(episodeId: String, position: Long): AdSegment?
    
    @Query("SELECT * FROM ad_segments WHERE episodeId = :episodeId AND startTime > :currentPosition ORDER BY startTime ASC LIMIT 1")
    suspend fun getNextAdSegment(episodeId: String, currentPosition: Long): AdSegment?
}
