package hz.wq.httplib.api.result

data class UserBean(
    val email: String,
    val id: Int,
    val nickname: String,
    val token: Any,
    val userName: String,
    val userPic: String,
    val userTokenKey: String
)