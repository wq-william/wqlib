package hz.wq.httplib.interfaces


interface ApiCallback<BaseBean, T> {
    /**
     * 成功回调
     */
    fun onSuccess(bean: T, serviceResultBean: BaseBean, originalString: String)

    /**
     * 失败回调
     */
    fun onFail(serviceResultBean: BaseBean, originalString: String, isNetworkError: Boolean)
}

interface ApiCallbackOriginal {
    /**
     * 成功回调
     */
    fun onSuccessResult(body: String?)

    /**
     * 失败回调
     */
    fun onFailResult(httpErrorCode: Int, msg: String?)
}