package ir.tinydeveloper.fallingwords.ui.model

sealed class AppEvent {
    data class OnNavigateScreen(val route: String): AppEvent()
    data class OnNavigateBottomSheetSection(val route: String): AppEvent()
    object OnGetWords: AppEvent()
    data class OnStartGame(val questionCount: Int): AppEvent()
    object OnNextQuestion: AppEvent()
    object SetQuestionTime: AppEvent()
    data class SetQuestionAnswer(val answer: Boolean?): AppEvent()
    object StartTimer: AppEvent()
    object StopTimer: AppEvent()
}