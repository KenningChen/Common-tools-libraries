package com.kenning.kcutil.utils.datepicker

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kenning.kcutil.KCUtil
import java.lang.RuntimeException

/**
 *Description : 在指定布局下方弹出日历选择器,容器只能是fragment
 *
 *Date : 2021/5/8 3:23 下午
 * @param sourceFragment 跳转来源
 *@author : KenningChen
 */
class DatePickerBuilder {

    private var sourceFragment: Fragment? = null

    private var activity: FragmentActivity? = null

    private var iPickerListener: IPickerListener

    constructor(sourceFragment: Fragment) {
        this.sourceFragment = sourceFragment
        iPickerListener = sourceFragment as IPickerListener
    }

    constructor(activity: FragmentActivity) {
        this.activity = activity
        iPickerListener = activity as IPickerListener
        location = PickerControl.ShowLocation.BOTTOM
    }

    private lateinit var mViewModel: PickerViewModel

    var beginDate = ""
    var endDate = ""
    var isSingleDate = true

    var location = PickerControl.ShowLocation.TOP

//    var location = PickerControl.ShowLocation.BOTTOM

    var requestCode = -1

    var title = "开始日期"

    var mNonChange = true

    fun setDateTitle(title: String): DatePickerBuilder {
        this.title = title
        return this
    }

    /**
     * 开始时间
     */
    fun setBeginDate(begin: String): DatePickerBuilder {
        this.beginDate = begin
        return this
    }

    /**
     * 结束时间
     */
    fun setEndDate(end: String): DatePickerBuilder {
        this.endDate = end
        return this
    }

    fun setNeedChangeState(need:Boolean):DatePickerBuilder{
        mNonChange = !need
        return this
    }

    /**
     * 是否单时间选择
     * @param isSingleDate true 不区分开始和结束时间 只需要设置[setBeginDate]
     * @param isSingleDate false 区分开始和结束时间 需要同时设置[setBeginDate]和[setEndDate]
     */
    fun setSingle(isSingleDate: Boolean): DatePickerBuilder {
        this.isSingleDate = isSingleDate
        return this
    }

    /**
     * 回调code，用于同一页面有多个调取的情况
     */
    fun setRequestCode(code: Int): DatePickerBuilder {
        requestCode = code
        return this
    }

//    fun setAlpha(alpha:Float): DatePickerBuilder {
//        this.alpha = alpha
//        return this
//    }
    /**
     * 设置日期选择器的显示位置 [PickerControl.ShowLocation.TOP]为某一控件下方显示，需要fragment容器
     * [PickerControl.ShowLocation.BOTTOM]为[BottomSheetDialogFragment]的形式显示
     */
    fun setLoaction(location: PickerControl.ShowLocation): DatePickerBuilder {
        this.location = location
        //fragmentactivity为调用方时，仅支持下方弹出方式
        if (activity != null && location == PickerControl.ShowLocation.TOP) {
            this.location = PickerControl.ShowLocation.BOTTOM
        }
        return this
    }

    /**
     * 构建日期选择器
     */
    private fun build(): DatePickerFragment {
        val tagetFragment = DatePickerFragment()
        val bundle = Bundle()
        bundle.putString("start", beginDate)
        bundle.putString("end", endDate)
        bundle.putString("title", title)
        bundle.putBoolean("isSingleDate", isSingleDate)
//        bundle.putFloat("alpha",alpha)
//        bundle.putString("location",location.name)
        bundle.putInt("code", requestCode)
        tagetFragment.arguments = bundle

        mViewModel =
            ViewModelProvider(
                sourceFragment?.requireActivity() ?: activity as ViewModelStoreOwner,
                ViewModelProvider.AndroidViewModelFactory.getInstance(KCUtil.application!!)
            ).get(PickerViewModel::class.java)

        mViewModel.tagetFragment.value = iPickerListener


        return tagetFragment
    }

    private fun buildCenter(): DatePickerCenterFragment {
        val tagetFragment = DatePickerCenterFragment()
        val bundle = Bundle()
        bundle.putString("start", beginDate)
        bundle.putInt("code", requestCode)
        bundle.putBoolean("nonchange", mNonChange)
        tagetFragment.arguments = bundle

        mViewModel =
            ViewModelProvider(
                sourceFragment?.requireActivity() ?: activity as ViewModelStoreOwner,
                ViewModelProvider.AndroidViewModelFactory.getInstance(KCUtil.application!!)
            ).get(PickerViewModel::class.java)

        mViewModel.tagetFragment.value = iPickerListener


        return tagetFragment
    }

    /**
     * 构建日期选择器[BottomSheetDialogFragment]的方式
     */
    private fun buildBottom(): DatePickerBottomFragment {
        val tagetFragment = DatePickerBottomFragment()
        val bundle = Bundle()
        bundle.putString("start", beginDate)
        bundle.putString("end", endDate)
        bundle.putString("title", title)
        bundle.putBoolean("isSingleDate", isSingleDate)
//        bundle.putFloat("alpha",alpha)
//        bundle.putString("location",location.name)
        bundle.putInt("code", requestCode)
        tagetFragment.arguments = bundle

        mViewModel =
            ViewModelProvider(
                sourceFragment?.requireActivity() ?: activity as ViewModelStoreOwner,
                ViewModelProvider.AndroidViewModelFactory.getInstance(KCUtil.application!!)
            ).get(PickerViewModel::class.java)

        mViewModel.tagetFragment.value = iPickerListener


        return tagetFragment
    }

    /**
     * 跳转日期选择器
     * @param containerViewId 选择器附着的容器id，确保activity下的所有碎片布局的该id为唯一值，
     * 该参数仅在[PickerControl.ShowLocation.TOP]时生效,且必须调用方是[Fragment],[FragmentActivity]时，
     * 仅支持[PickerControl.ShowLocation.BOTTOM]
     */
    fun start(@IdRes containerViewId: Int = -1) {
        if (location == PickerControl.ShowLocation.TOP) {
            if (containerViewId == -1) {
                throw RuntimeException("请设置参数:containerViewId")
            }
            val fm = sourceFragment!!.requireActivity().supportFragmentManager.beginTransaction()
            fm.add(containerViewId, build(), null)
                .addToBackStack(null).commit()
        } else if (location == PickerControl.ShowLocation.CENTER) {
            buildCenter().show(
                sourceFragment?.requireActivity()?.supportFragmentManager
                    ?: activity!!.supportFragmentManager, null
            )
        } else {
            //
            buildBottom().show(
                sourceFragment?.requireActivity()?.supportFragmentManager
                    ?: activity!!.supportFragmentManager, null
            )
        }
    }
}