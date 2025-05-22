package com.podcastr2.ui.viewmodels

import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest=Config.NONE)
class DirectDownloadViewModelTest {

    private lateinit var viewModel: DirectDownloadViewModel

    @Before
    fun setUp() {
        val application = RuntimeEnvironment.getApplication()
        viewModel = DirectDownloadViewModel(application)
    }

    @Test
    fun `generateFilenameFromUrl with URL with query parameters no guess`() {
        val url = "http://example.com/audio_file.mp3?session=123&token=abc"
        val filename = viewModel.generateFilenameFromUrl(url)
        assertEquals("audio_file.mp3", filename)
    }

    @Test
    fun `generateFilenameFromUrl with empty URL`() {
        val url = ""
        val filename = viewModel.generateFilenameFromUrl(url)
        // Should generate a timestamp-based filename or handle empty URL gracefully
        assertTrue("Filename should end with .mp3", filename.endsWith(".mp3"))
        assertTrue("Filename should not be empty", filename.isNotEmpty())
    }

    @Test
    fun `generateFilenameFromUrl with no extension`() {
        val url = "http://example.com/myepisode"
        val filename = viewModel.generateFilenameFromUrl(url)
        // Should add .mp3 extension
        assertTrue("Filename should end with .mp3", filename.endsWith(".mp3"))
    }

    @Test
    fun `generateFilenameFromUrl with filename already sanitized`() {
        val url = "http://example.com/already_sanitized_episode_123.mp3"
        val filename = viewModel.generateFilenameFromUrl(url)
        assertEquals("already_sanitized_episode_123.mp3", filename)
    }
}
