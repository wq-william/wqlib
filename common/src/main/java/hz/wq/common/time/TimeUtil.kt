package hz.wq.common.time

import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间工具类
 */
object TimeUtil {

    // 获取当前时间（Date类型）
    fun getCurrentDate(): Date = Date()

    // 获取当前时间戳（毫秒）
    fun getCurrentTimestampMillis(): Long = System.currentTimeMillis()

    // 获取当前时间戳（秒）
    fun getCurrentTimestampSeconds(): Long = System.currentTimeMillis() / 1000

    // 时间戳（毫秒）转Date
    fun timestampToDate(timestamp: Long): Date = Date(timestamp)

    // 时间戳（秒）转Date
    fun timestampSecondsToDate(timestampSeconds: Long): Date = Date(timestampSeconds * 1000)

    // Date转指定格式的字符串
    fun dateToString(date: Date, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }

    // 字符串转Date（需指定格式）
    fun stringToDate(dateString: String, pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return try {
            sdf.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    // Date类型增加指定天数
    fun addDays(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

    // Date类型增加指定小时数
    fun addHours(date: Date, hours: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.HOUR_OF_DAY, hours)
        return calendar.time
    }

    // Date类型增加指定分钟数
    fun addMinutes(date: Date, minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.MINUTE, minutes)
        return calendar.time
    }

    // 计算两个日期之间相差的秒数
    fun diffInSeconds(startDate: Date, endDate: Date): Long {
        return (endDate.time - startDate.time) / 1000
    }

    // 计算两个日期之间相差的分钟数
    fun diffInMinutes(startDate: Date, endDate: Date): Long {
        return diffInSeconds(startDate, endDate) / 60
    }

    // 计算两个日期之间相差的小时数
    fun diffInHours(startDate: Date, endDate: Date): Long {
        return diffInMinutes(startDate, endDate) / 60
    }

    // 计算两个日期之间相差的天数（忽略时、分、秒）
    fun diffInDays(startDate: Date, endDate: Date, ignoreTime: Boolean = true): Long {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.setTime(startDate)
        cal2.setTime(endDate)

        if (ignoreTime) {
            // 忽略时间部分，只比较日期
            cal1.set(Calendar.HOUR_OF_DAY, 0)
            cal1.set(Calendar.MINUTE, 0)
            cal1.set(Calendar.SECOND, 0)
            cal1.set(Calendar.MILLISECOND, 0)

            cal2.set(Calendar.HOUR_OF_DAY, 0)
            cal2.set(Calendar.MINUTE, 0)
            cal2.set(Calendar.SECOND, 0)
            cal2.set(Calendar.MILLISECOND, 0)
        }

        return (cal2.timeInMillis - cal1.timeInMillis) / (1000 * 60 * 60 * 24)
    }

    // 计算两个日期之间相差的月数（大致计算，不考虑天数差异）
    fun diffInMonths(startDate: Date, endDate: Date): Int {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.setTime(startDate)
        cal2.setTime(endDate)

        val yearsDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)
        val monthsDiff = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH)

        return yearsDiff * 12 + monthsDiff
    }

    // ... 可以继续添加其他时间计算的方法，如addMonths, addYears等
}