package com.kenning.kcutil.utils.recyclerviewextend

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.kenning.kcutil.utils.other.getColorResource

/**
 * RecycleView分隔线
 */
class RecycleViewDivider(context: Context, orientation: Int) : ItemDecoration() {
    private var mPaint: Paint? = null
    private var mDivider: Drawable?
    private var mDividerHeight = 2 //分割线高度，默认为1px
    private val mOrientation //列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
            : Int
    private var inset = 0
    private var insetright = 0

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation 列表方向
     * @param drawableId  分割线图片
     */
    constructor(context: Context, orientation: Int, drawableId: Int) : this(context, orientation) {
        mDivider = ContextCompat.getDrawable(context, drawableId)
        mDividerHeight = mDivider!!.intrinsicHeight
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    constructor(context: Context, orientation: Int, dividerHeight: Int, dividerColor: Int) : this(
        context,
        orientation
    ) {
        mDividerHeight = dividerHeight
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = dividerColor
        mPaint!!.style = Paint.Style.STROKE
        //设置虚线效果
        mPaint!!.pathEffect = DashPathEffect(floatArrayOf(3f, 2f), 0f)
    }

    constructor(
        context: Context,
        dividerHeight: Int,
        @ColorRes dividerColor: Int,
        leftinset: Int,
        rightinset: Int
    ) : this(context, LinearLayoutManager.VERTICAL) {
        inset = leftinset
        insetright = rightinset
        mDividerHeight = dividerHeight
        mPaint = Paint()
        mPaint!!.color = getColorResource(dividerColor)
    }

    //获取分割线尺寸
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect[0, 0, 0] = mDividerHeight
    }

    //绘制分割线
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontal(c, parent)
        } else {
            drawVertical(c, parent)
        }
    }

    //绘制横向 item 分割线
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.measuredWidth - parent.paddingRight
        val childSize = parent.childCount
        for (i in 0 until childSize - 1) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + layoutParams.bottomMargin
            val bottom = top + mDividerHeight

            var newleft = left
            var newright = right
            if (mDivider != null) {
                newleft = if (inset > 0) {
                    left + inset
                } else {
                    left
                }
                newright = if (insetright > 0) {
                    right - insetright
                } else {
                    right
                }
                mDivider!!.setBounds(newleft, top, newright, bottom)
                mDivider!!.draw(canvas)
            }
            if (mPaint != null) {
                canvas.drawRect(
                    newleft.toFloat(),
                    top.toFloat(),
                    newright.toFloat(),
                    bottom.toFloat(),
                    mPaint!!
                )
            }
        }
    }

    //绘制纵向 item 分割线
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.measuredHeight - parent.paddingBottom
        val childSize = parent.childCount
        for (i in 0 until childSize) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + layoutParams.rightMargin
            val right = left + mDividerHeight
            if (mDivider != null) {
                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(canvas)
            }
            if (mPaint != null) {
                canvas.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    mPaint!!
                )
            }
        }
    }

    companion object {
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    init {
        require(!(orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL)) { "请输入正确的参数！" }
        mOrientation = orientation
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }
}