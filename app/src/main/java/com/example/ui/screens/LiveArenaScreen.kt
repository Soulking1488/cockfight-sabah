package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel

@Composable
fun LiveArenaScreen(
    viewModel: CockfightViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentMatch = uiState.matches.firstOrNull { it.id == uiState.currentMatchId }
        ?: uiState.matches.firstOrNull()

    var chatInput by remember { mutableStateOf("") }
    val quickChips = listOf(10.0, 50.0, 100.0, 200.0, 500.0)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("live_arena_screen")
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 6.dp, bottom = 80.dp)
    ) {
        // 1. Dynamic Event Notification Ticker
        item {
            val currentEvent = uiState.eventNotifications.getOrNull(uiState.activeEventIndex)
            FlashyTickerBanner(
                event = currentEvent,
                onClick = { }
            )
        }

        // 2. Arena Switcher Horizontal Row (Gelanggang Sabah)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LOKASI GELANGGANG SABAH",
                        color = CasinoGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "4 Gelanggang Aktif",
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(uiState.arenas) { arena ->
                        val isSelected = arena.id == uiState.selectedArenaId
                        Surface(
                            modifier = Modifier
                                .clickable { viewModel.selectArena(arena.id) }
                                .testTag("arena_pill_${arena.id}"),
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) CasinoGold else CasinoSurfaceElevated,
                            border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(arena.flagEmoji, fontSize = 12.sp)
                                Column {
                                    Text(
                                        text = arena.name,
                                        color = if (isSelected) Color.Black else Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${arena.location} • LIVE",
                                        color = if (isSelected) Color(0xFF6B3A00) else MeronRedBright,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. Live Arena Video Streaming Player (Compact 210dp height with YouTube BTUtspf5rRo embed)
        if (currentMatch != null) {
            item {
                ArenaStreamPlayer(
                    match = currentMatch,
                    activeCamera = uiState.selectedCameraAngle,
                    isMuted = uiState.isStreamMuted,
                    isLowLatency = uiState.isLowLatencyMode,
                    reactions = uiState.liveReactions,
                    onCameraSelect = { viewModel.setCameraAngle(it) },
                    onToggleMute = { viewModel.toggleStreamMute() },
                    onToggleLatency = { viewModel.toggleLowLatency() },
                    onSendReaction = { viewModel.sendCrowdReaction(it) }
                )
            }

            // 4. Quick Live Betting Desk with Chips & 3-Way Buttons
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CasinoSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardGlowBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "⚡ TARUHAN PANTAS",
                                    color = CasinoGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "• Baki: RM ${String.format("%.2f", uiState.wallet.balanceRm)}",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Kumpulan: RM ${(currentMatch.totalPoolRm / 1000).toInt()}K",
                                color = BddGreenBright,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Casino Chip Presets Bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Pilih Cip:", color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                quickChips.forEach { chip ->
                                    val isSelected = uiState.currentStakeAmount == chip
                                    Surface(
                                        modifier = Modifier.clickable { viewModel.setStakeAmount(chip) },
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (isSelected) CasinoGold else CasinoSurfaceElevated,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) Color.White else CasinoBorder
                                        )
                                    ) {
                                        Text(
                                            text = "RM ${chip.toInt()}",
                                            color = if (isSelected) Color.Black else Color.White,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Big 3-Way Action Bet Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // MERAH (Meron)
                            Button(
                                onClick = { viewModel.openBetSlip(BetSide.MERON) },
                                modifier = Modifier
                                    .weight(1.1f)
                                    .height(44.dp)
                                    .testTag("live_bet_meron"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MeronRed),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("MERAH (MERON)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                                    Text("ODDS ${currentMatch.meron.odds}", fontSize = 11.sp, fontWeight = FontWeight.Black, color = CasinoGoldBright)
                                }
                            }

                            // SERI (BDD)
                            Button(
                                onClick = { viewModel.openBetSlip(BetSide.BDD) },
                                modifier = Modifier
                                    .weight(0.7f)
                                    .height(44.dp)
                                    .testTag("live_bet_draw"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                                border = androidx.compose.foundation.BorderStroke(1.dp, BddGreen),
                                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("SERI", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                                    Text("${currentMatch.bddOdds}x", fontSize = 11.sp, fontWeight = FontWeight.Black, color = BddGreenBright)
                                }
                            }

                            // BIRU (Wala)
                            Button(
                                onClick = { viewModel.openBetSlip(BetSide.WALA) },
                                modifier = Modifier
                                    .weight(1.1f)
                                    .height(44.dp)
                                    .testTag("live_bet_wala"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072FF)),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("BIRU (WALA)", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.White)
                                    Text("ODDS ${currentMatch.wala.odds}", fontSize = 11.sp, fontWeight = FontWeight.Black, color = CasinoGoldBright)
                                }
                            }
                        }
                    }
                }
            }

            // 5. Tale of the Tape & Fighter Details
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CasinoSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "BUTIRAN AYAM JUARA GELANGGANG",
                            color = CasinoGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )

                        // Meron Fighter Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33FF0844))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("🔴 ${currentMatch.meron.name}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("${currentMatch.meron.breed} • ${currentMatch.meron.breeder}", color = Color.LightGray, fontSize = 9.sp)
                                Text("Taktik: ${currentMatch.meron.fightingStyle}", color = Color(0xFFFFB2C9), fontSize = 9.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("${currentMatch.meron.weightKg} kg", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("${currentMatch.meron.record}", color = BddGreenBright, fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }

                        // Wala Fighter Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x330072FF))
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("🔵 ${currentMatch.wala.name}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("${currentMatch.wala.breed} • ${currentMatch.wala.breeder}", color = Color.LightGray, fontSize = 9.sp)
                                Text("Taktik: ${currentMatch.wala.fightingStyle}", color = Color(0xFFB2EBF2), fontSize = 9.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("${currentMatch.wala.weightKg} kg", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("${currentMatch.wala.record}", color = BddGreenBright, fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }

        // 6. Flashy Sponsor Banner
        item {
            val activeBanner = uiState.sponsorBanners.getOrNull(uiState.currentBannerIndex)
            if (activeBanner != null) {
                FlashySponsorBanner(
                    banner = activeBanner,
                    onClaimClick = { viewModel.showPromoDialog(it) }
                )
            }
        }

        // 7. Other Active Derby Matches in Sabah (Previews)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "PERLAWANAN DERBI SABAH LAIN HARI INI",
                    color = CasinoGold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )

                uiState.matches.filter { it.id != uiState.currentMatchId }.take(2).forEach { m ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectMatch(m.id) },
                        shape = RoundedCornerShape(10.dp),
                        color = CasinoSurfaceElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("FIGHT #${m.matchNumber} • ${m.arena.name}", color = CasinoGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text("${m.meron.name} (${m.meron.odds}) vs ${m.wala.name} (${m.wala.odds})", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Button(
                                onClick = { viewModel.selectMatch(m.id) },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CasinoGold),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text("LIHAT", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }

        // 8. Live Crowd Arena Chat & Community
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CasinoSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SORAKAN GELANGGANG (SABAH)",
                            color = CasinoGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "34,820 Online",
                            color = Color.LightGray,
                            fontSize = 9.sp
                        )
                    }

                    // Chat messages list
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        uiState.liveChatMessages.takeLast(3).forEach { msg ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CasinoSurfaceElevated)
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(msg.badge, fontSize = 9.sp)
                                Text(msg.username, color = CasinoGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text(msg.message, color = Color.White, fontSize = 10.sp, modifier = Modifier.weight(1f))
                            }
                        }
                    }

                    // Chat Input Box
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = chatInput,
                            onValueChange = { chatInput = it },
                            placeholder = { Text("Kirim sorakan gelanggang bah...", fontSize = 10.sp) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CasinoGold,
                                unfocusedBorderColor = CasinoBorder,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            maxLines = 1
                        )

                        IconButton(
                            onClick = {
                                if (chatInput.isNotBlank()) {
                                    viewModel.sendChatMessage(chatInput)
                                    chatInput = ""
                                }
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .background(CasinoGold, RoundedCornerShape(10.dp))
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Hantar", tint = Color.Black, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    }
}
