package ir.tinydeveloper.fallingwords.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val spaceDefault: Dp = 0.dp,
    val spaceSmallest : Dp = 2.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceLargeSmall: Dp = 12.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceSmallLarge: Dp = 24.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp
)
val LocalSpacing = compositionLocalOf{ Spacing() }

data class Sizing(
    val bottomSheetMinSize: Dp = 150.dp,
    val halfAlpha: Float = 0.5f,
    val half: Float = 0.5f,
    val splashScreenTime: Long = 2000L,
    val bottomSheetExpandedRadius: Dp = 0.dp,
    val bottomSheetRadius: Dp = 50.dp,
    val startPositionWordItem: Float = -240f,
    val endPositionWordItem: Float = 700f,
    val normalLine: Float = 145f,
    val leftLine: Float = 0f,
    val rightLine: Float = 290f,
    val questionsCount: Int = 5,
    val resultChartInfoBox: Dp = 10.dp,
    val resultBarRadius: CornerRadius = CornerRadius(100f),
    val resultBarHeight: Dp = 24.dp,
)
val LocalSizing = compositionLocalOf { Sizing() }