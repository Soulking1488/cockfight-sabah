# Cockfight Sabah — Technical Blueprint & Feature Specification

## 1. Executive Summary

**Cockfight Sabah (Sabung Ayam Live Malaysia)** is an Android application built natively using **Kotlin** and **Jetpack Compose (Material 3)**. It is tailored specifically for the East Malaysian sports entertainment market (Sabah, Borneo), delivering real-time live arena match feeds, dynamic multi-angle camera switching, instant 3-way betting desks denominated in Malaysian Ringgit (RM), interactive fighter statistics, and an integrated casino-grade wallet.

---

## 2. Feature Implementation Status Matrix

| Module | Feature | Implementation Status | Notes |
| :--- | :--- | :---: | :--- |
| **Video & Stream** | HD Arena Live Broadcast Canvas | ✅ Implemented | High-definition action visual with arena lighting effects |
| | ESPN/UFC Lower-Third Scorebug | ✅ Implemented | Clean broadcast strip with timer, fight #, and live odds |
| | Multi-Angle Camera Switcher | ✅ Implemented | 5 angles: Gelanggang, Kamera Merah, Kamera Biru, Sudut 360, YouTube |
| | External YouTube Official Stream | ✅ Implemented | Direct deep-link launcher to match ID `BTUtspf5rRo` |
| | Real-Time Floating Emoji Reactions | ✅ Implemented | Instant crowd reactions (🔥, 🐓, 💰) |
| | Ultra-Low Latency Toggle & Audio Mute | ✅ Implemented | UI toggles for live audio and latency mode |
| | Direct HLS / RTMP Video Decoder | ❌ Not Implemented | Currently uses graphic broadcast stream + YouTube launcher |
| | Live Arena Ambient Audio Stream | ❌ Not Implemented | Audio mute toggle is present; actual audio stream requires live stream URL |
| **Betting Desk** | 3-Way Wagering (Merah, Biru, Seri) | ✅ Implemented | Meron (0.94x), Wala (1.92x), Seri / BDD (8.00x) |
| | Fast Chip Value Presets | ✅ Implemented | RM 10, RM 50, RM 100, RM 200, RM 500 quick taps |
| | Interactive Bet Slip Bottom Sheet | ✅ Implemented | Dynamic stake input, potential win calculator & validation |
| | In-Play & Settled Slip Tracking | ✅ Implemented | Full receipt history with payout calculations |
| | Dynamic Odds Fluctuation Engine | ✅ Implemented | Periodic ticker simulation of market odds shifts |
| | Auto Cash-Out / Partial Cash-Out | ❌ Not Implemented | Planned for future live match iterations |
| | Multi-Match Parlay / Accumulator | ❌ Not Implemented | Currently single-match straight bets only |
| **Wallet & Banking** | Malaysian Ringgit (RM) Balance | ✅ Implemented | Complete balance state management & deductions |
| | Deposit Flow (FPX, DuitNow, TnG) | ✅ Implemented | Interactive deposit dialog with quick tier buttons |
| | Withdrawal Flow (Malaysian Banks) | ✅ Implemented | Payout requests with bank details and validation |
| | Real-Time Transaction Ledger | ✅ Implemented | In-memory transaction records in `WalletScreen` |
| | Real Payment Gateway API Integration | ❌ Not Implemented | Needs merchant keys (e.g., Curlec, Razer, Stripe) |
| | E-KYC Identity Verification | ❌ Not Implemented | Planned for biometric / MyKad IC scanning |
| **Sabah Arenas** | Multi-Location Derby Schedule | ✅ Implemented | Tuaran, Penampang, Keningau, Tawau gelanggangs |
| | Rooster "Tale of the Tape" Profiles | ✅ Implemented | Breed, weight (kg), fighting style, W/L record |
| | Rooster Directory ("Browse Chickens") | ✅ Implemented | Searchable directory in navigation drawer |
| | Derby Tournament Brackets | ⏳ Prototype UI | Listed in `MatchesScreen`, full tournament tree pending |
| **Navigation & UI** | Animated Splash Screen with Custom Logo | ✅ Implemented | Scaled golden halo intro loading `logo.png` |
| | Custom Top Bar & Balance Pill | ✅ Implemented | Displays `logo.png`, app brand, and balance in RM |
| | Modal Side Navigation Drawer | ✅ Implemented | My Bettings, Coupons, Rooster Directory, Settings, etc. |
| | Promotional & VIP Rewards Modal | ✅ Implemented | Claimable bonuses (Piala Emas Sabah, Cashback) |
| | Interactive Dialogs (Coupons, Support) | ✅ Implemented | Modals for coupons, marketing, and help support |
| | Dark / Light Theme Toggle | ❌ Not Implemented | App is hardcoded to dark obsidian gold theme |
| | Multi-Language Localization (BM/EN/Dusun) | ⏳ Partial | UI is primarily Bahasa Malaysia & English |
| **Backend & Cloud** | Persistent Database (Firestore / SQL) | ❌ Not Implemented | Currently local reactive StateFlow state |
| | User Authentication (Google Sign-In / SMS OTP) | ❌ Not Implemented | Uses local guest profile (`John Doe (Sabah)`) |
| | WebSocket Real-Time Odds & State Sync | ❌ Not Implemented | Uses coroutine-based ticker loop |
| | Push Notifications (FCM) | ❌ Not Implemented | Requires Firebase Cloud Messaging setup |

---

## 3. Detailed Architecture of Implemented Features

### 3.1. User Interface & State Management
- **Framework**: Jetpack Compose using Material 3 guidelines and responsive components.
- **State Architecture**: `CockfightViewModel` exposes a unified `StateFlow<CockfightUiState>` consumed via `collectAsState()` in `MainActivity.kt`.
- **Theme Palette**:
  - `CasinoBackground`: Deep obsidian `#0C031A`
  - `CasinoGold`: `#FFD700` and `CasinoGoldDark`: `#B8860B`
  - `MeronRed`: Intense crimson `#D50000`
  - `WalaBlue`: Royal blue `#0072FF`
  - `NeonEmerald`: Winner green `#00E676`

### 3.2. Broadcast Video Player (`ArenaStreamPlayer.kt`)
- Designed following sports broadcast standards (ESPN / UFC):
  - **Broadcast Scorebug**: Slim lower-third strip docking fighter odds, current round timer, and fight ID without obscuring live arena action.
  - **Fallback Arena Visuals**: Bundled high-resolution action graphics (`R.drawable.img_arena_stream`) ensuring zero blank or gray frames.
  - **YouTube Stream Launcher**: Direct deep-linking to YouTube ID `BTUtspf5rRo` with clear in-app metadata.
  - **Channel Switcher**: Multi-angle selector chips allowing users to toggle between different arena viewpoints.

### 3.3. Malaysian Ringgit (RM) Wallet (`BettingComponents.kt` & `WalletScreen.kt`)
- Supports betting in Malaysian Ringgit (`RM`).
- Quick chip selectors: `RM 10`, `RM 50`, `RM 100`, `RM 200`, `RM 500`.
- Validation against wallet balance before placing bets.
- Simulated deposit channel picker: FPX (Maybank2u, CIMB, Public Bank, RHB), DuitNow QR, and Touch 'n Go eWallet.

### 3.4. Custom Branding & Launch Screen (`MainActivity.kt` & `logo.png`)
- Custom `logo.png` integrated into:
  - System splash window background via `themes.xml`.
  - In-app animated splash screen with scale and golden halo effects.
  - Top navigation bar branding.
  - Side navigation drawer profile avatar.

---

## 4. Backlog: What Has NOT Been Implemented Yet

The following items represent the planned engineering roadmap for transitioning from this UI prototype into a live, production-grade gambling and sports streaming service:

### 4.1. Cloud Backend & Real-Time Sync
1. **Cloud Database (Firebase Firestore or PostgreSQL)**:
   - Store user accounts, bet records, transaction receipts, and live match results in a secure cloud database rather than ephemeral in-memory state.
2. **WebSocket / gRPC Server**:
   - Replace the local coroutine timer loop with sub-second WebSocket feeds broadcasting official referee timers, live odds adjustments, and match outcomes.
3. **User Authentication & Google Sign-In**:
   - Replace the static profile (`John Doe (Sabah)`) with real Google Sign-In via `CredentialManager` and phone number SMS OTP verification.

### 4.2. Video & Media Streaming Infrastructure
1. **ExoPlayer HLS / RTMP Integration**:
   - Integrate `androidx.media3.exoplayer` to decode low-latency HLS (`.m3u8`) or WebRTC video feeds directly from live arena encoders.
2. **Live Arena Audio Track**:
   - Feed synchronized multi-channel arena audio (cheering, referee announcements, rooster crowing) into ExoPlayer with hardware volume controls.
3. **Picture-in-Picture (PiP) Mode**:
   - Support Android system PiP so users can browse other apps while watching the live Sabah match stream.

### 4.3. Payment & Banking Integrations
1. **Live Payment Gateway**:
   - Webhook integration with Malaysian payment processors (e.g., Curlec by Razorpay, Razer Merchant Services, or Stripe Malaysia) for real FPX and DuitNow QR collections.
2. **Automated Bank Payouts**:
   - Integrate automated payout APIs for Malaysian bank transfers upon withdrawal approval.
3. **E-KYC & Anti-Money Laundering (AML)**:
   - Integrate document scanning (MyKad / Passport) for age verification (18+) and regulatory compliance.

### 4.4. Advanced Wagering Features
1. **Parlay / Combo Bets**:
   - Enable users to place accumulator bets across multiple consecutive Sabah derby matches for multiplied payouts.
2. **Early Cash-Out**:
   - Dynamic real-time cash-out option allowing users to lock in partial winnings or mitigate losses during mid-fight action.
3. **Bet Sharing & Social Touts**:
   - Generate shareable bet slip image cards for WhatsApp and Telegram groups.

### 4.5. Push Notifications & Localization
1. **Firebase Cloud Messaging (FCM)**:
   - Automated push notifications when favorite roosters enter the ring, when a match is about to begin, or when a bet wins.
2. **Trilingual Localization**:
   - Full string resource translation for Bahasa Malaysia, English, and Kadazan-Dusun.

---

## 5. Summary & Verification

- **Code Quality**: Clean MVVM architecture with separate presentation, domain, and data layers.
- **Verification**: Fully covered by local unit and Robolectric tests (`gradle :app:testDebugUnitTest`), compiling with zero fatal errors.
- **License**: Mozilla Public License 2.0 (MPLv2).
