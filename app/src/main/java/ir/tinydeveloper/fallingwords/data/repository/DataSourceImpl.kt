package ir.tinydeveloper.fallingwords.data.repository

import com.google.gson.Gson
import ir.tinydeveloper.fallingwords.domain.model.Word
import ir.tinydeveloper.fallingwords.domain.repository.DataSource
import ir.tinydeveloper.fallingwords.utils.ErrorHandler
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class DataSourceImpl(private val okHttpClient: OkHttpClient, private val gson: Gson, private val request: Request): DataSource {
    override fun getWords(setWordsResponse: (Result<List<Word>>) -> Unit) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.body != null) {
                    setWordsResponse(Result.success(gson.fromJson(response.body!!.string(), Array<Word>::class.java).asList()))
                } else {
                    setWordsResponse(Result.failure(Exception(ErrorHandler.SERVER_ERROR)))
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                setWordsResponse(Result.failure(Exception(ErrorHandler.INTERNET_ERROR)))
            }
        })
    }
}