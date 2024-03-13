package com.kenning.tbean

import java.io.Serializable

/**
 *Description :
 *
 *Date : 2022/9/14
 *@author : KenningChen
 */
class BeanB : Serializable,Cloneable{

    var eName = ""

    var eClass = ""

    override fun clone(): BeanB {
        return super.clone() as BeanB
    }
}