package com.kenning.kcutil.utils.datepicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.kenning.kcutil.KCUtil
import com.kenning.kcutil.R
import com.kenning.kcutil.databinding.ViewDatepickerBottomBinding
import com.kenning.kcutil.databinding.ViewDatepickerCenterBinding
import com.kenning.kcutil.utils.date.Date_Format
import com.kenning.kcutil.utils.date.formatBy
import com.kenning.kcutil.utils.date.parseBy
import com.kenning.kcutil.utils.math.toInt_
import com.kenning.kcutil.utils.other.ScreenUtil
import java.text.SimpleDateFormat

/**
 *Description : 居中单日期选择,支持按月 按日
 *@author : KenningChen
 *Date : 2023/2/21
 */
class DatePickerCenterFragment : DialogFragment(R.layout.view_datepicker_center), IDatePickerBase {

    private val mViewModel: PickerViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(KCUtil.application!!)
        ).get(PickerViewModel::class.java)
    }
    override var startdate: String = ""
    override var enddate: String = ""

    override var isSingleDate: Boolean = true

    override lateinit var mView: View

    // 是否需要日/月切换按钮
    var mNonChange = true

    var formatstr = ""

    // 日期格式是否包含-
    var isFormat_ = true

    var type = ""

    override fun putDateToView(vararg date: String, type: Int, isSignal: Boolean) {
        var year = date[0]
        var month = date[1]
        var day = date[2]

        this.startdate = "${year}-${month}-${day}"
    }

    lateinit var control: PickerControl

    var bundle = Bundle()

    var code = -1

    private lateinit var mContext: Context

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            (ScreenUtil.getScreenWidth() * 0.8).toInt_(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (mViewModel.tagetFragment.value as IPickerListener).onDismissPicker()
                    dismiss()
//                    isEnabled = false
//                    requireActivity().supportFragmentManager.popBackStack()//onBackPressedDispatcher.onBackPressed()
//                    isEnabled = true
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        isCancelable = false
        dialog?.window?.apply {
            decorView.setBackgroundColor(Color.TRANSPARENT)
        }
        mView = ViewDatepickerCenterBinding.inflate(layoutInflater).root
        return mView//super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        mNonChange = bundle.getBoolean("nonchange", true)
        code = bundle.getInt("code", -1)
        startdate = bundle.getString("start", "")
        //判断日期格式
        if (!startdate.contains("-")) {
            isFormat_ = false
            val year = startdate.substring(0, 4)
            val month = startdate.substring(4, 6)
            val day =
                if (startdate.length > 6)
                    startdate.substring(6, startdate.length)
                else ""
            if (day.isEmpty()) {
                type = "MM"
                formatstr = "yyyyMM"
                startdate = "${year}-${month}-01"
            } else {
                type = "DD"
                formatstr = "yyyyMMdd"
                startdate = "${year}-${month}-$day"
            }
        } else {
            kotlin.runCatching {
                startdate parseBy Date_Format.YMD
            }.onSuccess {
                //传入的日期格式为yyyy-MM-dd
                type = "DD"
                formatstr = "yyyy-MM-dd"
            }.onFailure {
                //传入的日期格式为yyyy-MM
                type = "MM"
                startdate = "${startdate}-01"
                formatstr = "yyyy-MM"
            }
        }
        if (mNonChange) {
            type = "DD"
            formatstr = "yyyy-MM-dd"
        }

        control = PickerControl(this)
        control.type = type

        control.setCurrentDate()

        initView()
        bindEvent()
    }

    private fun initView() {
        if (mNonChange) {
            mView.findViewById<View>(R.id.switchType).visibility = View.GONE
        }
    }

    private fun bindEvent() {
        mView.findViewById<View>(R.id.cancel).setOnClickListener {
            (mViewModel.tagetFragment.value as IPickerListener).onDismissPicker()
            dismiss()
        }

        mView.findViewById<View>(R.id.select).setOnClickListener {

            //日期回传
            var time =
                if (control.type == "MM") {
                    formatstr = formatstr.replace("-dd", "".replace("dd", ""))
                    (startdate parseBy Date_Format.YMD)!! formatBy SimpleDateFormat(formatstr)
                } else {
                    if (!formatstr.contains("dd")) {
                        if (isFormat_)
                            formatstr = formatstr + "-dd"
                        else
                            formatstr = formatstr + "dd"
                    }
                    (startdate parseBy Date_Format.YMD)!! formatBy SimpleDateFormat(formatstr)
                }
            if (mNonChange) formatstr = "yyyy-MM-dd"
            val suc = (mViewModel.tagetFragment.value as IPickerListener).onDateChange(
                code, time, ""
            )
//            needBackValues = true
            if (suc) {
                (mViewModel.tagetFragment.value as IPickerListener).onDismissPicker()
                dismiss()
            }
        }

        mView.findViewById<View>(R.id.layoutDateCheck).setOnClickListener { }
        mView.findViewById<View>(R.id.layoutOnly).setOnClickListener { }
        mView.findViewById<View>(R.id.viewbottom).setOnClickListener { }

        control.bindClick()
    }
}