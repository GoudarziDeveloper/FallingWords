package ir.tinydeveloper.fallingwords.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import ir.tinydeveloper.fallingwords.R

@Composable
fun WordItem(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize
){
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ic_balloons),
            contentDescription = stringResource(id = R.string.balloons_image)
        )
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.ic_box),
                contentDescription = stringResource(R.string.box_image)
            )
            Text(text = text, fontSize = fontSize, fontWeight = FontWeight.Bold)
        }
    }
}