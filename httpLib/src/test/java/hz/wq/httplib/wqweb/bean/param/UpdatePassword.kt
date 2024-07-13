package hz.wq.httplib.wqweb.bean.param

data class UpdatePassword(
    val oldPassword: String,
    val newPassword: String,
    val newRePassword: String,
    )