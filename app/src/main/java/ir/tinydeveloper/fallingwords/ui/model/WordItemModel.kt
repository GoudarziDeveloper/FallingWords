package ir.tinydeveloper.fallingwords.ui.model

data class WordItemModel(
    val question: String,
    val answer: String,
    var userAnswer: Boolean?,
    val generatedAnswer: String,
    var timeToAnswer: Long? = null
)
