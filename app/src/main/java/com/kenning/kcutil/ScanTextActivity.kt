package com.kenning.kcutil

import android.os.Bundle
import com.kenning.base.BaseActivity
import com.kenning.kcutil.widget.CameraScanBean
import com.kenning.kcutil.widget.ImplScanResult

/**
 *Description :
 *@author : KenningChen
 *Date : 2024-04-26
 */
class ScanTextActivity:BaseActivity(), ImplScanResult {
    override fun closeAct() {
    }

    override fun getBeforeData() {
    }

    override fun defaultData() {
    }

    override fun dealData() {
    }

    override fun initView() {
    }

    override fun bindClickEvent() {
    }

    override fun reLoadApp() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kenning.kcutil.R.layout.textact)
    }

    override fun onBackPressedSupport() {
        setScanResult("dslkajflkadsjf\n")
        finish()
    }

    override fun setScanResult(result: String):CameraScanBean {
        return CameraScanBean(result)
    }
}