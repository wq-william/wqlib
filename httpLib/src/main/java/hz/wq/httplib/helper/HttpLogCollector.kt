package hz.wq.httplib.helper

import hz.wq.httplib.bean.LogRequestBean
import hz.wq.httplib.bean.LogResponseBean
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * 定义全局的 日志收集 对象
 */
object HttpLogCollector {
    private val _logRequestFlow = MutableSharedFlow<LogRequestBean>()
    private val _logResponseFlow = MutableSharedFlow<LogResponseBean>()

    //使用log收集器
    val logRequestFlow: SharedFlow<LogRequestBean> get() = _logRequestFlow
    val logResponseFlow: SharedFlow<LogResponseBean> get() = _logResponseFlow


    suspend fun request(requestLog: LogRequestBean) {
        _logRequestFlow.emit(requestLog)
    }

    suspend fun response(responseLog: LogResponseBean) {
        _logResponseFlow.emit(responseLog)
    }
}
