package ir.tinydeveloper.fallingwords.ui.screen.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.theme.Sizing
import ir.tinydeveloper.fallingwords.ui.theme.Spacing

@Composable
fun ResultBarFooter(
    sizing: Sizing,
    spacing: Spacing,
    noAnswerColor: Color,
    wrongAnswerColor: Color,
    correctAnswerColor: Color,
){
    Row(modifier = Modifier.fillMaxWidth()) {
        ChartInfoBox(
            modifier = Modifier.weight(1f),
            color = correctAnswerColor,
            title = stringResource(id = R.string.correct),
            sizing = sizing,
            spacing = spacing
        )
        ChartInfoBox(
            modifier = Modifier.weight(1f),
            color = noAnswerColor,
            title = stringResource(id = R.string.no_answer),
            sizing = sizing,
            spacing = spacing
        )
        ChartInfoBox(
            modifier = Modifier.weight(1f),
            color = wrongAnswerColor,
            title = stringResource(id = R.string.incorrect),
            sizing = sizing,
            spacing = spacing
        )
    }
}

@Composable
fun ChartInfoBox(modifier: Modifier, color: Color, title: String, sizing: Sizing, spacing: Spacing){
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(sizing.resultChartInfoBox)){
            drawRect(
                color = color,
                size = size
            )
        }
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(text = title, fontSize = MaterialTheme.typography.labelLarge.fontSize, fontWeight = FontWeight.Bold)
    }
}
