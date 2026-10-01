package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun ArenaStreamPlayer(
    match: Match,
    activeCamera: CameraAngle,
    isMuted: Boolean,
    isLowLatency: Boolean,
    reactions: List<LiveReaction>,
    onCameraSelect: (CameraAngle) -> Unit,
    onToggleMute: () -> Unit,
    onToggleLatency: () -> Unit,
    onSendReaction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "live_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "live_pulse_alpha"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("arena_stream_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CasinoSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Main Live Video Broadcast Frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color.Black)
            ) {
                // High Quality Arena Stream Visual
                Image(
                    painter = painterResource(id = R.drawable.img_arena_stream),
                    contentDescription = "Siaran Langsung Gelanggang Sabah",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Subtle broadcast gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xCC0C031A),
                                    Color(0x11000000),
                                    Color(0xEE0C031A)
                                )
                            )
                        )
                )

                // Top Broadcast Status Bar (Clean & Uncluttered)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Live Pill & Arena Name
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MeronRed.copy(alpha = pulseAlpha)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(Color.White, CircleShape)
                                )
                                Text(
                                    text = "LANGSUNG HD",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xCC1A093D)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Text("🇲🇾", fontSize = 10.sp)
                                Text(
                                    text = match.arena.name,
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Right: Viewers Count & Audio Toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xAA000000)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Penonton",
                                    tint = CasinoGold,
                                    modifier = Modifier.size(11.dp)
                                )
                                Text(
                                    text = "${match.viewersCount / 1000}.${(match.viewersCount % 1000) / 100}K",
                                    color = CasinoGold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onToggleMute() },
                            shape = CircleShape,
                            color = Color(0xAA000000)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                    contentDescription = "Audio",
                                    tint = Color.White,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }
                }

                // If YouTube Live is active, show the clean YouTube direct banner in the center
                if (activeCamera == CameraAngle.YOUTUBE_LIVE) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.youtube.com/watch?v=${match.youtubeVideoId}")
                                )
                                context.startActivity(intent)
                            },
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xEE160A2A),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CasinoGold)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFFF0000),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(18.dp))
                                }
                            }
                            Column {
                                Text(
                                    text = "Siaran YouTube Rasmi • Perlawanan #42",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Ketuk untuk tonton video penuh di YouTube (ID: ${match.youtubeVideoId})",
                                    color = CasinoGold,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Icon(Icons.Outlined.OpenInNew, contentDescription = "Buka", tint = Color.LightGray, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                // Lower-Third Scorebug Strip (ESPN/UFC Broadcast Style - Does not block the fight!)
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xDD0D031F),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33FFFFFF))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Merah Fighter Label & Odds
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(MeronRed, CircleShape)
                            )
                            Text(
                                text = match.meron.name,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.widthIn(max = 90.dp)
                            )
                            Text(
                                text = "${match.meron.odds}",
                                color = CasinoGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        // Round / Timer Center Badge
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0x55FFFFFF)
                        ) {
                            Text(
                                text = if (match.status == MatchStatus.BETTING_OPEN) {
                                    "MASA 00:${match.bettingSecondsLeft.coerceAtLeast(0).toString().padStart(2, '0')}"
                                } else {
                                    "TAJI 01:${match.fightDurationSeconds.coerceAtLeast(0).toString().padStart(2, '0')}"
                                },
                                color = if (match.status == MatchStatus.BETTING_OPEN) Color.White else NeonEmerald,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }

                        // Biru Fighter Label & Odds
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${match.wala.odds}",
                                color = CasinoGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = match.wala.name,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.widthIn(max = 90.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF0072FF), CircleShape)
                            )
                        }
                    }
                }

                // Floating Reactions (Top Right of lower third)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 8.dp, bottom = 36.dp),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("🔥", "🐓", "💰").forEach { emoji ->
                        Surface(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onSendReaction(emoji) },
                            shape = CircleShape,
                            color = Color(0x99000000),
                            border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0x44FFFFFF))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(emoji, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Camera Angle & YouTube Video Switching Horizontal Row (Sleek broadcast tabs)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CasinoSurfaceElevated)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "KAMERA:",
                    color = CasinoGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(end = 6.dp)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    CameraAngle.values().forEach { cam ->
                        val isSelected = cam == activeCamera
                        Surface(
                            modifier = Modifier
                                .clickable { onCameraSelect(cam) }
                                .testTag("camera_angle_${cam.name.lowercase()}"),
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSelected) CasinoGold else Color(0x22FFFFFF),
                            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(0.5.dp, Color(0x33FFFFFF))
                        ) {
                            Text(
                                text = cam.label,
                                color = if (isSelected) Color.Black else Color.White,
                                fontSize = 9.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
