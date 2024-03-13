package com.kenning.kcutil.utils.dialog.easydialog

import android.app.Activity
import android.content.Context
import com.kenning.kcutil.utils.math.toInt_
import com.kenning.kcutil.utils.other.ScreenUtil

/**
 *Description :
 *@author : KenningChen
 *Date : 2021/11/5
 */
class DialogTools {

    /**是否支持协程*/
    var supportSuspend = false

    var dialogWidthPre = 80
    var dialogMaxHeightPre = 60
    var botteomButtonHeight = 40f

    /**设置底部操作按钮的排列方式
     *
     * 0 横向
     *
     * 1 纵向
     * */
    var bottomButtonOption = 0

    /** 不再提示的标记*/
    var promptTag = ""

    var showPicture = false

    var promptMsg = ""

    var errorTitlePic = -1

    /**协程对话框时,返回结果类型*/
    var resultType:Class<*> = String::class.java

    /**
     * 对话框宽度
     * -1为内部默认值
     */
    fun dialogWidth() : Int = (ScreenUtil.getScreenWidth()*dialogWidthPre*0.01).toInt_()

    fun dialogMaxHeight() : Int = (ScreenUtil.getScreenHeight()*dialogMaxHeightPre*0.01).toInt_()

    /**
     * Recyclerview适配器的item之间的高度
     */
    fun recycleViewDividerHeight():Int = ScreenUtil.dip2px(1f)

    /**
     * Recyclerview分割线距离左边的距离
     */
    fun recycleViewDividerLeft():Int = ScreenUtil.dip2px(10f)

    /**
     * Recyclerview分割线距离右边的距离
     */
    fun recycleViewDividerRight():Int = ScreenUtil.dip2px(10f)

    /**
     * 底部按钮高度
     */
    fun buttonHeight():Int = ScreenUtil.dip2px(botteomButtonHeight)

    fun resultType():Class<*> = resultType

    /**
     * 控件各个边角的圆半径
     */
    fun radius():Int = ScreenUtil.dip2px(6f)

    /**
     * 设置本地临时存储数据
     *
     * @param c
     * @param setType
     * @param setKey
     * @param setValue
     */
    fun setPreferences(
        c: Context, setType: String, setKey: String,
        setValue: String
    ) {

        val editor = c.getSharedPreferences(
            setType,
            Activity.MODE_PRIVATE
        ).edit()
        editor.putString(setKey, setValue)
        editor.apply()
    }

    fun getPreferences(
        c: Context, getType: String,
        getKey: String, defValue: String
    ): String {
        var getValue = ""

        val preferences = c.getSharedPreferences(
            getType,
            Activity.MODE_PRIVATE
        )
        getValue = preferences.getString(getKey, defValue)?:defValue

        return getValue
    }
}