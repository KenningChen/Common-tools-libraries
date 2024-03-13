package com.kenning.kcutil.widget.basicview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.FloatRange;
import androidx.appcompat.widget.AppCompatTextView;

import com.kenning.kcutil.R;

/**
 * Description :
 * author : KenningChen
 * Date : 4/1/21 10:34 AM
 */
public class BackGroundTextView extends AppCompatTextView {
    private int mNormalBackgroundColor = 0;
    private int mNormalBackgroundColor_end = 0;
    private float mRadius = 0;
    private float corner_topright = 0;
    private float corner_bottomright = 0;
    private float corner_topleft = 0;
    private float corner_bottomleft = 0;

    private boolean mRound;
    private GradientDrawable mNormalBackground;

    private boolean mRound_left;
    private boolean mRound_right;


    //stroke
    private float mStrokeDashWidth = 0;
    private float mStrokeDashGap = 0;
    private int mNormalStrokeWidth = 0;
    private int mPressedStrokeWidth = 0;
    private int mUnableStrokeWidth = 0;
    private int mNormalStrokeColor = 0;
    private int mPressedStrokeColor = 0;
    private int mUnableStrokeColor = 0;

    //渐变色走向
    private int gradientstate = 6;
    
    public BackGroundTextView(Context context) {
        this(context,null);
    }

    public BackGroundTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackGroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    private void setup(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StateCustomView);
        mNormalBackground = new GradientDrawable();
        mNormalBackgroundColor = a.getColor(R.styleable.StateCustomView_normalBackgroundColor, 0);
        mNormalBackgroundColor_end = a.getColor(R.styleable.StateCustomView_normalBackgroundColor_end, 0);
        gradientstate = a.getInt(R.styleable.StateCustomView_gradientstate, 6);
        if (mNormalBackgroundColor_end!=0) {
            //设置渐变色
            int[] colors = {mNormalBackgroundColor, mNormalBackgroundColor_end};
            mNormalBackground.setColors(colors);
            mNormalBackground.setOrientation(GradientDrawable.Orientation.values()[gradientstate]);
//            mNormalBackground = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);

            mNormalBackground.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        }else {
            mNormalBackground.setColor(mNormalBackgroundColor);
        }

        //set stroke
        mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.StateCustomView_strokeDashWidth, 0);
        mStrokeDashGap = a.getDimensionPixelSize(R.styleable.StateCustomView_strokeDashWidth, 0);
        mNormalStrokeWidth = a.getDimensionPixelSize(R.styleable.StateCustomView_normalStrokeWidth, 0);
        mPressedStrokeWidth = a.getDimensionPixelSize(R.styleable.StateCustomView_pressedStrokeWidth, 0);
        mUnableStrokeWidth = a.getDimensionPixelSize(R.styleable.StateCustomView_unableStrokeWidth, 0);
        mNormalStrokeColor = a.getColor(R.styleable.StateCustomView_normalStrokeColor, 0);
        mPressedStrokeColor = a.getColor(R.styleable.StateCustomView_pressedStrokeColor, 0);
        mUnableStrokeColor = a.getColor(R.styleable.StateCustomView_unableStrokeColor, 0);
        setStroke();


        mRadius = a.getDimensionPixelSize(R.styleable.StateCustomView_bgradius, 0);
        corner_topright = a.getDimensionPixelSize(R.styleable.StateCustomView_top_right_radius, 0);
        corner_bottomright = a.getDimensionPixelSize(R.styleable.StateCustomView_bottom_right_radius, 0);
        corner_bottomleft = a.getDimensionPixelSize(R.styleable.StateCustomView_bottom_left_radius, 0);
        corner_topleft = a.getDimensionPixelSize(R.styleable.StateCustomView_top_left_radius, 0);
        mRound = a.getBoolean(R.styleable.StateCustomView_rounds, false);
        mRound_left = a.getBoolean(R.styleable.StateCustomView_leftrounds, false);
        mRound_right = a.getBoolean(R.styleable.StateCustomView_rightrounds, false);
        if (corner_topright>0||corner_bottomright>0 || corner_topleft>0 || corner_bottomleft>0){
            float[] radii = {corner_topleft,corner_topleft,corner_topright,corner_topright,corner_bottomright,corner_bottomright,corner_bottomleft,corner_bottomleft};
            setRadius(radii);
        }else
        mNormalBackground.setCornerRadius(mRadius);


        setBackground(mNormalBackground);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRound_left && mRound_right) {
            int height = getMeasuredHeight();
            setEachCornerRadius(height / 2,height / 2,height / 2,height / 2);
        }else if (mRound_left){
            int height = getMeasuredHeight();
            setEachCornerRadius(height / 2,0,height / 2,0);
        }else if (mRound_right){
            int height = getMeasuredHeight();
            setEachCornerRadius(0,height / 2,0,height / 2);
        }else
        setRound(mRound);
    }

    public void setRadius(@FloatRange(from = 0) float radius) {
        this.mRadius = radius;
        mNormalBackground.setCornerRadius(mRadius);
    }

    public void setRound(boolean round){
        this.mRound = round;
        int height = getMeasuredHeight();
        if(mRound){
            setRadius(height / 2f);
        }
    }

    /**
     *   左上角半径
     *  右上角半径
     *  左下角半径
     *  右下角半径
     * */
    public void setRadius(float[] radii){
        mNormalBackground.setCornerRadii(radii);
    }

    public void setEachCornerRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        float[] radius = new float[]{
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius
        };
        mNormalBackground.setCornerRadii(radius);
    }

    private void setStroke(){
        setStroke(mNormalBackground, mNormalStrokeColor, mNormalStrokeWidth);
    }

    private void setStroke(GradientDrawable mBackground, int mStrokeColor, int mStrokeWidth) {
        mBackground.setStroke(mStrokeWidth, mStrokeColor, mStrokeDashWidth, mStrokeDashGap);
    }

    //-----------------------------------------

    public void setStrokeColor(int mStrokeColor) {
        mNormalBackground.setStroke(mNormalStrokeColor, mStrokeColor, mStrokeDashWidth, mStrokeDashGap);
        setBackground(mNormalBackground);
    }

    public void setNormalBackgroundColor(int color){
        mNormalBackground.setColor(color);
        setBackground(mNormalBackground);
    }

    public void setmNormalBackgroundColor(
            int gradientstate,
            int colorstart,
            int colorend
    ) {
        if (colorend != 0) {
            //设置渐变色
            int[] colors = {colorstart, colorend};
            mNormalBackground.setColors(colors);
            mNormalBackground.setOrientation(GradientDrawable.Orientation.values()[gradientstate]);
//            mNormalBackground = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            mNormalBackground.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        } else {
            mNormalBackground.setColor(colorstart);
        }
        setBackground(mNormalBackground);
    }
}
