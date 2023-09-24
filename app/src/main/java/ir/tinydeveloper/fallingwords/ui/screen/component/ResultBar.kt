package ir.tinydeveloper.fallingwords.ui.screen.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import ir.tinydeveloper.fallingwords.ui.theme.LocalSizing

@Composable
fun ResultBar(
    correctAnswer: Int,
    noAnswer: Int,
    modifier: Modifier,
    wrongAnswerColor: Color,
    correctAnswerColor: Color,
    noAnswerColor: Color,
    questionCount: Int
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    val correctAnswerWidthRatio = remember {
        Animatable(0f)
    }
    val wrongAnswerWidthRatio = remember {
        Animatable(0f)
    }
    val noAnswerWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = correctAnswer) {
        correctAnswerWidthRatio.animateTo(correctAnswer.toFloat(), animationSpec = tween(durationMillis = 500))
    }
    LaunchedEffect(key1 = noAnswer) {
        noAnswerWidthRatio.animateTo(noAnswer.toFloat(), animationSpec = tween(durationMillis = 500))
    }
    LaunchedEffect(key1 = questionCount) {
        wrongAnswerWidthRatio.animateTo(questionCount.toFloat(), animationSpec = tween(durationMillis = 500))
    }

    val sizing = LocalSizing.current
    Canvas(modifier = modifier) {
        val correctWidth = (correctAnswerWidthRatio.value * size.width) / questionCount
        val noAnswerWidth = (correctAnswerWidthRatio.value * size.width) / questionCount + correctWidth
        drawRoundRect(
            color = wrongAnswerColor,
            size = Size(width = size.width, height = size.height),
            cornerRadius = sizing.resultBarRadius
        )
        drawRoundRect(
            color = backgroundColor,
            size = Size(width = noAnswerWidth, height = size.height),
            cornerRadius = sizing.resultBarRadius
        )
        drawRoundRect(
            color = noAnswerColor,
            size = Size(width = noAnswerWidth, height = size.height),
            cornerRadius = sizing.resultBarRadius
        )
        drawRoundRect(
            color = backgroundColor,
            size = Size(width = correctWidth, height = size.height),
            cornerRadius = sizing.resultBarRadius
        )
        drawRoundRect(
            color = correctAnswerColor,
            size = Size(width = correctWidth, height = size.height),
            cornerRadius = sizing.resultBarRadius
        )
    }
}