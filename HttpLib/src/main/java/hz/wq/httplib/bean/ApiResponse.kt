package hz.wq.httplib.bean

/**
 * API数据装载
 */
data class ApiResponse<T>(
    val code: String? = null,
    val message: String? = null,
    val data: T? = null,
    val httpResponse: HttpResponse
){
}