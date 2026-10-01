package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.model.BetSide
import com.example.model.BetStatus
import com.example.model.BetTicket
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel

@Composable
fun MyBetsScreen(
    viewModel: CockfightViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf("ACTIVE") }

    val activeBets = uiState.activeBets.filter { it.status == BetStatus.IN_PLAY || it.status == BetStatus.PENDING }
    val settledBets = uiState.activeBets.filter { it.status == BetStatus.WON || it.status == BetStatus.LOST || it.status == BetStatus.CASHED_OUT }

    val totalStaked = uiState.activeBets.sumOf { it.stakeAmount }
    val totalWon = uiState.activeBets.filter { it.status == BetStatus.WON }.sumOf { it.payoutAmount }
    val netProfit = totalWon - totalStaked

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_bets_screen")
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // Bets Summary Stats Card
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CasinoSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "RINGKASAN PRESTASI TARUHAN (RM)",
                        color = CasinoGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Jumlah Ditaruh", color = Color.LightGray, fontSize = 11.sp)
                            Text("RM ${String.format("%.2f", totalStaked)}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Column {
                            Text("Jumlah Pulangan", color = Color.LightGray, fontSize = 11.sp)
                            Text("RM ${String.format("%.2f", totalWon)}", color = BddGreenBright, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("Untung / Rugi Bersih", color = Color.LightGray, fontSize = 11.sp)
                            Text(
                                text = "${if (netProfit >= 0) "+" else ""}RM ${String.format("%.2f", netProfit)}",
                                color = if (netProfit >= 0) BddGreenBright else MeronRedBright,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }

        // Active vs Settled Tabs
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTab = "ACTIVE" },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedTab == "ACTIVE") CasinoGold else CasinoSurface,
                    border = if (selectedTab == "ACTIVE") null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                ) {
                    Text(
                        text = "Sedang Bertarung (${activeBets.size})",
                        color = if (selectedTab == "ACTIVE") Color.Black else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTab = "SETTLED" },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selectedTab == "SETTLED") CasinoGold else CasinoSurface,
                    border = if (selectedTab == "SETTLED") null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                ) {
                    Text(
                        text = "Rekod Selesai (${settledBets.size})",
                        color = if (selectedTab == "SETTLED") Color.Black else Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }

        // Bet Tickets List
        val currentList = if (selectedTab == "ACTIVE") activeBets else settledBets

        if (currentList.isEmpty()) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CasinoSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Tiada rekod taruhan dalam bahagian ini", color = Color.LightGray, fontSize = 12.sp)
                    }
                }
            }
        } else {
            items(currentList) { ticket ->
                BetTicketCard(
                    ticket = ticket,
                    onCashout = { viewModel.cashOutBet(ticket.ticketId) }
                )
            }
        }
    }
}

@Composable
fun BetTicketCard(
    ticket: BetTicket,
    onCashout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("ticket_${ticket.ticketId}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CasinoSurface),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            when (ticket.status) {
                BetStatus.WON -> BddGreen
                BetStatus.LOST -> MeronRedDark
                BetStatus.CASHED_OUT -> CasinoGold
                else -> CasinoBorder
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Ticket Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(ticket.ticketId, color = CasinoGold, fontSize = 11.sp, fontWeight = FontWeight.Black)
                    Text("•", color = Color.Gray, fontSize = 10.sp)
                    Text("FIGHT #${ticket.matchNumber}", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (ticket.status) {
                        BetStatus.IN_PLAY -> Color(0x3300E676)
                        BetStatus.WON -> BddGreen
                        BetStatus.LOST -> MeronRedDark
                        BetStatus.CASHED_OUT -> CasinoGold
                        else -> Color.DarkGray
                    }
                ) {
                    Text(
                        text = when (ticket.status) {
                            BetStatus.IN_PLAY -> "SEDANG BERTARUNG"
                            BetStatus.WON -> "MENANG"
                            BetStatus.LOST -> "KALAH"
                            BetStatus.CASHED_OUT -> "DITEBUS AWAL"
                            else -> "MENUNGGU"
                        },
                        color = if (ticket.status == BetStatus.WON || ticket.status == BetStatus.CASHED_OUT) Color.Black else Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Selection details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = ticket.selectionName,
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = ticket.arenaName,
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("ODDS: ${ticket.odds}", color = CasinoGoldBright, fontSize = 12.sp, fontWeight = FontWeight.Black)
                    Text("Taruhan: RM ${ticket.stakeAmount.toInt()}", color = Color.LightGray, fontSize = 11.sp)
                }
            }

            Divider(color = Color(0x22FFFFFF))

            // Footer / Payout / Cashout Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (ticket.status == BetStatus.WON) "JUMLAH DIMENANGI" else "POTENSI PULANGAN",
                        color = Color.LightGray,
                        fontSize = 9.sp
                    )
                    Text(
                        text = "RM ${String.format("%.2f", if (ticket.status == BetStatus.WON) ticket.payoutAmount else ticket.potentialPayout)}",
                        color = if (ticket.status == BetStatus.WON) BddGreenBright else CasinoGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                if (ticket.status == BetStatus.IN_PLAY && ticket.canCashOut) {
                    Button(
                        onClick = onCashout,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CasinoGold),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = "TEBUS RM ${String.format("%.2f", ticket.currentCashoutValue)}",
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }
    }
}
