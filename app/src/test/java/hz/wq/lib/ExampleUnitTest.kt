package hz.wq.lib

import hz.wq.httplib.TestUse
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var test = TestUse()
        println("test:${test.testUseFun()}")
        assertEquals(4, 2 + 2)
    }
}