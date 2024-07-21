package hz.wq.common.util

import hz.wq.common.util.log.LogUtils.wqLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DebounceUtil {
    // 用于存储上一次调用防抖函数的时间
    private var lastCallTimeMillisMap = mutableMapOf<String, Long>()

    // 用于存储防抖函数的Job
    private var job: Job? = null

    /**
     * 在指定的CoroutineScope作用域中，实现防抖功能。
     * 防抖动是一种节流技术，用于减少事件处理的频率。例如，当用户快速连续输入时，可以使用防抖动来降低处理频率，从而避免过多的计算或请求。
     *
     * @param delayMillis 延迟执行的时间，以毫秒为单位。
     * @param scope CoroutineScope作用域，在该作用域下启动一个新的协程。
     * @param block 要延迟执行的协程块。
     */
    fun debounce(delayMillis: Long, scope: CoroutineScope, block: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            block()
        }
    }

    /**
     * 检查是否通过了防抖动检查。
     *
     * 防抖动是一种技术，用于限制操作的频率，以防止在短时间内进行过多的操作，例如连续点击按钮。
     * 该函数检查自上一次调用以来是否已经过了指定的时间间隔。如果已经过了指定的时间间隔，则允许操作通过，
     * 并更新上一次调用的时间。如果还没有过指定的时间间隔，则操作被阻止。
     *
     * @param debounceKey 用于标识防抖动的键，可以用于区分不同的操作。默认值为"commonDebounce"。
     * @param debounceTimeMillis 指定的时间间隔，以毫秒为单位。默认值为600毫秒。
     * @return 如果已经过了指定的时间间隔，则返回true，否则返回false。
     */
    fun isPassDebounce(debounceKey: String="commonDebounce", debounceTimeMillis: Long = 600):Boolean {
            val currentTimeMillis = System.currentTimeMillis()
            var lastCallTimeMillis = lastCallTimeMillisMap[debounceKey] ?: 0L

            "currentTimeMillis:$currentTimeMillis".wqLog()
            "lastCallTimeMillis:$lastCallTimeMillis".wqLog()
            "debounceTimeMillis:$debounceTimeMillis".wqLog()

            if (currentTimeMillis - lastCallTimeMillis >= debounceTimeMillis) {
                lastCallTimeMillisMap[debounceKey] = currentTimeMillis
                // 如果距离上次调用已经超过了防抖时间，则通过
                return true
            } else {
                // 如果距离上次调用没有超过防抖时间，则忽略这次调用
                // 可以通过某种方式记录这次调用，以便在将来的某个时间点执行
                return false
            }
    }
}