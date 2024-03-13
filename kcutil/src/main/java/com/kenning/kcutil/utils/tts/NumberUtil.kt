package com.kenning.kcutil.utils.tts

/**
 *Description :
 *@author : KenningChen
 *Date : 2023-11-14
 */
object NumberUtil {

    fun SpeakNumberOneByOne(string: String): String {
        return string
            .replace("0", "零")
            .replace("1", "一")
            .replace("2", "二")
            .replace("3", "三")
            .replace("4", "四")
            .replace("5", "五")
            .replace("6", "六")
            .replace("7", "七")
            .replace("8", "八")
            .replace("9", "九")
    }
}