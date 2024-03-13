package com.kenning.kcutil.utils.tts

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.kenning.kcutil.R
import com.kenning.kcutil.utils.dialog.easydialog.EasyDialog
import com.kenning.kcutil.utils.other.isTargetAPKInstalled
import com.kenning.kcutil.utils.recyclerviewextend.BaseRecyclerViewHolder
import com.kenning.kcutil.utils.recyclerviewextend.BaseRecyclerWithLayoutAdapter
import java.util.ArrayList
import kotlin.math.log

/**
 *Description :
 *@author : KenningChen
 *Date : 2023-11-13
 */
class TTSAdapter(
    var context: Context,
    var dialog: EasyDialog,
    private val onItemClick: (String) -> Unit
) : BaseRecyclerWithLayoutAdapter(R.layout.item_tts_downconfig){
    var isclick = false
    override fun _BindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        holder.apply {
//            if(isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName())){
//                setVisiable(R.id.tvClose, View.VISIBLE)
//                setVisiable(R.id.tvDownload, View.GONE)
//            }else{
//                setVisiable(R.id.tvClose, View.GONE)
//                setVisiable(R.id.tvDownload, View.VISIBLE)
//            }

            getView<View>(R.id.tvClose).setOnClickListener {
                if (!isclick) {
                    TTSUtil.reInit()
                }
                TTSUtil.getInstance()?.stopSpeak()
               dialog.getBaseDialog()?.dismiss()
            }

            getView<View>(R.id.tvSpeak).setOnClickListener {
                isclick = true
                onItemClick.invoke("播报测试")
            }
            getView<View>(R.id.tvDownload).setOnClickListener {
                onItemClick.invoke("立即下载")
                Thread{
                    kotlin.run {
                        while (true){
                            if (isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName())) {
                                (context as Activity).runOnUiThread {
                                    setVisiable(R.id.tvClose, View.VISIBLE)
                                    setVisiable(R.id.tvDownload, View.GONE)
                                    setVisiable(R.id.tvSpeak, View.VISIBLE)
                                    setVisiable(R.id.textView3, View.VISIBLE)
                                    setText(R.id.textView2, "语音引擎安装成功！\n请点击【播报测试】检测语音播报是否正常")
                                }
                                Thread.interrupted()
                            }
                        }
                    }
                }.start()
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}