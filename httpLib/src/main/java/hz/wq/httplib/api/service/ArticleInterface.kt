package hz.wq.httplib.api.service

import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.api.param.AddArticleBean
import retrofit2.http.Body
import retrofit2.http.POST

interface ArticleInterface {
    @POST("/article")
    suspend fun addArticle(@Body article: AddArticleBean): ApiResponse<String>
}