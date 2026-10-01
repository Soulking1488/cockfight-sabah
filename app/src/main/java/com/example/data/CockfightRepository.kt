package com.example.data

import com.example.model.*

class CockfightRepository {

    fun getArenas(): List<Arena> = listOf(
        Arena(
            id = "arena_tuaran",
            name = "Gelanggang Tuaran",
            location = "Tuaran, Sabah",
            flagEmoji = "🇲🇾",
            liveViewers = 34820,
            currentMatchNumber = 42,
            isLive = true
        ),
        Arena(
            id = "arena_penampang",
            name = "Arena Penampang",
            location = "Kota Kinabalu, Sabah",
            flagEmoji = "🇲🇾",
            liveViewers = 21450,
            currentMatchNumber = 27,
            isLive = true
        ),
        Arena(
            id = "arena_keningau",
            name = "Keningau Borneo Ring",
            location = "Keningau, Sabah",
            flagEmoji = "🇲🇾",
            liveViewers = 15890,
            currentMatchNumber = 18,
            isLive = true
        ),
        Arena(
            id = "arena_tawau",
            name = "Tawau Bay Cockpit",
            location = "Tawau, Sabah",
            flagEmoji = "🇲🇾",
            liveViewers = 11200,
            currentMatchNumber = 15,
            isLive = true
        )
    )

    fun getInitialMatches(arenas: List<Arena>): List<Match> {
        val tuaranArena = arenas.first { it.id == "arena_tuaran" }
        val penampangArena = arenas.first { it.id == "arena_penampang" }
        val keningauArena = arenas.first { it.id == "arena_keningau" }

        return listOf(
            Match(
                id = "match_42",
                matchNumber = 42,
                arena = tuaranArena,
                derbyName = "Piala Emas Sabah 2026",
                status = MatchStatus.BETTING_OPEN,
                meron = Rooster(
                    id = "rooster_meron_42",
                    name = "KILAT KINABALU",
                    side = BetSide.MERON,
                    breeder = "Datuk James Wong",
                    farmLocation = "Keningau Farm, Sabah",
                    weightKg = 2.45,
                    wingSpanCm = 68,
                    breed = "Sweater 5K Roundhead Sabah",
                    record = "19M - 2K",
                    recentForm = listOf(true, true, true, false, true),
                    fightingStyle = "Tetakan Udara Laju (Aerial Slasher)",
                    odds = 0.94,
                    oddsTrend = OddsTrend.DOWN,
                    avatarColor = 0xFFFF0844
                ),
                wala = Rooster(
                    id = "rooster_wala_42",
                    name = "NAGA TUARAN",
                    side = BetSide.WALA,
                    breeder = "Haji Rashid",
                    farmLocation = "Tuaran Gamefowl, Sabah",
                    weightKg = 2.42,
                    wingSpanCm = 66,
                    breed = "Kelso / Lemon 84 Borneo",
                    record = "16M - 3K",
                    recentForm = listOf(true, true, false, true, true),
                    fightingStyle = "Balas Pukulan Tangkas",
                    odds = 1.92,
                    oddsTrend = OddsTrend.UP,
                    avatarColor = 0xFF0072FF
                ),
                bddOdds = 8.00,
                totalPoolRm = 128400.00,
                meronPoolRm = 81200.00,
                walaPoolRm = 41800.00,
                bddPoolRm = 5400.00,
                viewersCount = 34820,
                bettingSecondsLeft = 45,
                fightDurationSeconds = 0,
                cameraAngle = CameraAngle.RING_CENTER
            ),
            Match(
                id = "match_43",
                matchNumber = 43,
                arena = tuaranArena,
                derbyName = "Piala Emas Sabah 2026",
                status = MatchStatus.BETTING_OPEN,
                meron = Rooster(
                    id = "rooster_meron_43",
                    name = "PENAMPANG WARRIOR",
                    side = BetSide.MERON,
                    breeder = "Michael Stephen",
                    farmLocation = "Penampang, Sabah",
                    weightKg = 2.38,
                    wingSpanCm = 67,
                    breed = "Hatch Claret Borneo Cross",
                    record = "14M - 1K",
                    recentForm = listOf(true, true, true, true, false),
                    fightingStyle = "Pacak Sayap & Taji Kilat",
                    odds = 1.05,
                    oddsTrend = OddsTrend.STABLE,
                    avatarColor = 0xFFFF0844
                ),
                wala = Rooster(
                    id = "rooster_wala_43",
                    name = "TAWAU FALCON",
                    side = BetSide.WALA,
                    breeder = "Awang Damit",
                    farmLocation = "Tawau Gamepit, Sabah",
                    weightKg = 2.40,
                    wingSpanCm = 69,
                    breed = "Albany / Radio Blend",
                    record = "21M - 4K",
                    recentForm = listOf(true, false, true, true, true),
                    fightingStyle = "Serangan Bawah Padu",
                    odds = 1.82,
                    oddsTrend = OddsTrend.DOWN,
                    avatarColor = 0xFF0072FF
                ),
                bddOdds = 8.00,
                totalPoolRm = 99800.00,
                meronPoolRm = 56200.00,
                walaPoolRm = 38900.00,
                bddPoolRm = 4700.00,
                viewersCount = 28100,
                bettingSecondsLeft = 180,
                fightDurationSeconds = 0,
                cameraAngle = CameraAngle.RING_CENTER
            ),
            Match(
                id = "match_27",
                matchNumber = 27,
                arena = penampangArena,
                derbyName = "Derbi Juara Kinabalu",
                status = MatchStatus.IN_PROGRESS,
                meron = Rooster(
                    id = "rooster_meron_27",
                    name = "BIRING KUNING SABAH",
                    side = BetSide.MERON,
                    breeder = "Kapitan Tan",
                    farmLocation = "Sandakan, Sabah",
                    weightKg = 2.50,
                    wingSpanCm = 70,
                    breed = "Ayam Biring Kuning Asli",
                    record = "22M - 2K",
                    recentForm = listOf(true, true, true, true, true),
                    fightingStyle = "Gagah Pukul Kepala",
                    odds = 0.88,
                    oddsTrend = OddsTrend.DOWN,
                    avatarColor = 0xFFFF0844
                ),
                wala = Rooster(
                    id = "rooster_wala_27",
                    name = "HITAM BORNEO",
                    side = BetSide.WALA,
                    breeder = "Cikgu Rosli",
                    farmLocation = "Ranau, Sabah",
                    weightKg = 2.48,
                    wingSpanCm = 69,
                    breed = "Black Shamo Ranau",
                    record = "17M - 5K",
                    recentForm = listOf(false, true, true, false, true),
                    fightingStyle = "Bertahan & Sambut Pantas",
                    odds = 2.05,
                    oddsTrend = OddsTrend.UP,
                    avatarColor = 0xFF0072FF
                ),
                bddOdds = 8.00,
                totalPoolRm = 158000.00,
                meronPoolRm = 102000.00,
                walaPoolRm = 51000.00,
                bddPoolRm = 5000.00,
                viewersCount = 21450,
                bettingSecondsLeft = 0,
                fightDurationSeconds = 52,
                cameraAngle = CameraAngle.RING_CENTER
            ),
            Match(
                id = "match_18",
                matchNumber = 18,
                arena = keningauArena,
                derbyName = "Borneo Masters 8-Cock Derby",
                status = MatchStatus.BETTING_OPEN,
                meron = Rooster(
                    id = "rooster_meron_18",
                    name = "RAJA TAJI KENINGAU",
                    side = BetSide.MERON,
                    breeder = "Felix Guntavid",
                    farmLocation = "Tambunan, Sabah",
                    weightKg = 2.52,
                    wingSpanCm = 71,
                    breed = "Paksa Kelso Tambunan",
                    record = "18M - 2K",
                    recentForm = listOf(true, true, true, true, true),
                    fightingStyle = "Tusukan Taji Tepat",
                    odds = 0.92,
                    oddsTrend = OddsTrend.STABLE,
                    avatarColor = 0xFFFF0844
                ),
                wala = Rooster(
                    id = "rooster_wala_18",
                    name = "BAYU LAHAD DATU",
                    side = BetSide.WALA,
                    breeder = "Pakcik Kassim",
                    farmLocation = "Lahad Datu, Sabah",
                    weightKg = 2.49,
                    wingSpanCm = 69,
                    breed = "Sweater Possum Borneo",
                    record = "15M - 3K",
                    recentForm = listOf(true, true, false, true, true),
                    fightingStyle = "Serang Rusuk Laju",
                    odds = 1.95,
                    oddsTrend = OddsTrend.UP,
                    avatarColor = 0xFF0072FF
                ),
                bddOdds = 8.00,
                totalPoolRm = 114500.00,
                meronPoolRm = 71000.00,
                walaPoolRm = 38500.00,
                bddPoolRm = 5000.00,
                viewersCount = 15890,
                bettingSecondsLeft = 120,
                fightDurationSeconds = 0,
                cameraAngle = CameraAngle.RING_CENTER
            ),
            Match(
                id = "match_41",
                matchNumber = 41,
                arena = tuaranArena,
                derbyName = "Piala Emas Sabah 2026",
                status = MatchStatus.FIGHT_ENDED,
                meron = Rooster(
                    id = "rooster_meron_41",
                    name = "HARIMAU KINABALU",
                    side = BetSide.MERON,
                    breeder = "Datuk James Wong",
                    farmLocation = "Keningau, Sabah",
                    weightKg = 2.44,
                    wingSpanCm = 68,
                    breed = "Mel Sims Hatch Sabah",
                    record = "15M - 4K",
                    recentForm = listOf(true, true, false, true, true),
                    fightingStyle = "Garang Berterusan",
                    odds = 0.95,
                    avatarColor = 0xFFFF0844
                ),
                wala = Rooster(
                    id = "rooster_wala_41",
                    name = "PETIR KUDAT",
                    side = BetSide.WALA,
                    breeder = "Kapitan Chong",
                    farmLocation = "Kudat, Sabah",
                    weightKg = 2.41,
                    wingSpanCm = 66,
                    breed = "Roundhead Grey Kudat",
                    record = "12M - 5K",
                    recentForm = listOf(false, true, true, false, false),
                    fightingStyle = "Pusing Balas",
                    odds = 1.90,
                    avatarColor = 0xFF0072FF
                ),
                bddOdds = 8.00,
                totalPoolRm = 108900.00,
                meronPoolRm = 64000.00,
                walaPoolRm = 40500.00,
                bddPoolRm = 4400.00,
                viewersCount = 31200,
                bettingSecondsLeft = 0,
                fightDurationSeconds = 68,
                winner = BetSide.MERON,
                cameraAngle = CameraAngle.RING_CENTER
            )
        )
    }

    fun getSponsorBanners(): List<SponsorBanner> = listOf(
        SponsorBanner(
            id = "sponsor_1",
            headline = "BONUS 200% PIALA EMAS SABAH",
            subline = "Deposit RM 50 DuitNow, dapatkan RM 150 Kredit Percuma!",
            badgeText = "EKSKLUSIF SABAH",
            bonusCode = "SABAH200",
            gradientType = 1,
            iconEmoji = "🎰",
            claimAmount = "RM 150 PERCUMA"
        ),
        SponsorBanner(
            id = "sponsor_2",
            headline = "DERBI JUARA KINABALU RM 500,000",
            subline = "Siaran langsung rasmi dibawakan oleh Kelab Kasino Borneo VIP",
            badgeText = "PENUBUH UTAMA",
            bonusCode = "KINABALU500K",
            gradientType = 2,
            iconEmoji = "🏆",
            claimAmount = "RM 50 CHIP"
        ),
        SponsorBanner(
            id = "sponsor_3",
            headline = "10% REBAT DUITNOW TANPA HAD",
            subline = "Pulangan tunai terus ke akaun DuitNow anda setiap Isnin!",
            badgeText = "REBAT TUNAI",
            bonusCode = "DUITNOW10",
            gradientType = 3,
            iconEmoji = "⚡",
            claimAmount = "10% REBAT"
        )
    )

    fun getInitialEventNotifications(): List<EventNotification> = listOf(
        EventNotification(
            id = "ev_1",
            icon = "🔥",
            text = "@SabahKing88 menang RM 8,850 di Meron (Fight #41)!",
            highlight = "MENANG RM 8,850"
        ),
        EventNotification(
            id = "ev_2",
            icon = "⚡",
            text = "Gelanggang Tuaran: Odds Kilat Kinabalu kini 0.94!",
            highlight = "ODDS TERKINI"
        ),
        EventNotification(
            id = "ev_3",
            icon = "💎",
            text = "Pemain VIP @BorneoWhale deposit RM 5,000 via DuitNow QR",
            highlight = "DEPOSIT DUITNOW"
        ),
        EventNotification(
            id = "ev_4",
            icon = "🚨",
            text = "Fight #42 Gelanggang Tuaran: Panggilan terakhir 30 saat!",
            highlight = "PANGGILAN AKHIR"
        ),
        EventNotification(
            id = "ev_5",
            icon = "🐓",
            text = "Ayam Kilat Kinabalu catat rekod 19 kemenangan di Sabah!",
            highlight = "JUARA SABAH"
        ),
        EventNotification(
            id = "ev_6",
            icon = "💰",
            text = "Jumlah Jackpot Derbi Borneo kini RM 380,000!",
            highlight = "MEGA JACKPOT"
        )
    )

    fun getInitialBetTickets(): List<BetTicket> = listOf(
        BetTicket(
            ticketId = "TK-98412",
            matchId = "match_42",
            matchNumber = 42,
            arenaName = "Gelanggang Tuaran, Sabah",
            side = BetSide.MERON,
            selectionName = "Meron - KILAT KINABALU",
            stakeAmount = 100.0,
            odds = 0.94,
            potentialPayout = 194.0,
            timestamp = System.currentTimeMillis() - 120_000,
            status = BetStatus.IN_PLAY,
            canCashOut = true,
            currentCashoutValue = 96.50
        ),
        BetTicket(
            ticketId = "TK-98305",
            matchId = "match_27",
            matchNumber = 27,
            arenaName = "Arena Penampang, KK",
            side = BetSide.WALA,
            selectionName = "Wala - HITAM BORNEO",
            stakeAmount = 50.0,
            odds = 2.05,
            potentialPayout = 152.50,
            timestamp = System.currentTimeMillis() - 360_000,
            status = BetStatus.IN_PLAY,
            canCashOut = true,
            currentCashoutValue = 48.00
        ),
        BetTicket(
            ticketId = "TK-97819",
            matchId = "match_41",
            matchNumber = 41,
            arenaName = "Gelanggang Tuaran, Sabah",
            side = BetSide.MERON,
            selectionName = "Meron - HARIMAU KINABALU",
            stakeAmount = 200.0,
            odds = 0.95,
            potentialPayout = 390.0,
            timestamp = System.currentTimeMillis() - 1200_000,
            status = BetStatus.WON,
            payoutAmount = 390.0,
            canCashOut = false,
            currentCashoutValue = 0.0
        )
    )

    fun getInitialTransactions(): List<Transaction> = listOf(
        Transaction(
            id = "tx_01",
            type = TransactionType.BET_WON,
            amount = 390.0,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Bayaran Kemenangan Meron Perlawanan #41",
            txHash = "DN-892418"
        ),
        Transaction(
            id = "tx_02",
            type = TransactionType.BET_PLACED,
            amount = -100.0,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Taruhan Meron Fight #42 (Kilat Kinabalu)",
            txHash = "DN-438921"
        ),
        Transaction(
            id = "tx_03",
            type = TransactionType.DEPOSIT,
            amount = 1000.0,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Deposit Segera DuitNow QR (Maybank)",
            txHash = "DN-782910"
        ),
        Transaction(
            id = "tx_04",
            type = TransactionType.VIP_BONUS,
            amount = 50.0,
            currency = "RM",
            status = TransactionStatus.COMPLETED,
            description = "Rebat Tunai Mingguan VIP Gold Sabah",
            txHash = "DN-124982"
        )
    )

    fun getInitialChatMessages(): List<LiveChatMessage> = listOf(
        LiveChatMessage("m1", "PendekarTuaran", "👑 VIP SABAH", "Kilat Kinabalu memang mantap hari ni bah! Penuh Meron!", BetSide.MERON, 500.0),
        LiveChatMessage("m2", "KeningauBoy88", "🔥 PRO", "Naga Tuaran ada kelebihan jangkauan taji, odds 1.92 sedap!", BetSide.WALA, 200.0),
        LiveChatMessage("m3", "Datuk_KK", "💎 HIGH ROLLER", "Hantam RM 2,000 atas Meron pusingan 1!", BetSide.MERON, 2000.0),
        LiveChatMessage("m4", "AyamSabah_Fan", "⭐", "Semoga tuah menyebelahi kita semua di Gelanggang Tuaran! 🐓🔥"),
        LiveChatMessage("m5", "TawauSpur", "⚡", "Jaga tetakan balas Naga Tuaran, ayam baka liat tu!")
    )
}
