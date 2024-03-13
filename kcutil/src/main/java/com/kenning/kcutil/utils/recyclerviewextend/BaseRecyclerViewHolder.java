package com.kenning.kcutil.utils.recyclerviewextend;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kenning.kcutil.utils.other.IEditListenerConfiger;
import com.kenning.kcutil.utils.other.ILoadOverConfiger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description :
 * <p>
 * author : created by shaohua.chen on 2021/2/23 11:37 AM
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder implements IEditListenerConfiger,
        ILoadOverConfiger {
    View itemView;
    SparseArray<View> views;//存放itemview中的子view
    public BaseRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        views = new SparseArray<>();
    }

    //供adapter使用,返回holder
    public static <T extends BaseRecyclerViewHolder> T getHolder(Context context, ViewGroup parent, int layoutId) {
        return (T) new BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    //获取view
    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public View getItemView() {
        return itemView;
    }

    //设置点击事件监听
    public BaseRecyclerViewHolder setOnclickListioner(int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    //设置点击事件监听
    public BaseRecyclerViewHolder setOnclickListioner(int[] viewIds, View.OnClickListener onClickListener) {
        for (int index=0;index<viewIds.length;index++) {
            getView(viewIds[index]).setOnClickListener(onClickListener);
        }
        return this;
    }

    public BaseRecyclerViewHolder setText(int viewId, String descrp) {
        ((TextView) getView(viewId)).setText(descrp);
        return this;
    }

    public BaseRecyclerViewHolder setText(int viewId, Spanned descrp) {
        ((TextView) getView(viewId)).setText(descrp);
        return this;
    }

    public BaseRecyclerViewHolder setText(int viewId,int resId){
        ((TextView) getView(viewId)).setText(resId);
        return this;
    }

    public BaseRecyclerViewHolder setVisiable(int viewId,@Visibility int visibility){
        getView(viewId).setVisibility(visibility);
        return this;
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

//    public BaseRecyclerViewHolder setImageView(int imagViewId, String url) {
//        GlideApp.with(AIApplication.mContext).load(url).error(R.drawable.ic_default).placeholder(R.drawable.ic_default)
//                .transform(new GlideRoundTransform()).into((ImageView) getView(imagViewId));
//        return this;
//    }

    public BaseRecyclerViewHolder setImageView(int imagViewId, int resId){
        ((ImageView)getView(imagViewId)).setImageResource(resId);
        return this;
    }

    public BaseRecyclerViewHolder setImageView(int imagViewId, Bitmap bitmap){
        ((ImageView)getView(imagViewId)).setImageBitmap(bitmap);
        return this;
    }

    @Override
    public boolean isEditWork() {
        return isEditWork;
    }

    @Override
    public void setEditWork(boolean isEditWork) {
        this.isEditWork = isEditWork;
    }

    /**
     * 默认可编辑
     */
    boolean isEditWork = true;

    @Override
    public boolean isLoadOver() {
        return isLoadOver;
    }

    @Override
    public void setLoadOver(boolean isLoadOver) {
        this.isLoadOver = isLoadOver;
    }

    /**
     * 默认未绘制完成
     */
    boolean isLoadOver = false;
}
