package com.kenning.kcutil.utils.other

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import com.kenning.kcutil.KCUtil

/**
 *Description :
 *@author : KenningChen
 *Date : 2024-04-26
 */
object KeyBoardUtil {

    fun Activity.CloseSoftInput() {
        if (this.currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(
                this.currentFocus!!
                    .windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0) //强制隐藏键盘
        }else{
            val imm =  this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
        }
    }

    fun PopupWindow.CloseSoftInput() {
        val imm = KCUtil.application!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.contentView.windowToken, 0) //强制隐藏键盘
    }

    fun EditText.CloseSoftInput() {
        when(this.context){
            is Activity -> {
                val imm =  (this.context as Activity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, 0)
            }
            is ContextThemeWrapper -> {
                val imm =  (this.context as ContextThemeWrapper).getSystemService(Context.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                imm.hideSoftInputFromWindow(this.windowToken, 0)
            }
        }
    }
}