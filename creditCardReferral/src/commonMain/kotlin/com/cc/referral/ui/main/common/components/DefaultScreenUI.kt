package com.cc.referral.ui.main.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Colors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cc.referral.AppColors
import com.cc.referral.business.domain.ModalBottomSheetInfo
import com.cc.referral.business.domain.PlatformInfo
import com.cc.referral.business.domain.ProgressBarState
import com.cc.referral.business.domain.UIComponent
import com.cc.referral.getPlatform
import com.cc.referral.ui.onboarding.components.EnterOTPBs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DefaultScreenUI(
    modifier: Modifier = Modifier,
    uiComponent: UIComponent? = UIComponent.None,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    isDrawerEnabled: Boolean = false,
    isTopBarVisible: Boolean = true,
    navController: NavController? = null,
    modalBottomSheetInfo: ModalBottomSheetInfo? = null,
    sheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
    onModalBottomSheetClosed: () -> Unit = {},
    onDialogDismissed: () -> Unit = {},
    bottomBar: (@Composable () -> Unit)? = null,
    topBar: @Composable (scaffoldState: ScaffoldState?) -> Unit = { scaffoldState ->

        val coroutineScope = rememberCoroutineScope()


    },
    content: @Composable () -> Unit,
) {


    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val drawerLevelModalBottomSheetInfo: MutableState<ModalBottomSheetInfo?> =
        remember { mutableStateOf(null) }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    LaunchedEffect(modalBottomSheetState.isVisible) {
        if (!modalBottomSheetState.isVisible) {
            drawerLevelModalBottomSheetInfo.value = null
            onModalBottomSheetClosed()
        }
    }

    LaunchedEffect(modalBottomSheetInfo, drawerLevelModalBottomSheetInfo.value) {
        if (modalBottomSheetInfo != null && modalBottomSheetInfo != ModalBottomSheetInfo.None) {
            modalBottomSheetState.show()
        } else if (drawerLevelModalBottomSheetInfo.value != null) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = AppColors.white,
        sheetState = modalBottomSheetState,
        sheetContent = {

            val modalBottomSheetInfo = modalBottomSheetInfo ?: drawerLevelModalBottomSheetInfo.value
            when (modalBottomSheetInfo) {

                is ModalBottomSheetInfo.EnterOTP -> {
                    val otpInput: MutableState<String> = remember { mutableStateOf("") }

                    EnterOTPBs(
                        phoneNumber = modalBottomSheetInfo.phoneNumber,
                        otpInput = otpInput,
                        onVerifyClicked = {
                            modalBottomSheetInfo.onVerifyClicked(it)
                        }
                    )
                }

                ModalBottomSheetInfo.None -> {}

                else -> {}
            }
        },
        scrimColor = Color.Transparent,
        sheetShape = sheetShape,
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                if (isTopBarVisible) {
                    topBar(scaffoldState)
                }
            },
            bottomBar = {
                bottomBar?.invoke()
            },
            drawerGesturesEnabled = isDrawerEnabled,
            drawerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
        ) {

            Box(
                modifier = modifier.padding(it).fillMaxSize()
                    .pointerInput(Unit) {
                        if (getPlatform() is PlatformInfo.IOS) {
                            detectHorizontalDragGestures { change, dragAmount ->
                                if (dragAmount > 0) {
                                    // Start detecting the drag from the left edge
                                    if (change.position.x < 100) {  // Close to the left edge
                                        // Detect swipe movement towards the right
                                        if (dragAmount > 10f) { // If drag is greater than 10 pixels
//                                    onBackSwipeDetected()  // Trigger back swipe action
                                            println("87868687 onBackSwipeDetected: $it")
                                            navController?.popBackStack()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {

                content()

                if (progressBarState is ProgressBarState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}