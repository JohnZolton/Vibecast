package com.podcastr2.ui.screens

import android.content.Context
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.podcastr2.PodcastrApp
import com.podcastr2.data.model.Episode
import com.podcastr2.ui.viewmodels.DirectDownloadViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectDownloadScreen(
    onNavigateBack: () -> Unit,
    viewModel: DirectDownloadViewModel = viewModel()
) {
    val context = LocalContext.current
    val episodes by viewModel.directDownloadEpisodes.collectAsState(initial = emptyList())
    val statusMessage by viewModel.statusMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Show status message in snackbar
    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Direct Download") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    content = { Text(data.visuals.message) }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            UrlInputSection(viewModel)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Downloaded Episodes",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (episodes.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No downloaded episodes yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(episodes) { episode ->
                        EpisodeItem(episode = episode, viewModel = viewModel)
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun UrlInputSection(viewModel: DirectDownloadViewModel) {
    val context = LocalContext.current
    var pageUrl by remember { mutableStateOf("") }
    var mp3Url by remember { mutableStateOf("") }
    var filename by remember { mutableStateOf("") }
    var isExtractMode by remember { mutableStateOf(true) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { isExtractMode = true },
                    modifier = Modifier.weight(1f),
                    enabled = !isExtractMode
                ) {
                    Text("Extract MP3")
                }
                
                Button(
                    onClick = { isExtractMode = false },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    enabled = isExtractMode
                ) {
                    Text("Direct MP3")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isExtractMode) {
                // Extract mode UI
                OutlinedTextField(
                    value = pageUrl,
                    onValueChange = { pageUrl = it },
                    label = { Text("Podcast Page URL") },
                    placeholder = { Text("https://fountain.fm/episode/...") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (pageUrl.isNotEmpty()) {
                            IconButton(onClick = { pageUrl = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = filename,
                    onValueChange = { filename = it },
                    label = { Text("Filename") },
                    placeholder = { Text("episode.mp3") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (validateExtractInput(pageUrl, filename, context)) {
                                viewModel.extractAndDownload(pageUrl, filename)
                                // Clear fields after starting download
                                pageUrl = ""
                                filename = ""
                            }
                        }
                    ),
                    trailingIcon = {
                        if (filename.isNotEmpty()) {
                            IconButton(onClick = { filename = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        if (validateExtractInput(pageUrl, filename, context)) {
                            viewModel.extractAndDownload(pageUrl, filename)
                            // Clear fields after starting download
                            pageUrl = ""
                            filename = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Extract and Download")
                }
            } else {
                // Direct mode UI
                OutlinedTextField(
                    value = mp3Url,
                    onValueChange = { mp3Url = it },
                    label = { Text("MP3 URL") },
                    placeholder = { Text("https://episodes.castos.com/...mp3") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        if (mp3Url.isNotEmpty()) {
                            IconButton(onClick = { mp3Url = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = filename,
                    onValueChange = { filename = it },
                    label = { Text("Filename") },
                    placeholder = { Text("episode.mp3") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (validateDirectInput(mp3Url, filename, context)) {
                                viewModel.downloadFromUrl(mp3Url, filename)
                                // Clear fields after starting download
                                mp3Url = ""
                                filename = ""
                            }
                        }
                    ),
                    trailingIcon = {
                        if (filename.isNotEmpty()) {
                            IconButton(onClick = { filename = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        if (validateDirectInput(mp3Url, filename, context)) {
                            viewModel.downloadFromUrl(mp3Url, filename)
                            // Clear fields after starting download
                            mp3Url = ""
                            filename = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Download MP3")
                }
            }
        }
    }
}

// MediaPlayer for audio playback
private var mediaPlayer: MediaPlayer? = null

// Helper function to get the application instance
private fun Context.getApp(): PodcastrApp {
    return applicationContext as PodcastrApp
}

@Composable
fun EpisodeItem(
    episode: Episode,
    viewModel: DirectDownloadViewModel
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var duration by remember { mutableStateOf(0) }
    
    // Get the saved playback speed from settings
    val playbackSpeed by context.getApp().settingsRepository.playbackSpeed.collectAsState()
    
    // Clean up MediaPlayer when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
    
    // Update progress periodically while playing
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    progress = player.currentPosition.toFloat() / player.duration
                    duration = player.duration
                }
            }
            kotlinx.coroutines.delay(1000)
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Episode") },
            text = { Text("Are you sure you want to delete ${episode.title}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Stop playback if playing
                        if (isPlaying) {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            isPlaying = false
                        }
                        
                        // Delete the episode
                        viewModel.deleteEpisode(episode)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = episode.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                Text(
                    text = dateFormat.format(episode.publishDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Speed button
            if (isPlaying) {
                IconButton(onClick = { expanded = !expanded }) {
                    Text(
                        text = "${playbackSpeed}x",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            
            // Play/Pause button
            IconButton(
                onClick = {
                    // Play the episode
                    val file = episode.downloadPath?.let { File(it) }
                    if (file != null && file.exists()) {
                        if (isPlaying) {
                            // Pause playback
                            mediaPlayer?.pause()
                            isPlaying = false
                            Toast.makeText(context, "Paused ${episode.title}", Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                if (mediaPlayer == null) {
                                    // Start new playback
                                    mediaPlayer = MediaPlayer().apply {
                                        setDataSource(file.absolutePath)
                                        prepare()
                                        
                                        // Set playback speed
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            val params = PlaybackParams().apply {
                                                speed = playbackSpeed
                                                pitch = 1.0f  // Keep pitch normal
                                            }
                                            playbackParams = params
                                        }
                                        
                                        start()
                                        setOnCompletionListener {
                                            isPlaying = false
                                            progress = 0f
                                        }
                                    }
                                } else {
                                    // Resume playback
                                    mediaPlayer?.start()
                                }
                                isPlaying = true
                                Toast.makeText(context, "Playing ${episode.title} at ${playbackSpeed}x", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error playing file: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(
                    if (isPlaying) Icons.Default.Clear else Icons.Default.PlayArrow, 
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
            
            // Delete button
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
        
        // Progress bar and seek bar
        if (isPlaying || progress > 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Slider(
                    value = progress,
                    onValueChange = { newProgress ->
                        progress = newProgress
                        mediaPlayer?.let { player ->
                            val newPosition = (newProgress * player.duration).toInt()
                            player.seekTo(newPosition)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Time display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formatTime(mediaPlayer?.currentPosition ?: 0))
                    Text(formatTime(duration))
                }
            }
        }
        
        // Speed selector
        if (expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SpeedButton(speed = 0.5f, currentSpeed = playbackSpeed, context = context)
                SpeedButton(speed = 1.0f, currentSpeed = playbackSpeed, context = context)
                SpeedButton(speed = 1.5f, currentSpeed = playbackSpeed, context = context)
                SpeedButton(speed = 2.0f, currentSpeed = playbackSpeed, context = context)
                SpeedButton(speed = 3.0f, currentSpeed = playbackSpeed, context = context)
            }
        }
    }
}

@Composable
fun SpeedButton(speed: Float, currentSpeed: Float, context: Context) {
    val isSelected = speed == currentSpeed
    
    Button(
        onClick = {
            // Update playback speed
            context.getApp().settingsRepository.setPlaybackSpeed(speed)
            
            // Apply to current playback if playing
            if (mediaPlayer?.isPlaying == true && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                try {
                    val params = mediaPlayer?.playbackParams?.apply {
                        this.speed = speed
                    }
                    params?.let { mediaPlayer?.playbackParams = it }
                    
                    Toast.makeText(context, "Speed changed to ${speed}x", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error changing speed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        },
        modifier = Modifier.padding(horizontal = 4.dp),
        colors = if (isSelected) {
            androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        } else {
            androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    ) {
        Text("${speed}x")
    }
}

private fun validateExtractInput(pageUrl: String, filename: String, context: Context): Boolean {
    if (pageUrl.isBlank()) {
        Toast.makeText(context, "Please enter a podcast page URL", Toast.LENGTH_SHORT).show()
        return false
    }
    
    if (filename.isBlank()) {
        Toast.makeText(context, "Please enter a filename", Toast.LENGTH_SHORT).show()
        return false
    }
    
    if (!filename.endsWith(".mp3")) {
        Toast.makeText(context, "Filename must end with .mp3", Toast.LENGTH_SHORT).show()
        return false
    }
    
    return true
}

private fun validateDirectInput(mp3Url: String, filename: String, context: Context): Boolean {
    if (mp3Url.isBlank()) {
        Toast.makeText(context, "Please enter an MP3 URL", Toast.LENGTH_SHORT).show()
        return false
    }
    
    if (filename.isBlank()) {
        Toast.makeText(context, "Please enter a filename", Toast.LENGTH_SHORT).show()
        return false
    }
    
    if (!filename.endsWith(".mp3")) {
        Toast.makeText(context, "Filename must end with .mp3", Toast.LENGTH_SHORT).show()
        return false
    }
    
    return true
}

/**
 * Format milliseconds into a time string (MM:SS)
 */
private fun formatTime(millis: Int): String {
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
