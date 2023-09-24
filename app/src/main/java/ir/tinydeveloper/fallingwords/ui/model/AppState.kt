package ir.tinydeveloper.fallingwords.ui.model

import androidx.compose.foundation.gestures.DraggableState
import ir.tinydeveloper.fallingwords.domain.model.Word
import ir.tinydeveloper.fallingwords.ui.navigation.BottomSheetSectionRoutes
import ir.tinydeveloper.fallingwords.ui.navigation.Routes

data class AppState(
    val screen: String = Routes.SPLASH_SCREEN,
    val isLoading: Boolean = false,
    val bottomSheetSection: String = BottomSheetSectionRoutes.GAME_INFO,
    val words: List<Word>? = null,
    val questions: List<WordItemModel>? = null,
    val gameIsRun: Boolean = false,
    val currentQuestion: Int = 0,
    val horizontalDraggableState: DraggableState,
    val correctAnswer: Int = 0,
    val noAnswer: Int = 0,
    val wrongAnswer: Int = 0,
    val runningTime: Long = 0,
    val shouldRunningTimer: Boolean = false
)
