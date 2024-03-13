package com.kenning.kcutil.utils.datepicker

/**
 * description：
 * author: KenningChen
 * date: ON 2022/9/16
 */
interface IPickerListener {
    fun onDismissPicker()
    fun onDateChange(requestcode: Int, start: String, end: String):Boolean
}