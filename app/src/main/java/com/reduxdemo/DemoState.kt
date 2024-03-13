package com.reduxdemo

import ceneax.app.lib.redux.IReduxState

/**
 *Description :
 *
 *Date : 2022/9/29
 *@author : KenningChen
 */
data class DemoState(

    //按钮2的文字变化
    val text:String = "",
    val text2:String = ""

) :IReduxState