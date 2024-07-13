package hz.wq.httplib.bean

/**
 * HTTP数据封装
 *
 * @property httpStatusCode
 * @property httpMessage
 * @property httpHeaders
 * @property httpRawContent
 */
class HttpResponse(
    val httpStatusCode: Int? = null,//http状态码 200 404 501等等
    val httpMessage: String? = null,//http 的msg
    val httpHeaders: Map<String, List<String>>? = null, //http头信息
    val httpRawContent: String? = null  //保存解析前的完整内容
) {
    fun getResponseString(): String {
        return "HttpResponse(httpStatusCode=$httpStatusCode, httpMessage=$httpMessage, httpHeaders=$httpHeaders)"
    }
}