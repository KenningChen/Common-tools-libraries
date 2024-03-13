package com.kenning.kcutil.utils.other

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import com.kenning.kcutil.KCUtil

/**
 *  屏幕相关的方法
 *  @author : zyl
 *  created at 2021-02-20 14:54
 */
object ScreenUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(dpValue: Float): Int {
        val scale = KCUtil.application?.resources?.displayMetrics?.density?:-1f
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(spValue: Float): Int {
        val fontScale = KCUtil.application?.resources?.displayMetrics?.scaledDensity?:-1f
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(pxValue: Float): Int {
        val scale = KCUtil.application?.resources?.displayMetrics?.density?:-1f
        return (pxValue / scale + 0.5f).toInt()
    }

    fun dp2px(dpValue: Float): Float {
        return (0.5f + dpValue * Resources.getSystem().displayMetrics.density)
    }

    /**
     * 获取屏幕高度(px)
     */
    fun getScreenHeight(): Int {
        return KCUtil.application?.resources?.displayMetrics?.heightPixels?:-1
    }

    /**
     * 获取屏幕宽度(px)
     */
    fun getScreenWidth(): Int {
        return KCUtil.application?.resources?.displayMetrics?.widthPixels?:-1
    }
}

fun View.getLocationWidth():Int{
    val location = intArrayOf(0,0)
    this.getLocationOnScreen(location)
    return location[0]
}

fun View.getLocationHeight2():Int{
    val location = intArrayOf(0,0)
    this.getLocationOnScreen(location)
    return location[1]
}

fun View.getLocationHeight():Int{
    val globalRect = Rect()
    getGlobalVisibleRect(globalRect)
    return globalRect.bottom
}
/**获取 view 的（顶部）高度绝对坐标*/
fun View.getLocationHeight_Top():Int{
    val globalRect = Rect()
    getGlobalVisibleRect(globalRect)
    return globalRect.top
}

/**
 * 获取状态栏高度
 * @param context
 * @return
 */
fun getStatusBarHeight() :Int{
    val resourceId = KCUtil.application?.resources?.getIdentifier("status_bar_height", "dimen",
        "android")?:-1
    if (resourceId == -1) return -1
    val height = KCUtil.application?.resources?.getDimensionPixelSize(resourceId)?:-1
    return height
}