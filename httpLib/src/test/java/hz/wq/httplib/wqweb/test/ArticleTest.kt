package hz.wq.httplib.wqweb.test

import hz.wq.common.log.LogUtils.wqLog
import hz.wq.httplib.utils.HttpUtil
import hz.wq.httplib.wqweb.Config.isSuccess
import hz.wq.httplib.wqweb.Config.wqWebDoMain
import hz.wq.httplib.wqweb.bean.param.AddArticleBean
import hz.wq.httplib.wqweb.interfaces.ArticleInterface
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ArticleTest {
    @Test
    fun 增加文章() = runTest {
        try {
            val apiService = HttpUtil.getApiService(wqWebDoMain, ArticleInterface::class.java, UserTest().getHeadMap())
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