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
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cc.referral.business.domain.Screens
import com.cc.referral.ui.onboarding.AppOverviewScreen
import com.cc.referral.ui.onboarding.auth.AuthScreen
import com.cc.referral.ui.onboarding.login.LoginScreen
import com.cc.referral.ui.onboarding.register.RegisterScreen
import com.cc.referral.ui.onboarding.auth.AuthNavEvents
import com.cc.referral.ui.onboarding.login.LoginViewModel
import com.cc.referral.ui.onboarding.register.RegisterViewModel
import com.cc.referral.ui.splash.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


object AppColors {

    val white = Color(0xFFffffff)
    val whiteECEEEA = Color(0xFFECEEEA)
    val lightGreen = Color(0xFF9FE870)
    val darkGreen = Color(0xFF153200)
    val green15320077 = Color(0x77153200)
    val black = Color(0xFF000000)
    val purple = Color(0xFF8134af)
    val blue = Color(0xFF02B1E6)
    val grey = Color(0xFF222222)
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

            NavHost(navController = navController, startDestination = Screens.AppOverView) {

                composable<Screens.Splash> {
                    SplashScreen()
                    coroutineScope.launch {
                        delay(1500L)
                        navController.navigate(Screens.AppOverView)
                    }
                }

                composable<Screens.AppOverView> {
                    AppOverviewScreen {
                        navController.navigate(Screens.Auth)
                    }
                }

                composable<Screens.Auth> {
                    AuthScreen(){
                        if (it == AuthNavEvents.NavigateToRegisterScreen) navController.navigate(Screens.Register)
                        else if (it == AuthNavEvents.NavigateToLoginScreen) navController.navigate(Screens.Login)
                    }
                }

                composable<Screens.Register> {
                    val registerViewModel = koinViewModel<RegisterViewModel>()
                    RegisterScreen(registerViewModel.state.collectAsState().value,registerViewModel::onEvent){

                    }
                }

                composable<Screens.Login> {
                    val loginViewModel = koinViewModel<LoginViewModel>()
                    LoginScreen(
                        loginViewModel.state.collectAsState().value,
                        loginViewModel::onEvent
                    ){

                    }
                }
            }
        }
    }
}

class DemoViewModelOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

