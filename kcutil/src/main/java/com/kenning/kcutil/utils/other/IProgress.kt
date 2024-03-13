package com.kenning.kcutil.utils.other

/**
 *Description :
 *author : KenningChen
 *Date : 2021/7/1
 */
interface IProgress {
    /**
     * 加载进度框
     * @param context String
     * @param isCancelable Boolean
     */
    fun showProgress(context: String = "", isCancelable: Boolean = false)

    /**
     * 取消进度框
     */
    fun dismissProgress()
}