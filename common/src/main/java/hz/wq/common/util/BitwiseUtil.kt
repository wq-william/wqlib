package hz.wq.common.util

/**
 * 位操作，参考 BitTest
 */
object BitwiseUtil {

    /**
     * 设置value特定位范围的值
     * start 起始下标 （包含start）
     * end 结束下标 （包含end）
     */
    fun setBitsInRange(value: Int, start: Int, end: Int, newBits: Int): Int {
//        val start = start
//        val end = end

        // 创建一个掩码，其中指定范围内的位设置为 1
        var mask = (1 shl (end - start + 1)) - 1
        mask = mask shl start

        // 使用 or 运算符将新值设置到指定范围内，而不影响其他位
        return (value and mask.inv()) or (newBits shl start)
    }

    /**
     * 获取value特定位范围的值
     * start 起始下标 （包含start）
     * end 结束下标 （包含end），如不传，自动获取单个字节
     */
    fun getBitsInRange(value: Int, start: Int, end: Int = start): Int {
//        val start = start
//        val end = end

        // 创建一个掩码，其中指定范围内的位设置为 1
        var mask = (1 shl (end - start + 1)) - 1
        mask = mask shl start

        // 使用 and 运算符提取指定范围内的位
        return (value and mask) ushr start
    }

    /**
     * 清除特定位范围的值（设置为0）
     * start 起始下标 （包含start）
     * end 结束下标 （包含end）
     */
    fun clearBitsInRange(value: Int, start: Int, end: Int): Int {
//        val start = start
//        val end = end

        // 创建一个掩码，其中指定范围内的位设置为 1
        var mask = (1 shl (end - start + 1)) - 1
        mask = mask shl start

        // 使用 and 运算符清除指定范围内的位，而不影响其他位
        return value and mask.inv()
    }


    //------------------------------------------------------------------------------------------------------------
    // 上下分割，上面的3个方法基本可以通用了（用法见Test），上方方法不满足业务，再考虑用下方的方法
    //------------------------------------------------------------------------------------------------------------


    // 获取int类型值中指定位置的byte
    fun getByteFromInt(value: Int, bytePosition: Int): Byte {
        require(!(bytePosition < 0 || bytePosition >= 4)) { "Byte position is out of range for an int." }
        return ((value shr (bytePosition * 8)) and 0xFF).toByte()
    }

    // 获取byte中指定位置的bit
    fun getBitFromByte(value: Byte, bitPosition: Int): Boolean {
        require(!(bitPosition < 0 || bitPosition >= 8)) { "Bit position is out of range for a byte." }
        return (value.toInt() and (1 shl bitPosition)) != 0
    }

    // 获取int类型值中指定位置的bit
    fun getBitFromInt(value: Int, bitPosition: Int): Boolean {
        require(!(bitPosition < 0 || bitPosition >= 32)) { "Bit position is out of range for an int." }
        return (value and (1 shl bitPosition)) != 0
    }

    // 设置int类型值中指定位置的byte
    fun setByteInInt(value: Int, bytePosition: Int, newValue: Byte): Int {
        require(!(bytePosition < 0 || bytePosition >= 4)) { "Byte position is out of range for an int." }
        val mask = 0xFF shl (bytePosition * 8)
        return (value and mask) or (newValue.toInt() shl (bytePosition * 8))
    }

    // 设置byte中指定位置的bit
    fun setBitInByte(value: Byte, bitPosition: Int, newValue: Boolean): Byte {
        require(!(bitPosition < 0 || bitPosition >= 8)) { "Bit position is out of range for a byte." }
        val mask = (1 shl bitPosition).toByte()
        return (if (newValue) (value.toInt() or mask.toInt()) else (value.toInt() and mask.toInt())).toByte()
    }

    // 设置int类型值中指定位置的bit
    fun setBitInInt(value: Int, bitPosition: Int, newValue: Boolean): Int {
        require(!(bitPosition < 0 || bitPosition >= 32)) { "Bit position is out of range for an int." }
        val mask = 1 shl bitPosition
        return (if (newValue) (value or mask) else (value and mask))
    }

    // 翻转int类型值中指定位置的bit
    fun flipBitInInt(value: Int, bitPosition: Int): Int {
        require(!(bitPosition < 0 || bitPosition >= 32)) { "Bit position is out of range for an int." }
        val mask = 1 shl bitPosition
        return value xor mask
    }

    // 翻转byte中指定位置的bit
    fun flipBitInByte(value: Byte, bitPosition: Int): Byte {
        require(!(bitPosition < 0 || bitPosition >= 8)) { "Bit position is out of range for a byte." }
        val mask = (1 shl bitPosition).toByte()
        return (value.toInt() xor mask.toInt()).toByte()
    }

}