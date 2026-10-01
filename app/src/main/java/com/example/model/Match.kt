package com.example.model

enum class BetSide(val labelMalay: String) {
    MERON("Merah (Meron)"),   // Red corner
    WALA("Biru (Wala)"),      // Blue corner
    BDD("Seri (BDD)")         // Both Die / Seri
}

enum class MatchStatus {
    BETTING_OPEN,
    LAST_CALL,
    IN_PROGRESS,
    FIGHT_ENDED,
    CANCELLED
}

enum class OddsTrend {
    UP,
    DOWN,
    STABLE
}

enum class CameraAngle(val label: String, val iconName: String) {
    YOUTUBE_LIVE("🔴 YouTube Live", "Live"),
    RING_CENTER("Gelanggang", "Center"),
    MERON_CORNER("Kamera Merah", "Red Corner"),
    WALA_CORNER("Kamera Biru", "Blue Corner"),
    OVERHEAD("Sudut Atas 360", "Sky View")
}

data class Rooster(
    val id: String,
    val name: String,
    val side: BetSide,
    val breeder: String,
    val farmLocation: String, // Sabah towns: Tuaran, Penampang, Keningau, Tawau, etc.
    val weightKg: Double,
    val wingSpanCm: Int,
    val breed: String,
    val record: String, // e.g., "19M - 2K"
    val recentForm: List<Boolean>, // true = Win, false = Loss
    val fightingStyle: String,
    val odds: Double,
    val oddsTrend: OddsTrend = OddsTrend.STABLE,
    val avatarColor: Long
)

data class Arena(
    val id: String,
    val name: String,
    val location: String,
    val flagEmoji: String = "🇲🇾",
    val liveViewers: Int,
    val currentMatchNumber: Int,
    val isLive: Boolean = true
)

data class Match(
    val id: String,
    val matchNumber: Int,
    val arena: Arena,
    val derbyName: String,
    val status: MatchStatus,
    val meron: Rooster,
    val wala: Rooster,
    val bddOdds: Double = 8.00,
    val totalPoolRm: Double,
    val meronPoolRm: Double,
    val walaPoolRm: Double,
    val bddPoolRm: Double,
    val viewersCount: Int,
    val bettingSecondsLeft: Int,
    val fightDurationSeconds: Int = 0,
    val winner: BetSide? = null,
    val cameraAngle: CameraAngle = CameraAngle.YOUTUBE_LIVE,
    val youtubeVideoId: String = "BTUtspf5rRo"
)
