package hz.wq.httplib.utils

import com.blankj.utilcode.util.LogUtils
import java.text.SimpleDateFormat
import java.util.Date

//import kotlin.reflect.full.declaredMemberProperties
//import kotlin.reflect.full.memberProperties
//import kotlin.reflect.jvm.isAccessible
//import kotlin.reflect.jvm.javaField
var isLogTest = true
fun String.wqLog(): String {
    if (isLogTest) {
        println("wq：${this}")
    } else {
        LogUtils.i("wq：${this}")
    }

    return this
}

//fun String.wqLog() = println("wq：${this}")
fun String.lookCurrentTime() {
    var startTime = Date()
    var str = SimpleDateFormat("mm:ss.SSS").format(startTime)
    "lookTime $this :${str}".wqLog()
}

fun String.replaceBlank(): String {
    return replace(" ", "")
}


///**
// * 打印对象的属性
// */
//fun Any.logObjectProperties(isHasPrivate: Boolean = false) {
//    val properties = if (isHasPrivate) {
//        this::class.declaredMemberProperties
//    } else {
//        this::class.memberProperties
//    }
//    "---------------------- start 打印对象属性  ${this.javaClass.simpleName} ---------------------- ".wqLog()
//    properties.forEach { property ->
//        property.isAccessible = true
//        "属性 ${property.name}: ${property.javaField?.get(this)}".wqLog()
//    }
//    "---------------------- end 打印对象属性 ${this.javaClass.simpleName} ---------------------- ".wqLog()
//}