package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class SideMenuDestination {
    MY_BETTINGS,
    COUPONS,
    BROWSE_CHICKENS,
    HISTORY,
    HELP,
    SETTINGS,
    MARKETING_PROGRAMME,
    ACCOUNT
}

@Composable
fun CockfightSideDrawerContent(
    userName: String = "John Doe (Sabah)",
    userEmail: String = "johndoe14@gmail.com",
    totalCredit: Double = 1450.0,
    onMenuSelect: (SideMenuDestination) -> Unit,
    onAddCredit: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .width(300.dp)
            .fillMaxHeight()
            .testTag("side_menu_drawer"),
        drawerContainerColor = Color(0xFF000000),
        drawerContentColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // User Profile Header (Sabah, Malaysia localized)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMenuSelect(SideMenuDestination.ACCOUNT) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = userName,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text("🇲🇾", fontSize = 14.sp)
                    }
                    Text(
                        text = userEmail,
                        color = Color(0xFF00E676),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = CasinoSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, CasinoGold),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            // Total Credit Card in RM (Matching the green gradient card from user's image)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAddCredit() }
                    .testTag("side_menu_credit_card"),
                shape = RoundedCornerShape(12.dp),
                color = Color.Transparent
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF00B074), Color(0xFF00C853))
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Total Credit :",
                            color = Color(0xFFE8F5E9),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "RM ${totalCredit.toInt()}",
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Black
                            )

                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Butiran Kredit",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "+Add Credit",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Menu List Items (Matching the user's example image)
            val menuItems = listOf(
                SideMenuDestination.MY_BETTINGS to "My Bettings",
                SideMenuDestination.COUPONS to "Coupons",
                SideMenuDestination.BROWSE_CHICKENS to "Browse Chickens",
                SideMenuDestination.HISTORY to "History",
                SideMenuDestination.HELP to "Help",
                SideMenuDestination.SETTINGS to "Settings",
                SideMenuDestination.MARKETING_PROGRAMME to "Marketing Programme",
                SideMenuDestination.ACCOUNT to "Account"
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                menuItems.forEach { (dest, label) ->
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onMenuSelect(dest) }
                            .padding(vertical = 4.dp)
                            .testTag("menu_item_${dest.name.lowercase()}")
                    )
                }
            }
        }
    }
}
