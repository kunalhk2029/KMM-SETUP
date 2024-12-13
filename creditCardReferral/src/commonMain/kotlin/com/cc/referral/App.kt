package com.cc.referral

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cc.referral.business.domain.Screens
import com.cc.referral.ui.main.cards.CardOffers
import com.cc.referral.ui.main.kyc.KYCScreen1
import com.cc.referral.ui.main.kyc.KYCViewModel
import com.cc.referral.ui.onboarding.AppOverviewScreen
import com.cc.referral.ui.onboarding.auth.AuthScreen
import com.cc.referral.ui.onboarding.login.LoginScreen
import com.cc.referral.ui.onboarding.register.RegisterScreen
import com.cc.referral.ui.onboarding.auth.AuthNavEvents
import com.cc.referral.ui.onboarding.login.LoginNavEvents
import com.cc.referral.ui.onboarding.login.LoginViewModel
import com.cc.referral.ui.onboarding.register.RegisterNavEvents
import com.cc.referral.ui.onboarding.register.RegisterViewModel
import com.cc.referral.ui.splash.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


object AppColors {

    val white = Color(0xFFffffff)
    val white60 = Color(0x60ffffff)
    val whiteECEEEA = Color(0xFFECEEEA)
    val redE87070 = Color(0xFFE87070)
    val lightGreen = Color(0xFF9FE870)
    val darkGreen = Color(0xFF153200)
    val green85947B = Color(0xFF85947B)
    val green15320077 = Color(0x77153200)
    val green15320061 = Color(0x61153200)
    val green21DD08 = Color(0xFF21DD08)
    val black = Color(0xFF000000)
    val black33 = Color(0x33000000)
    val purple = Color(0xFF8134af)
    val blue = Color(0xFF70DAE8)
    val blue70C3E8 = Color(0xFF70C3E8)
    val greyF4F4F4 = Color(0xFFF4F4F4)
    val greyEFEFEF = Color(0xFFEFEFEF)

}

@Composable
@Preview
fun App(androidPlatformSpecificMethods: AndroidPlatformSpecificMethods? = null) {

    val lightColors = lightColors(
        primary = Color(0xFF4298f5),
        onPrimary = Color(0xFFffffff),
        background = Color(0xFFf0f2f2),
        onBackground = Color(0xFF000000),
        surface = Color(0xFFffffff),
    )

    val darkColors = darkColors(
        primary = Color(0xFF4298f5),
        onPrimary = Color(0xFFffffff),
        onSecondary = Color(0xFFffffff),
        background = Color(0xFF171717),
        onBackground = Color(0xFFffffff),
        surface = Color(0xFF202124),
        onSurface = Color(0xFFffffff),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(10.dp),
        large = RoundedCornerShape(15.dp)
    )

    val isDarkTheme = isSystemInDarkTheme()

    MaterialTheme(
        shapes = shapes,
        colors = if (isDarkTheme) darkColors else lightColors
    ) {

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {

            val navController = rememberNavController()

            val coroutineScope = rememberCoroutineScope()

            val demoViewModelOwner: DemoViewModelOwner =
                remember { DemoViewModelOwner() }

            LaunchedEffect(navController.currentBackStack) {
                navController.currentBackStack.collect {
                    val lastRoute = it.lastOrNull()?.destination?.route

                }
            }

            NavHost(navController = navController, startDestination = Screens.Splash) {

                composable<Screens.Splash> {
                    SplashScreen()
                    LaunchedEffect(Unit) {
                        coroutineScope.launch {
                            delay(1500L)
                            navController
                                .navigate(
                                    Screens.AppOverView, navOptions = NavOptions.Builder()
                                        .setPopUpTo(Screens.Splash, true)
                                        .build()
                                )
                        }
                    }
                }

                composable<Screens.AppOverView> {
                    AppOverviewScreen {
                        navController.navigate(Screens.Auth)
                    }
                }

                composable<Screens.Auth> {
                    AuthScreen() {
                        if (it == AuthNavEvents.NavigateToRegisterScreen) navController.navigate(
                            Screens.Register
                        )
                        else if (it == AuthNavEvents.NavigateToLoginScreen) navController.navigate(
                            Screens.Login
                        )
                    }
                }

                composable<Screens.Register> {
                    val registerViewModel = koinViewModel<RegisterViewModel>()
                    RegisterScreen(
                        registerViewModel.state.collectAsState().value,
                        registerViewModel::onEvent
                    ) {
                        if (it == RegisterNavEvents.NavigateToCards) navController.navigate(Screens.CardOffers)
                    }
                }

                composable<Screens.Login> {
                    val loginViewModel = koinViewModel<LoginViewModel>()
                    LoginScreen(
                        loginViewModel.state.collectAsState().value,
                        loginViewModel::onEvent
                    ) {
                        if (it == LoginNavEvents.NavigateToCards) navController.navigate(Screens.CardOffers)
                    }
                }

                composable<Screens.KYC1> {
                    val kycViewModel = koinViewModel<KYCViewModel>()
                    KYCScreen1(kycViewModel.state.collectAsState().value)
                }

                composable<Screens.CardOffers> {
                    CardOffers()
                }
            }
        }
    }
}

class DemoViewModelOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

