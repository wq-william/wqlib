package hz.wq.httplib.api.param

data class AddArticleBean(
    val categoryId: Int,
    val title: String,
    val content: String,
    val coverImg: String,
    val state: String
)