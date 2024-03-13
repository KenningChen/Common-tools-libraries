package com.kenning.kcutil.utils.other

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.kenning.kcutil.R

/**
 *Description :
 *@author : KenningChen
 *Date : 2023-10-31
 */
class SpannableStringUtil(val context: Context, val textColor: Int = R.color.color_333333) {
    private var msg = ""

    private val style = SpannableStringBuilder()


    private val foregroundColorSpan3 =
        ForegroundColorSpan(
            ResourcesCompat.getColor(
                context.resources,
                textColor, null
            )
        )

    fun setMsg(msg: String): SpannableStringUtil {
        this.msg = msg
        style.append(msg)
        style.setSpan(
            foregroundColorSpan3,
            0,
            msg.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    /**
     *
     * @param events Array<out Pair<String, () -> Unit>> string 表示点击事件的文字内容, ()-> 点击事件
     */
    fun setClickRect(vararg events: Pair<String,(()->Unit)?>): SpannableStringUtil{
        events.forEach {action->
            val agreement = action.first
            // 设置部分文字点击事件
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    action.second?.invoke()
                }
            }
            style.setSpan(
                clickableSpan,
                msg.indexOf(agreement),
                msg.indexOf(agreement) + agreement.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return this
    }

    fun setColorRect(vararg events: Pair<String, (Int)?>): SpannableStringUtil{
        events.forEach {action->
            val agreement = action.first

            val foregroundColorSpan = ForegroundColorSpan(
                ResourcesCompat.getColor(
                    context.resources,
                    action.second?:R.color.color_00ABF3,
                    null
                )
            )
            style.setSpan(
                foregroundColorSpan,
                msg.indexOf(agreement),
                msg.indexOf(agreement) + agreement.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return this
    }

    fun toSpannableStringBuilder(): SpannableStringBuilder{
        return style
    }
}