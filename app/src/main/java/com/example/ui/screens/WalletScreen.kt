package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.model.Transaction
import com.example.model.TransactionType
import com.example.model.VipTier
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel

@Composable
fun WalletScreen(
    viewModel: CockfightViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val wallet = uiState.wallet

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("wallet_screen")
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // High Saturation Casino Gold Card in RM
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("wallet_balance_card"),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GoldMetallicGradient)
                        .border(1.5.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("JUMLAH BAKI DOMPET (SABAH)", color = Color(0xFF3E2300), fontSize = 11.sp, fontWeight = FontWeight.Black)
                                Text(
                                    text = "RM ${String.format("%.2f", wallet.balanceRm)}",
                                    color = Color(0xFF1E0A00),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text("≈ 315 USDT • Pindahan DuitNow & FPX Auto-Kredit", color = Color(0xFF5A3500), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }

                            // VIP Tier Badge
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFF261000)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.WorkspacePremium, contentDescription = "VIP", tint = CasinoGold, modifier = Modifier.size(16.dp))
                                    Text(wallet.vipTier.tierName, color = CasinoGold, fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }

                        Divider(color = Color(0x33000000))

                        // In-play Locked & Points
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Sedang Bertarung", color = Color(0xFF4A2B00), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text("RM ${wallet.lockedInBets.toInt()}", color = Color(0xFF1E0A00), fontSize = 13.sp, fontWeight = FontWeight.Black)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Rebat VIP Sabah", color = Color(0xFF4A2B00), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Text("${wallet.vipTier.rakebackRate}% Tanpa Had", color = Color(0xFF1E0A00), fontSize = 13.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }

        // Action Buttons: Deposit & Withdraw (RM)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.openDepositModal() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("action_deposit_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Deposit", tint = Color.Black)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("TAMBAH RM", color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight.Black)
                }

                Button(
                    onClick = { viewModel.openWithdrawModal() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("action_withdraw_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MeronRed),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Default.ArrowCircleUp, contentDescription = "Keluarkan", tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("KELUAR DUIT", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Black)
                }
            }
        }

        // Security Status Badges
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = CasinoSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Shield, contentDescription = "DuitNow", tint = BddGreenBright, modifier = Modifier.size(16.dp))
                        Text("DuitNow Sah", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Fingerprint, contentDescription = "2FA", tint = NeonCyan, modifier = Modifier.size(16.dp))
                        Text("2FA MyKad", color = Color.LightGray, fontSize = 11.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Bolt, contentDescription = "Pantas", tint = CasinoGold, modifier = Modifier.size(16.dp))
                        Text("< 2m Masuk Bank", color = Color.LightGray, fontSize = 11.sp)
                    }
                }
            }
        }

        // Transaction History Header
        item {
            Text(
                text = "PENYATA TRANSAKSI DUITNOW & BANK (RM)",
                color = CasinoGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }

        // Transaction List
        items(wallet.transactions) { tx ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = CasinoSurface,
                border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = when (tx.type) {
                                TransactionType.DEPOSIT -> Color(0x3300E676)
                                TransactionType.BET_WON -> Color(0x33FFD700)
                                TransactionType.WITHDRAWAL -> Color(0x33FF0844)
                                TransactionType.BET_PLACED -> Color(0x332979FF)
                                else -> Color(0x33B300FF)
                            }
                        ) {
                            Icon(
                                imageVector = when (tx.type) {
                                    TransactionType.DEPOSIT -> Icons.Default.ArrowDownward
                                    TransactionType.BET_WON -> Icons.Default.EmojiEvents
                                    TransactionType.WITHDRAWAL -> Icons.Default.ArrowUpward
                                    TransactionType.BET_PLACED -> Icons.Default.SportsScore
                                    else -> Icons.Default.Stars
                                },
                                contentDescription = null,
                                tint = when (tx.type) {
                                    TransactionType.DEPOSIT -> BddGreenBright
                                    TransactionType.BET_WON -> CasinoGold
                                    TransactionType.WITHDRAWAL -> MeronRedBright
                                    TransactionType.BET_PLACED -> WalaBlueBright
                                    else -> NeonPurple
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(18.dp)
                            )
                        }

                        Column {
                            Text(tx.description, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("ID: ${tx.txHash}", color = Color.Gray, fontSize = 10.sp)
                        }
                    }

                    Text(
                        text = "${if (tx.amount > 0) "+" else ""}RM ${String.format("%.2f", tx.amount)}",
                        color = if (tx.amount > 0) BddGreenBright else Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}
