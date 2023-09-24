package ir.tinydeveloper.fallingwords.ui.screen.main_screen

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.model.AppEvent
import ir.tinydeveloper.fallingwords.ui.navigation.BottomSheetSectionRoutes
import ir.tinydeveloper.fallingwords.ui.screen.AppViewModel
import ir.tinydeveloper.fallingwords.ui.screen.component.WordItem
import ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section.GameInfo
import ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section.ResultInfo
import ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section.RunTimeInfo
import ir.tinydeveloper.fallingwords.ui.theme.LocalSizing
import ir.tinydeveloper.fallingwords.ui.theme.LocalSpacing
import ir.tinydeveloper.fallingwords.utils.toShowTime
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(appViewModel: AppViewModel){
    val spacing = LocalSpacing.current
    val sizing = LocalSizing.current
    val state = appViewModel.state
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded)
    )
    val wordItemAnim = remember{ Animatable(initialValue = sizing.startPositionWordItem) }
    val wordItemAnswerAnim = remember{ Animatable(initialValue = sizing.normalLine) }
    val animatedRadius by animateDpAsState(
        targetValue =
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) sizing.bottomSheetExpandedRadius
        else sizing.bottomSheetRadius, label = ""
    )
    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue){
        when(state.bottomSheetSection){
            BottomSheetSectionRoutes.GAME_INFO -> {
                if (scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded){
                    appViewModel.onEvent(AppEvent.OnNavigateBottomSheetSection(BottomSheetSectionRoutes.RUN_TIME_INFO))
                    appViewModel.onEvent(AppEvent.OnStartGame(questionCount = sizing.questionsCount))
                }
            }
            BottomSheetSectionRoutes.RUN_TIME_INFO -> {
                if (scaffoldState.bottomSheetState.targetValue == SheetValue.Expanded){
                    appViewModel.onEvent(AppEvent.OnNavigateBottomSheetSection(BottomSheetSectionRoutes.RESULT_INFO))
                }
            }
            BottomSheetSectionRoutes.RESULT_INFO -> {
            }
        }
    }
    BottomSheetScaffold(
        sheetContainerColor = MaterialTheme.colorScheme.primaryContainer,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topEnd = animatedRadius, topStart = animatedRadius),
        sheetContent = {
            when(state.bottomSheetSection){
                BottomSheetSectionRoutes.GAME_INFO -> {
                    GameInfo()
                }
                BottomSheetSectionRoutes.RUN_TIME_INFO -> {
                    state.questions?.get(state.currentQuestion)?.let { question ->
                        RunTimeInfo(
                            question = question.question,
                            questionNumber = state.currentQuestion + 1,
                            userAnswer = question.userAnswer,
                            time = state.runningTime.toShowTime()
                        )
                    }
                }
                BottomSheetSectionRoutes.RESULT_INFO -> {
                    LaunchedEffect(key1 = true){
                        scaffoldState.bottomSheetState.expand()
                    }
                    ResultInfo(appViewModel)
                }
            }
        },
        sheetPeekHeight = sizing.bottomSheetMinSize
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    state = state.horizontalDraggableState,
                    onDragStopped = {
                        if (wordItemAnswerAnim.value == sizing.normalLine) {
                            if (!appViewModel.isSwipeToTheLeft) {
                                wordItemAnswerAnim.animateTo(
                                    targetValue = sizing.rightLine,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearEasing
                                    )
                                )
                            } else {
                                wordItemAnswerAnim.animateTo(
                                    targetValue = sizing.leftLine,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearEasing
                                    )
                                )
                            }
                            appViewModel.onEvent(AppEvent.StopTimer)
                            appViewModel.onEvent(AppEvent.SetQuestionTime)
                        }
                    },
                    orientation = Orientation.Horizontal
                )
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_background),
                contentDescription = stringResource(R.string.backgtround_image)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = spacing.spaceLarge)
                    .padding(bottom = spacing.spaceSmall)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.ic_wrong_bucket),
                    contentDescription = stringResource(id = R.string.wrong_bucket_image)
                )
                Image(
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.ic_normal_bucket),
                    contentDescription = stringResource(id = R.string.normal_bucket_image)
                )
                Image(
                    modifier = Modifier.weight(1f),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(id = R.drawable.ic_correct_bucket),
                    contentDescription = stringResource(id = R.string.correct_bucket_image)
                )
            }
            state.questions?.get(state.currentQuestion)?.let {
                LaunchedEffect(key1 = state.currentQuestion){
                    if (state.gameIsRun){
                        appViewModel.onEvent(AppEvent.StartTimer)
                        appViewModel.onEvent(AppEvent.SetQuestionTime)
                        wordItemAnswerAnim.animateTo(
                            targetValue = sizing.normalLine,
                            animationSpec = tween(durationMillis = 0, easing = LinearEasing)
                        )
                        wordItemAnim.animateTo(
                            targetValue = sizing.startPositionWordItem,
                            animationSpec = tween(durationMillis = 0, easing = LinearEasing)
                        )
                        wordItemAnim.animateTo(
                            targetValue = sizing.endPositionWordItem,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
                        )
                        if (wordItemAnim.value == sizing.endPositionWordItem){
                            appViewModel.onEvent(AppEvent.SetQuestionAnswer(
                                when(wordItemAnswerAnim.value){
                                    sizing.normalLine -> {
                                        appViewModel.onEvent(AppEvent.SetQuestionTime)
                                        null
                                    }
                                    sizing.leftLine -> false
                                    sizing.rightLine -> true
                                    else -> { null }
                                }
                            ))
                            appViewModel.onEvent(AppEvent.OnNextQuestion)
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    WordItem(
                        modifier = Modifier
                            .fillMaxWidth(sizing.half)
                            .offset(y = wordItemAnim.value.dp, x = wordItemAnswerAnim.value.dp)
                            .padding(spacing.spaceMedium),
                        text = it.answer
                    )
                }
            }
        }
    }
}