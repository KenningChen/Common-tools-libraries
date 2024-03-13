package com.kenning.base

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * Created by 陈少华 on 2018/6/13.
 * 用于一级嵌套
 */
open class BaseMainFragment : SupportFragment() {
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
        if (childFragmentManager.backStackEntryCount > 1) {
            popChild()
        } else {
//            if (this instanceof ZhihuFirstFragment) {   // 如果是 第一个Fragment 则退出app
//                _mActivity.finish();
//            } else {                                    // 如果不是,则回到第一个Fragment
//                _mBackToFirstListener.onBackToFirstFragment();
//            }
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                _mActivity.finish()
            } else {
                TOUCH_TIME = System.currentTimeMillis()
                Toast.makeText(
                    activity,
                    "再按一次退出",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return true
    }

    private var TOUCH_TIME: Long = 0

    companion object {
        // 再点一次退出程序时间设置
        private const val WAIT_TIME = 2000L
    }
}