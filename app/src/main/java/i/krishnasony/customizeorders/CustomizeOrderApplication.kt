package i.krishnasony.customizeorders

import android.app.Application
import com.facebook.stetho.Stetho
import i.krishnasony.customizeorders.di.appModule
import i.krishnasony.customizeorders.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomizeOrderApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin

        startKoin{
            androidLogger()
            androidContext(this@CustomizeOrderApplication)
            modules(listOf(appModule, roomModule))
        }
        Stetho.initializeWithDefaults(this)


    }

}