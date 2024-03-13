package com.kenning.kcutil.utils.datepicker

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *Description : 记录跳转日期选择器的来源fragment
 *
 *Date : 2022/9/15
 *@author : KenningChen
 */
class PickerViewModel : ViewModel() {

    var tagetFragment = MutableLiveData<IPickerListener>()
}