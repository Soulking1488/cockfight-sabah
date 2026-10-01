package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BettingSlipSheet(
    match: Match?,
    side: BetSide?,
    currentStake: Double,
    walletBalance: Double,
    onStakeChange: (Double) -> Unit,
    onSideChange: (BetSide) -> Unit,
    onPlaceBet: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (match == null || side == null) return

    val odds = when (side) {
        BetSide.MERON -> match.meron.odds
        BetSide.WALA -> match.wala.odds
        BetSide.BDD -> match.bddOdds
    }

    val selectionTitle = when (side) {
        BetSide.MERON -> "MERAH (Sudut Meron)"
        BetSide.WALA -> "BIRU (Sudut Wala)"
        BetSide.BDD -> "SERI (BDD)"
    }

    val fighterName = when (side) {
        BetSide.MERON -> match.meron.name
        BetSide.WALA -> match.wala.name
        BetSide.BDD -> "Keputusan Seri Perlawanan #${match.matchNumber}"
    }

    val potentialProfit = currentStake * odds
    val potentialTotalReturn = currentStake + potentialProfit

    val chipValues = listOf(10.0, 50.0, 100.0, 500.0, 1000.0, 5000.0)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CasinoSurface,
        scrimColor = Color(0x99000000),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier.testTag("betting_slip_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = CasinoGold
                        ) {
                            Text(
                                text = "SLIP TARUHAN",
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = "FIGHT #${match.matchNumber} • ${match.arena.name}",
                            color = Color.LightGray,
                            fontSize = 11.sp
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.White)
                }
            }

            // Side Selector Toggle (Meron, BDD, Wala)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Meron side option
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSideChange(BetSide.MERON) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (side == BetSide.MERON) MeronRed else Color(0x33FF0844),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (side == BetSide.MERON) Color.White else MeronRedDark
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("MERAH", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        Text("${match.meron.odds}", color = CasinoGoldBright, fontSize = 13.sp, fontWeight = FontWeight.Black)
                    }
                }

                // BDD option
                Surface(
                    modifier = Modifier
                        .weight(0.7f)
                        .clickable { onSideChange(BetSide.BDD) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (side == BetSide.BDD) BddGreen else Color(0x3300E676),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (side == BetSide.BDD) Color.White else BddGreen
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("SERI", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        Text("${match.bddOdds}", color = CasinoGoldBright, fontSize = 13.sp, fontWeight = FontWeight.Black)
                    }
                }

                // Wala side option
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSideChange(BetSide.WALA) },
                    shape = RoundedCornerShape(12.dp),
                    color = if (side == BetSide.WALA) Color(0xFF0072FF) else Color(0x330072FF),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (side == BetSide.WALA) Color.White else WalaBlueDark
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("BIRU", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        Text("${match.wala.odds}", color = CasinoGoldBright, fontSize = 13.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            // Selection Summary Box
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CasinoSurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(selectionTitle, color = CasinoGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(fighterName, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("KALI GANDA", color = Color.LightGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "$odds",
                            color = CasinoGoldBright,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Casino Chip Picker Bar (in RM)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("PILIH NILAI CHIP (RM)", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("Baki: RM ${String.format("%.2f", walletBalance)}", color = CasinoGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    chipValues.forEach { chipVal ->
                        val isSelected = currentStake == chipVal
                        val chipColor = when (chipVal) {
                            10.0 -> ChipWhite
                            50.0 -> ChipRed
                            100.0 -> ChipBlue
                            500.0 -> ChipGreen
                            1000.0 -> ChipGold
                            else -> ChipPurple
                        }

                        Surface(
                            modifier = Modifier
                                .size(46.dp)
                                .clickable { onStakeChange(chipVal) },
                            shape = CircleShape,
                            color = if (isSelected) chipColor else Color(0x33FFFFFF),
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                if (isSelected) Color.White else chipColor
                            )
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (chipVal >= 1000) "${(chipVal / 1000).toInt()}K" else "${chipVal.toInt()}",
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }

            // Stake Quick Adjustment Buttons (1/2, 2X, ALL IN)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onStakeChange((currentStake / 2).coerceAtLeast(10.0)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("1/2", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { onStakeChange((currentStake * 2).coerceAtMost(walletBalance)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("2X", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { onStakeChange(walletBalance.coerceAtLeast(10.0)) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CasinoGold)
                ) {
                    Text("SEMUA (ALL IN)", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
            }

            // Calculation Breakdown Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF130429),
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Jumlah Taruhan:", color = Color.LightGray, fontSize = 12.sp)
                        Text("RM ${String.format("%.2f", currentStake)}", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Potensi Untung Bersih:", color = Color.LightGray, fontSize = 12.sp)
                        Text("+RM ${String.format("%.2f", potentialProfit)}", color = BddGreenBright, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Divider(color = Color(0x33FFFFFF), modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("PULANGAN KESELURUHAN:", color = CasinoGold, fontSize = 12.sp, fontWeight = FontWeight.Black)
                        Text(
                            "RM ${String.format("%.2f", potentialTotalReturn)}",
                            color = CasinoGoldBright,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            // Place Bet Button
            Button(
                onClick = onPlaceBet,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("confirm_place_bet_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (side == BetSide.MERON) MeronRed else if (side == BetSide.WALA) Color(0xFF0072FF) else BddGreen
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "SAHKAN TARUHAN RM ${currentStake.toInt()}",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
