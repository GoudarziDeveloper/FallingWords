package ir.tinydeveloper.fallingwords.data.remote

import ir.tinydeveloper.fallingwords.utils.Constants.SERVER_URL
import okhttp3.Request

class RemoteRequests {
    fun getWordsRequest(): Request{
        return Request.Builder().url(SERVER_URL).build()
    }
}