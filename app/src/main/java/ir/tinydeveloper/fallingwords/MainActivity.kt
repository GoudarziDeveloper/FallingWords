package ir.tinydeveloper.fallingwords

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.tinyDeveloper.core.util.UiEvent
import ir.tinydeveloper.fallingwords.ui.model.AppEvent
import ir.tinydeveloper.fallingwords.ui.navigation.Routes
import ir.tinydeveloper.fallingwords.ui.screen.AppViewModel
import ir.tinydeveloper.fallingwords.ui.screen.AppViewModelFactory
import ir.tinydeveloper.fallingwords.ui.screen.main_screen.MainScreen
import ir.tinydeveloper.fallingwords.ui.screen.splash_screen.SplashScreen
import ir.tinydeveloper.fallingwords.ui.theme.FallingWordsTheme

class MainActivity : ComponentActivity() {
    private lateinit var appViewModel: AppViewModel
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        setContent {
            FallingWordsTheme {
                val snackBarState = remember { SnackbarHostState() }
                appViewModel = viewModel(factory = AppViewModelFactory(Application.appModule.useCases))
                val state = appViewModel.state
                val context = LocalContext.current
                LaunchedEffect(key1 = Unit){
                    appViewModel.uiEvent.collect {
                        when(it){
                            is UiEvent.ShowSnackBar -> {
                                snackBarState.showSnackbar(it.message.asString(context))
                            }
                            is UiEvent.Navigate -> {
                                appViewModel.onEvent(AppEvent.OnNavigateScreen(it.route))
                            }
                            else -> Unit
                        }
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackBarState) },
                    content = {
                        when(state.screen){
                            Routes.SPLASH_SCREEN -> {
                                SplashScreen(appViewModel = appViewModel)
                            }
                            Routes.MAIN_SCREEN -> {
                                MainScreen(appViewModel = appViewModel)
                            }
                        }
                    }
                )
            }
        }
    }
}
