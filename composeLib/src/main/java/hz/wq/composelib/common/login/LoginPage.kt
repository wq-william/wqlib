package hz.wq.composelib.common.login

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
import hz.wq.composelib.common.login.viewModel.LoginViewModel
import hz.wq.common.log.LogUtils.wqLog

// ViewModel 示例


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(viewModel: LoginViewModel) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val loginState = viewModel.loginResult.collectAsState(initial = null).value
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
                    viewModel.login(userName, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("登录")
            }
            Spacer(modifier = Modifier.height(186.dp))
        }
    }

    LaunchedEffect(userName) {
        userName.wqLog()
    }
    LaunchedEffect(password) {
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
    LoginPage(LoginViewModel())
}