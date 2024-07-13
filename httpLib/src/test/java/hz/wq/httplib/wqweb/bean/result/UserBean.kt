package hz.wq.httplib.wqweb.bean.result

data class UserBean(
    val email: String,
    val id: Int,
    val nickname: String,
    val token: Any,
    val userName: String,
    val userPic: String,
    val userTokenKey: String
)