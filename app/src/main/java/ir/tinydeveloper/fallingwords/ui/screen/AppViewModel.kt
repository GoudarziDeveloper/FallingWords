package ir.tinydeveloper.fallingwords.ui.screen

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.tinyDeveloper.core.util.UiEvent
import ir.tinyDeveloper.core.util.UiText
import ir.tinydeveloper.fallingwords.R
import ir.tinydeveloper.fallingwords.domain.use_caes.UseCases
import ir.tinydeveloper.fallingwords.ui.model.AppEvent
import ir.tinydeveloper.fallingwords.ui.model.AppState
import ir.tinydeveloper.fallingwords.ui.model.WordItemModel
import ir.tinydeveloper.fallingwords.ui.navigation.BottomSheetSectionRoutes
import ir.tinydeveloper.fallingwords.utils.ErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class AppViewModel(private val useCases: UseCases): ViewModel() {
    var isSwipeToTheLeft by mutableStateOf(false)
    var state by mutableStateOf(AppState(
        horizontalDraggableState = DraggableState { delta ->
            isSwipeToTheLeft = delta < 0
        },
    ))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AppEvent){
        when(event){
            is AppEvent.OnNavigateScreen -> {
                state = state.copy(screen = event.route)
            }
            is AppEvent.OnNavigateBottomSheetSection -> {
                state = state.copy(bottomSheetSection = event.route)
            }
            is AppEvent.OnGetWords -> {
                getWords()
            }
            is AppEvent.OnStartGame -> {
                generateQuestions(event.questionCount)
            }
            is AppEvent.OnNextQuestion -> {
                state.questions?.let {
                    state = if (state.currentQuestion + 1 < it.count())
                        state.copy(currentQuestion = state.currentQuestion + 1)
                    else{
                        findResult()
                        state.copy(
                            gameIsRun = false,
                            currentQuestion = 0,
                            bottomSheetSection = BottomSheetSectionRoutes.RESULT_INFO
                        )
                    }
                }
            }
            is AppEvent.SetQuestionTime -> {
                if (state.questions?.get(state.currentQuestion)?.timeToAnswer == null){
                    state.questions?.get(state.currentQuestion)?.timeToAnswer = System.currentTimeMillis()
                }else {
                    state.questions?.get(state.currentQuestion)?.timeToAnswer = System.currentTimeMillis() - state.questions?.get(state.currentQuestion)!!.timeToAnswer!!
                }
            }
            is AppEvent.SetQuestionAnswer -> {
                state.questions?.get(state.currentQuestion)?.userAnswer = event.answer
            }
            is AppEvent.StartTimer -> {
                state = state.copy(shouldRunningTimer = true)
                viewModelScope.launch(Dispatchers.IO){
                    timer()
                }
            }
            is AppEvent.StopTimer -> {
                state = state.copy(shouldRunningTimer = false)
            }
        }
    }

    private fun getWords(){
        state = state.copy(isLoading = true)
        useCases.getWordsUseCase { response ->
            response.onSuccess {
                state = state.copy(isLoading = false, words = it)
            }
            response.onFailure {
                state = state.copy(isLoading = false)
                viewModelScope.launch {
                    when(it.message){
                        ErrorHandler.SERVER_ERROR -> {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.an_error)))
                        }
                        ErrorHandler.INTERNET_ERROR -> {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.connection_error)))
                        }
                    }
                }
            }
        }
    }

    private fun generateQuestions(count: Int){
        state.words?.let {
            val list = mutableListOf<WordItemModel>()
            for (item in 0 until count){
                val newItem = it[Random.nextInt(0, it.count())]
                val answer = if (Random.nextInt(10) % 2 == 0){
                    newItem.text_spa
                }else {
                    it[Random.nextInt(0, it.size)].text_spa
                }
                list.add(
                    WordItemModel(question = newItem.text_eng, answer = newItem.text_spa, generatedAnswer = answer, timeToAnswer = null, userAnswer = null)
                )
            }
            state = state.copy(questions = list, gameIsRun = true)
        }
    }

    private fun findResult(){
        state.questions?.let {
            var wrongAnswer = 0
            var noAnswer = 0
            var correctAnswer = 0
            for (item in it){
                if (item.userAnswer != null){
                    if (item.answer == item.generatedAnswer && item.userAnswer!!){
                        correctAnswer++
                    }else if (item.answer != item.generatedAnswer && !item.userAnswer!!){
                        correctAnswer++
                    }else {
                        wrongAnswer++
                    }
                }else {
                    noAnswer++
                }
            }
            state = state.copy(correctAnswer = correctAnswer, wrongAnswer = wrongAnswer, noAnswer = noAnswer)
        }
    }

    private suspend fun timer(){
        if (state.shouldRunningTimer){
            delay(10)
            state = state.copy(runningTime = state.runningTime + 10)
            timer()
        }
    }
}

class AppViewModelFactory(private val useCases: UseCases): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppViewModel(useCases = useCases) as T
    }
}
