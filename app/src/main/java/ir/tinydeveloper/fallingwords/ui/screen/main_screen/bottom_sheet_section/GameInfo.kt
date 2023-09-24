package ir.tinydeveloper.fallingwords.ui.screen.main_screen.bottom_sheet_section

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.ui.theme.LocalSpacing

@Composable
fun GameInfo(){
    val spacing = LocalSpacing.current
    Column(modifier = Modifier
        .scrollable(rememberScrollState(), Orientation.Vertical)
        .fillMaxWidth()
        .padding(spacing.spaceMedium)){
        Text(
            text = stringResource(id = R.string.about_game),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = stringResource(id = R.string.game_info),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
        Text(
            text = stringResource(id = R.string.to_start_game),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}