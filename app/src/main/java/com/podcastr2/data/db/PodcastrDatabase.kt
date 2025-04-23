package com.podcastr2.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.podcastr2.data.model.AdSegment
import com.podcastr2.data.model.DownloadTask
import com.podcastr2.data.model.Episode
import com.podcastr2.data.model.Podcast

@Database(
    entities = [
        Podcast::class,
        Episode::class,
        AdSegment::class,
        DownloadTask::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PodcastrDatabase : RoomDatabase() {
    
    abstract fun podcastDao(): PodcastDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun adSegmentDao(): AdSegmentDao
    abstract fun downloadTaskDao(): DownloadTaskDao
    
    companion object {
        @Volatile
        private var INSTANCE: PodcastrDatabase? = null
        
        fun getDatabase(context: Context): PodcastrDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PodcastrDatabase::class.java,
                    "podcastr_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        // Alias for getDatabase to maintain compatibility with code that expects getInstance
        fun getInstance(context: Context): PodcastrDatabase = getDatabase(context)
    }
}
