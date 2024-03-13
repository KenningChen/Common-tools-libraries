package com.kenning.kcmixbill.data


/**
 *Description : 公式
 *
 *Date : 2022/9/26
 *@author : KenningChen
 */
open class ArithmeticModel:AnnotationArithmeticResolve() {
    fun editQty(qty:String) = setUnitQty_(qty)
}