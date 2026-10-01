package com.example.model

data class SponsorBanner(
    val id: String,
    val headline: String,
    val subline: String,
    val badgeText: String,
    val bonusCode: String,
    val gradientType: Int, // 1, 2, 3
    val iconEmoji: String,
    val claimAmount: String,
    val targetUrl: String = ""
)

data class EventNotification(
    val id: String,
    val icon: String,
    val text: String,
    val highlight: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class LiveReaction(
    val id: String,
    val emoji: String,
    val user: String,
    val xOffset: Float
)

data class LiveChatMessage(
    val id: String,
    val username: String,
    val badge: String,
    val message: String,
    val sideSupported: BetSide? = null,
    val betAmount: Double? = null
)
