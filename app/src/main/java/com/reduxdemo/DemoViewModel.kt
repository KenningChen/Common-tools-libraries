package com.reduxdemo

import ceneax.app.lib.redux.ReduxReducer

/**
 *Description :
 *
 *Date : 2022/9/29
 *@author : KenningChen
 */
class DemoViewModel: ReduxReducer<DemoState>() {


    fun updatatext(content:String) = setState { copy(text = content) }
    fun updatatext2(content:String) = setState { copy(text2 = content) }
}