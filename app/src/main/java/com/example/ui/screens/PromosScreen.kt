package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FlashySponsorBanner
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun PromosScreen(
    viewModel: CockfightViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val wheelRotation = remember { Animatable(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var spinResult by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("promos_screen")
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        // Daily Lucky Rooster Spin Card in RM
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("daily_lucky_spin_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CasinoSurfaceElevated),
                border = androidx.compose.foundation.BorderStroke(2.dp, CardGlowBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("RODA TUAH AYAM SABAH", color = CasinoGold, fontSize = 14.sp, fontWeight = FontWeight.Black)
                                Text("🇲🇾", fontSize = 12.sp)
                            }
                            Text("Putaran percuma setiap 24 jam untuk pemain aktif", color = Color.LightGray, fontSize = 11.sp)
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = MeronRed) {
                            Text("PUTAR PERCUMA", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }

                    // Rotating Casino Lucky Wheel Indicator
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .rotate(wheelRotation.value)
                            .background(GoldMetallicGradient, CircleShape)
                            .border(3.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Roda",
                            tint = Color(0xFF1E0A00),
                            modifier = Modifier.size(54.dp)
                        )
                    }

                    if (spinResult != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0x3300E676),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BddGreen)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Celebration, "Menang", tint = CasinoGold, modifier = Modifier.size(16.dp))
                                Text(spinResult!!, color = BddGreenBright, fontSize = 12.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (!isSpinning) {
                                isSpinning = true
                                scope.launch {
                                    val spins = 360f * 4 + Random.nextInt(0, 360)
                                    wheelRotation.animateTo(
                                        targetValue = wheelRotation.value + spins,
                                        animationSpec = tween(durationMillis = 2000)
                                    )
                                    val rewards = listOf(
                                        "RM 25 Chip Percuma",
                                        "RM 50 Bonus Derbi Tuaran",
                                        "10% Pas Rebat DuitNow",
                                        "RM 100 Tiket VIP Kinabalu",
                                        "Gandaan Odds 2x"
                                    )
                                    val picked = rewards.random()
                                    spinResult = "Tahniah! Anda memenangi: $picked!"
                                    viewModel.depositFunds(25.0, "RODA_TUAH")
                                    isSpinning = false
                                }
                            }
                        },
                        enabled = !isSpinning,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("spin_wheel_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CasinoGold)
                    ) {
                        Text(
                            text = if (isSpinning) "SEDANG MEMUTAR..." else "PUTAR UNTUK REZEKI RM PERCUMA",
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }

        // Sponsor Promotions Header
        item {
            Text(
                text = "PROMOSI TAJAAN & GANJARAN VIP SABAH",
                color = CasinoGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }

        // List of all Flashy Sponsor Banners
        items(uiState.sponsorBanners) { banner ->
            FlashySponsorBanner(
                banner = banner,
                onClaimClick = { viewModel.showPromoDialog(it) }
            )
        }
    }
}
