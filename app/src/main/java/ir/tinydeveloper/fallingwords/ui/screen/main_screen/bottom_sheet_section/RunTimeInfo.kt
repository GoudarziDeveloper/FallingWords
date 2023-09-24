package ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.theme.LocalSizing
import ir.tinydeveloper.fallingwords.ui.theme.LocalSpacing

@Composable
fun RunTimeInfo(questionNumber: Int, question: String, userAnswer: Boolean?, time: String){
    val spacing = LocalSpacing.current
    val sizing = LocalSizing.current
    Row(modifier = Modifier.padding(spacing.spaceMedium)) {
        Column(modifier = Modifier.weight(sizing.half)) {
            Text(
                text = question,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                text = stringResource(id = R.string.question) + " " + questionNumber,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
        Column(modifier = Modifier.weight(sizing.half)) {
            Text(
                text = stringResource(id = R.string.time) + " " + time,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Text(
                text = stringResource(id = R.string.run_time_status) + " " +
                        when (userAnswer) {
                            null -> stringResource(id = R.string.no_answer)
                            false -> stringResource(id = R.string.incorrect)
                            else -> stringResource(id = R.string.correct)
                        },
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}