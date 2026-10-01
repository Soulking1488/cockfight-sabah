package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.BetSide
import com.example.model.CameraAngle
import com.example.ui.components.SideMenuDestination
import com.example.viewmodel.CockfightViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Cockfight", appName)
  }

  @Test
  fun `verify initial betting and wallet state in RM`() {
    val viewModel = CockfightViewModel()
    val state = viewModel.uiState.value

    assertTrue("Matches should not be empty", state.matches.isNotEmpty())
    assertTrue("Arenas in Sabah should not be empty", state.arenas.isNotEmpty())
    assertTrue("Sponsor banners should be loaded", state.sponsorBanners.isNotEmpty())
    assertTrue("Initial wallet balance in RM should be positive", state.wallet.balanceRm > 0)
    assertNotNull("Current match should exist", state.matches.firstOrNull { it.id == state.currentMatchId })
    assertTrue("Current arena should be in Sabah", state.arenas.any { it.location.contains("Sabah") })
  }

  @Test
  fun `verify bet placement and wallet deduction in RM`() {
    val viewModel = CockfightViewModel()
    val initialBalance = viewModel.uiState.value.wallet.balanceRm
    viewModel.openBetSlip(BetSide.MERON)
    viewModel.setStakeAmount(50.0)
    viewModel.placeBet()

    val newBalance = viewModel.uiState.value.wallet.balanceRm
    assertEquals(initialBalance - 50.0, newBalance, 0.01)
  }

  @Test
  fun `verify side menu options exist`() {
    val destinations = SideMenuDestination.values()
    assertTrue(destinations.contains(SideMenuDestination.MY_BETTINGS))
    assertTrue(destinations.contains(SideMenuDestination.COUPONS))
    assertTrue(destinations.contains(SideMenuDestination.BROWSE_CHICKENS))
    assertTrue(destinations.contains(SideMenuDestination.HISTORY))
    assertTrue(destinations.contains(SideMenuDestination.HELP))
    assertTrue(destinations.contains(SideMenuDestination.SETTINGS))
    assertTrue(destinations.contains(SideMenuDestination.MARKETING_PROGRAMME))
    assertTrue(destinations.contains(SideMenuDestination.ACCOUNT))
  }

  @Test
  fun `verify embedded YouTube live match video`() {
    val viewModel = CockfightViewModel()
    val state = viewModel.uiState.value
    val match = state.matches.first { it.id == state.currentMatchId }

    assertEquals("BTUtspf5rRo", match.youtubeVideoId)
    assertEquals(CameraAngle.YOUTUBE_LIVE, state.selectedCameraAngle)
  }

  @Test
  fun `verify custom logo resource exists`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val drawable = androidx.core.content.ContextCompat.getDrawable(context, R.drawable.logo)
    assertNotNull("Custom logo drawable should be resolvable", drawable)
  }
}
