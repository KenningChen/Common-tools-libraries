package com.kenning.kcutil.utils.dialog.fragmentdialog

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IntRange
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kenning.kcutil.R
import com.kenning.kcutil.utils.dialog.easydialog.BaseDialog
import com.kenning.kcutil.utils.math.toInt_
import com.kenning.kcutil.utils.other.ScreenUtil
import com.kenning.kcutil.utils.other.getColorResource
import com.kenning.kcutil.widget.basicview.BackGroundTextView
import kotlinx.android.synthetic.main.dialogfragment_base.layoutBody
import kotlinx.android.synthetic.main.dialogfragment_base.layoutBottom
import kotlinx.android.synthetic.main.dialogfragment_base.layoutBottomButton
import kotlinx.android.synthetic.main.dialogfragment_base.main
import kotlinx.android.synthetic.main.dialogfragment_base.tvTitle
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 *Description :
 *@author : KenningChen
 *Date : 2023/10/21
 */
class BaseFragmentDialog(
    private val bodyView: View,
    @IntRange(from = 75,to= 90) val widthPer:Int = 80,
    @IntRange(from = 60,to= 100) val heightPer:Int? = null
): DialogFragment(R.layout.dialogfragment_base) {

    private var title: String? = "提示"
    
    /**设置底部操作按钮的排列方式
     *
     * 0 横向
     *
     * 1 纵向
     * */
    private var bottomButtonOption = 0
    
    /**
     * 控件各个边角的圆半径
     */
    fun radius():Int = ScreenUtil.dip2px(5f)

    /**
     * 底部按钮高度
     */
    fun buttonHeight():Int = ScreenUtil.dip2px(40f)

    // 隐藏底部控件
    private var hideBottom = false

    // 隐藏标题栏
    private var hideTitle = false


    private var modes: Array<out DialogFragmentButtonMode>? = null

    private val mSubscribe = BaseDialog.Subscribe<Any?>()
    private var mResult:Any? = null

    private var bodyPadding = false

    private var cancelable = false

    private var keyCancel = false

    fun cancelAble(cancel: Boolean = false): BaseFragmentDialog{
        cancelable = cancel
        return this
    }

    fun keyCancelAble(cancel: Boolean = false): BaseFragmentDialog{
        keyCancel = cancel
        return this
    }

    fun hideTitle(hide: Boolean = false): BaseFragmentDialog{
        this.hideTitle = hide
        return this
    }

    fun hideBottom(hide: Boolean = false): BaseFragmentDialog{
        this.hideBottom = hide
        return this
    }

    fun needBodyPadding(need: Boolean = false): BaseFragmentDialog{
        bodyPadding = need
        return this
    }

    fun setTitle(title:String): BaseFragmentDialog{
        this.title = title
        return this
    }

    fun setButtonMode(vararg modes: DialogFragmentButtonMode): BaseFragmentDialog {
        this.modes = modes
        return this
    }

    private fun setBottomLayout(modes: Array<out DialogFragmentButtonMode>? = null) {
        layoutBottomButton.removeAllViews()
        layoutBottomButton.orientation = bottomButtonOption
        if (modes != null && modes.isNotEmpty()) {
            var index = 0
            for (item in modes) {
                var button: BackGroundTextView = LayoutInflater.from(requireActivity())
                    .inflate(R.layout.item_button, null) as BackGroundTextView
                button.tag = index
                val params: LinearLayout.LayoutParams
                if (bottomButtonOption == 0) {//横向排列button
                    params = LinearLayout.LayoutParams(
                        0, buttonHeight(), 1f
                    )
                    if (index == 0 && modes.size - 1 > index) {
                        button.setEachCornerRadius(
                            0,
                            0,
                            radius(),
                            0
                        )
                    } else if (index == 0 && modes.size - 1 == index) {
                        button.setEachCornerRadius(
                            0,
                            0,
                            radius(),
                            radius()
                        )
                    } else if (index != 0 && modes.size - 1 == index) {
                        button.setEachCornerRadius(
                            0,
                            0,
                            0,
                            radius()
                        )
                    }
                } else {
                    params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, buttonHeight()
                    )
                    if (modes.size - 1 == index) {
                        button.setEachCornerRadius(
                            0,
                            0,
                            radius(),
                            radius()
                        )
                    }
                }

                if (index > 0) {
                    var textview = TextView(requireActivity())
                    val params: LinearLayout.LayoutParams =
                        if (bottomButtonOption == 0) {
                            LinearLayout.LayoutParams(
                                ScreenUtil.dip2px(1f), LinearLayout.LayoutParams.MATCH_PARENT
                            )
                        } else {
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(1f)
                            )
                        }
                    textview.setBackgroundColor(getColorResource(R.color.color_EAEEEF))
                    textview.layoutParams = params
                    layoutBottomButton.addView(textview)
                }

                button.text = item.text
                button.setOnClickListener {
//                    mResult = (it as TextView).text.toString()
                    mResult = (it as TextView).text.toString()
                    item.click?.apply {
                        invoke(this@BaseFragmentDialog)
                        dismiss()
                    } ?: kotlin.run {
                        dismiss()
                    }
                }
                button.setTextColor(getColorResource(item.textcolor))
                button.setNormalBackgroundColor(getColorResource(item.backgroundcolor))
                button.layoutParams = params

                layoutBottomButton.addView(button)

                index++
            }
        } else {
            var button: BackGroundTextView =
                LayoutInflater.from(requireActivity())
                    .inflate(R.layout.item_button, null) as BackGroundTextView
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                0, buttonHeight(), 1f
            )
            button.setEachCornerRadius(
                0,
                0,
                radius(),
                radius()
            )

            button.text = "我知道了"

            button.setTextColor(getColorResource(R.color.color_333333))
            button.setNormalBackgroundColor(getColorResource(R.color.white))
            button.layoutParams = params

            button.setOnClickListener {
                mResult = (it as TextView).text.toString()
//                mResult = (it as TextView).text.toString()
                dismiss()
            }
            layoutBottomButton.addView(button)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        isCancelable = keyCancel
        dialog?.window?.apply {
            decorView.setBackgroundColor(Color.TRANSPARENT)
        }

        getDialog()?.setCanceledOnTouchOutside(cancelable);

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        // 设置禁止通过物理返回键关闭对话框
        dialog!!.setOnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // 处理物理返回键事件
                !keyCancel // 返回 true 表示消费掉按键事件，不关闭对话框
            } else false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindEvent()
    }

    private fun bindEvent() {

    }

    private fun initView() {
        val params = main.layoutParams
        params.width = (ScreenUtil.getScreenWidth()*widthPer*0.01).toInt_()
        heightPer?.apply {
            params.height = (ScreenUtil.getScreenWidth()*this*0.01).toInt_()
        }

        main.layoutParams = params

        main.setEachCornerRadius(radius(), radius(), radius(), radius())
        tvTitle.setEachCornerRadius(radius(), radius(), 0, 0)
        tvTitle.text = title
        if (hideTitle){
            tvTitle.visibility = View.GONE
        }
        if (bodyPadding)
        layoutBody.setPadding(radius(), radius(), radius(), radius())

        Handler(Looper.getMainLooper()).post {
            layoutBody.removeAllViews()
            layoutBody.addView(bodyView)
            if (hideBottom){
                layoutBottom.visibility = View.GONE
                layoutBody.setEachCornerRadius(
                    0,0,
                    radius(), radius()
                )
                return@post
            }
            setBottomLayout(modes)
        }
    }

    suspend fun showAsSuspend(manager: FragmentManager, tag: String?) = suspendCoroutine<Any?> {
        mSubscribe.onSubscribe { value ->
            it.resume(value)
        }
        this.show(manager, tag)
    }

    override fun dismiss() {
        super.dismiss()
        mSubscribe.post(mResult)
    }
}