package hz.wq.lib

import android.app.Application
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyApp : Application() {
    private val appScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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
        appScope.launch(Dispatchers.IO) {
            ToastUtils
                .getDefaultMaker()
                .setMode(ToastUtils.MODE.DARK)
                .setGravity(Gravity.CENTER, 0, 20)
        }
    }

}