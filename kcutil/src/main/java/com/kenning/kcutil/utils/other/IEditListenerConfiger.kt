package com.kenning.kcutil.utils.other

/**
 * [android.widget.EditText.addTextChangedListener]
 * [android.widget.CheckBox.setOnCheckedChangeListener]
 *
 * 设置此参数 阻止上述方法代码块内的代码执行
 * */
interface IEditListenerConfiger {
    var isEditWork:Boolean
}