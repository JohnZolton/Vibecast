package com.podcastr2.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

/**
 * Represents a podcast feed (RSS or YouTube)
 */
@Entity(tableName = "podcasts")
data class Podcast(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val author: String,
    val imageUrl: String,
    val feedUrl: String,
    val website: String,
    val isYouTube: Boolean,
    val lastUpdated: Date,
    val lastChecked: Date
)

/**
 * Represents a podcast episode
 */
@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey val id: String,
    val podcastId: String,
    val title: String,
    val description: String,
    val audioUrl: String,
    val imageUrl: String,
    val duration: Long, // in milliseconds
    val publishDate: Date,
    val isDownloaded: Boolean = false,
    val downloadPath: String? = null,
    val isTranscribed: Boolean = false,
    val transcriptionPath: String? = null,
    val isAnalyzed: Boolean = false,
    val lastPlayedPosition: Long = 0,
    val isCompleted: Boolean = false
)

/**
 * Represents a podcast with its episodes
 */
data class PodcastWithEpisodes(
    @Embedded val podcast: Podcast,
    @Relation(
        parentColumn = "id",
        entityColumn = "podcastId"
    )
    val episodes: List<Episode>
)

/**
 * Represents an ad segment within a podcast episode
 */
@Entity(tableName = "ad_segments")
data class AdSegment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val episodeId: String,
    val startTime: Long, // in milliseconds
    val endTime: Long, // in milliseconds
    val confidence: Float, // 0.0 to 1.0
    val transcriptSnippet: String
)

/**
 * Represents an episode with its ad segments
 */
data class EpisodeWithAdSegments(
    @Embedded val episode: Episode,
    @Relation(
        parentColumn = "id",
        entityColumn = "episodeId"
    )
    val adSegments: List<AdSegment>
)

/**
 * Represents the download status of an episode
 */
enum class DownloadStatus {
    QUEUED,
    DOWNLOADING,
    COMPLETED,
    FAILED,
    TRANSCRIBING,
    TRANSCRIBED,
    ANALYZING,
    ANALYZED
}

/**
 * Represents a download task
 */
@Entity(tableName = "download_tasks")
data class DownloadTask(
    @PrimaryKey val episodeId: String,
    val status: DownloadStatus,
    val progress: Int, // 0-100
    val errorMessage: String? = null,
    val createdAt: Date,
    val updatedAt: Date
)
