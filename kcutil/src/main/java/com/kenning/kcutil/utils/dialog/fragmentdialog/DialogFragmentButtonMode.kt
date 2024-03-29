package com.kenning.kcutil.utils.dialog.fragmentdialog

import androidx.annotation.ColorRes
import com.kenning.kcutil.R

/**
 *Description :
 *@author : KenningChen
 *Date : 2021/11/5
 */
class DialogFragmentButtonMode {
    var text:String

    @ColorRes
    var textcolor:Int

    @ColorRes
    var backgroundcolor:Int

    var click:((BaseFragmentDialog?) -> Unit)?

    constructor(
        text: String
    ){
        this.text = text
        textcolor = R.color.color_333333
        this.backgroundcolor = R.color.white
        click = null
    }

    constructor(
        text: String,
        click: ((BaseFragmentDialog?) -> Unit)? = null
    ){
        this.text = text
        this.textcolor = R.color.color_333333
        this.backgroundcolor = R.color.white
        this.click = click
    }



    constructor(
        text: String,
        @ColorRes  textcolor: Int = R.color.color_333333
    ){
        this.text = text
        this.textcolor = textcolor
        this.backgroundcolor = R.color.white
        click = null
    }

    constructor(
        text: String,
        @ColorRes  textcolor: Int = R.color.color_333333,
        click: ((BaseFragmentDialog?) -> Unit)? = null
    ){
        this.text = text
        this.textcolor = textcolor
        this.backgroundcolor = R.color.white
        this.click = click
    }




    constructor(
        text: String,
        @ColorRes  textcolor: Int = R.color.color_333333,
        @ColorRes  backgroundcolor: Int = R.color.white
    ){
        this.text = text
        this.textcolor = textcolor
        this.backgroundcolor = backgroundcolor
        click = null
    }

    constructor(
         text: String,
        @ColorRes  textcolor: Int = R.color.color_333333,
        @ColorRes  backgroundcolor: Int = R.color.white,
         click: ((BaseFragmentDialog?) -> Unit)? = null
    ){
        this.text = text
        this.textcolor = textcolor
        this.backgroundcolor = backgroundcolor
        this.click = click
    }

}
