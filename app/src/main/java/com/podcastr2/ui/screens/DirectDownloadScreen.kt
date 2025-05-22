package com.podcastr2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.podcastr2.data.model.Episode
import com.podcastr2.ui.viewmodels.DirectDownloadViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectDownloadScreen(
    onNavigateBack: () -> Unit,
    viewModel: DirectDownloadViewModel = viewModel()
) {
    val episodes by viewModel.directDownloadEpisodes.collectAsState(initial = emptyList())
    val statusMessage by viewModel.statusMessage.collectAsState()
    val playbackError by viewModel.playbackError.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentlyPlayingUri by viewModel.currentlyPlayingEpisodeUri.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            if (it.isNotBlank()) {
                scope.launch {
                    snackbarHostState.showSnackbar(it)
                }
            }
        }
    }

    if (playbackError != null) {
        AlertDialog(
            onDismissRequest = { viewModel.clearPlaybackError() },
            title = { Text("Playback Error", color = MaterialTheme.colorScheme.onErrorContainer) },
            text = { Text(playbackError!!, color = MaterialTheme.colorScheme.onErrorContainer) },
            containerColor = MaterialTheme.colorScheme.errorContainer,
            confirmButton = {
                TextButton(onClick = { viewModel.clearPlaybackError() }) {
                    Text("OK", color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Direct Download") }, // Colors should be handled by theme
                navigationIcon = {}, // Remove the navigation icon
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Or primary for more emphasis
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(12.dp),
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                    snackbarData = data
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                UrlInputSection(viewModel)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Downloaded Episodes",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (episodes.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
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
                        .padding(horizontal = 16.dp)
                ) {
                    items(episodes) { episode ->
                        val isThisEpisodeCurrentlyPlaying = episode.downloadPath == currentlyPlayingUri && isPlaying
                        EpisodeItem(
                            episode = episode,
                            viewModel = viewModel,
                            isPlayingCurrentEpisode = isThisEpisodeCurrentlyPlaying
                        )
                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                    }
                }
            }
            if (currentlyPlayingUri != null) {
                CentralPlayerControls(viewModel = viewModel, allEpisodes = episodes)
            }
        }
    }
}

@Composable
fun UrlInputSection(viewModel: DirectDownloadViewModel) {
    val context = LocalContext.current
    var url by remember { mutableStateOf("") }
    // var filename by remember { mutableStateOf("") } // Removed filename state
    val cornerRadius = 12.dp

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Podcast URL or MP3 URL") },
                placeholder = { Text("https://example.com/episode or ...mp3") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(cornerRadius),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    if (url.isNotEmpty()) {
                        IconButton(onClick = { url = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear URL",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp)) // Spacer between URL field and button

            // Filename OutlinedTextField and its Spacer removed

            FilledTonalButton(
                onClick = {
                    if (validateInput(url, context)) { // Pass only url and context
                        viewModel.downloadEpisode(url) // Call with only url
                        url = ""
                        // filename = "" // Removed
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(cornerRadius),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Download")
            }
        }
    }
}


@Composable
fun EpisodeItem(
    episode: Episode,
    viewModel: DirectDownloadViewModel,
    isPlayingCurrentEpisode: Boolean
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Episode", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            text = { Text("Are you sure you want to delete ${episode.title}?", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            confirmButton = {
                TextButton(
                    onClick = {
                        if (episode.downloadPath == viewModel.currentlyPlayingEpisodeUri.value) {
                            viewModel.pausePlayback()
                        }
                        viewModel.deleteEpisode(episode)
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 4.dp), // Added horizontal padding
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = episode.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            Text(
                text = dateFormat.format(episode.publishDate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (episode.isDownloading) {
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { (episode.downloadProgress ?: 0) / 100f },
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Downloading: ${episode.downloadProgress ?: 0}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else if (episode.isDownloadComplete || episode.isDownloaded) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Download complete",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 6.dp))
                    Text(
                        text = "Download complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        IconButton(
            onClick = {
                if (episode.downloadPath != null) {
                    val currentUri = viewModel.currentlyPlayingEpisodeUri.value
                    val globalIsPlaying = viewModel.isPlaying.value

                    if (episode.downloadPath == currentUri) {
                        if (globalIsPlaying) viewModel.pausePlayback() else viewModel.resumePlayback()
                    } else {
                        viewModel.playEpisode(episode)
                    }
                }
            },
            enabled = !episode.isDownloading && (episode.isDownloadComplete || episode.isDownloaded),
            modifier = Modifier.size(40.dp) // Consistent icon button size
        ) {
            Icon(
                imageVector = if (isPlayingCurrentEpisode) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isPlayingCurrentEpisode) "Pause" else "Play",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.size(40.dp) // Consistent icon button size
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f) // Slightly less prominent
            )
        }
    }
}


@Composable
fun CentralPlayerControls(
    viewModel: DirectDownloadViewModel,
    allEpisodes: List<Episode>
) {
    val currentlyPlayingUri by viewModel.currentlyPlayingEpisodeUri.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val totalDuration by viewModel.totalDuration.collectAsState()
    val currentSpeed by viewModel.currentPlaybackSpeed.collectAsState()
    var isPlayerMinimized by remember { mutableStateOf(false) }

    val currentEpisode = remember(currentlyPlayingUri, allEpisodes) {
        allEpisodes.find { it.downloadPath == currentlyPlayingUri }
    }

    if (currentEpisode == null || currentlyPlayingUri == null) {
        return
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp, // Reduced elevation
        color = MaterialTheme.colorScheme.surfaceContainerHigh // Elevated surface color
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentEpisode.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { isPlayerMinimized = !isPlayerMinimized }) {
                    Icon(
                        imageVector = if (isPlayerMinimized) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (isPlayerMinimized) "Maximize" else "Minimize",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Conditional rendering for Slider and time labels
            if (!isPlayerMinimized) {
                Slider(
                    value = currentPosition.toFloat(),
                    onValueChange = { viewModel.seekTo(it.toLong()) },
                    valueRange = 0f..(totalDuration.takeIf { it > 0 }?.toFloat() ?: 100f),
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formatTime(currentPosition.toInt()), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(formatTime(totalDuration.toInt()), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Spacer(modifier = Modifier.height(8.dp)) // Spacer after time labels before control buttons
            }

            // Control buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isPlayerMinimized) Arrangement.Center else Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isPlayerMinimized) {
                    IconButton(onClick = { viewModel.rewind() }) {
                        Icon(Icons.Filled.FastRewind, contentDescription = "Rewind", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
                IconButton(
                    onClick = { if (isPlaying) viewModel.pausePlayback() else viewModel.resumePlayback() },
                    modifier = Modifier.size(56.dp) // Larger central button
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary // Prominent color for play/pause
                    )
                }
                if (!isPlayerMinimized) {
                    IconButton(onClick = { viewModel.fastForward() }) {
                        Icon(Icons.Filled.FastForward, contentDescription = "Fast Forward", tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
            // Spacer before speed controls (only if not minimized)
            if (!isPlayerMinimized) {
                Spacer(modifier = Modifier.height(16.dp)) // This spacer is fine here
                Text("Playback Speed:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val speeds = listOf(1.0f, 1.5f, 2.0f)
                    speeds.forEach { speed ->
                        SpeedButton(
                            speed = speed,
                            currentSpeed = currentSpeed,
                            onClick = { viewModel.setPlaybackSpeed(speed) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SpeedButton(speed: Float, currentSpeed: Float, onClick: () -> Unit) {
    val isSelected = speed == currentSpeed
    val cornerRadius = 12.dp
    FilledTonalButton( // Changed to FilledTonalButton for consistency
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 4.dp),
        shape = RoundedCornerShape(cornerRadius),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text("${speed}x")
    }
}


private fun validateInput(url: String, context: Context): Boolean { // Removed filename parameter
    if (url.isBlank()) {
        Toast.makeText(context, "Please enter a URL", Toast.LENGTH_SHORT).show()
        return false
    }
    // Removed filename validation
    return true
}

private fun formatTime(millis: Int): String {
    if (millis < 0) return "00:00"
    val totalSeconds = millis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
