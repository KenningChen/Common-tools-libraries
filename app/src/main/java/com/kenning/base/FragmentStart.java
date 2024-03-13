package com.kenning.base;

import android.os.Bundle;


/**
 * creater by chenshaohua on 7/6/18 18:38
 **/
public interface FragmentStart {
    default void FragmentStart(SupportFragment fragment){}

    /**
     * 带返回的跳转
     * @param fragment
     * @param requestCode
     */
    default void FragmentStartForResult(SupportFragment sourceFragment, SupportFragment fragment, int requestCode){

    }

    default void CustomeFragmentResult(int requestCode, int resultCode, Bundle data){}
}
