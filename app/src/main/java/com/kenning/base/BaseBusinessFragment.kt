package com.kenning.base

import android.app.Activity
import android.content.Context
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.ISupportFragment

/**
 * Created by 陈少华 on 2018/6/13.
 * 用于具体业务中（非主页）
 * modify by KenningChen on 2021/3/28
 */
open class BaseBusinessFragment : SupportFragment() {
    //    protected OnBackToFirstListener _mBackToFirstListener;
    //
    //    @Override
    //    public void onAttach(Context context) {
    //        super.onAttach(context);
    //        if (context instanceof OnBackToFirstListener) {
    //            _mBackToFirstListener = (OnBackToFirstListener) context;
    //        } else {
    //            throw new RuntimeException(context.toString()
    //                    + " must implement OnBackToFirstListener");
    //        }
    //    }
    override fun onDetach() {
        super.onDetach()
        //        _mBackToFirstListener = null;
    }

    lateinit var mContext: Context

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mContext = activity
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    override fun onBackPressedSupport(): Boolean {
        if (_mActivity.supportFragmentManager.backStackEntryCount > 1) {
            val lastfragment = _mActivity.supportFragmentManager.fragments.last()
            if (lastfragment !is ISupportFragment){
                _mActivity.supportFragmentManager.popBackStack()
            }else {
                pop()
            }
        } else {
//            if (this instanceof ZhihuFirstFragment) {   // 如果是 第一个Fragment 则退出app
//                _mActivity.finish();
//            } else {                                    // 如果不是,则回到第一个Fragment
//                _mBackToFirstListener.onBackToFirstFragment();
//            }
//            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
            //            } else {
//                TOUCH_TIME = System.currentTimeMillis();
//                Toast.makeText(getActivity(), "再按一次退出", Toast.LENGTH_SHORT).show();
//            }
        }
        return true
    } //    // 再点一次退出程序时间设置
    //    private static final long WAIT_TIME = 2000L;
    //    private long TOUCH_TIME = 0;
}