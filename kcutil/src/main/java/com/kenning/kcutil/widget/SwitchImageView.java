package com.kenning.kcutil.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.kenning.kcutil.R;

/**
 * Description :
 * author : KenningChen
 * Date : 2021/4/22 9:21 AM
 */
public class SwitchImageView extends AppCompatImageView {

    /**
     * 记录点击状态 默认未点击
     */
    private boolean checked = false;

    /**
     * 是否被点击后，改变资源文件，如果为fale，则仅执行系统点击事件，不改变src的资源图片
     * @默认为true
     */
    private boolean autoSrcChange = true;

    private Drawable mNormalDrawable;

    private Drawable mPressedDrawable;

    public SwitchImageView(Context context) {
        this(context, null);
    }

    public SwitchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SwitchImageView);
        mNormalDrawable = a.getDrawable(R.styleable.SwitchImageView_checkOffBackground);
        mPressedDrawable = a.getDrawable(R.styleable.SwitchImageView_checkOnBackground);
        checked = a.getBoolean(R.styleable.SwitchImageView_checkstate, false);
        autoSrcChange = a.getBoolean(R.styleable.SwitchImageView_autoSrcChange, true);
        a.recycle();

        setChecked(checked);

        setOnClickListener(v -> setChecked(!checked,autoSrcChange));

    }

    public void setOnSwitchListener(OnSwitchListener listener) {
        this.listener = listener;
    }

    private OnSwitchListener listener;

    public interface OnSwitchListener {
        void checkedChangeListener(boolean isChecked);
    }

    public void setmNormalDrawable(Drawable mNormalDrawable){
        this.mNormalDrawable = mNormalDrawable;
        setImageDrawable(mNormalDrawable);
    }

    public void setOffDrawable(Drawable mNormalDrawable){
        this.mNormalDrawable = mNormalDrawable;
    }

    public void setOnDrawable(Drawable mNormalDrawable){
        this.mPressedDrawable = mNormalDrawable;
    }

    /**
     *
     * @param checked
     * @param isFinger 是否是手指触发的该方法（true 不改变资源文件）
     */
    public void setChecked(boolean checked,boolean isFinger) {
        if (isFinger) {
            this.checked = checked;
            if (checked) {
                setImageDrawable(mPressedDrawable);
            } else {
                setImageDrawable(mNormalDrawable);
            }
        }
        if (listener!=null)
            listener.checkedChangeListener(checked);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            setImageDrawable(mPressedDrawable);
        } else {
            setImageDrawable(mNormalDrawable);
        }
        if (listener!=null)
            listener.checkedChangeListener(checked);
    }

    /**
     * 禁止点击事件改变图标的时候，此方法生效 且 不建议使用setChecked 因为会同时触发点击事件
     * @param checked
     */
    public void switchImageState(boolean checked){
        if (autoSrcChange){
            if (checked) {
                setImageDrawable(mPressedDrawable);
            } else {
                setImageDrawable(mNormalDrawable);
            }
        }
    }

    public boolean getChecked(){
        return checked;
    }
}
