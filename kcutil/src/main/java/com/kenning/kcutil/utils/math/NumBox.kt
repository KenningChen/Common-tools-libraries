package com.kenning.kcutil.utils.math

/**
 *Description : 比较大小的包装类
 *@author : KenningChen
 *Date : 2024-04-19
 */
//class NumBox(private val content:Any) : Comparable<NumBox>{
//    fun getValue() = content
//    override fun compareTo(other: NumBox): Int {
//        if (this.getValue() is String && other.getValue() is Number && this.getValue().isNumeric()){
//            return this.getValue().JIAN(other.getValue()).toInt_()
//        }else if (this.getValue() is Number && other.getValue() is String && other.getValue().isNumeric()){
//            return this.getValue().JIAN(other.getValue()).toInt_()
//        }else if (this.getValue() is Number && other.getValue() is Number){
//            return this.getValue().JIAN(other.getValue()).toInt_()
//        }else if (this.getValue() is String && other.getValue() is String &&
//            this.getValue().isNumeric() && other.getValue().isNumeric()){
//            return this.getValue().JIAN(other.getValue()).toInt_()
//        } else {
//            return throw UnsupportedOperationException("Cannot compare values of types ${this.getValue().javaClass.simpleName} and ${other.getValue().javaClass.simpleName}")
//        }
//    }
//
//    // 重载大于号（>）运算符
//    operator fun NumBox.compareTo(other: NumBox): Boolean {
//        return this.compareTo(other) > 0
//    }
//
//    // 重载小于号（<）运算符
//    operator fun NumBox.compareTo(other: NumBox): Boolean {
//        return this.compareTo(other) < 0
//    }
//}


class NumBox(private val content: Any) : Comparable<NumBox> {
    fun get() = content
    override fun compareTo(other: NumBox): Int {

        if (this.get() is String && other.get() is Number && this.get().isNumeric()){
            return if(this.get().JIAN(other.get()).toDouble_()>0) 1 else -1
        }else if (this.get() is Number && other.get() is String && other.get().isNumeric()){
            return if(this.get().JIAN(other.get()).toDouble_()>0) 1 else -1
        }else if (this.get() is Number && other.get() is Number){
            return if(this.get().JIAN(other.get()).toDouble_()>0) 1 else -1
        }else if (this.get() is String && other.get() is String &&
            this.get().isNumeric() && other.get().isNumeric()){
            return if(this.get().JIAN(other.get()).toDouble_()>0) 1 else -1
        } else {
            return throw UnsupportedOperationException("Cannot compare values of types ${this.get
                ().javaClass.simpleName} and ${other.get().javaClass.simpleName} or string is " +
                    "not Number")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NumBox) return false

        if (this.get() is String && other.get() is Number && this.get().isNumeric()){
            return this.get().JIAN(other.get()).toDouble_() == 0.0
        }else if (this.get() is Number && other.get() is String && other.get().isNumeric()){
            return this.get().JIAN(other.get()).toDouble_() == 0.0
        }else if (this.get() is Number && other.get() is Number){
            return this.get().JIAN(other.get()).toDouble_() == 0.0
        }else if (this.get() is String && other.get() is String &&
            this.get().isNumeric() && other.get().isNumeric()){
            return this.get().JIAN(other.get()).toDouble_() == 0.0
        } else
            return content == other.content
    }

    override fun hashCode(): Int {
        return content?.hashCode() ?: 0
    }

    private fun String.isNumeric(): Boolean {
        return this.matches("-?\\d+".toRegex())
    }
}
