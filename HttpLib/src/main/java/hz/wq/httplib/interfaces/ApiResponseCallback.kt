package hz.wq.httplib.interfaces

import hz.wq.httplib.bean.ApiResponse


interface ApiResponseCallback<Result> {
    fun onResult(result: ApiResponse<Result>?)
}