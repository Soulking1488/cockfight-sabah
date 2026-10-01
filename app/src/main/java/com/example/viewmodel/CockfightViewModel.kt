package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CockfightRepository
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class CockfightUiState(
    val arenas: List<Arena> = emptyList(),
    val selectedArenaId: String = "arena_tuaran",
    val matches: List<Match> = emptyList(),
    val currentMatchId: String = "match_42",
    val selectedCameraAngle: CameraAngle = CameraAngle.YOUTUBE_LIVE,
    val isStreamMuted: Boolean = false,
    val isStreamPlaying: Boolean = true,
    val isLowLatencyMode: Boolean = true,
    val sponsorBanners: List<SponsorBanner> = emptyList(),
    val currentBannerIndex: Int = 0,
    val eventNotifications: List<EventNotification> = emptyList(),
    val activeEventIndex: Int = 0,
    val liveChatMessages: List<LiveChatMessage> = emptyList(),
    val liveReactions: List<LiveReaction> = emptyList(),
    val activeBets: List<BetTicket> = emptyList(),
    val wallet: WalletState = WalletState(),
    val selectedBetSide: BetSide? = null,
    val currentStakeAmount: Double = 100.0,
    val isBetSlipVisible: Boolean = false,
    val isDepositModalOpen: Boolean = false,
    val isWithdrawModalOpen: Boolean = false,
    val activePromoDialog: SponsorBanner? = null,
    val snackbarMessage: String? = null
)

class CockfightViewModel(
    private val repository: CockfightRepository = CockfightRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CockfightUiState())
    val uiState: StateFlow<CockfightUiState> = _uiState.asStateFlow()

    init {
        val initialArenas = repository.getArenas()
        val initialMatches = repository.getInitialMatches(initialArenas)
        val initialBanners = repository.getSponsorBanners()
        val initialEvents = repository.getInitialEventNotifications()
        val initialBets = repository.getInitialBetTickets()
        val initialTx = repository.getInitialTransactions()
        val initialChat = repository.getInitialChatMessages()

        _uiState.update {
            it.copy(
                arenas = initialArenas,
                matches = initialMatches,
                sponsorBanners = initialBanners,
                eventNotifications = initialEvents,
                activeBets = initialBets,
                wallet = it.wallet.copy(transactions = initialTx),
                liveChatMessages = initialChat
            )
        }

        startEventTickerLoop()
        startLiveSimulationLoop()
    }

    private fun startEventTickerLoop() {
        viewModelScope.launch {
            while (true) {
                delay(4000)
                _uiState.update { state ->
                    val nextEventIdx = if (state.eventNotifications.isNotEmpty()) {
                        (state.activeEventIndex + 1) % state.eventNotifications.size
                    } else 0
                    val nextBannerIdx = if (state.sponsorBanners.isNotEmpty()) {
                        (state.currentBannerIndex + 1) % state.sponsorBanners.size
                    } else 0
                    state.copy(
                        activeEventIndex = nextEventIdx,
                        currentBannerIndex = nextBannerIdx
                    )
                }
            }
        }
    }

    private fun startLiveSimulationLoop() {
        viewModelScope.launch {
            while (true) {
                delay(2000)
                _uiState.update { state ->
                    val updatedMatches = state.matches.map { match ->
                        if (match.id == state.currentMatchId) {
                            if (match.status == MatchStatus.BETTING_OPEN) {
                                val newSec = match.bettingSecondsLeft - 2
                                if (newSec <= 0) {
                                    match.copy(
                                        status = MatchStatus.IN_PROGRESS,
                                        bettingSecondsLeft = 0,
                                        fightDurationSeconds = 1
                                    )
                                } else {
                                    val delta = (Random.nextDouble(-0.02, 0.02) * 100).toInt() / 100.0
                                    val newMeronOdds = (match.meron.odds + delta).coerceIn(0.80, 1.20)
                                    val newWalaOdds = (match.wala.odds - delta).coerceIn(1.60, 2.30)
                                    val roundedMeron = String.format("%.2f", newMeronOdds).toDouble()
                                    val roundedWala = String.format("%.2f", newWalaOdds).toDouble()
                                    match.copy(
                                        bettingSecondsLeft = newSec,
                                        meron = match.meron.copy(
                                            odds = roundedMeron,
                                            oddsTrend = if (delta > 0) OddsTrend.UP else if (delta < 0) OddsTrend.DOWN else OddsTrend.STABLE
                                        ),
                                        wala = match.wala.copy(
                                            odds = roundedWala,
                                            oddsTrend = if (delta < 0) OddsTrend.UP else if (delta > 0) OddsTrend.DOWN else OddsTrend.STABLE
                                        )
                                    )
                                }
                            } else if (match.status == MatchStatus.IN_PROGRESS) {
                                val nextFightSec = match.fightDurationSeconds + 2
                                match.copy(fightDurationSeconds = nextFightSec)
                            } else {
                                match
                            }
                        } else {
                            match
                        }
                    }

                    val updatedReactions = state.liveReactions.takeLast(6)

                    state.copy(
                        matches = updatedMatches,
                        liveReactions = updatedReactions
                    )
                }
            }
        }
    }

    fun selectArena(arenaId: String) {
        _uiState.update { state ->
            val matchInArena = state.matches.firstOrNull { it.arena.id == arenaId }
            state.copy(
                selectedArenaId = arenaId,
                currentMatchId = matchInArena?.id ?: state.currentMatchId
            )
        }
    }

    fun selectMatch(matchId: String) {
        _uiState.update { state ->
            val match = state.matches.firstOrNull { it.id == matchId }
            state.copy(
                currentMatchId = matchId,
                selectedArenaId = match?.arena?.id ?: state.selectedArenaId
            )
        }
    }

    fun setCameraAngle(angle: CameraAngle) {
        _uiState.update { it.copy(selectedCameraAngle = angle) }
    }

    fun toggleStreamMute() {
        _uiState.update { it.copy(isStreamMuted = !it.isStreamMuted) }
    }

    fun toggleLowLatency() {
        _uiState.update { it.copy(isLowLatencyMode = !it.isLowLatencyMode) }
    }

    fun openBetSlip(side: BetSide) {
        _uiState.update {
            it.copy(
                selectedBetSide = side,
                isBetSlipVisible = true
            )
        }
    }

    fun closeBetSlip() {
        _uiState.update { it.copy(isBetSlipVisible = false) }
    }

    fun setStakeAmount(amount: Double) {
        _uiState.update { it.copy(currentStakeAmount = amount) }
    }

    fun placeBet() {
        val state = _uiState.value
        val side = state.selectedBetSide ?: return
        val stake = state.currentStakeAmount
        val currentMatch = state.matches.firstOrNull { it.id == state.currentMatchId } ?: return

        if (stake <= 0) {
            showSnackbar("Sila masukkan jumlah pertaruhan yang sah (RM)")
            return
        }

        if (state.wallet.balanceRm < stake) {
            showSnackbar("Baki tidak mencukupi! Tekan Tambah Kredit untuk deposit DuitNow (RM)")
            return
        }

        val odds = when (side) {
            BetSide.MERON -> currentMatch.meron.odds
            BetSide.WALA -> currentMatch.wala.odds
            BetSide.BDD -> currentMatch.bddOdds
        }

        val selectionName = when (side) {
            BetSide.MERON -> "Merah - ${currentMatch.meron.name}"
            BetSide.WALA -> "Biru - ${currentMatch.wala.name}"
            BetSide.BDD -> "Seri (BDD)"
        }

        val potentialPayout = stake + (stake * odds)
        val ticketId = "TK-" + Random.nextInt(10000, 99999)

        val newTicket = BetTicket(
            ticketId = ticketId,
            matchId = currentMatch.id,
            matchNumber = currentMatch.matchNumber,
            arenaName = currentMatch.arena.name,
            side = side,
            selectionName = selectionName,
            stakeAmount = stake,
            odds = odds,
            potentialPayout = potentialPayout,
            status = BetStatus.IN_PLAY,
            canCashOut = true,
            currentCashoutValue = stake * 0.95
        )

        val newTx = Transaction(
            id = "tx_${System.currentTimeMillis()}",
            type = TransactionType.BET_PLACED,
            amount = -stake,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Pertaruhan #${currentMatch.matchNumber} ($selectionName)",
            txHash = "DN-" + Random.nextInt(100000, 999999)
        )

        _uiState.update { s ->
            s.copy(
                wallet = s.wallet.copy(
                    balanceRm = s.wallet.balanceRm - stake,
                    lockedInBets = s.wallet.lockedInBets + stake,
                    vipPoints = s.wallet.vipPoints + (stake * 10).toInt(),
                    transactions = listOf(newTx) + s.wallet.transactions
                ),
                activeBets = listOf(newTicket) + s.activeBets,
                isBetSlipVisible = false,
                snackbarMessage = "🎉 Pertaruhan berjaya: RM ${stake.toInt()} pada $selectionName!"
            )
        }

        sendCrowdReaction("💰")
    }

    fun cashOutBet(ticketId: String) {
        val state = _uiState.value
        val ticket = state.activeBets.firstOrNull { it.ticketId == ticketId } ?: return
        if (!ticket.canCashOut) return

        val cashoutVal = ticket.currentCashoutValue

        val updatedBets = state.activeBets.map {
            if (it.ticketId == ticketId) {
                it.copy(
                    status = BetStatus.CASHED_OUT,
                    canCashOut = false,
                    payoutAmount = cashoutVal
                )
            } else it
        }

        val cashoutTx = Transaction(
            id = "tx_${System.currentTimeMillis()}",
            type = TransactionType.CASHOUT,
            amount = cashoutVal,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Tebus Awal: ${ticket.selectionName}",
            txHash = "DN-" + Random.nextInt(100000, 999999)
        )

        _uiState.update { s ->
            s.copy(
                activeBets = updatedBets,
                wallet = s.wallet.copy(
                    balanceRm = s.wallet.balanceRm + cashoutVal,
                    lockedInBets = (s.wallet.lockedInBets - ticket.stakeAmount).coerceAtLeast(0.0),
                    transactions = listOf(cashoutTx) + s.wallet.transactions
                ),
                snackbarMessage = "⚡ Berjaya tebus awal RM ${String.format("%.2f", cashoutVal)}!"
            )
        }
    }

    fun depositFunds(amount: Double, paymentMethod: String = "DuitNow QR") {
        if (amount <= 0) return
        val newTx = Transaction(
            id = "tx_${System.currentTimeMillis()}",
            type = TransactionType.DEPOSIT,
            amount = amount,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Deposit Segera $paymentMethod",
            txHash = "DN-" + Random.nextInt(100000, 999999)
        )

        _uiState.update { s ->
            s.copy(
                wallet = s.wallet.copy(
                    balanceRm = s.wallet.balanceRm + amount,
                    vipPoints = s.wallet.vipPoints + (amount * 5).toInt(),
                    transactions = listOf(newTx) + s.wallet.transactions
                ),
                isDepositModalOpen = false,
                snackbarMessage = "✅ Berjaya deposit RM ${String.format("%.2f", amount)} melalui $paymentMethod!"
            )
        }
    }

    fun withdrawFunds(amount: Double, bankDetails: String) {
        val currentBalance = _uiState.value.wallet.balanceRm
        if (amount <= 0 || amount > currentBalance) {
            showSnackbar("Baki RM tidak mencukupi atau jumlah tidak sah")
            return
        }

        val newTx = Transaction(
            id = "tx_${System.currentTimeMillis()}",
            type = TransactionType.WITHDRAWAL,
            amount = -amount,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Pengeluaran Segera DuitNow ke $bankDetails",
            txHash = "DN-" + Random.nextInt(100000, 999999)
        )

        _uiState.update { s ->
            s.copy(
                wallet = s.wallet.copy(
                    balanceRm = s.wallet.balanceRm - amount,
                    transactions = listOf(newTx) + s.wallet.transactions
                ),
                isWithdrawModalOpen = false,
                snackbarMessage = "🚀 Pengeluaran dihantar! RM ${String.format("%.2f", amount)} dipindahkan ke akaun bank anda."
            )
        }
    }

    fun sendCrowdReaction(emoji: String) {
        val newReaction = LiveReaction(
            id = "r_${System.currentTimeMillis()}",
            emoji = emoji,
            user = "Anda",
            xOffset = Random.nextFloat()
        )
        _uiState.update { it.copy(liveReactions = it.liveReactions + newReaction) }
    }

    fun sendChatMessage(text: String) {
        if (text.isBlank()) return
        val newMsg = LiveChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            username = "Anda (Sabah)",
            badge = "💎 VIP",
            message = text.trim()
        )
        _uiState.update { it.copy(liveChatMessages = it.liveChatMessages + newMsg) }
    }

    fun openDepositModal() {
        _uiState.update { it.copy(isDepositModalOpen = true) }
    }

    fun closeDepositModal() {
        _uiState.update { it.copy(isDepositModalOpen = false) }
    }

    fun openWithdrawModal() {
        _uiState.update { it.copy(isWithdrawModalOpen = true) }
    }

    fun closeWithdrawModal() {
        _uiState.update { it.copy(isWithdrawModalOpen = false) }
    }

    fun showPromoDialog(banner: SponsorBanner) {
        _uiState.update { it.copy(activePromoDialog = banner) }
    }

    fun dismissPromoDialog() {
        _uiState.update { it.copy(activePromoDialog = null) }
    }

    fun claimPromoBonus(banner: SponsorBanner) {
        val bonusAmount = 50.0
        val bonusTx = Transaction(
            id = "tx_${System.currentTimeMillis()}",
            type = TransactionType.VIP_BONUS,
            amount = bonusAmount,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Bonus Tajaan: ${banner.headline} (Kod: ${banner.bonusCode})",
            txHash = "DN-" + Random.nextInt(100000, 999999)
        )

        _uiState.update { s ->
            s.copy(
                wallet = s.wallet.copy(
                    balanceRm = s.wallet.balanceRm + bonusAmount,
                    transactions = listOf(bonusTx) + s.wallet.transactions
                ),
                activePromoDialog = null,
                snackbarMessage = "🎁 Berjaya tebus RM 50 Kredit Pertaruhan Percuma (${banner.bonusCode})!"
            )
        }
    }

    fun showSnackbar(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
