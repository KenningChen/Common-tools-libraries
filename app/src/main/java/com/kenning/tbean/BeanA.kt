package com.kenning.tbean

import java.io.Serializable

/**
 *Description :
 *
 *Date : 2022/9/14
 *@author : KenningChen
 */
class BeanA :Serializable,Cloneable{

    var name = ""

    var age = 1

    var level = 10

    var extenddata = ArrayList<BeanB>()

    public override fun clone(): BeanA {
        return super.clone() as BeanA
    }
}