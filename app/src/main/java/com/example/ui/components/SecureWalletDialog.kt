package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.SponsorBanner
import com.example.model.WalletState
import com.example.ui.theme.*

@Composable
fun DepositDialog(
    wallet: WalletState,
    onDismiss: () -> Unit,
    onConfirmDeposit: (Double, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMethod by remember { mutableStateOf("DuitNow QR") }
    var amountText by remember { mutableStateOf("100") }
    val quickAmounts = listOf(50.0, 100.0, 250.0, 500.0, 1000.0)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("deposit_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CardGlowBorder),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = CasinoGold
                        ) {
                            Icon(
                                Icons.Default.AccountBalanceWallet,
                                contentDescription = "Deposit",
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(20.dp)
                            )
                        }
                        Text(
                            "TAMBAH KREDIT (RM)",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                // Security Banner
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x3300E676),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BddGreen)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = "Keselamatan", tint = BddGreenBright, modifier = Modifier.size(14.dp))
                        Text(
                            "DuitNow & FPX Auto-Kredit • Saluran Selamat 256-Bit",
                            color = BddGreenBright,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Payment Method Selector
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Kaedah Pembayaran Tempatan:", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("DuitNow QR", "FPX Bank", "Touch 'n Go", "Sabah Pay").forEach { method ->
                            val isSel = method == selectedMethod
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedMethod = method },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) CasinoGold else CasinoSurfaceElevated,
                                border = if (isSel) null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                            ) {
                                Text(
                                    text = method,
                                    color = if (isSel) Color.Black else Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // Deposit Info Box (DuitNow QR Info)
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF100324),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Maklumat Saluran $selectedMethod:", color = CasinoGold, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (selectedMethod == "DuitNow QR") "ID: ${wallet.duitNowId}" else "Pindahan Segera Semua Bank Malaysia (FPX)",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Salin",
                                tint = CasinoGold,
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable { }
                            )
                        }
                    }
                }

                // Quick Amount Selection (in RM)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Pilih Jumlah Deposit (RM):", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        quickAmounts.forEach { amt ->
                            val isSel = amountText == amt.toInt().toString()
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { amountText = amt.toInt().toString() },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) MeronRed else CasinoSurfaceElevated,
                                border = if (isSel) null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                            ) {
                                Text(
                                    text = "RM ${amt.toInt()}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                // Confirm Deposit Button
                Button(
                    onClick = {
                        val parsed = amountText.toDoubleOrNull() ?: 50.0
                        onConfirmDeposit(parsed, selectedMethod)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_deposit_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold)
                ) {
                    Text(
                        "DEPOSIT SEGERA RM $amountText",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun WithdrawDialog(
    balance: Double,
    onDismiss: () -> Unit,
    onConfirmWithdraw: (Double, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var bankAccount by remember { mutableStateOf("Maybank - 5142 8921 3491 (Azman)") }
    var withdrawAmount by remember { mutableStateOf("100") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("withdraw_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, MeronRedDark),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MeronRed
                        ) {
                            Icon(
                                Icons.Default.CurrencyExchange,
                                contentDescription = "Keluarkan Duit",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(20.dp)
                            )
                        }
                        Text(
                            "PENGELUARAN TUNAI (RM)",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                // Balance Available
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CasinoSurfaceElevated,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Baki Boleh Dikeluarkan:", color = Color.LightGray, fontSize = 12.sp)
                        Text("RM ${String.format("%.2f", balance)}", color = CasinoGoldBright, fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                }

                // Bank Account / DuitNow details
                OutlinedTextField(
                    value = bankAccount,
                    onValueChange = { bankAccount = it },
                    label = { Text("Akaun Bank / DuitNow (Maybank, CIMB, dsb.)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CasinoGold,
                        unfocusedBorderColor = CasinoBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 1
                )

                // Amount Input with Max button
                OutlinedTextField(
                    value = withdrawAmount,
                    onValueChange = { withdrawAmount = it },
                    label = { Text("Jumlah Pengeluaran (RM)") },
                    trailingIcon = {
                        TextButton(onClick = { withdrawAmount = balance.toInt().toString() }) {
                            Text("MAKSIMUM", color = CasinoGold, fontWeight = FontWeight.Black)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CasinoGold,
                        unfocusedBorderColor = CasinoBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 1
                )

                // Speed Guarantee
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x33FF0844)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Security, contentDescription = "Pantas", tint = MeronRedBright, modifier = Modifier.size(14.dp))
                        Text(
                            "Pengeluaran Automatik DuitNow: < 2 Minit ke Akaun",
                            color = Color(0xFFFFB2C9),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Confirm Withdrawal Button
                Button(
                    onClick = {
                        val parsed = withdrawAmount.toDoubleOrNull() ?: 50.0
                        onConfirmWithdraw(parsed, bankAccount)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_withdraw_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MeronRed)
                ) {
                    Text(
                        "HANTAR PENGELUARAN RM $withdrawAmount",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun PromoClaimDialog(
    banner: SponsorBanner?,
    onDismiss: () -> Unit,
    onClaim: (SponsorBanner) -> Unit,
    modifier: Modifier = Modifier
) {
    if (banner == null) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("promo_claim_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(2.dp, CardGlowBorder),
            shadowElevation = 20.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(banner.iconEmoji, fontSize = 42.sp)

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MeronRedDark
                ) {
                    Text(
                        text = banner.badgeText,
                        color = CasinoGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = banner.headline,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = banner.subline,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CasinoSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CasinoGold)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("KOD PROMOSI:", color = Color.LightGray, fontSize = 11.sp)
                        Text(banner.bonusCode, color = CasinoGoldBright, fontSize = 14.sp, fontWeight = FontWeight.Black)
                    }
                }

                Button(
                    onClick = { onClaim(banner) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold)
                ) {
                    Text(
                        "TEBUS ${banner.claimAmount} SEKARANG",
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}
