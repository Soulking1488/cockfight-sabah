package com.example.model

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    BET_PLACED,
    BET_WON,
    CASHOUT,
    VIP_BONUS
}

enum class TransactionStatus {
    COMPLETED,
    PROCESSING,
    FAILED
}

enum class VipTier(val tierName: String, val rakebackRate: Double, val badgeColor: Long) {
    SILVER("Silver VIP", 0.5, 0xFFB0BEC5),
    GOLD("Gold VIP Sabah", 1.0, 0xFFFFD700),
    PLATINUM("Platinum Borneo", 1.8, 0xFFE0E0E0),
    DIAMOND("Diamond Kinabalu", 2.5, 0xFF00E5FF),
    HIGH_ROLLER("High Roller Whale", 3.8, 0xFFFF007F)
}

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val currency: String = "RM",
    val status: TransactionStatus,
    val description: String,
    val txHash: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class WalletState(
    val balanceRm: Double = 1450.00,
    val lockedInBets: Double = 150.00,
    val vipTier: VipTier = VipTier.GOLD,
    val vipPoints: Int = 12450,
    val vipNextTierPoints: Int = 20000,
    val duitNowId: String = "011-2894-9821 (Azman)",
    val depositAddressTrc20: String = "TQn9Y2khEsLJW1ChVWFMSMeRDow5K9qU7M",
    val bankAccountName: String = "Maybank / CIMB / Sabah Pay",
    val isTwoFactorEnabled: Boolean = true,
    val isColdVaultSecured: Boolean = true,
    val transactions: List<Transaction> = emptyList()
)
