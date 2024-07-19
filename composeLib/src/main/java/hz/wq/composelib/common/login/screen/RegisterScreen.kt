package hz.wq.composelib.common.login.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import hz.wq.composelib.common.CommonTopAppBar
import hz.wq.composelib.common.ImgButton
import hz.wq.composelib.common.login.viewModel.BaseLoginViewModel

@Composable
fun RegisterScreen(
    viewModel: BaseLoginViewModel
) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = "注册",
                rightImgs = arrayOf(
                    ImgButton(Icons.Filled.Close) {
                        viewModel.finishActivity()
                    }
                )
            )
        },
        content = { innerPadding -> // 这里的Lambda表达式是Scaffold所期望的
            content(innerPadding, viewModel) // 调用你的Composable函数并传递innerPadding
        }

    )
}

@Composable
fun content(innerPadding: PaddingValues, viewModel: BaseLoginViewModel) {
    var userName by remember { mutableStateOf(viewModel.userName.value) }
    var password by remember { mutableStateOf(viewModel.password.value) }
    var verificationCode by remember { mutableStateOf(viewModel.verificationCode.value) }
    var showPassword by remember { mutableStateOf(false) }

    initLaunchedEffect(viewModel, userName, password, verificationCode)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... 其他UI元素

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("用户名") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
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
        OutlinedTextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = { Text("验证码") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.login()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("注册")
        }
    }
}

@Composable
fun initLaunchedEffect(viewModel: BaseLoginViewModel, userName: String, password: String, verificationCode: String) {
    LaunchedEffect(userName) {
        viewModel.onUserNameChanged(userName)
    }
    LaunchedEffect(password) {
        viewModel.onUserNameChanged(userName)
    }
    LaunchedEffect(verificationCode) {
        viewModel.onUserNameChanged(verificationCode)
    }
}
