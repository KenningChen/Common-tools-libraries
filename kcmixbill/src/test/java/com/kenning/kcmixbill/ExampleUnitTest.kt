package com.kenning.kcmixbill

import com.google.gson.Gson
import com.kenning.kcmixbill.data.Bean2
import com.kenning.kcmixbill.data.BillBean
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
        assertEquals(4, 2 + 2)
        val data = BillBean()
//        data.setUnitQty_("23.08")
        println(data.gettitle())
//        data.setUnitQty_("123")
//        println(data.Qty)
    }
}