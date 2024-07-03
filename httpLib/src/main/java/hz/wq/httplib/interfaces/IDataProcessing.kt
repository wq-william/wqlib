package hz.wq.httplib.interfaces

/**
 * 数据加工
 */
interface IDataProcessing {
    /**
     * 处理请求前的数据（加密等）
     * 如果是FromBody多个参数，这个方法会调用多次
     *
     * @param preSendKey   发送前的key（如果是FromBody，那么会存在多个key，value）
     * @param preSendData   发送前数据
     * @return
     */
    fun processingBeforeRequest(preSendKey:String?,preSendData:String):String
    /**
     * 处理得到的数据（解密等）
     *
     * @param preResponseData
     * @return
     */
    fun processingAfterResponse(preResponseData:String):String
}