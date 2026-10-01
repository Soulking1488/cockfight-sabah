package com.example.ui.components

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
import androidx.compose.ui.window.Dialog
import com.example.model.Rooster
import com.example.ui.theme.*

@Composable
fun BrowseChickensDialog(
    roosters: List<Rooster>,
    onDismiss: () -> Unit,
    onSelectRooster: (Rooster) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedBreedFilter by remember { mutableStateOf("ALL") }

    val filteredRoosters = if (selectedBreedFilter == "ALL") {
        roosters
    } else {
        roosters.filter { it.breed.contains(selectedBreedFilter, ignoreCase = true) }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .testTag("browse_chickens_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CardGlowBorder),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "BROWSE CHICKENS (AYAM SABUNG SABAH)",
                            color = CasinoGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Koleksi Baka Juara Taji Borneo & Sabah",
                            color = Color.LightGray,
                            fontSize = 11.sp
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                // Breed Filter Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("ALL", "Sweater", "Kelso", "Hatch", "Biring").forEach { breed ->
                        val isSel = selectedBreedFilter == breed
                        Surface(
                            modifier = Modifier.clickable { selectedBreedFilter = breed },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSel) CasinoGold else CasinoSurfaceElevated,
                            border = if (isSel) null else androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder)
                        ) {
                            Text(
                                text = breed,
                                color = if (isSel) Color.Black else Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Roosters List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredRoosters) { bird ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectRooster(bird) }
                                .testTag("chicken_item_${bird.id}"),
                            shape = RoundedCornerShape(12.dp),
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(bird.avatarColor),
                                        modifier = Modifier.size(38.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("🐓", fontSize = 18.sp)
                                        }
                                    }

                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = bird.name,
                                            color = Color.White,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                        Text(
                                            text = "${bird.breed} • ${bird.breeder}",
                                            color = Color.LightGray,
                                            fontSize = 10.sp
                                        )
                                        Text(
                                            text = "Tempat: ${bird.farmLocation}",
                                            color = CasinoGoldBright,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0x3300E676)
                                    ) {
                                        Text(
                                            text = bird.record,
                                            color = BddGreenBright,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${bird.weightKg} kg",
                                        color = Color.LightGray,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class BetCoupon(
    val code: String,
    val title: String,
    val discount: String,
    val expiry: String,
    val isApplied: Boolean = false
)

@Composable
fun CouponsDialog(
    onDismiss: () -> Unit,
    onApplyCoupon: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var appliedCode by remember { mutableStateOf<String?>(null) }
    val coupons = remember {
        listOf(
            BetCoupon("SABAH50", "RM 50 Cip Taruhan Percuma", "100% Taruhan Tanpa Risiko", "Sah 7 Hari"),
            BetCoupon("SLASHER100", "100% Padanan Deposit DuitNow", "Sehingga RM 500 Bonus", "Sah 14 Hari"),
            BetCoupon("CASHBACK10", "10% Pas Rebat Harian Sabah", "Rebat DuitNow Automatik", "Tiada Tamat Tempoh"),
            BetCoupon("PIALAEMAS", "Tiket VIP Piala Emas Sabah", "Gandaan Kemenangan 2x", "Derbi Pusingan 42-50")
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("coupons_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CardGlowBorder),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("KUPON & BAUCAR SAYA", color = CasinoGold, fontSize = 15.sp, fontWeight = FontWeight.Black)
                        Text("Gunakan baucar untuk pertaruhan gelanggang", color = Color.LightGray, fontSize = 11.sp)
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                coupons.forEach { coupon ->
                    val isUsed = appliedCode == coupon.code
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = CasinoSurfaceElevated,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isUsed) BddGreen else CasinoBorder
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(shape = RoundedCornerShape(4.dp), color = MeronRed) {
                                        Text(coupon.code, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                    Text(coupon.expiry, color = Color.Gray, fontSize = 9.sp)
                                }
                                Text(coupon.title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(coupon.discount, color = CasinoGoldBright, fontSize = 10.sp)
                            }

                            Button(
                                onClick = {
                                    appliedCode = coupon.code
                                    onApplyCoupon(coupon.code)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isUsed) BddGreen else CasinoGold
                                ),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isUsed) "AKTIF" else "GUNA",
                                    color = Color.Black,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MarketingProgramDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var copied by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("marketing_programme_dialog"),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("PROGRAM PEMASARAN SABAH", color = CasinoGold, fontSize = 15.sp, fontWeight = FontWeight.Black)
                        Text("Kongsi link & raih 35% komisyen DuitNow sepanjang hayat", color = Color.LightGray, fontSize = 11.sp)
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                // Stats Dashboard Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        color = CasinoSurfaceElevated
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Pemain Aktif Dijemput", color = Color.LightGray, fontSize = 10.sp)
                            Text("24 Pemain", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Black)
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        color = CasinoSurfaceElevated
                    ) {
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Jumlah Komisyen", color = Color.LightGray, fontSize = 10.sp)
                            Text("RM 1,280", color = BddGreenBright, fontSize = 14.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                // Referral Link Box
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF100324),
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
                            Text("Link Rujukan Eksklusif Anda:", color = CasinoGold, fontSize = 10.sp)
                            Text("cockfight.my/sabah/johndoe14", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { copied = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CasinoGold),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(if (copied) "DISALIN!" else "SALIN", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                // Tier Benefit details
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("• 35% bahagian keuntungan dari setiap pertaruhan rakan anda", color = Color.LightGray, fontSize = 11.sp)
                    Text("• Bayaran mingguan automatik terus ke akaun DuitNow anda (RM)", color = Color.LightGray, fontSize = 11.sp)
                    Text("• Pengurus akaun affiliate VIP khas di Kota Kinabalu", color = Color.LightGray, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun HelpSupportDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("help_support_dialog"),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("BANTUAN & PERATURAN GELANGGANG", color = CasinoGold, fontSize = 14.sp, fontWeight = FontWeight.Black)
                        Text("Panduan Sabung Ayam Sabah & Khidmat Pelanggan", color = Color.LightGray, fontSize = 11.sp)
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("1. Peraturan Merah (Meron) & Biru (Wala)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Merah menandakan ayam pilihan gelanggang (tuan rumah/juara), Biru adalah pencabar. Keputusan disahkan oleh pengadil rasmi gelanggang.", color = Color.LightGray, fontSize = 11.sp)

                    Text("2. Seri (BDD - Both Die Draw)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Jika kedua-dua ayam tidak dapat meneruskan pertarungan serentak, taruhan SERI membayar 8.00x nilai pertaruhan.", color = Color.LightGray, fontSize = 11.sp)

                    Text("3. Tebus Awal (Cashout Segera)", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Boleh tebus nilai kemenangan awal dalam RM sebelum pertarungan tamat.", color = Color.LightGray, fontSize = 11.sp)
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold)
                ) {
                    Icon(Icons.Default.Headphones, contentDescription = "Bantuan", tint = Color.Black)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SOKONGAN LIVE CHAT 24/7 (SABAH)", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currencyChoice by remember { mutableStateOf("Ringgit Malaysia (RM)") }
    var soundEnabled by remember { mutableStateOf(true) }
    var hapticsEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("settings_dialog"),
            shape = RoundedCornerShape(20.dp),
            color = CasinoSurface,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CasinoBorder),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("TETAPAN PLATFORM", color = CasinoGold, fontSize = 15.sp, fontWeight = FontWeight.Black)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Mata Wang Paparan", color = Color.White, fontSize = 12.sp)
                        Text(currencyChoice, color = CasinoGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bunyi Sorakan Gelanggang", color = Color.White, fontSize = 12.sp)
                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = { soundEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CasinoGold)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Getaran Haptik Chip", color = Color.White, fontSize = 12.sp)
                        Switch(
                            checked = hapticsEnabled,
                            onCheckedChange = { hapticsEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CasinoGold)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Makluman Panggilan Terakhir", color = Color.White, fontSize = 12.sp)
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CasinoGold)
                        )
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold)
                ) {
                    Text("SIMPAN TETAPAN", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun AccountDialog(
    userName: String = "John Doe (Sabah)",
    userEmail: String = "johndoe14@gmail.com",
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .testTag("account_dialog"),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("PROFIL AKAUN (SABAH)", color = CasinoGold, fontSize = 15.sp, fontWeight = FontWeight.Black)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Tutup", tint = Color.LightGray)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = CasinoGold,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("JD", color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Black)
                        }
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(userName, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Black)
                            Text("🇲🇾", fontSize = 12.sp)
                        }
                        Text(userEmail, color = Color(0xFF00E676), fontSize = 12.sp)
                        Text("Ahli VIP Gold Sabah • ID #SBH-89241", color = Color.LightGray, fontSize = 10.sp)
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CasinoSurfaceElevated,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Status Pengesahan KYC", color = Color.LightGray, fontSize = 11.sp)
                            Text("Tahap 2 Disahkan (MyKad) ✅", color = BddGreenBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Saluran Pembayaran", color = Color.LightGray, fontSize = 11.sp)
                            Text("DuitNow & FPX Aktif", color = NeonCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Perlindungan Dompet", color = Color.LightGray, fontSize = 11.sp)
                            Text("Simpanan Sejuk Multi-Sig 🔒", color = CasinoGoldBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MeronRedDark)
                ) {
                    Text("TUKAR DOMPET / LOG KELUAR", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
