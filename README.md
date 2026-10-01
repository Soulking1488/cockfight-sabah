# Cockfight Sabah (Sabung Ayam Live Malaysia)

[![License: MPL 2.0](https://img.shields.io/badge/License-MPL_2.0-brightgreen.svg)](https://opensource.org/licenses/MPL-2.0)
[![Android](https://img.shields.io/badge/Platform-Android_14+-3DDC84.svg?logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.0-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/UI-Jetpack_Compose_M3-4285F4.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)

A high-performance Android application prototype simulating a premier **Live Cockfighting (Sabung Ayam Live)** sports entertainment and betting platform tailored for Sabah, Malaysia. Featuring real-time match streaming, multi-angle arena camera switching, dynamic live betting odds, Malaysian Ringgit (RM) wallet transactions, and audience interactions.

---

## 🌟 Key Features

### 📺 1. Live Arena Video Streaming
- **Broadcast Scorebug Display**: Professional ESPN/UFC lower-third scoreboard strip showcasing live fight timer, match round, and real-time fighter odds without obstructing the arena view.
- **Multi-Angle Camera Feeds**: Instantly switch between:
  - 🏟️ **Gelanggang Utama** (Main Arena Center View)
  - 🔴 **Kamera Merah** (Red Corner Focus)
  - 🔵 **Kamera Biru** (Blue Corner Focus)
  - 📐 **Sudut Atas 360** (Top-Down Arena Cam)
  - 📺 **YouTube Live** (Direct launch link to official match stream `BTUtspf5rRo`)
- **Interactive Crowd Reactions**: Real-time reaction floating emojis (🔥, 🐓, 💰, 👏) and active viewer count (`34.8K Online`).

### ⚡ 2. Instant Live Betting Desk (Malaysian Ringgit - RM)
- **3-Way Betting Options**:
  - **MERAH (Meron)**: Favorite fighter odds with automatic multiplier calculation.
  - **SERI (BDD / Both Die Draw)**: High-yield draw odds (up to 8.00x).
  - **BIRU (Wala)**: Challenger fighter odds.
- **Fast Casino Chip Selectors**: One-tap chip presets for `RM 10`, `RM 50`, `RM 100`, `RM 200`, and `RM 500`.
- **Interactive Bet Slip**: Real-time potential payout calculator with instant bet confirmation and slip history.

### 🐓 3. Sabah Arenas & Fighter Profiles
- **Sabah Gelanggang Locations**:
  - 🇲🇾 *Gelanggang Tuaran*, Sabah
  - 🇲🇾 *Arena Penampang*, Kota Kinabalu
  - 🇲🇾 *Keningau Borneo Ring*, Keningau
  - 🇲🇾 *Kompleks Tawau*, Tawau
- **Tale of the Tape**: Comprehensive rooster specifications including Breed (Ayam Hujung Taji, Pama, Asil, Saigon), Weight (kg), Breeder name, Fighting Style, and Win/Loss records.

### 💰 4. Integrated RM Wallet & Financials
- **Ringgit Malaysia (RM) Denominated**:
  - **Instant Deposit**: Supporting FPX Online Banking (Maybank2u, CIMB Clicks, Public Bank, RHB), DuitNow QR, and Touch 'n Go eWallet.
  - **Secure Withdrawal**: Direct local Malaysian bank payouts with real-time balance updates.
  - **Betting History**: Full tracking of active, won, and settled bet slips.

### 📱 5. User Experience & Navigation
- **Branded Splash Screen**: Animated launch screen showcasing the custom logo with casino gold glow animations.
- **Side Navigation Drawer**: Quick access to My Bettings, Coupons & Vouchers, Rooster Directory, Betting History, Help & Support (WhatsApp / Telegram 24/7), Settings, Marketing Programme, and Account Profile.
- **Live Crowd Chat**: Real-time Sabahan audience chatter and celebratory win notifications.

---

## 🏗️ Architecture & Technology Stack

- **Platform**: Android (minSdk 26, targetSdk 36, compileSdk 36)
- **Programming Language**: Kotlin (100%)
- **UI Toolkit**: Jetpack Compose with Material 3 (M3)
- **Design System**: Dark Gold Casino Aesthetics (Obsidian Black, Imperial Purple, Gold Metallic Gradient, Neon Emerald)
- **Architecture**: MVVM (Model-View-ViewModel) + StateFlow & unidirectional data flow
- **Unit & Robolectric Testing**: Complete local JVM test coverage with Robolectric

---

## 📁 Project Structure

```
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt               # Main entry, bottom nav, splash screen & top bar
│   │   │   │   ├── model/CockfightModels.kt       # Data classes (Match, Rooster, Bet, Arena, Wallet)
│   │   │   │   ├── viewmodel/CockfightViewModel.kt # Business logic, live odds simulation, betting desk
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── ArenaStreamPlayer.kt   # Live video player & broadcast scorebug
│   │   │   │   │   │   ├── BettingComponents.kt   # Bet slip sheets, deposit & withdrawal dialogs
│   │   │   │   │   │   ├── CockfightSideDrawer.kt # Side navigation drawer & menu options
│   │   │   │   │   │   └── FlashyComponents.kt    # Tickers, banners, and crowd reactions
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── LiveArenaScreen.kt     # Main live streaming & quick betting view
│   │   │   │   │   │   ├── MatchesScreen.kt       # Sabah Derby schedule and tournament matches
│   │   │   │   │   │   ├── MyBetsScreen.kt        # Active and settled user bet records
│   │   │   │   │   │   ├── WalletScreen.kt        # Ringgit Malaysia balance, deposit & payout
│   │   │   │   │   │   └── PromosScreen.kt        # VIP bonuses and event promotions
│   │   │   │   │   └── theme/                     # Color scheme, typography, and shapes
│   │   │   ├── res/
│   │   │   │   ├── drawable/                      # Custom logo.png, arena visuals, and icons
│   │   │   │   └── values/                        # strings.xml, themes.xml, colors.xml
│   │   └── test/                                  # Robolectric & JUnit test suite
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or higher
- Android SDK with Platform 36

### Build & Run
1. Clone this repository:
   ```bash
   git clone <repository_url>
   cd cockfight-sabah
   ```
2. Build the debug APK:
   ```bash
   gradle assembleDebug
   ```
3. Run Robolectric and unit tests:
   ```bash
   gradle :app:testDebugUnitTest
   ```

---

## 📄 License

This Source Code Form is subject to the terms of the **Mozilla Public License, v. 2.0**.
If a copy of the MPL was not distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/.

See the [LICENSE](LICENSE) file for details.
