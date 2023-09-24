package ir.tinydeveloper.fallingwords.ui.screen.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.model.AppEvent
import ir.tinydeveloper.fallingwords.ui.navigation.Routes
import ir.tinydeveloper.fallingwords.ui.screen.AppViewModel
import ir.tinydeveloper.fallingwords.ui.theme.LocalSizing
import ir.tinydeveloper.fallingwords.ui.theme.LocalSpacing
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    appViewModel: AppViewModel
){
    val spacing = LocalSpacing.current
    val sizing = LocalSizing.current
    var timerIsDown by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit){
        appViewModel.onEvent(AppEvent.OnGetWords)
        delay(sizing.splashScreenTime)
        timerIsDown = true
    }
    LaunchedEffect(key1 = timerIsDown){
        if (timerIsDown && !appViewModel.state.isLoading && appViewModel.state.words != null)
            appViewModel.onEvent(AppEvent.OnNavigateScreen(Routes.MAIN_SCREEN))
    }
    LaunchedEffect(key1 = appViewModel.state.isLoading){
        if (timerIsDown && !appViewModel.state.isLoading && appViewModel.state.words != null)
            appViewModel.onEvent(AppEvent.OnNavigateScreen(Routes.MAIN_SCREEN))
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(spacing.spaceMedium)){
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(sizing.half)
            .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_balloons),
                contentDescription = stringResource(id = R.string.balloons_image)
            )
            Box(contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(id = R.drawable.ic_box),
                    contentDescription = stringResource(id = R.string.box_image)
                )
                Text(text = stringResource(id = R.string.splash_app_name), fontWeight = FontWeight.Bold)
            }
        }
        if (!appViewModel.state.isLoading && appViewModel.state.words == null)
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = spacing.spaceExtraLarge)
                    .clickable { appViewModel.onEvent(AppEvent.OnGetWords) },
                text = stringResource(id = R.string.try_again)
            )
        Text(modifier = Modifier.align(Alignment.BottomCenter), text = stringResource(id = R.string.beranding))
    }
}