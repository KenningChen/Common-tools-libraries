package com.kenning.kcutil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kenning.base.BaseBusinessFragment
import com.kenning.kcutil.databinding.FragmentFirstBinding
import com.kenning.kcutil.utils.date.DateEnum
import com.kenning.kcutil.utils.date.DateExtendUtil
import com.kenning.kcutil.utils.datepicker.DatePickerBuilder
import com.kenning.kcutil.utils.datepicker.DatePickerFragment
import com.kenning.kcutil.utils.datepicker.IPickerListener
import com.kenning.kcutil.utils.datepicker.PickerControl
import com.kenning.kcutil.utils.math.CHENG
import com.kenning.kcutil.utils.math.keepPoint
import com.kenning.kcutil.utils.other.ToastUtil

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseBusinessFragment(), IPickerListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
        binding.scanView.setRightImageClickEvent {
            ToastUtil.show("哦吼")
        }.setOnScanCallback {
            binding.tvResult.text = it
        }.setOnClickListener {
            ToastUtil.show("hahfdah")
        }
//        binding.scanView.requestFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismissPicker() {
//        Log.d("kenning", "日期选择器关闭")
    }

    override fun onDateChange(requestcode: Int, start: String, end: String):Boolean {
//        binding.textviewFirst.text = "$start - $end"
        return true
    }
}