package com.kenning.kcutil.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import com.kenning.kcutil.KCUtil
import com.kenning.kcutil.R
import com.kenning.kcutil.utils.math.toFloat_
import com.kenning.kcutil.utils.other.KeyBoardUtil.CloseSoftInput
import com.kenning.kcutil.utils.other.ScreenUtil
import com.kenning.kcutil.utils.other.ToastUtil
import com.kenning.kcutil.utils.other.getColorResource
import com.kenning.kcutil.utils.other.inOf

/**
 *Description :扫描框控件
 *@author : KenningChen
 *Date : 2024-04-25
 */
class ScanViewGroup @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    private var isFirst = true

    // 放大镜
    private var mImgSearch = R.drawable.svg_kcutil_search

    // 扫描
    private var mImgScan = R.drawable.svg_kcutil_scan

    // 删除
    private var mImgDelete = R.drawable.svg_kcutil_delete

    // 右更多
    private var mImgMore = R.drawable.svg_kcutil_rightmore


    // 模式，0：扫描模式，1：选择模式，2：扫描+选择
    private var mMode = 0

    // 模式，0：亮色，1：暗色
    private var mTheme = 1

    // 默认内边距
    private var mPadding = ScreenUtil.dip2px(10f)

    // 文本字体大小
    private var mTextSize = ScreenUtil.sp2px(14f).toFloat_()

    // EditText的hint
    private var mHint = ""

    // Tag模式的文本内容
    private var mTitleContent = ""

    // 整个ViewGroup是否只包含EditText
    private var mOnlyEditText = false

    // 是否已Textview的形式展示,edittext控件禁止焦点及键盘事件
    private var mAsTextView = false

    // 是否显示删除按钮
    private var mShowDeleteIcon = false

    // 是否显示复合组件的shape，边框及圆角
    private var mShowShape = true

    // 边框颜色
    private var mBorderColor = Color.parseColor("#00ABF3")

    // 边框细度
    private var mBorderWidth = 1f

    // 背景颜色
    private var mBackColor = Color.TRANSPARENT

    // 边框圆角大小
    private var mCornerRadius = 10f

    // 扫描完是否自动清空EditText
    private var mAutoClear = true

    // 是否显示扫描的图片
    private var mShowScanImage = true

    // 是否显示搜索的图片
    private var mShowSearchImg = true

    // 是否显示选择按钮
    private var mShowSelectButton = true

    // 文本内容及图标亮色显示
    private var mLightContent = false

    // 右更多的点击事件是否与EditText作为TextView时的点击保持一致
    private var mSameRightView = true

    private var mEditText: AppCompatEditText? = null
    private var mScanImgView: ImageView? = null
    private var mSearchImgView: ImageView? = null
    private var mDeleteView: ImageView? = null
    private var mSelectImgView: ImageView? = null
    private var mTagView: AppCompatTextView? = null


    // EditText扫描完成后的回调
    private var mOnScanCallback: (String) -> Unit = {}

    // 清除事件
    private var mOnDeleteListener: (() -> Unit)? = null

    // EditText的 Touch事件
    private var mTouchEvent: ((MotionEvent) -> Unit)? = null

    // 右更多（自定义）图标的点击事件
    private var mClickEvent: (() -> Unit)? = null

    // EditText作为TextView使用时的点击事件
    private var mOnClickListener: ((View) -> Unit)? = null

    // 摄像头扫描按钮事件
    private var mOnCameraScanClickListener: (() -> Unit)? = null

    init {
        initAttrs()
        initView()
        bindEvent()
    }

    private fun initAttrs() {
        if (attrs == null) {
            return
        }

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanEditText)
        mAsTextView = typedArray.getBoolean(R.styleable.ScanEditText_asTextview, false)
        mLightContent = typedArray.getBoolean(R.styleable.ScanEditText_lightContent, mLightContent)
        mBorderColor = typedArray.getColor(R.styleable.ScanEditText_borderColor, mBorderColor)
        mBackColor = typedArray.getColor(R.styleable.ScanEditText_backgroundColor, mBackColor)
        mMode = typedArray.getInt(R.styleable.ScanEditText_viewType, 0)
        mTheme = typedArray.getInt(R.styleable.ScanEditText_groupTheme, 0)
        mImgMore = typedArray.getResourceId(R.styleable.ScanEditText_rightImage, mImgMore)
        mCornerRadius = typedArray.getDimension(R.styleable.ScanEditText_cornerRadius, 10f)
        mBorderWidth = typedArray.getDimension(R.styleable.ScanEditText_borderWidth, 1f)
        val textSize = typedArray.getDimension(R.styleable.ScanEditText_textSize, -1f)
        mHint = typedArray.getString(R.styleable.ScanEditText_hint) ?: ""
        mTitleContent = typedArray.getString(R.styleable.ScanEditText_titleContent) ?: ""
        mAutoClear = typedArray.getBoolean(R.styleable.ScanEditText_autoClear, mAutoClear)
        mOnlyEditText = typedArray.getBoolean(R.styleable.ScanEditText_onlyEditText, mOnlyEditText)
        mShowSelectButton =
            typedArray.getBoolean(R.styleable.ScanEditText_showRightImg, mShowSelectButton)
        mShowDeleteIcon =
            typedArray.getBoolean(R.styleable.ScanEditText_showDeleteImg, mShowDeleteIcon)
        mShowScanImage =
            typedArray.getBoolean(R.styleable.ScanEditText_showScanImg, mShowScanImage)
        mShowSearchImg =
            typedArray.getBoolean(R.styleable.ScanEditText_showSearchImg, mShowSearchImg)
        mShowShape =
            typedArray.getBoolean(R.styleable.ScanEditText_showShape, mShowShape)
        typedArray.recycle()

        if (textSize == -1f) {
            val typedText = context.obtainStyledAttributes(attrs, R.styleable.ThemeTextSize)
            mTextSize = typedText.getDimension(R.styleable.ThemeTextSize_normalSize, mTextSize)
            typedText.recycle()
        } else {
            mTextSize = textSize
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL

        // 从左到右控件添加顺序  搜索标-》标题-》编辑框-》删除-》扫描-》选择

        // 类型一：该复合控件中只有EditText
        if (mOnlyEditText) {
            mEditText = createEditText()
            addView(mEditText)
            return
        }

        // 满足条件时创建对应的控件
        mEditText = createEditText(mAsTextView)
        if (mMode inOf arrayListOf(0, 2)) {
            if (mTitleContent.isNotEmpty()) {
                mTagView = createTitleView(mTitleContent)
                // 有扫描功能
                if (mShowScanImage && !mAsTextView) {
                    // 显示扫描标
                    mScanImgView = createImageView(mImgScan)
                }
            } else {
                // 有扫描功能
                if (mShowScanImage) {
                    // 显示扫描标
                    mScanImgView = createImageView(mImgScan)
                }
                if (mShowSearchImg) {
                    // 显示搜索标
                    mSearchImgView = createImageView(mImgSearch)
                }
            }
            if (mMode == 2) {
                if (mShowSelectButton) {
                    mSelectImgView = createImageView(mImgMore, 1)
                }
            }
        }else{
            if (mTitleContent.isNotEmpty()) {
                mTagView = createTitleView(mTitleContent)
            }

            if (mShowSelectButton) {
                mSelectImgView = createImageView(mImgMore, 1)
            }
        }

        if (mShowDeleteIcon) {
            // 显示删除标
            mDeleteView = createImageView(mImgDelete, 0)
        }

        var isaddpadding = false
        mSearchImgView?.apply {
            addView(this)
            isaddpadding = true
            this@ScanViewGroup.setPadding(ScreenUtil.dip2px(8f), 0, ScreenUtil.dip2px(8f), 0)
        }
        mTagView?.apply {
            addView(this)
        }
        addView(mEditText)
        mDeleteView?.apply {
            addView(this)
        }
        mScanImgView?.apply {
            addView(this)
        }
        mSelectImgView?.apply {
            addView(this)
        }

        if (!isaddpadding) {
            setPadding(0, 0, ScreenUtil.dip2px(5f), 0)
        }

        // 暗色模式 文本及图标颜色 设置为白色
        if (mTheme == 0) {
            addShape(mShowShape)
            if (mLightContent) {
                setContentLight()
            }
        } else if (mTheme == 1) {
            val gradientDrawable = GradientDrawable()
            gradientDrawable.setColor(getColorResource(R.color.groupTheme_Dark))
            gradientDrawable.cornerRadius = ScreenUtil.dip2px(mCornerRadius).toFloat_()
            background = gradientDrawable
        }/*else{
//            // =2 自定义
//            val gradientDrawable = GradientDrawable()
//            gradientDrawable.setStroke(ScreenUtil.dip2px(1f), mBorderColor)
//            gradientDrawable.setColor(mBackColor)
//            gradientDrawable.cornerRadius = ScreenUtil.dip2px(mCornerRadius).toFloat_()
//            background = gradientDrawable
        }*/
    }


    private fun bindEvent() {
        // 主事件 edittext 监听事件
        if (mAsTextView) {
            mEditText?.setOnClickListener{mOnClickListener?.invoke(it)}
        } else {
            mEditText?.addTextChangedListener { editable ->
                if (mAsTextView) return@addTextChangedListener

                editable?.toString()?.let {
                    if (!it.endsWith("\n")) {
                        return@addTextChangedListener
                    }

                    val content = it.substring(0, it.length - 1)
                    editable.clear()
                    if (!mAutoClear && !content.contains("\n")) {
                        editable.append(content)
                        mEditText?.setSelection(content.length)
                    }

                    mOnScanCallback?.invoke(content)
                    // 识别成功关闭软键盘
                    runCatching {
                        (context as? Activity)?.CloseSoftInput()
                    }
                }
            }
        }

        // 清除事件
        mDeleteView?.setOnClickListener {
            mEditText?.setText("")
            mOnDeleteListener?.invoke()
        }

        // edittext touch事件
        mEditText?.setOnTouchListener { v, event ->
            mTouchEvent?.invoke(event)
            false
        }

        // 右更多点击事件
        mSelectImgView?.setOnClickListener {
            if (mSameRightView) {
                mOnClickListener?.run {
                    this(it)
                } ?: run {
                    mClickEvent?.invoke()
                }
            } else {
                mClickEvent?.invoke()
            }
        }

        mScanImgView?.setOnClickListener {
            mOnCameraScanClickListener?.invoke()?:run {
                if (KCUtil.ScanClassAbPath.isNotEmpty()){
                    runCatching {
                        val clazz = Class.forName(KCUtil.ScanClassAbPath)
                        context.startActivity(Intent(context,clazz))
                    }.onFailure {
                        ToastUtil.show(it.toString())
                    }
                }else{
                    ToastUtil.show("请在Application类中调用setScanClassPath方法,或手动调用setOnCameraListener")
                }
            }
        }
    }

    /**
     * 设置文字及图片颜色为白色
     */
    fun setContentLight() {
        mTagView?.setTextColor(getColorResource(R.color.white))
        mEditText?.setTextColor(getColorResource(R.color.white))
        mEditText?.setHintTextColor(getColorResource(R.color.white))

        mSearchImgView?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        mDeleteView?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        mScanImgView?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
        mSelectImgView?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
    }

    /**
     * 设置背景色及边框
     *
     * [showShape] 是否显示边框
     */
    private fun addShape(showShape: Boolean) {
        val gradientDrawable = GradientDrawable()
        if (showShape) {
            gradientDrawable.setStroke(ScreenUtil.dip2px(mBorderWidth), mBorderColor)
        }
        gradientDrawable.setColor(mBackColor)
        gradientDrawable.cornerRadius = ScreenUtil.dip2px(mCornerRadius).toFloat_()
        background = gradientDrawable
    }

    /**
     * 生成一个ImageView
     *
     * [resId] 图片资源ID
     *
     * [marginDirection] margin外边距的方向，-1：无边距，0：右侧应用边距，1：左侧应用边距
     */
    private fun createImageView(
        resId: Int,
        marginDirection: Int = -1,
        size: Float = 22f
    ): ImageView = ImageView(context).also {
        it.setImageResource(resId)
        val width = ScreenUtil.dip2px(size)
        val lp = LayoutParams(width, width)
        when (marginDirection) {
            -1 -> {
                lp.setMargins(0, 0, 0, 0)
            }

            0 -> {
                lp.setMargins(0, 0, mPadding, 0)
            }

            else -> {
                lp.setMargins(mPadding, 0, 0, 0)
            }
        }
        it.layoutParams = lp
    }

    /**
     * 生成一个EditText
     *
     * [asTextView] 是否作为TextView使用
     */
    private fun createEditText(asTextView: Boolean = false): AppCompatEditText =
        AppCompatEditText(context)
            .also {
                it.background = null
                it.setTextColor(Color.BLACK)
                it.layoutParams = LayoutParams(
                    0, /*if (mOnlyEditText)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else*/
                    ScreenUtil.dip2px(42f), 1f
                )
                it.gravity = Gravity.CENTER_VERTICAL
                it.hint = mHint
                it.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
                if (!mOnlyEditText && mMode != 1) {
                    val padding = ScreenUtil.dip2px(10f)
                    it.setPadding(padding, 0, padding, 0)
                } else {
                    it.setPadding(0, 0, 0, 0)
                }
                it.isFocusable = true
                it.isFocusableInTouchMode = true

                if (asTextView) {
                    it.isCursorVisible = false
                    it.isFocusable = false
                    it.keyListener = null
                }
            }

    /**
     * 生成一个TextView，表现为TagView
     *
     * [text] TagView的内容
     */
    private fun createTitleView(text: String): AppCompatTextView = AppCompatTextView(context).also {
        it.setTextColor(resources.getColor(R.color.gray))
        it.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
        it.gravity = Gravity.CENTER
        it.text = text
        val padding = ScreenUtil.dip2px(10f)
        it.setPadding(padding, 0, padding, 0)
        val lp =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        it.layoutParams = lp
    }

    /**
     * 扫描图标的事件
     */
    fun setOnCameraListener(listener: () -> Unit): ScanViewGroup {
        mOnCameraScanClickListener = listener
        return this
    }

    /**
     * 设置扫描获取到处理后的内容后的回调事件
     */
    fun setOnScanCallback(callback: (String) -> Unit): ScanViewGroup {
        mOnScanCallback = callback
        return this
    }

    /**
     * 清除事件
     */
    fun setOnDeleteListener(listener: () -> Unit): ScanViewGroup {
        mOnDeleteListener = listener
        return this
    }

    /**
     * EditText获取焦点
     */
    fun requestEditFocus() {
        mEditText?.apply {
            requestFocus()
            setSelection(this.text.toString().length)
        }
    }

    /**
     * 设置EditText的Touch事件
     */
    fun setOnTouchEvent(touchEvent: (MotionEvent) -> Unit): ScanViewGroup {
        mTouchEvent = touchEvent
        return this
    }

    /**
     * EditText作为TextView使用时的点击事件
     *
     * [sameRightView] 是否该事件同时对右更多（自定义）图标生效 默认生效
     */
    fun setOnClickListener(sameRightView: Boolean = true, listener: (View) -> Unit): ScanViewGroup {
        mOnClickListener = listener
        return this
    }

    /**
     * 清空操作
     */
    fun clear(callback: (() -> Unit)? = null){
        mEditText?.setText("")
        callback?.invoke()
    }

    /**
     * 设置最右边图片的点击事件
     */
    fun setRightImageClickEvent(clickEvent: () -> Unit): ScanViewGroup {
        mClickEvent = clickEvent
        return this
    }

    /**
     * 设置Title提示
     */
    fun setTitleContent(content: String) {
        mTagView?.text = content
    }

    /**
     * 设置编辑框的hint内容
     */
    fun setHint(hint: String) {
        mEditText?.hint = hint
    }

    /**
     * 获取文本内容
     */
    fun getText() = mEditText?.text?.toString()

    @SuppressLint("SetTextI18n")
    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus && !isFirst){
            val content = KCUtil.getScanResult()?.result?:""
            if (content.isEmpty()){

            }else {
                if (content.endsWith("\n")) {
                    mEditText?.setText(content)
                }else{
                    mEditText?.setText("${content}\n")
                }
            }
            requestEditFocus()
            KCUtil.setScanResult(null)
        }
        isFirst = false
    }
}


/**
 * 扫描回调接口
 */
interface ImplScanResult{
    // 赋值扫描结果
    fun setScanResult(result: String):CameraScanBean
}

/**
 * 扫描结果实例
 */
class CameraScanBean(val result: String){
    init {
        KCUtil.setScanResult(this)
    }
}