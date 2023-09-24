package ir.tinydeveloper.fallingwords.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ir.tinydeveloper.fallingwords.ui.theme.Spacing

@Composable
fun ResultBox(modifier: Modifier = Modifier, value: String, title: String, spacing: Spacing){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.displayMedium.fontSize
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
    }
}