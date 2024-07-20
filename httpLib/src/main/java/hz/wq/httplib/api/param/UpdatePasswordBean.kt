package hz.wq.httplib.api.param

data class UpdatePasswordBean(
    val oldPassword: String,
    val newPassword: String,
    val newRePassword: String,
    )