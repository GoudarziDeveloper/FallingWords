package ir.tinydeveloper.fallingwords.domain.use_caes

import ir.tinydeveloper.fallingwords.domain.model.Word
import ir.tinydeveloper.fallingwords.domain.repository.DataSource

class GetWordsUseCase(private val dataSource: DataSource) {
    operator fun invoke(setWordsResponse: (Result<List<Word>>) -> Unit){
        dataSource.getWords(setWordsResponse = setWordsResponse)
    }
}