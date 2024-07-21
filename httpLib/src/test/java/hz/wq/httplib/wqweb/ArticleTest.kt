package hz.wq.httplib.wqweb

import hz.wq.common.util.log.LogUtils.wqLog
import hz.wq.httplib.api.service.ArticleInterface
import hz.wq.httplib.helper.HttpHelper
import hz.wq.httplib.api.param.AddArticleBean
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ArticleTest {
    @Test
    fun 增加文章() = runTest {
        try {
            val apiService = HttpHelper.getApiService(wqWebDoMain, ArticleInterface::class.java, UserTest().getHeadMap())
            val addArticleBean = AddArticleBean(
                categoryId = 1,
                title="testTitle",
                content="wqTest文章content",
                coverImg = "http://wqTest",
                state = "已发布",
                )
            val result = apiService.addArticle(addArticleBean)
            result.toString().wqLog()
            result.httpResponse.getResponseString().wqLog()
            if (result.isSuccess()) {
                assert(true)
            } else {
                assert(false)
            }
       } catch (e: Exception) {
           e.printStackTrace()
            assert(false)
       }
   }
}