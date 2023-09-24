package ir.tinydeveloper.fallingwords.domain.repository

import ir.tinydeveloper.fallingwords.domain.model.Word

interface DataSource {
    fun getWords(setWordsResponse: (Result<List<Word>>) -> Unit)
}