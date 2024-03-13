package com.kenning.kcutil

import android.app.Application
import com.kenning.kcutil.utils.tts.TTSUtil

/**
 *Description :工具类的入口,初始化的地方,在项目application中初始化
 *
 *Date : 2022/9/5
 *@author : KenningChen
 */
object KCUtil {

    internal var application:Application?=null

    fun initKCUtil(application: Application){
        this.application = application
        TTSUtil.getInstance();
    }
}