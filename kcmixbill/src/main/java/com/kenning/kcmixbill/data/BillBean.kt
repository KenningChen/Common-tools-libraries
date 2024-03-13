package com.kenning.kcmixbill.data

import com.kenning.kcmixbill.billmath.MathTag

/**
 *Description :
 *
 *Date : 2022/9/23
 *@author : KenningChen
 */
@MathTag.XXTag(true)
class BillBean : ArithmeticModel(){

    var name = ""

    var type = ""

//    @MathTag.UnitQtyTag
    var Qty = ""

}