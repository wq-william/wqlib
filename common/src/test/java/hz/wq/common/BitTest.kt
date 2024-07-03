package hz.wq.common

import hz.wq.common.util.BitwiseUtil
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * 创建者: W~Q
 */
class BitTest {

    @Test
    fun testBit() = runTest {
        // 示例数据，一个 32 位的整数
        var data: Int = 0x12345678 // 二进制表示为：0001 0010 0011 0100 0101 0110 0111 1000
//        // 示例数据
//        var data: Int = 0b1100110011001100 // 二进制表示为：1100 1100 1100 1100
        // 示例数据
//        var data: Int = 0

        // 获取第3位
        val bits3 = BitwiseUtil.getBitsInRange(data, 3)
        println("bits3: $bits3")

        // 获取 2-4 位
        val bits2To4 = BitwiseUtil.getBitsInRange(data, 2, 4)
        println("2-4 位: $bits2To4")
        // 获取 1-4 位
        val bits1To4 = BitwiseUtil.getBitsInRange(data, 1, 4)
        println("1-4 位: $bits1To4")

        // 获取 5-11 位
        var bits5To11 = BitwiseUtil.getBitsInRange(data, 5, 11)
        println("5-11 位: $bits5To11")

        // 获取 12-16 位
        var bits12To16 = BitwiseUtil.getBitsInRange(data, 12, 16)
        println("12-16 位: $bits12To16")

        data = 0
        // 设置第1-4位（从右往左数，从0开始）
        data = BitwiseUtil.setBitsInRange(data, 1, 4, 10)
        println("设置第1-4位后的数据: $data")

//        data = 0
        // 设置第5-11位 共7位
        data = BitwiseUtil.setBitsInRange(data, 5, 11, 127)
        println("设置第5-11位后的数据: $data")

        // 设置第12-16位 共5位
        data = BitwiseUtil.setBitsInRange(data, 12, 16, 0b11111)
        println("设置第12-16位后的数据: $data")

        // 清除第1-11位（设置为0）
        data = BitwiseUtil.clearBitsInRange(data, 1, 11)
        println("清除第1-11位后的数据: $data")

        // 设置1-4位（设置为1）
        data = BitwiseUtil.setBitsInRange(data, 1, 4, 0b1111)
        println("设置第1-4位后的数据: $data")

        // 设置1-2位（设置为0）
        data = BitwiseUtil.setBitsInRange(data, 1, 2, 0b00)
        println("设置第1-2位后的数据: $data")

    }
}