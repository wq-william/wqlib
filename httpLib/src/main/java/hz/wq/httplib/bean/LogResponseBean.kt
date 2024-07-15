package hz.wq.httplib.bean

/**
 * 接收的日志bean
 */
data class LogResponseBean(
    var url: String,
    var code: Int,
    var message: String? = null,
    var heads: Map<String, List<String>>? = null,
    var body: String? = null,
) {
    fun getLogString(separate: String = "\n"): String {
        val urlString = "url=$url"
        val codeString = "${separate}code=$code"
        val headsString = if (heads.isNullOrEmpty()) "" else "${separate}heads=$heads"
        val bodyString = if (body.isNullOrEmpty()) "" else "${separate}body=$body"
        return "Response $urlString$codeString$headsString$bodyString"
    }
}