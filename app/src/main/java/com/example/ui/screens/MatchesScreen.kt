package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BetSide
import com.example.model.Match
import com.example.model.MatchStatus
import com.example.ui.components.FlashySponsorBanner
import com.example.ui.components.MatchCard
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel

@Composable
fun MatchesScreen(
    viewModel: CockfightViewModel,
    onNavigateToArena: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredMatches = uiState.matches.filter { match ->
        when (selectedFilter) {
            "LIVE" -> match.status == MatchStatus.IN_PROGRESS || match.status == MatchStatus.LAST_CALL
            "OPEN" -> match.status == MatchStatus.BETTING_OPEN
            "ENDED" -> match.status == MatchStatus.FIGHT_ENDED
            else -> true
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("matches_screen")
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // Derby Schedule Header Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CasinoSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("PIALA EMAS SABAH 2026", color = CasinoGold, fontSize = 13.sp, fontWeight = FontWeight.Black)
                            Text("🇲🇾", fontSize = 12.sp)
                        }
                        Text("Jadual Penuh Pertarungan Derbi Seluruh Sabah", color = Color.LightGray, fontSize = 11.sp)
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MeronRedDark
                    ) {
                        Text(
                            text = "HARI KE-3",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Filter Pills Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to "Semua (${uiState.matches.size})",
                    "LIVE" to "🔴 Langsung",
                    "OPEN" to "💰 Taruhan",
                    "ENDED" to "📋 Keputusan"
                ).forEach { (key, label) ->
                    val isSel = selectedFilter == key
                    Surface(
                        modifier = Modifier
                            .clickable { selectedFilter = key }
                            .testTag("filter_$key"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSel) CasinoGold else CasinoSurface,
                        border = if (isSel) null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                    ) {
                        Text(
                            text = label,
                            color = if (isSel) Color.Black else Color.White,
                            fontSize = 11.sp,
                            fontWeight = if (isSel) FontWeight.Black else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Match Cards
        items(filteredMatches) { match ->
            val isCurrent = match.id == uiState.currentMatchId
            MatchCard(
                match = match,
                isSelected = isCurrent,
                onSelectMatch = {
                    viewModel.selectMatch(it)
                    onNavigateToArena()
                },
                onQuickBet = { targetMatch, side ->
                    viewModel.selectMatch(targetMatch.id)
                    viewModel.openBetSlip(side)
                }
            )
        }

        // Sponsor Banner in matches feed
        item {
            val sponsor = uiState.sponsorBanners.firstOrNull()
            if (sponsor != null) {
                FlashySponsorBanner(
                    banner = sponsor,
                    onClaimClick = { viewModel.showPromoDialog(it) }
                )
            }
        }
    }
}
