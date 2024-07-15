package hz.wq.httplib.bean

/**
 * 发送的日志bean
 */
data class LogRequestBean(
    var url: String,
    var method: String,
    var heads: Map<String, List<String>>? = null,
    var params: Map<String, String?>? = null,
    var body: String? = null,
) {
    fun getLogString(separate: String = "\n"): String {
        val urlString = "url=$url"
        val methodString = "method=$method"
        val headsString = if (heads.isNullOrEmpty()) "" else "${separate}heads=$heads"
        val paramsString = if (params.isNullOrEmpty()) "" else "${separate}params=$params"
        val bodyString = if (body.isNullOrEmpty()) "" else "${separate}body=$body"
        return "Request $urlString$methodString$headsString$paramsString$bodyString"
    }
}