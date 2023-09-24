package ir.tinydeveloper.fallingwords

import android.app.Application
import ir.tinydeveloper.fallingwords.di.AppModule
import ir.tinydeveloper.fallingwords.di.AppModuleImpl

class Application: Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}