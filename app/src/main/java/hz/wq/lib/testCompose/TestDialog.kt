package hz.wq.lib.testCompose

import android.app.Activity
import android.view.Gravity
import androidx.activity.ComponentActivity
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
import hz.wq.common.dialog.AddressDialog
import hz.wq.common.dialog.BaseDialog
import hz.wq.common.dialog.DateDialog
import hz.wq.common.dialog.InputDialog
import hz.wq.common.dialog.MenuDialog
import hz.wq.common.dialog.MessageDialog
import hz.wq.common.dialog.PayPasswordDialog
import hz.wq.common.dialog.SelectDialog
import hz.wq.common.dialog.TimeDialog
import hz.wq.common.dialog.TipsDialog
import hz.wq.common.dialog.UpdateDialog
import hz.wq.common.dialog.WaitDialog
import hz.wq.common.dialog.manager.DialogManager
import hz.wq.lib.HomeTabActivity
import hz.wq.lib.MyApp
import java.util.ArrayList
import java.util.Calendar
import java.util.HashMap

/**
 * 创建者: W~Q
 * 创建时间:   2024/7/22 10:03
 * 描述: TODO
 */
object TestDialog {
    private fun getActivity(): ComponentActivity = ActivityUtils.getTopActivity() as ComponentActivity
    private fun toast(msg: String) {
        ToastUtils.showShort(msg)
    }
    fun message() {
        // 消息对话框
        MessageDialog.Builder(getActivity())
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
        InputDialog.Builder(getActivity())
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
        MenuDialog.Builder(getActivity())
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

    fun menuCenter() {
        val data = ArrayList<String>()
        for (i in 0..3) {
            data.add("我是数据" + (i + 1))
        }
        // 居中选择框
        MenuDialog.Builder(getActivity())
            .setGravity(Gravity.CENTER)
            // 设置 null 表示不显示取消按钮
            //.setCancel(null)
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

    fun radio() {
        // 单选对话框
        SelectDialog.Builder(getActivity())
            .setTitle("请选择你的性别")
            .setList("男", "女")
            // 设置单选模式
            .setSingleSelect()
            // 设置默认选中
            .setSelect(0)
            .setListener(object : SelectDialog.OnListener<String> {

                override fun onSelected(dialog: BaseDialog?, data: HashMap<Int, String>) {
                    toast("确定了：$data")
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }

    fun checkBox() {
        // 多选对话框
        SelectDialog.Builder(getActivity())
            .setTitle("请选择工作日")
            .setList("星期一", "星期二", "星期三", "星期四", "星期五")
            // 设置最大选择数
            .setMaxSelect(3)
            // 设置默认选中
            .setSelect(2, 3, 4)
            .setListener(object : SelectDialog.OnListener<String> {

                override fun onSelected(dialog: BaseDialog?, data: HashMap<Int, String>) {
                    toast("确定了：$data")
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }
    fun toastSuccess() {

        // 成功
        TipsDialog.Builder(getActivity())
            .setIcon(TipsDialog.ICON_FINISH)
            .setMessage("完成")
            .show()
    }
    fun toastFail() {
        // 失败
        TipsDialog.Builder(getActivity())
            .setIcon(TipsDialog.ICON_ERROR)
            .setMessage("错误")
            .show()
    }
    fun toastWarning() {
        // 警告
        TipsDialog.Builder(getActivity())
            .setIcon(TipsDialog.ICON_WARNING)
            .setMessage("错误")
            .show()
    }
    /** 等待对话框 */
    private var waitDialog: BaseDialog? = null
    fun loading() {
        if (waitDialog == null) {
            waitDialog = WaitDialog.Builder(getActivity()) // 消息文本可以不用填写
                .setMessage("加载中...")
                .create()
        }
        waitDialog?.apply {
            if (!isShowing) {
                show()
                postDelayed({ dismiss() }, 2000)
            }
        }
    }

    fun pay() {
        // 支付密码输入对话框
        PayPasswordDialog.Builder(getActivity())
            .setTitle("请输入密码")
            .setSubTitle("购买一个购物车")
            .setMoney("￥ 100.00")
            // 设置点击按钮后不关闭对话框
            //.setAutoDismiss(false)
            .setListener(object : PayPasswordDialog.OnListener {

                override fun onCompleted(dialog: BaseDialog?, password: String) {
                    toast(password)
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()

    }
    fun selectCity() {
// 选择地区对话框
        AddressDialog.Builder(getActivity())
            .setTitle("请选择地区") // 设置默认省份
            //.setProvince("广东省")
            // 设置默认城市（必须要先设置默认省份）
            //.setCity("广州市")
            // 不选择县级区域
            //.setIgnoreArea()
            .setListener(object : AddressDialog.OnListener {

                override fun onSelected(dialog: BaseDialog?, province: String, city: String, area: String) {
                    toast(province + city + area)
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }

    fun selectDay(){
        // 日期选择对话框
        DateDialog.Builder(getActivity())
            .setTitle("请选择日期")
            // 确定按钮文本
            .setConfirm("确定")
            // 设置 null 表示不显示取消按钮
            .setCancel("取消")
            // 设置日期
            //.setDate("2018-12-31")
            //.setDate("20181231")
            //.setDate(1546263036137)
            // 设置年份
            //.setYear(2018)
            // 设置月份
            //.setMonth(2)
            // 设置天数
            //.setDay(20)
            // 不选择天数
            //.setIgnoreDay()
            .setListener(object : DateDialog.OnListener {

                override fun onSelected(dialog: BaseDialog?, year: Int, month: Int, day: Int) {
                    toast(year.toString() + "年" + month +
                            "月" + day + "日")

//                    // 如果不指定时分秒则默认为现在的时间
//                    val calendar: Calendar = Calendar.getInstance()
//                    calendar.set(Calendar.YEAR, year)
//                    // 月份从零开始，所以需要减 1
//                    calendar.set(Calendar.MONTH, month - 1)
//                    calendar.set(Calendar.DAY_OF_MONTH, day)
//                    toast("时间戳：" + calendar.timeInMillis)
                    //toast(new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss").format(calendar.getTime()));
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }
    fun selectTime(){
        // 时间选择对话框
        TimeDialog.Builder(getActivity())
            .setTitle("请选择时间")
            // 确定按钮文本
            .setConfirm("确定")
            // 设置 null 表示不显示取消按钮
            .setCancel("取消")
            // 设置时间
            //.setTime("23:59:59")
            //.setTime("235959")
            // 设置小时
            //.setHour(23)
            // 设置分钟
            //.setMinute(59)
            // 设置秒数
            //.setSecond(59)
            // 不选择秒数
            //.setIgnoreSecond()
            .setListener(object : TimeDialog.OnListener {

                override fun onSelected(dialog: BaseDialog?, hour: Int, minute: Int, second: Int) {
                    toast(hour.toString() + "时" + minute + "分"+ second + "秒")

//                    // 如果不指定年月日则默认为今天的日期
//                    val calendar: Calendar = Calendar.getInstance()
//                    calendar.set(Calendar.HOUR_OF_DAY, hour)
//                    calendar.set(Calendar.MINUTE, minute)
//                    calendar.set(Calendar.SECOND, second)
//                    toast("时间戳：" + calendar.timeInMillis)
                    //toast(new SimpleDateFormat("yyyy年MM月dd日 kk:mm:ss").format(calendar.getTime()));
                }

                override fun onCancel(dialog: BaseDialog?) {
                    toast("取消了")
                }
            })
            .show()
    }
    fun share() {
//        toast("记得改好第三方 AppID 和 Secret，否则会调不起来哦")
//        val content = UMWeb("https://github.com/getActivity/AndroidProject-Kotlin")
//        content.title = "Github"
//        content.setThumb(UMImage(this, R.mipmap.launcher_ic))
//        content.description = getString(R.string.app_name)
//
//        // 分享对话框
//        ShareDialog.Builder(this)
//            .setShareLink(content)
//            .setListener(object : OnShareListener {
//
//                override fun onSucceed(platform: Platform?) {
//                    toast("分享成功")
//                }
//
//                override fun onError(platform: Platform?, t: Throwable) {
//                    toast(t.message)
//                }
//
//                override fun onCancel(platform: Platform?) {
//                    toast("分享取消")
//                }
//            })
//            .show()
    }

    fun update() {
        // 升级对话框
        UpdateDialog.Builder(getActivity())
            // 版本名
            .setVersionName("5.2.0")
            // 是否强制更新
            .setForceUpdate(false)
            // 更新日志
            .setUpdateLog("到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥")
            // 下载 URL
            .setDownloadUrl("https://dldir1.qq.com/weixin/android/weixin807android1920_arm64.apk")
            // 文件 MD5
            .setFileMd5("df2f045dfa854d8461d9cefe08b813c8")
            .show()
    }

    fun continueDialog() {
        val dialog1: BaseDialog = MessageDialog.Builder(getActivity())
            .setTitle("温馨提示")
            .setMessage("我是第一个弹出的对话框")
            .setConfirm("确认")
            .setCancel("取消")
            .create()

        val dialog2: BaseDialog = MessageDialog.Builder(getActivity())
            .setTitle("温馨提示")
            .setMessage("我是第二个弹出的对话框")
            .setConfirm("确认")
            .setCancel("取消")
            .create()
        DialogManager.getInstance(getActivity()).addShow(dialog1)
        DialogManager.getInstance(getActivity()).addShow(dialog2)
    }


}

data class GridItem(val text: String, var onClick: () -> Unit = { })

val gridItems = listOf(
    GridItem("消息提示选择") { TestDialog.message() },
    GridItem("输入") { TestDialog.input() },
    GridItem("底部选择") { TestDialog.menuBottom() },
    GridItem("居中选择") { TestDialog.menuCenter() },
    GridItem("单选") { TestDialog.radio() },
    GridItem("多选") { TestDialog.checkBox() },
    GridItem("Toast 居中 成功") { TestDialog.toastSuccess() },
    GridItem("Toast 居中 失败") { TestDialog.toastFail() },
    GridItem("Toast 居中 警告") { TestDialog.toastWarning() },
    GridItem("加载中") { TestDialog.loading() },
    GridItem("支付") { TestDialog.pay() },
    GridItem("选择城市") { TestDialog.selectCity() },
    GridItem("选择日期_年月日") { TestDialog.selectDay() },
    GridItem("选择日期_时分秒") { TestDialog.selectTime() },
//    GridItem("分享") { TestDialog.share() },
    GridItem("升级") { TestDialog.update() },
    GridItem("连续弹出") { TestDialog.continueDialog() },
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

