package ir.tinydeveloper.fallingwords.di

import android.content.Context
import com.google.gson.Gson
import ir.tinydeveloper.fallingwords.data.remote.RemoteRequests
import ir.tinydeveloper.fallingwords.data.repository.DataSourceImpl
import ir.tinydeveloper.fallingwords.domain.repository.DataSource
import ir.tinydeveloper.fallingwords.domain.use_caes.GetWordsUseCase
import ir.tinydeveloper.fallingwords.domain.use_caes.UseCases
import okhttp3.OkHttpClient

interface AppModule{
    val okHttpClient: OkHttpClient
    val requests: RemoteRequests
    val gson: Gson
    val dataSource: DataSource
    val useCases: UseCases
}
class AppModuleImpl(private val appContext: Context): AppModule {
    override val okHttpClient: OkHttpClient by lazy {
        OkHttpClient()
    }
    override val requests: RemoteRequests by lazy {
        RemoteRequests()
    }
    override val gson: Gson by lazy {
        Gson()
    }
    override val dataSource: DataSource by lazy {
        DataSourceImpl(okHttpClient, gson, requests.getWordsRequest())
    }
    override val useCases: UseCases by lazy {
        UseCases(getWordsUseCase = GetWordsUseCase(dataSource))
    }
}