package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BetSide
import com.example.model.Rooster
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.CockfightViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class CockfightTab(val label: String) {
    LIVE_ARENA("Gelanggang"),
    MATCHES("Derbi Sabah"),
    MY_BETS("Taruhan"),
    WALLET("Dompet RM"),
    PROMOS("VIP Ganjaran")
}

class MainActivity : ComponentActivity() {

    private val viewModel: CockfightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CockfightApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CockfightApp(viewModel: CockfightViewModel) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(CockfightTab.LIVE_ARENA) }
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var isBrowseChickensOpen by remember { mutableStateOf(false) }
    var isCouponsOpen by remember { mutableStateOf(false) }
    var isMarketingProgramOpen by remember { mutableStateOf(false) }
    var isHelpSupportOpen by remember { mutableStateOf(false) }
    var isSettingsOpen by remember { mutableStateOf(false) }
    var isAccountOpen by remember { mutableStateOf(false) }

    if (showSplash) {
        CockfightSplashScreen(onFinished = { showSplash = false })
        return
    }

    // Handle back button: first close drawer if open, else return to Live Arena
    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch { drawerState.close() }
    }

    if (!drawerState.isOpen && currentTab != CockfightTab.LIVE_ARENA) {
        BackHandler {
            currentTab = CockfightTab.LIVE_ARENA
        }
    }

    // Launch snackbar when viewModel posts a message
    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    val currentMatch = uiState.matches.firstOrNull { it.id == uiState.currentMatchId }

    val allRoosters = remember(uiState.matches) {
        uiState.matches.flatMap { listOf(it.meron, it.wala) }.distinctBy { it.name }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            CockfightSideDrawerContent(
                userName = "John Doe (Sabah)",
                userEmail = "johndoe14@gmail.com",
                totalCredit = uiState.wallet.balanceRm,
                onMenuSelect = { destination ->
                    coroutineScope.launch { drawerState.close() }
                    when (destination) {
                        SideMenuDestination.MY_BETTINGS -> currentTab = CockfightTab.MY_BETS
                        SideMenuDestination.COUPONS -> isCouponsOpen = true
                        SideMenuDestination.BROWSE_CHICKENS -> isBrowseChickensOpen = true
                        SideMenuDestination.HISTORY -> currentTab = CockfightTab.MY_BETS
                        SideMenuDestination.HELP -> isHelpSupportOpen = true
                        SideMenuDestination.SETTINGS -> isSettingsOpen = true
                        SideMenuDestination.MARKETING_PROGRAMME -> isMarketingProgramOpen = true
                        SideMenuDestination.ACCOUNT -> isAccountOpen = true
                    }
                },
                onAddCredit = {
                    coroutineScope.launch { drawerState.close() }
                    viewModel.openDepositModal()
                }
            )
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("cockfight_main_scaffold"),
            containerColor = CasinoBackground,
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .padding(bottom = 70.dp)
                        .testTag("cockfight_snackbar_host")
                )
            },
            topBar = {
                CockfightTopBar(
                    balanceRm = uiState.wallet.balanceRm,
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    },
                    onDepositClick = { viewModel.openDepositModal() },
                    onBalanceClick = { currentTab = CockfightTab.WALLET }
                )
            },
            bottomBar = {
                CockfightBottomNav(
                    currentTab = currentTab,
                    activeBetsCount = uiState.activeBets.count { it.status.name == "IN_PLAY" },
                    onTabSelect = { currentTab = it }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    CockfightTab.LIVE_ARENA -> LiveArenaScreen(viewModel = viewModel)
                    CockfightTab.MATCHES -> MatchesScreen(
                        viewModel = viewModel,
                        onNavigateToArena = { currentTab = CockfightTab.LIVE_ARENA }
                    )
                    CockfightTab.MY_BETS -> MyBetsScreen(viewModel = viewModel)
                    CockfightTab.WALLET -> WalletScreen(viewModel = viewModel)
                    CockfightTab.PROMOS -> PromosScreen(viewModel = viewModel)
                }
            }
        }
    }

    // Interactive Bet Slip Sheet
    if (uiState.isBetSlipVisible && uiState.selectedBetSide != null) {
        BettingSlipSheet(
            match = currentMatch,
            side = uiState.selectedBetSide,
            currentStake = uiState.currentStakeAmount,
            walletBalance = uiState.wallet.balanceRm,
            onStakeChange = { viewModel.setStakeAmount(it) },
            onSideChange = { viewModel.openBetSlip(it) },
            onPlaceBet = { viewModel.placeBet() },
            onDismiss = { viewModel.closeBetSlip() }
        )
    }

    // Secure Deposit Dialog (in RM)
    if (uiState.isDepositModalOpen) {
        DepositDialog(
            wallet = uiState.wallet,
            onDismiss = { viewModel.closeDepositModal() },
            onConfirmDeposit = { amt, method -> viewModel.depositFunds(amt, method) }
        )
    }

    // Secure Withdraw Dialog (in RM)
    if (uiState.isWithdrawModalOpen) {
        WithdrawDialog(
            balance = uiState.wallet.balanceRm,
            onDismiss = { viewModel.closeWithdrawModal() },
            onConfirmWithdraw = { amt, addr -> viewModel.withdrawFunds(amt, addr) }
        )
    }

    // Sponsor Promo Claim Dialog
    if (uiState.activePromoDialog != null) {
        PromoClaimDialog(
            banner = uiState.activePromoDialog,
            onDismiss = { viewModel.dismissPromoDialog() },
            onClaim = { viewModel.claimPromoBonus(it) }
        )
    }

    // Side Menu: Browse Chickens Dialog
    if (isBrowseChickensOpen) {
        BrowseChickensDialog(
            roosters = allRoosters,
            onDismiss = { isBrowseChickensOpen = false },
            onSelectRooster = { _ ->
                isBrowseChickensOpen = false
                currentTab = CockfightTab.MATCHES
            }
        )
    }

    // Side Menu: Coupons Dialog
    if (isCouponsOpen) {
        CouponsDialog(
            onDismiss = { isCouponsOpen = false },
            onApplyCoupon = { code ->
                viewModel.showSnackbar("Kupon '$code' diaktifkan untuk taruhan seterusnya!")
                isCouponsOpen = false
            }
        )
    }

    // Side Menu: Marketing Programme Dialog
    if (isMarketingProgramOpen) {
        MarketingProgramDialog(
            onDismiss = { isMarketingProgramOpen = false }
        )
    }

    // Side Menu: Help & Support Dialog
    if (isHelpSupportOpen) {
        HelpSupportDialog(
            onDismiss = { isHelpSupportOpen = false }
        )
    }

    // Side Menu: Settings Dialog
    if (isSettingsOpen) {
        SettingsDialog(
            onDismiss = { isSettingsOpen = false }
        )
    }

    // Side Menu: Account Profile Dialog
    if (isAccountOpen) {
        AccountDialog(
            userName = "John Doe (Sabah)",
            userEmail = "johndoe14@gmail.com",
            onDismiss = { isAccountOpen = false }
        )
    }
}

/**
 * Animated Splash Screen with the user's custom logo.png
 */
@Composable
fun CockfightSplashScreen(
    onFinished: () -> Unit
) {
    var startAnim by remember { mutableStateOf(false) }
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnim) 1f else 0.7f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "logo_scale"
    )
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "logo_alpha"
    )

    LaunchedEffect(Unit) {
        startAnim = true
        delay(1800)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CasinoBackground)
            .clickable { onFinished() }
            .testTag("cockfight_splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo with golden halo glow
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            ) {
                Surface(
                    modifier = Modifier.size(130.dp),
                    shape = CircleShape,
                    color = CasinoSurfaceElevated,
                    border = BorderStroke(3.dp, CasinoGold),
                    shadowElevation = 16.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo Cockfight Sabah",
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.alpha(alphaAnim.value)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "COCKFIGHT",
                        color = CasinoGold,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(shape = RoundedCornerShape(6.dp), color = MeronRed) {
                        Text(
                            text = "SABAH",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "GELANGGANG SABUNG AYAM LANGSUNG 🇲🇾",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Smooth linear loading indicator
            CircularProgressIndicator(
                color = CasinoGold,
                strokeWidth = 3.dp,
                modifier = Modifier.size(28.dp)
            )

            Text(
                text = "Menghubungkan ke Gelanggang Siaran...",
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun CockfightTopBar(
    balanceRm: Double,
    onMenuClick: () -> Unit,
    onDepositClick: () -> Unit,
    onBalanceClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .testTag("cockfight_top_bar"),
        color = CasinoSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Hamburger Menu Button & Custom App Logo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Hamburger Menu Icon
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("side_menu_hamburger_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Buka Menu",
                        tint = CasinoGold,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Custom User Logo.png
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Cockfight",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, CasinoGold, CircleShape)
                )

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "COCKFIGHT",
                            color = CasinoGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Surface(shape = RoundedCornerShape(4.dp), color = MeronRed) {
                            Text(
                                "SABAH",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = "SABUNG AYAM LIVE • MALAYSIA",
                        color = Color.LightGray,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Wallet Balance & Quick Deposit Pill (in RM)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Balance Pill
                Surface(
                    modifier = Modifier
                        .clickable { onBalanceClick() }
                        .testTag("top_bar_balance_pill"),
                    shape = RoundedCornerShape(20.dp),
                    color = CasinoSurfaceElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CasinoGoldDark)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("RM", color = CasinoGold, fontSize = 11.sp, fontWeight = FontWeight.Black)
                        Text(
                            text = String.format("%.2f", balanceRm),
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                // Quick Deposit Button
                Button(
                    onClick = onDepositClick,
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("top_bar_deposit_button"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CasinoGold),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+ TAMBAH",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CockfightBottomNav(
    currentTab: CockfightTab,
    activeBetsCount: Int,
    onTabSelect: (CockfightTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cockfight_bottom_nav"),
        color = CasinoSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CasinoBorder),
        shadowElevation = 16.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            modifier = Modifier.navigationBarsPadding(),
            tonalElevation = 0.dp
        ) {
            CockfightTab.values().forEach { tab ->
                val isSelected = currentTab == tab
                val icon = when (tab) {
                    CockfightTab.LIVE_ARENA -> if (isSelected) Icons.Filled.LiveTv else Icons.Outlined.LiveTv
                    CockfightTab.MATCHES -> if (isSelected) Icons.Filled.SportsScore else Icons.Outlined.SportsScore
                    CockfightTab.MY_BETS -> if (isSelected) Icons.Filled.ConfirmationNumber else Icons.Outlined.ConfirmationNumber
                    CockfightTab.WALLET -> if (isSelected) Icons.Filled.AccountBalanceWallet else Icons.Outlined.AccountBalanceWallet
                    CockfightTab.PROMOS -> if (isSelected) Icons.Filled.LocalFireDepartment else Icons.Outlined.LocalFireDepartment
                }

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelect(tab) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (tab == CockfightTab.MY_BETS && activeBetsCount > 0) {
                                    Badge(containerColor = MeronRed) {
                                        Text("$activeBetsCount", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else if (tab == CockfightTab.LIVE_ARENA) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(MeronRed, CircleShape)
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.label,
                                tint = if (isSelected) CasinoGold else Color.LightGray
                            )
                        }
                    },
                    label = {
                        Text(
                            text = tab.label,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal,
                            color = if (isSelected) CasinoGold else Color.LightGray
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CasinoGold,
                        selectedTextColor = CasinoGold,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray,
                        indicatorColor = CasinoSurfaceElevated
                    )
                )
            }
        }
    }
}
