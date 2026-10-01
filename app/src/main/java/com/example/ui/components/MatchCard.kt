package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun MatchCard(
    match: Match,
    isSelected: Boolean,
    onSelectMatch: (String) -> Unit,
    onQuickBet: (Match, BetSide) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelectMatch(match.id) }
            .testTag("match_card_${match.matchNumber}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CasinoSurface),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(1.5.dp, CardGlowBorder)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Match Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = CasinoGold
                    ) {
                        Text(
                            text = "FIGHT #${match.matchNumber}",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "${match.arena.name} • ${match.derbyName}",
                        color = Color.LightGray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Match Status Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when (match.status) {
                        MatchStatus.BETTING_OPEN -> Color(0x3300E676)
                        MatchStatus.LAST_CALL -> Color(0x33FF0844)
                        MatchStatus.IN_PROGRESS -> Color(0x33FFD700)
                        MatchStatus.FIGHT_ENDED -> Color(0x33888888)
                        else -> Color(0x33888888)
                    },
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        when (match.status) {
                            MatchStatus.BETTING_OPEN -> BddGreen
                            MatchStatus.LAST_CALL -> MeronRedBright
                            MatchStatus.IN_PROGRESS -> CasinoGold
                            else -> Color.Gray
                        }
                    )
                ) {
                    Text(
                        text = when (match.status) {
                            MatchStatus.BETTING_OPEN -> "TARUHAN (${match.bettingSecondsLeft}s)"
                            MatchStatus.LAST_CALL -> "🚨 PANGGILAN AKHIR"
                            MatchStatus.IN_PROGRESS -> "⚔️ SEDANG BERTARUNG"
                            MatchStatus.FIGHT_ENDED -> "SELESAI"
                            else -> "JADUAL"
                        },
                        color = when (match.status) {
                            MatchStatus.BETTING_OPEN -> BddGreenBright
                            MatchStatus.LAST_CALL -> MeronRedBright
                            MatchStatus.IN_PROGRESS -> CasinoGold
                            else -> Color.LightGray
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Roosters Comparison (Merah Meron vs Biru Wala)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Meron (Red) Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x33FF0844))
                        .border(1.dp, MeronRedDark, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MERAH",
                            color = MeronRedBright,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${match.meron.odds}",
                                color = CasinoGoldBright,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                            if (match.meron.oddsTrend == OddsTrend.UP) {
                                Icon(Icons.Default.ArrowUpward, "Odds Naik", tint = BddGreen, modifier = Modifier.size(12.dp))
                            } else if (match.meron.oddsTrend == OddsTrend.DOWN) {
                                Icon(Icons.Default.ArrowDownward, "Odds Turun", tint = MeronRedBright, modifier = Modifier.size(12.dp))
                            }
                        }
                    }

                    Text(
                        text = match.meron.name,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    Text(
                        text = "${match.meron.breeder} (${match.meron.farmLocation})",
                        color = Color.LightGray,
                        fontSize = 10.sp,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${match.meron.weightKg} kg • ${match.meron.record}",
                        color = Color(0xFFFFB2C9),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )

                    // Form dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        match.meron.recentForm.takeLast(5).forEach { isWin ->
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(if (isWin) BddGreen else MeronRedDark, CircleShape)
                            )
                        }
                    }
                }

                // Center VS Element
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "VS",
                        color = CasinoGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "SERI\n${match.bddOdds}",
                        color = BddGreenBright,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Wala (Blue) Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x330072FF))
                        .border(1.dp, WalaBlueDark, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (match.wala.oddsTrend == OddsTrend.UP) {
                                Icon(Icons.Default.ArrowUpward, "Odds Naik", tint = BddGreen, modifier = Modifier.size(12.dp))
                            } else if (match.wala.oddsTrend == OddsTrend.DOWN) {
                                Icon(Icons.Default.ArrowDownward, "Odds Turun", tint = MeronRedBright, modifier = Modifier.size(12.dp))
                            }
                            Text(
                                text = "${match.wala.odds}",
                                color = CasinoGoldBright,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Text(
                            text = "BIRU",
                            color = WalaBlueBright,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Text(
                        text = match.wala.name,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )

                    Text(
                        text = "${match.wala.breeder} (${match.wala.farmLocation})",
                        color = Color.LightGray,
                        fontSize = 10.sp,
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${match.wala.weightKg} kg • ${match.wala.record}",
                        color = Color(0xFFB2EBF2),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End
                    )

                    // Form dots
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        match.wala.recentForm.takeLast(5).forEach { isWin ->
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(if (isWin) BddGreen else MeronRedDark, CircleShape)
                            )
                        }
                    }
                }
            }

            // Quick Betting Buttons Bar with RM Stakes
            if (match.status == MatchStatus.BETTING_OPEN) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Bet Meron
                    Button(
                        onClick = { onQuickBet(match, BetSide.MERON) },
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .testTag("bet_meron_${match.matchNumber}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MeronRed),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "TARUH MERAH ${match.meron.odds}",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    // Bet Draw
                    Button(
                        onClick = { onQuickBet(match, BetSide.BDD) },
                        modifier = Modifier
                            .weight(0.7f)
                            .height(38.dp)
                            .testTag("bet_draw_${match.matchNumber}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BddGreen),
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        Text(
                            text = "SERI ${match.bddOdds}",
                            color = BddGreenBright,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    // Bet Wala
                    Button(
                        onClick = { onQuickBet(match, BetSide.WALA) },
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .testTag("bet_wala_${match.matchNumber}"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0072FF)),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "TARUH BIRU ${match.wala.odds}",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            } else if (match.status == MatchStatus.FIGHT_ENDED) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x33FFFFFF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "PEMENANG: ${match.winner ?: BetSide.MERON} (Ulangan Tersedia)",
                        color = CasinoGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}
