package com.reduxdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ceneax.app.lib.redux.IReduxView
import ceneax.app.lib.redux.ReduxView
import ceneax.app.lib.redux.observe
import com.kenning.kcutil.databinding.ActDemoBinding
import java.util.*

/**
 *Description :
 *
 *Date : 2022/9/29
 *@author : KenningChen
 */
class ReduxTestAct : AppCompatActivity(),
    IReduxView<DemoState, DemoEffect> by ReduxView() {

    lateinit var mBinding: ActDemoBinding

    fun text(): String = UUID.randomUUID().toString().substring(0..10)

    override fun onCreate(savedInstanceState: Bundle?) {
        mBinding = ActDemoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        mBinding.tv1.setOnClickListener { effect.showTost("呵呵") }
        mBinding.tv2.setOnClickListener {
           effect.changetext(text())
        }
        mBinding.tv3.setOnClickListener {
            effect.changetext2(text())
        }

    }

    override fun invalidate(state: DemoState) {
//        mBinding.tv2.text = state.text
//        mBinding.tv3.text = state.text2

    }
}