package com.example.model

enum class BetStatus {
    PENDING,
    IN_PLAY,
    WON,
    LOST,
    CASHED_OUT
}

data class BetTicket(
    val ticketId: String,
    val matchId: String,
    val matchNumber: Int,
    val arenaName: String,
    val side: BetSide,
    val selectionName: String,
    val stakeAmount: Double,
    val odds: Double,
    val potentialPayout: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val status: BetStatus = BetStatus.IN_PLAY,
    val payoutAmount: Double = 0.0,
    val canCashOut: Boolean = true,
    val currentCashoutValue: Double = stakeAmount * 0.92
)
