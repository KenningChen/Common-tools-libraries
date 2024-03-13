package com.kenning.kcmixbill.data

import com.kenning.kcmixbill.billmath.MathTag
import kotlin.Throws
import java.lang.Exception

/**
 * Description :在需要商品价格及金额等相关运算的实例继承该类,用该类提供的方法进行取值和赋值操作,在继承了该类的实例中,将
 * 需要参与运算的实际参数标记为[MathTag]中的对应的[Annotation],如:折扣标记为[@MathTag.DiscountTag]
 *
 * Date : 2022/9/23
 *
 * @author : KenningChen
 */
open class AnnotationArithmeticResolve {
    /**[单位数量]的标记类型*/
    private val unitQtyTag_class = MathTag.UnitQtyTag::class.java as Class<Annotation>

    /**[开单单价]的标记类型*/
    private val unitPriceTag_class = MathTag.UnitPriceTag::class.java as Class<Annotation>

    /**[折前金额]的标记类型*/
    private val totalTag_class = MathTag.TotalTag::class.java as Class<Annotation>

    /**[含税金额]的标记类型*/
    private val taxTotalTag_class = MathTag.TaxTotalTag::class.java as Class<Annotation>

    /**[税额]的标记类型*/
    private val taxTag_class = MathTag.TaxTag::class.java as Class<Annotation>

    /**[税率]的标记类型*/
    private val taxRateTag_class = MathTag.TaxRateTag::class.java as Class<Annotation>

    /**[含税单价]的标记类型*/
    private val taxPriceTag_class = MathTag.TaxPriceTag::class.java as Class<Annotation>

    /**[折后金额]的标记类型*/
    private val discountTotalTag_class = MathTag.DiscountTotalTag::class.java as Class<Annotation>

    /**[折扣]的标记类型*/
    private val discountTag_class = MathTag.DiscountTag::class.java as Class<Annotation>

    /**[折后单价]的标记类型*/
    private val discountPriceTag_class = MathTag.DiscountPriceTag::class.java as Class<Annotation>

    /**[基本单位数量]的标记类型*/
    private val baseQtyTag_class = MathTag.BaseQtyTag::class.java as Class<Annotation>


    /**
     * 根据注解标签获取字段的值
     * @return String
     * @param clazz Class<Annotation>
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws Exception
     */
    @Throws(NoSuchMethodException::class, SecurityException::class, Exception::class)
    private fun validate(clazz: Class<Annotation>): String {
        var result = ""
        // 获取类的属性
        val fields = this.javaClass.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(clazz)) {
//                val fieldTag = field
//                    .getAnnotation(FieldTag::class.java)
                // 如果类型是String
                // 如果type是类类型，则前面包含"class "，后面跟类名
                if (field.genericType.toString() == "class java.lang.String") {
                    // 拿到该属性的getet方法
                    val m = this.javaClass.getMethod("get" + getMethodName(field.name))
                    println(field.name)
                    // 调用getter方法获取属性值
                    val `val` = m.invoke(this) as String
                    result = if (`val` == null || `val` == "") {
//                            throw new Exception(field.getName() + " 不能为空!");
                        "0"
                    }else {
                        `val`
                    }
                }
            }
        }
        return result
    }

    /**
     * 根据注解标签给对应字段赋值
     * @param value String?
     * @param clazz Class<Annotation>
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws Exception
     */
    @Throws(NoSuchMethodException::class, SecurityException::class, Exception::class)
    private fun setValues(value: String?,clazz: Class<Annotation>) {
        // 获取类的属性
        val fields = this.javaClass.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(clazz)) {
//                val fieldTag = field
//                    .getAnnotation(FieldTag::class.java)
                // 如果类型是String
                // 如果type是类类型，则前面包含"class "，后面跟类名
                if (field.genericType.toString() == "class java.lang.String") {
                    // 拿到该属性的setet方法
                    val m = this.javaClass.getMethod(
                        "set" + getMethodName(field.name),
                        field.type
                    )
                    // 调用setter方法赋值
                    m.invoke(this, value)
                }
            }
        }
    }

    /**
     * 把一个字符串的第一个字母大写、效率是最高的
     */
    @Throws(Exception::class)
    private fun getMethodName(fildeName: String): String {
        val items = fildeName.toByteArray()
        //如果首字母是大写的直接返回
        if (Character.isUpperCase(fildeName[0])) return fildeName
        //否则小写转大写
        items[0] = (items[0].toChar() - 'a' + 'A'.code).toByte()
        return String(items)
    }

    fun gettitle():String = validate(MathTag.XXTag::class.java as Class<Annotation>)

    /**
     * 获取[单位数量]
     * @return String
     */
    fun getUnitQty_():String = validate(unitQtyTag_class)

    /**
     * 赋值[单位数量]
     * @param v String
     */
    fun setUnitQty_(v:String) = setValues(v,unitQtyTag_class)


    /**
     * 获取[开单单价]
     * @return String
     */
    fun getUnitPrice_():String = validate(unitPriceTag_class)

    /**
     * 赋值[开单单价]
     * @param v String
     */
    fun setUnitPrice_(v:String) = setValues(v,unitPriceTag_class)


    /**
     * 获取[折前金额]
     * @return String
     */
    fun getTotal_():String = validate(totalTag_class)

    /**
     * 赋值[折前金额]
     * @param v String
     */
    fun setTotal_(v:String) = setValues(v,totalTag_class)


    /**
     * 获取[含税金额]
     * @return String
     */
    fun getTaxTotal_():String = validate(taxTotalTag_class)

    /**
     * 赋值[含税金额]
     * @param v String
     */
    fun setTaxTotal_(v:String) = setValues(v,taxTotalTag_class)


    /**
     * 获取[税额]
     * @return String
     */
    fun getTax_():String = validate(taxTag_class)

    /**
     * 赋值[税额]
     * @param v String
     */
    fun setTax_(v:String) = setValues(v,taxTag_class)


    /**
     * 获取[税率]
     * @return String
     */
    fun getTaxRate_():String = validate(taxRateTag_class)

    /**
     * 赋值[税率]
     * @param v String
     */
    fun setTaxRate_(v:String) = setValues(v,taxRateTag_class)


    /**
     * 获取[含税单价]
     * @return String
     */
    fun getTaxPrice_():String = validate(taxPriceTag_class)

    /**
     * 赋值[含税单价]
     * @param v String
     */
    fun setTaxPrice_(v:String) = setValues(v,taxPriceTag_class)


    /**
     * 获取[折后金额]
     * @return String
     */
    fun getDiscountTotal_():String = validate(discountTotalTag_class)

    /**
     * 赋值[折后金额]
     * @param v String
     */
    fun setDiscountTotal_(v:String) = setValues(v,discountTotalTag_class)


    /**
     * 获取[折扣]
     * @return String
     */
    fun getDiscount_():String = validate(discountTag_class)

    /**
     * 赋值[折扣]
     * @param v String
     */
    fun setDiscount_(v:String) = setValues(v,discountTag_class)


    /**
     * 获取[折后单价]
     * @return String
     */
    fun getDiscountPrice_():String = validate(discountPriceTag_class)

    /**
     * 赋值[折后单价]
     * @param v String
     */
    fun setDiscountPrice_(v:String) = setValues(v,discountPriceTag_class)


    /**
     * 获取[基本单位数量]
     * @return String
     */
    fun getBaseQty_():String = validate(baseQtyTag_class)

    /**
     * 赋值[基本单位数量]
     * @param v String
     */
    fun setBaseQty_(v:String) = setValues(v,baseQtyTag_class)

}