package ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.screen.AppViewModel
import ir.tinydeveloper.fallingwords.ui.screen.component.ResultBar
import ir.tinydeveloper.fallingwords.ui.screen.component.ResultBarFooter
import ir.tinydeveloper.fallingwords.ui.screen.component.ResultBox
import ir.tinydeveloper.fallingwords.ui.theme.LocalSizing
import ir.tinydeveloper.fallingwords.ui.theme.LocalSpacing

@Composable
fun ResultInfo(appViewModel: AppViewModel){
    val sizing = LocalSizing.current
    val spacing = LocalSpacing.current
    val wrongAnswerColor = MaterialTheme.colorScheme.primary
    val noAnswerColor = MaterialTheme.colorScheme.secondary.copy(sizing.halfAlpha)
    val correctColor = MaterialTheme.colorScheme.tertiary.copy(0.8f)
    val state = appViewModel.state
    Column(modifier = Modifier.padding(spacing.spaceMedium)) {
        ResultBar(
            correctAnswer = state.correctAnswer,
            noAnswer = state.noAnswer,
            modifier = Modifier
                .fillMaxWidth()
                .height(sizing.resultBarHeight),
            wrongAnswerColor = wrongAnswerColor,
            correctAnswerColor = correctColor,
            noAnswerColor = noAnswerColor,
            questionCount = sizing.questionsCount
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ResultBarFooter(
            sizing = sizing,
            spacing = spacing,
            noAnswerColor = noAnswerColor,
            wrongAnswerColor = wrongAnswerColor,
            correctAnswerColor = correctColor
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            ResultBox(
                modifier = Modifier.weight(1f),
                value = state.correctAnswer.toString(),
                title = stringResource(id = R.string.correct),
                spacing = spacing
            )
            ResultBox(
                modifier = Modifier.weight(1f),
                value = state.noAnswer.toString(),
                title = stringResource(id = R.string.no_answer),
                spacing = spacing
            )
            ResultBox(
                modifier = Modifier.weight(1f),
                value = state.wrongAnswer.toString(),
                title = stringResource(id = R.string.incorrect),
                spacing = spacing
            )
        }
    }
}