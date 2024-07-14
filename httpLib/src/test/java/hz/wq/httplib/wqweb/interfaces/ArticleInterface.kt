package hz.wq.httplib.wqweb.interfaces

import hz.wq.httplib.bean.ApiResponse
import hz.wq.httplib.wqweb.bean.param.AddArticleBean
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticleInterface {
    @POST("/article")
    suspend fun addArticle(@Body article: AddArticleBean): ApiResponse<String>
}