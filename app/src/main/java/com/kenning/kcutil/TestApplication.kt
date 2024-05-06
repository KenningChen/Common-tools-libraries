package com.kenning.kcutil

import android.app.Application
import me.yokeyword.fragmentation.Fragmentation

/**
 *Description :
 *
 *Date : 2022/9/5
 *@author : KenningChen
 */
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KCUtil.initKCUtil(this)

        KCUtil.setScanClassPath(ScanTextActivity::class.java)

        Fragmentation.builder()
            // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(BuildConfig.DEBUG) // 实际场景建议.debug(BuildConfig.DEBUG)
            .install();
    }
}