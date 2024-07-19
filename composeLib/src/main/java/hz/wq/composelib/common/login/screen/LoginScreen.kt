package hz.wq.composelib.common.login.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hz.wq.composelib.common.CommonTopAppBar
import hz.wq.composelib.common.ImgButton
import hz.wq.composelib.common.login.enums.LoginResult
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel
import hz.wq.common.log.LogUtils.wqLog

// ViewModel 示例


//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(viewModel: BaseLoginViewModel) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val loginState = viewModel.loginResult.collectAsState(initial = null).value

    val interactionSource = remember { MutableInteractionSource() }
    Column {
        CommonTopAppBar(
            rightImgs = arrayOf(
                ImgButton(Icons.Filled.Close) {
                    "rightBtn".wqLog()
                    viewModel.finishActivity()
                }
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "登录", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("用户名") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密码") },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Default.Face else Icons.Default.Lock,
                            contentDescription = if (showPassword) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("登录")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 注册按钮
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource, // 传入自定义的InteractionSource
                            indication = null // 禁用默认指示器
                        ) { "注册按钮 点击".wqLog() }
                        .padding(horizontal = 10.dp, vertical = 4.dp), // 这里定义了额外的点击区域
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "注册",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // 忘记密码按钮
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = interactionSource, // 传入自定义的InteractionSource
                            indication = null // 禁用默认指示器
                        ) { "忘记密码按钮 点击".wqLog() }
                        .padding(horizontal = 10.dp, vertical = 4.dp), // 这里定义了额外的点击区域
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "忘记密码?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(150.dp))
        }
    }

    LaunchedEffect(userName) {
        viewModel.onUserNameChanged(userName)
        userName.wqLog()
    }
    LaunchedEffect(password) {
        viewModel.onPasswordChanged(password)
        password.wqLog()
    }
    LaunchedEffect(loginState) {
        loginState?.let {
            when (it) {
                is LoginResult.LoginSuccess -> {
                    // 登录成功，保存数据并跳转到主页
                    // 假设你有一个保存数据的函数 saveUserData
                    // saveUserData(email)
                    "登录成功".wqLog()
                    viewModel.resetState()
                }

                is LoginResult.LoginError -> {
                    // 处理登录错误，例如显示错误消息
                    // showErrorDialog("Login failed.")
                    "登录错误".wqLog()
                    viewModel.resetState()
                }

                is LoginResult.LoginError -> {
                    "登录中 ".wqLog()
                    viewModel.resetState()
                }

                else -> {
                    "NoState".wqLog()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginPage() {
    LoginPage(object : BaseLoginViewModel() {
        override fun login(email: String, password: String) {
            emitLoginResult(if (email == "123" && password == "456") LoginResult.LoginSuccess else LoginResult.LoginError)
        }
    })
}