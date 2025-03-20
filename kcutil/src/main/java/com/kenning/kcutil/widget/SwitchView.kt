package com.kenning.kcutil.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.kenning.kcutil.R
import com.kenning.kcutil.utils.math.toInt_
import com.kenning.kcutil.utils.other.ScreenUtil.dip2px
import com.kenning.kcutil.utils.other.getColorResource
import com.kenning.kcutil.widget.SwitchImageView.OnSwitchListener
import kotlinx.coroutines.launch

/**
 *Description :类checkbox组合布局
 *@author : KenningChen
 *Date : 2023-05-31
 */
class SwitchView @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var dispactchEvent: (suspend () -> Boolean)? = null

    private var mNormalDrawable: Drawable? = null

    private var mPressedDrawable: Drawable? = null

    private var mImageWidth = 40f
    private var mImageHeight = 40f

    private var textPaddingRight = 2f

    private var mTextView: TextView? = null
    private var mText = ""
    private var mTextSize = 0f
    private var mTextColor = -1

    private var checked = false
    private var autoSrcChange = true

    private lateinit var mSwitchView: SwitchImageView

    private var listener: OnSwitchListener? = null

    private lateinit var maskView: LinearLayout

    init {
        initAttrs()
        initView()
    }

    private fun initAttrs() {
        if (attrs == null) {
            return
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView)
        mTextSize = typedArray.getDimension(R.styleable.SwitchView_kc_textsize, 10f)
        mImageWidth = typedArray.getDimension(R.styleable.SwitchView_kc_imageWidth, 40f)
        mImageHeight = typedArray.getDimension(R.styleable.SwitchView_kc_imageHeight, 40f)
        textPaddingRight = typedArray.getDimension(R.styleable.SwitchView_kc_textPaddingRight, 2f)
        mNormalDrawable = typedArray.getDrawable(R.styleable.SwitchView_kc_checkOffBackground)
        mPressedDrawable = typedArray.getDrawable(R.styleable.SwitchView_kc_checkOnBackground)
        checked = typedArray.getBoolean(R.styleable.SwitchView_kc_checkstate, false)
        autoSrcChange = typedArray.getBoolean(R.styleable.SwitchView_kc_autoSrcChange, true)
        mText = typedArray.getString(R.styleable.SwitchView_kc_text) ?: ""
        mTextColor = typedArray.getColor(
            R.styleable.SwitchView_kc_textColor, getColorResource(R.color.color_333333)
        )

        typedArray.recycle()
    }

    fun initView() {

        addView(creatLinearLayout())
        addView(creatMaskView())
        setOnClickListener {
            try {
                if (dispactchEvent != null) {
                    (context as LifecycleOwner).lifecycleScope.launch {
                        var result = dispactchEvent!!()
                        if (result) {
                            mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
                        }
                    }
                }else{
                    mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
                }
            }catch (e:Exception) {
                mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
            }
        }
    }

    private fun creatLinearLayout(): LinearLayout = LinearLayout(context).apply {
        gravity = Gravity.CENTER_VERTICAL
        // 父布局LinearLayoutCompat属性配置
        orientation = LinearLayout.HORIZONTAL
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER_VERTICAL
        )
        mSwitchView = creatSwitchView()
        addView(mSwitchView)

        // 添加文本控件
        if (mText.isNotEmpty()) {
            mTextView = createTextView()
            addView(mTextView)
        }
    }

    private fun creatMaskView(): LinearLayout = LinearLayout(context)
        .apply {
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(
                mImageWidth.toInt_(),
                mImageHeight.toInt_(),
                Gravity.CENTER_VERTICAL
            )
            setOnClickListener {
                try {
                    if (dispactchEvent != null) {
                        (context as LifecycleOwner).lifecycleScope.launch {
                            var result = dispactchEvent!!()
                            if (result) {
                                mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
                            }
                        }
                    }else{
                        mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
                    }
                }catch (e:Exception) {
                    mSwitchView.setChecked(!mSwitchView.checked,autoSrcChange)
                }
            }
        }

    private fun creatSwitchView(): SwitchImageView = SwitchImageView(context).also {
        it.layoutParams = LayoutParams(
            mImageWidth.toInt_(),
            mImageHeight.toInt_()
        )
        it.setOffDrawable(mNormalDrawable)
        it.setOnDrawable(mPressedDrawable)
        it.checked = checked
        it.isEnabled = false//设置为不可点击
    }

    /**
     * 生成一个EditText
     */
    private fun createTextView(): AppCompatTextView = AppCompatTextView(context).also {
        it.background = null
        it.setTextColor(Color.BLACK)
        it.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        it.gravity = Gravity.CENTER_VERTICAL
        it.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
        it.setTextColor(mTextColor)
        it.text = mText
        val padding = dip2px(textPaddingRight)
        it.setPadding(padding, 0, 0, 0)
    }

    fun setOnSwitchListener(listener: OnSwitchListener) {
        mSwitchView.setOnSwitchListener(listener)
    }

    fun setOnSwitchSuspendListener(
        dispactchEvent: (suspend () -> Boolean)? = null,
        listener: OnSwitchListener
    ) {
        this.dispactchEvent = dispactchEvent
        mSwitchView.setOnSwitchListener(listener)
    }

    fun setText(comment: String){
        mTextView?.text = comment
    }

    fun setChecked(checked:Boolean){
        mSwitchView.checked = checked
    }

    fun getChecked(): Boolean {
        return mSwitchView.checked
    }

    /**
     * 禁止点击事件改变图标的时候，此方法生效 且 不建议使用setChecked 因为会同时触发点击事件
     * @param checked
     */
    fun switchImageState(checked: Boolean) {
        if (autoSrcChange) {
            if (checked) {
                mSwitchView.setImageDrawable(mPressedDrawable)
            } else {
                mSwitchView.setImageDrawable(mNormalDrawable)
            }
        }
    }

    fun setOffDrawable(mNormalDrawable: Drawable?) {
        mSwitchView.setOffDrawable(mNormalDrawable)
    }

    fun setOnDrawable(mNormalDrawable: Drawable?) {
        mSwitchView.setOnDrawable(mNormalDrawable)
    }

    // 设置说明文本的文字颜色
    fun setTextColor(color: Int){
        mTextView?.setTextColor(color)
    }

    // 设置未选中状态图片的tint
    fun setOffDrawableTint(color: Int){
        mNormalDrawable?.setTintList(ColorStateList.valueOf(color))
    }

    // 设置选中状态图片的tint
    fun setOnDrawableTint(color: Int){
        mPressedDrawable?.setTintList(ColorStateList.valueOf(color))
    }
}