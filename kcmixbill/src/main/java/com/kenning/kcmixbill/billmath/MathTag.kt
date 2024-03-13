package com.kenning.kcmixbill.billmath

import java.lang.annotation.*
import java.lang.annotation.Retention

/**
 * Description :
 *
 *
 * Date : 2022/9/23
 *
 * @author : KenningChen
 */
class MathTag {
    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 基本单位数量 */
    annotation class BaseQtyTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.CLASS) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 基本单位数量 */
    annotation class XXTag(val isList: Boolean = false)

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 折后单价 */
    annotation class DiscountPriceTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 折扣 */
    annotation class DiscountTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 折后金额 */
    annotation class DiscountTotalTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 含税单价 */
    annotation class TaxPriceTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 税率 */
    annotation class TaxRateTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 单位数量 */
    annotation class UnitQtyTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 开单单价 */
    annotation class UnitPriceTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 折前金额 */
    annotation class TotalTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 含税金额 */
    annotation class TaxTotalTag(val value: String = "")

    //注解包含于Javadoc中
    @Documented //注解的作用目标
    @Target(AnnotationTarget.FIELD) //注解可以被继承
    @Inherited //注解的保留策略
    @Retention(RetentionPolicy.RUNTIME)
    /**开单公式 开单公式 税额 */
    annotation class TaxTag(val value: String = "")
}