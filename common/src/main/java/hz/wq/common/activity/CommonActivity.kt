package hz.wq.common.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding

/**
 * 创建者: W~Q
 * 描述: 通用的Activity ViewBinding
 */
abstract class CommonActivity<V : ViewBinding> : ComponentActivity() {
    private lateinit var mBinding: V
        private set

    abstract fun viewBindingInflater(): (inflater: LayoutInflater) -> V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = viewBindingInflater().invoke(layoutInflater)
        setContentView(mBinding.root)
    }

}