package hz.wq.common.util.ui

import com.blankj.utilcode.util.ActivityUtils
import hz.wq.common.dialog.MessageDialog

object DialogUtil {

    fun showDialogMsg(title: String, msg: String) {
        MessageDialog.Builder(ActivityUtils.getTopActivity())
            // 标题可以不用填写
            .setTitle(title)
            // 内容必须要填写
            .setMessage(msg)
            // 确定按钮文本
            .setConfirm("我知道了")
            // 设置 null 表示不显示取消按钮
            .setCancel(null)
            .show()
    }
}