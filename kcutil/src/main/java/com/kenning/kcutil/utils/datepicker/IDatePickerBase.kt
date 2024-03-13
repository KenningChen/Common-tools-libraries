package com.kenning.kcutil.utils.datepicker

import android.view.View
import androidx.fragment.app.Fragment

/**
 * description：
 * author: KenningChen
 * date: ON 2022/9/16
 */
interface IDatePickerBase {

    var startdate: String
    var enddate: String
    var isSingleDate: Boolean
    var mView:View

    fun putDateToView(vararg date: String, type: Int, isSignal: Boolean)
}