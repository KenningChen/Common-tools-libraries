package com.kenning.kcutil

import com.kenning.kcutil.utils.date.formatBy
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)

        println(Date() formatBy SimpleDateFormat("yyyyMMdd"))
        println(Date() formatBy SimpleDateFormat("yyyyMMdd"))
        println(Date() formatBy SimpleDateFormat("yyyyMMdd"))
        println(Date() formatBy SimpleDateFormat("yyyyMMdd"))
    }
}