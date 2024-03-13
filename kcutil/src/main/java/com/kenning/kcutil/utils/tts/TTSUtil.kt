package com.kenning.kcutil.utils.tts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentTransaction
import com.kenning.kcutil.KCUtil
import com.kenning.kcutil.R
import com.kenning.kcutil.utils.dialog.easydialog.EasyDialog
import com.kenning.kcutil.utils.math.toFloat_
import com.kenning.kcutil.utils.other.ToastUtil
import com.kenning.kcutil.utils.other.isTargetAPKInstalled
import com.kenning.kcutil.widget.SwitchImageView
import java.io.File
import java.io.FileOutputStream
import java.util.Locale


/**
 *Description :
 *@author : KenningChen
 *Date : 2023/11/11
 */
class TTSUtil() : UtteranceProgressListener() {
    private var mContext: Context? = null
    private var textToSpeech: TextToSpeech? = null // 系统语音播报类
    private var isSuccess = true

    private var speakSuccess: Boolean? = null

    constructor(context: Context) : this() {
        mContext = KCUtil.application
        var isInStallBaiduAPk = isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName())
        isSuccess = true
        textToSpeech = TextToSpeech(mContext, { i: Int ->
            //系统语音初始化成功
            if (i == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.CHINA)
                textToSpeech!!.setPitch(1.0f) // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                textToSpeech!!.setSpeechRate(if (isInStallBaiduAPk) 4.0f else 1.8f) // 播报速度
                textToSpeech!!.setOnUtteranceProgressListener(this@TTSUtil)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    //系统不支持中文播报
                    isSuccess = false
                }
            } else {
                isSuccess = false
            }
        }, TTSEngineEnum.BDTTS.packageName())

    }

    /**
     * @param playText 播报文本内容
     * @param speakEachNumber 文本中的数字内容是否依次读取
     */
    fun playText(playText: String?, utteranceid: String? = null): Int {
        if (!isSuccess) {
            ToastUtil.show("系统不支持中文播报，请到用户配置安装三方语音播报")
            return -1
        }
        if (textToSpeech != null) {
            val newtext =
                playText?.replace("-", "杠")
                    ?: kotlin.run { "" }
            return textToSpeech!!.speak(newtext, TextToSpeech.QUEUE_ADD, null, utteranceid)
        }
        return -1
    }

    fun stopSpeak() {
        textToSpeech?.stop()
//        textToSpeech?.shutdown()
    }

    /**
     * 是否支持中文播报
     */
    fun supportChinese() = isSuccess

    fun speakSUC() = speakSuccess

    override fun onStart(utteranceId: String?) {}
    override fun onDone(utteranceId: String?) {}
    override fun onError(utteranceId: String?) {}

    companion object {
        private var singleton: TTSUtil? = null
        fun getInstance(): TTSUtil? {
            if (singleton == null) {
                synchronized(TTSUtil::class.java) {
                    if (singleton == null) {
                        singleton = TTSUtil(KCUtil.application!!)
                    }
                }
            }
            return singleton
        }

        fun reInit() {
            singleton = null
        }

        fun reSetSpeech(speechRate: Float) {
            singleton!!.textToSpeech?.setSpeechRate(speechRate)
        }

        fun showDownLoadWindow(activity: Activity): EasyDialog {
            val dialog = EasyDialog(activity)
            var isInit = false
            var thread: Thread? = null
            dialog.setAdapter(TTSAdapter(activity, dialog) {

                if (it == "播报测试") {
                    if (!isInit) {
                        reInit()
                        isInit = true
                    }
                    Thread.sleep(200)
//                    getInstance()?.stopSpeak()
                    getInstance()?.playText("语音测试成功")
                }
                if (it == "立即下载") {
                    installed(activity, getTTSFilePath(activity, R.raw.baidutts))
                }
            }).needNoNButtons(true).cancelAble(false).keyCancelAble(false).build()

            return dialog
        }

        fun checkTTS(activity: Activity){
            if (!isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName()) && getInstance()?.isSuccess == false) {
                showDownLoadWindow(activity)
            }
        }

        /**
         * 跳转TTS配置页面
         */
        private fun startConfig(fm: FragmentTransaction, rootViewID: Int) {
            fm.add(
                rootViewID,
                TTSConfigFragment(),
                null
            ).addToBackStack(null).commit()
        }

        fun ttsInitSuccess() = getInstance()?.supportChinese() ?: false

        fun saveSpeed(speed: String) {
            setPreferences(
                setType = "TTS", setKey = "Speed", setValue = speed

            )
        }

        fun saveUsed(use: Boolean) {
            setPreferences(
                setType = "TTS", setKey = "TTSUse", setValue = "$use"
            )
        }

        fun getSpeed(): Float {
            kotlin.runCatching {
                getPreferences(getType = "TTS", getKey = "Speed", defValue = "1")
            }.onSuccess {
                return it.toFloat_()
            }.onFailure {
                return 1.0f
            }
            return 1.0f
        }

        fun getUse(): Boolean {
            kotlin.runCatching {
                getPreferences(getType = "TTS", getKey = "TTSUse", defValue = "true")
                    .equals("true", true)
            }.onSuccess {
                return it
            }.onFailure {
                return true
            }
            return true
        }

        /**获取引擎文件路径，存在去安装，没有跳设置界面 */
        fun getTTSFilePath(mContext: Activity, rawID: Int): String {
            try {
                val inputStream = mContext.resources.openRawResource(rawID)
                val apkFilePath: String = KCUtil.application!!.getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS
                )!!.path + "/baidutts.apk"
                val apkFile1 = File(apkFilePath)
                //文件已经存在
                if (apkFile1.exists()) {
                    return if (isTargetAPKInstalled(TTSEngineEnum.BDTTS.packageName())) {
                        //已经安装去设置（理论上不会进，因为上面就默认的中文百度引擎）
                        ""
                    } else {
                        //没有安装返回路径
                        apkFilePath
                    }
                }
                //从raw文件夹赋值一个到手机上
                val outputStream = FileOutputStream(apkFilePath)
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }
                outputStream.flush()
                outputStream.close()
                inputStream.close()
                return apkFilePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        /**安装TTS引擎 */
        fun installed(mContext: Activity, apkFilePath: String): Boolean {
            val apkFile = File(apkFilePath)
            if (!apkFile.exists()) {
                return true
            }
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val uriForFile = FileProvider.getUriForFile(
                    KCUtil.application!!,
                    KCUtil.application!!.packageName +".provider",
                    apkFile
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.setDataAndType(
                    uriForFile,
                    KCUtil.application!!.contentResolver.getType(uriForFile)
                )
            } else {
                val var2 = apkFile.name
                val var3 = var2.substring(var2.lastIndexOf(".") + 1, var2.length)
                    .lowercase(Locale.getDefault())
                val var1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(var3)
                intent.setDataAndType(Uri.fromFile(apkFile), var1)
            }
            try {
                mContext.startActivity(intent)
            } catch (var5: Exception) {
                var5.printStackTrace()
            }
            return false
        }
        // </editor-fold>
    }
}

private fun setPreferences(
    setType: String, setKey: String,
    setValue: String
) {

    val editor = KCUtil.application!!.getSharedPreferences(
        setType,
        Activity.MODE_PRIVATE
    ).edit()
    editor.putString(setKey, setValue)
    editor.apply()
}

private fun getPreferences(
    getType: String,
    getKey: String, defValue: String
): String {
    var getValue = ""

    val preferences = KCUtil.application!!.getSharedPreferences(
        getType,
        Activity.MODE_PRIVATE
    )
    getValue = preferences.getString(getKey, defValue) ?: defValue

    return getValue
}

enum class TTSEngineEnum {
    BDTTS {
        override fun packageName() = "com.baidu.duersdk.opensdk"
    }
    ;

    abstract fun packageName(): String
}