package com.kenning.kcmixbill

/**
 *Description : 每张单据对应一个分类,切换分类时需要获取对应分类下的有权限的单据类型
 *
 *Date : 2022/9/23
 *@author : KenningChen
 */
interface ISwitchBill {

    /**
     * 单据切换
     */
    fun switchBill()

    /**
     * 单据分类切换
     */
    fun switchType()
}