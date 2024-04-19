package com.kenning.kcutil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.kenning.base.BaseActivity
import com.kenning.kcutil.databinding.ActivityMainBinding
import com.kenning.kcutil.databinding.ViewTestDialogBinding
import com.kenning.kcutil.utils.date.DateExtendUtil
import com.kenning.kcutil.utils.date.Date_Format
import com.kenning.kcutil.utils.date.formatBy
import com.kenning.kcutil.utils.datepicker.IPickerListener
import com.kenning.kcutil.utils.dialog.easydialog.EasyDialog
import com.kenning.kcutil.utils.dialog.fragmentdialog.BaseFragmentDialog
import com.kenning.kcutil.utils.dialog.fragmentdialog.DialogFragmentButtonMode
import com.kenning.kcutil.utils.math.NumBox
import com.kenning.kcutil.utils.other.PermissionGroup
import com.kenning.kcutil.utils.other.ToastUtil
import com.kenning.kcutil.utils.other.setHook
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType
import java.util.Date

class MainActivity : BaseActivity(), IPickerListener {

    //    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view_body = /*LayoutInflater.from(this@MainActivity).inflate(
            com.kenning.kcutil.R.layout.view_test_dialog, null
        )*/TextView(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val da = DateExtendUtil.getBalanceDateByDay(
            Date().formatBy(Date_Format.YMD),
            Date_Format.YMD,
            5
        )
//        supportFragmentManager.beginTransaction().add(R.id.fcvMain, FirstFragment(),"first").commit()
        loadRootFragment(binding.fcvMain.id, FirstFragment())

        binding.fab.setOnClickListener { view ->
//            lifecycleScope.launch {
//
//                val dialog = BaseFragmentDialog(view_body)
//                    .setTitle("测试")
//                    .hideTitle(true)
//                    .cancelAble(true)
//                    .keyCancelAble(true)
//                    .setButtonMode(
//                        DialogFragmentButtonMode("hh"),
//                        DialogFragmentButtonMode("YY")
//                    )
//                view_body.append("分段加载！")
//                val result = dialog
//                    .showAsSuspend(
//                        supportFragmentManager,
//                        BaseFragmentDialog::class.java.simpleName
//                    )
//                if (result.toString() == "YY"){
//                    ToastUtil.show("click yy")
//                }else{
//                    ToastUtil.show("click other")
//                }
//
////                view_body.view1.setOnClickListener {
////                    dialog.dismiss()
////                    ToastUtil.show("click view1") }
////                view_body.view2.setOnClickListener {
////                    dialog.dismiss()
////                    ToastUtil.show("click view2") }
//            }
            var a = NumBox("3")
            var b = NumBox(3)
            if (a >= b){
                ToastUtil.show(" ${a.get()} >= ${b.get()}")
            }else if (a == b){
                ToastUtil.show(" ${a.get()} = ${b.get()}")
            }else{
                ToastUtil.show(" ${a.get()} < ${b.get()}")
            }
        }
        binding.tagswitch.setOnSwitchSuspendListener({
            val result = EasyDialog(this@MainActivity).setContentMsg("测试")
                .buildAsSuspend()
            true
        }) {
            ToastUtil.show("成功了",15000)
//
//            TTSUtil.getInstance()?.playText("拣货货位A100-1")
        }
        binding.tagswitch.setHook(
            PermissionGroup.PHONE.name,
            "没有电话权限,无法执行该功能,请先去设置权限"
        )
    }

    class a()

    override fun closeAct() {
        TODO("Not yet implemented")
    }

    override fun getBeforeData() {
        TODO("Not yet implemented")
    }

    override fun defaultData() {
        TODO("Not yet implemented")
    }

    override fun dealData() {
        TODO("Not yet implemented")
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun bindClickEvent() {
        TODO("Not yet implemented")
    }

    override fun reLoadApp() {
        TODO("Not yet implemented")
    }

    override fun onDismissPicker() {

    }

    override fun onDateChange(requestcode: Int, start: String, end: String): Boolean {
        binding.tag11.text = start
        return true
    }
    fun getGenericsClass(cls: Class<*>, index: Int): Class<*> {
        val type = cls.genericSuperclass as ParameterizedType
        return type.actualTypeArguments[index] as Class<*>
    }
}