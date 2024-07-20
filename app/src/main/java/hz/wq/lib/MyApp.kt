package hz.wq.lib

import android.app.Application

class MyApp : Application() {

    companion object {
        @Volatile
        private var INSTANCE: MyApp? = null

        val instance: MyApp
            get() {
                return INSTANCE ?: synchronized(this) {
                    INSTANCE ?: MyApp().also { INSTANCE = it }
                }
            }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}