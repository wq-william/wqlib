package hz.wq.httplib.bean

/**
 * API数据装载
 */
data class ApiResponse<T>(
    val code: String? = null,
    val message: String? = null,
    val data: T? = null,
    val httpStatusCode: Int? = null,//http状态码 200 404 501等等
    val httpMessage: String? = null,//http 的msg
    val httpHeaders: Map<String, List<String>>? = null, //http头信息
    val httpRawContent: String? = null  //保存解析前的完整内容
)