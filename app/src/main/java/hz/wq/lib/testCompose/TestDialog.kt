package hz.wq.lib.testCompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import hz.wq.common.dialog.BaseDialog
import hz.wq.common.dialog.InputDialog
import hz.wq.common.dialog.MenuDialog
import hz.wq.common.dialog.MessageDialog
import hz.wq.lib.HomeTabActivity
import hz.wq.lib.MyApp
import java.util.ArrayList

/**
 * 创建者: W~Q
 * 创建时间:   2024/7/22 10:03
 * 描述: TODO
 */
object TestDialog {
    fun message() {
        // 消息对话框
        MessageDialog.Builder(ActivityUtils.getTopActivity())
            // 标题可以不用填写
            .setTitle("我是标题")
            // 内容必须要填写
            .setMessage("我是内容")
            // 确定按钮文本
            .setConfirm("确定")
            // 设置 null 表示不显示取消按钮
            .setCancel("取消")
            // 设置点击按钮后不关闭对话框
            //.setAutoDismiss(false)
            .setListener(object : MessageDialog.OnListener {

                override fun onConfirm(dialog: BaseDialog?) {
                    ToastUtils.showShort("确定了")
                }

                override fun onCancel(dialog: BaseDialog?) {
                    ToastUtils.showShort("取消了")
                }
            })
            .show()
    }

    fun input() {
        // 输入对话框
        InputDialog.Builder(ActivityUtils.getTopActivity())
            // 标题可以不用填写
            .setTitle("我是标题")
            // 内容可以不用填写
            .setContent("我是内容")
            // 提示可以不用填写
            .setHint("我是提示")
            // 确定按钮文本
            .setConfirm("确定")
            // 设置 null 表示不显示取消按钮
            .setCancel("取消")
            // 设置点击按钮后不关闭对话框
            //.setAutoDismiss(false)
            .setListener(object : InputDialog.OnListener {

                override fun onConfirm(dialog: BaseDialog?, content: String) {
                    toast("确定了：$content")
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }

    fun menuBottom() {
        val data = ArrayList<String>()
        for (i in 0..9) {
            data.add("我是数据" + (i + 1))
        }

        // 底部选择框
        MenuDialog.Builder(ActivityUtils.getTopActivity())
            // 设置 null 表示不显示取消按钮
            //.setCancel(getString(R.string.common_cancel))
            // 设置点击按钮后不关闭对话框
            //.setAutoDismiss(false)
            .setList(data)
            .setListener(object : MenuDialog.OnListener<String> {

                override fun onSelected(dialog: BaseDialog?, position: Int, data: String) {
                    toast("位置：$position，文本：$data")
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }

    private fun toast(msg: String) {
        ToastUtils.showShort(msg)
    }

//    fun input() {
//        // 消息对话框
//        MessageDialog.Builder(ActivityUtils.getTopActivity())
//            // 标题可以不用填写
//            .setTitle("我是标题")
//            // 内容必须要填写
//            .setMessage("我是内容")
//            // 确定按钮文本
//            .setConfirm("确定")
//            // 设置 null 表示不显示取消按钮
//            .setCancel("取消")
//            // 设置点击按钮后不关闭对话框
//            //.setAutoDismiss(false)
//            .setListener(object : MessageDialog.OnListener {
//
//                override fun onConfirm(dialog: BaseDialog?) {
//                    ToastUtils.showShort("确定了")
//                }
//
//                override fun onCancel(dialog: BaseDialog?) {
//                    ToastUtils.showShort("取消了")
//                }
//            })
//            .show()
//    }
}


data class GridItem(val text: String, var onClick: () -> Unit = { })

val gridItems = listOf(
    GridItem("Message Dialog") { TestDialog.message() },
    GridItem("Input Dialog") { TestDialog.input() },
    GridItem("Menu Bottom") { TestDialog.menuBottom() },
    GridItem("Button 4"),
    GridItem("Button 5"),
    GridItem("Button 6")
)

@Composable
fun DialogGridLayout(items: List<GridItem> = gridItems) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // 两列
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            Button(
                onClick = item.onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = item.text)
            }
        }
    }
}
