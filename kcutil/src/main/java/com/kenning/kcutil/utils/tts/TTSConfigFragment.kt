package com.kenning.kcutil.utils.tts

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kenning.kcutil.R
import com.kenning.kcutil.databinding.FragmentTtsSettingBinding
import com.kenning.kcutil.utils.dialog.easydialog.EasyDialog
import com.kenning.kcutil.utils.other.isTargetAPKInstalled
import com.kenning.kcutil.widget.SwitchImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *Description :语音配置页面
 *@author : KenningChen
 *Date : 2023-11-13
 */
class TTSConfigFragment : Fragment() {

    private lateinit var mContext: Context

    private lateinit var mView: View
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = FragmentTtsSettingBinding.inflate(layoutInflater).root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        bindClick()

    }

    fun initView() {
        if (TTSUtil.ttsInitSuccess()) {
            mView.findViewById<View>(R.id.lline).visibility = View.GONE
            mView.findViewById<View>(R.id.llInstall).visibility = View.GONE
        }

        mView.findViewById<SwitchImageView>(R.id.switchImage).checked = TTSUtil.getUse()

        Handler(Looper.getMainLooper()).post {
            if (isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName())) {
                mView.findViewById<View>(R.id.lline).visibility = View.GONE
                mView.findViewById<View>(R.id.llInstall).visibility = View.GONE
            }
        }

        mView.findViewById<SeekBar>(R.id.seekBarVolume).progress = when (TTSUtil.getSpeed()) {
            1.2f -> 2
            1.4f -> 3
            1.6f -> 4
            1.8f -> 5
            2.0f -> 6
            else -> 1
        }
    }

    fun bindClick() {
        mView.findViewById<View>(R.id.tvBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        mView.findViewById<View>(R.id.imageBack).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        mView.findViewById<SwitchImageView>(R.id.switchImage).setOnSwitchListener {
            TTSUtil.saveUsed(it)
            if (it) {
                mView.findViewById<View>(R.id.llSpeech).visibility = View.VISIBLE
                if (!isTargetAPKInstalled( TTSEngineEnum.BDTTS.packageName())) {
                    mView.findViewById<View>(R.id.lline).visibility = View.VISIBLE
                    mView.findViewById<View>(R.id.llInstall).visibility = View.VISIBLE
                } else {
                    mView.findViewById<View>(R.id.lline).visibility = View.GONE
                    mView.findViewById<View>(R.id.llInstall).visibility = View.GONE
                }
            } else {
                mView.findViewById<View>(R.id.llSpeech).visibility = View.GONE
                mView.findViewById<View>(R.id.lline).visibility = View.GONE
                mView.findViewById<View>(R.id.llInstall).visibility = View.GONE
            }
        }

        mView.findViewById<View>(R.id.downifly).setOnClickListener {
            val dialog = EasyDialog(requireContext())
            dialog.setTitle("未检测到语音引擎").setAdapter(TTSAdapter(requireContext(),dialog) {
                dialog.getBaseDialog()?.dismiss()
                if (it == "关闭语音提示") {
                    TTSUtil.saveUsed(false)
                    mView.findViewById<SwitchImageView>(R.id.switchImage).checked = TTSUtil.getUse()
                }
                if (it == "下载") {
                    TTSUtil.installed(
                        requireActivity(), TTSUtil.getTTSFilePath(
                            requireActivity(), R.raw
                                .baidutts
                        )
                    )
                }
            }).needNoNButtons(true).build()
        }

        mView.findViewById<SeekBar>(R.id.seekBarVolume).setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // 更新文本视图
                val SpeechRate = 1 + progress

                TTSUtil.saveSpeed(
                    when (SpeechRate) {
                        2 -> 1.2f.toString()
                        3 -> 1.4f.toString()
                        4 -> 1.6f.toString()
                        5 -> 1.8f.toString()
                        6 -> 2.0f.toString()
                        else -> 1.0f.toString()
                    }
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // 用户开始拖动滑动条时调用
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

                Log.e("kenning", TTSUtil.ttsInitSuccess().toString())

//                if (TTSUtil.ttsInitSuccess()) {
//                    TTSUtil.reSetSpeech(TTSUtil.getSpeed())
//                } else {
//                    TTSUtil.reInit()
//                }
                TTSUtil.getInstance()?.playText(
                    "当前语速为" + when (TTSUtil.getSpeed()) {
                        1.2f -> "2"
                        1.4f -> "3"
                        1.6f -> "4"
                        1.8f -> "5"
                        2.0f -> "6"
                        else -> "1"
                    }
                )
            }
        })
    }

}