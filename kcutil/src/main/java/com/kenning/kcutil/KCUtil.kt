package com.kenning.kcutil

import android.app.Application
import com.kenning.kcutil.widget.CameraScanBean
import com.kenning.kcutil.widget.ImplScanResult

/**
 *Description :工具类的入口,初始化的地方,在项目application中初始化
 *
 *Date : 2022/9/5
 *@author : KenningChen
 */
object KCUtil {

    internal var application: Application? = null

    fun initKCUtil(application: Application) {
        this.application = application
    }

    // 指定默认扫描跳转的Class的文件路径
    var ScanClassAbPath = ""
        private set

    fun <T : ImplScanResult> setScanClassPath(clazz: Class<T>) {
        ScanClassAbPath = clazz.name
    }


    private var bean:CameraScanBean?=null
    fun setScanResult(bean: CameraScanBean?){
        this.bean = bean
    }

    fun getScanResult() = bean
}