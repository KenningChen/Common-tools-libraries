package com.kenning.kcutil.utils.encrypt

import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec

/**
 *Description : 文件（数据）加密
 *
 *author : created by shaohua.chen on 2021/1/22 10:12 AM
 */
object DESEncrypt {
//    /**
//     * 对字符串进行MD5加密
//     *
//     * @param string
//     * @return
//     */
//    fun md5(string: String): String {
//        val hash: ByteArray
//        hash = try {
//            MessageDigest.getInstance("MD5")
//                .digest(string.toByteArray(charset("UTF-8")))
//        } catch (e: NoSuchAlgorithmException) {
//            throw RuntimeException("Huh, MD5 should be supported?", e)
//        } catch (e: UnsupportedEncodingException) {
//            throw RuntimeException("Huh, UTF-8 should be supported?", e)
//        }
//        val hex = StringBuilder(hash.size * 2)
//        for (b in hash) {
//            if ((b and 0xFF.toByte()) < 0x10) hex.append("0")
//            hex.append(Integer.toHexString((b and 0xFF.toByte()).toInt()))
//        }
//        return hex.toString()
//    }


    /**
     * 进行MD5加密
     * @param strSrc 原始的SPKEY
     * @return byte[] 指定加密方式为md5后的byte[]
     */
    private fun md5(strSrc: String): ByteArray? {
        var returnByte: ByteArray? = null
        try {
            val md5 = MessageDigest.getInstance("MD5")
            returnByte = md5.digest(strSrc.toByteArray(charset("GBK")))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return returnByte
    }


    /**
     * 3-DES加密
     * @param  src 要进行3-DES加密的String
     * @param  spkey 分配的SPKEY
     * @return String 3-DES加密后的String
     */
    fun encrypt(src: String, spkey: String): String? {
        var requestValue: String? = ""
        try {
            //得到3-DES的密钥匙
            val enKey = getEnKey(spkey)
            //要进行3-DES加密的内容在进行/"UTF-16LE/"取字节
            val src2 = src.toByteArray(charset("UTF-16LE"))
            //进行3-DES加密后的内容的字节
            val encryptedData = Encrypt(src2, enKey)
            //进行3-DES加密后的内容进行BASE64编码
            val base64String = getBase64Encode(encryptedData)
            //BASE64编码去除换行符后
            val base64Encrypt = filter(base64String)
            //对BASE64编码中的HTML控制码进行转义的过程
            requestValue = getURLEncode(base64Encrypt)
            //System.out.println(requestValue);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestValue
    }


    /**
     * 3-DES解密
     * @param  src 要进行3-DES解密的String
     * @param  spkey 分配的SPKEY
     * @return String 3-DES加密后的String
     */
    fun decrypt(src: String, spkey: String): String? {
        var requestValue: String? = ""
        try {
            //得到3-DES的密钥匙
            //URLDecoder.decodeTML控制码进行转义的过程
            val URLValue = getURLDecoderdecode(src)
            //进行3-DES加密后的内容进行BASE64编码
            val base64DValue: ByteArray = Base64.decode(URLValue, Base64.DEFAULT)
            //要进行3-DES加密的内容在进行/"UTF-16LE/"取字节
            requestValue = deCrypt(base64DValue, spkey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestValue
    }


    /**
     * 3-DES加密
     * @param   src 要进行3-DES加密的byte[]
     * @param   enKey 3-DES加密密钥
     * @return byte[] 3-DES加密后的byte[]
     */
    private fun Encrypt(src: ByteArray, enKey: ByteArray?): ByteArray? {
        var encryptedData: ByteArray? = null
        try {
            val dks = DESedeKeySpec(enKey)
            val keyFactory = SecretKeyFactory.getInstance("DESede")
            val key = keyFactory.generateSecret(dks)
            val cipher = Cipher.getInstance("DESede")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            encryptedData = cipher.doFinal(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encryptedData
    }

    /**
     *
     * 进行3-DES解密（密钥匙等同于加密的密钥匙）。
     * @param   debase64 要进行3-DES解密byte[]
     * @param   spKey 分配的SPKEY
     * @return String 3-DES解密后的String
     */
    private fun deCrypt(debase64: ByteArray, spKey: String): String? {
        var strDe: String? = null
        var cipher: Cipher? = null
        try {
            cipher = Cipher.getInstance("DESede")
            val key = getEnKey(spKey)
            val dks = DESedeKeySpec(key)
            val keyFactory = SecretKeyFactory.getInstance("DESede")
            val sKey = keyFactory.generateSecret(dks)
            cipher.init(Cipher.DECRYPT_MODE, sKey)
            val ciphertext = cipher.doFinal(debase64)

            strDe = String(ciphertext, Charset.forName("UTF-16LE"))
        } catch (ex: Exception) {
            strDe = ""
            ex.printStackTrace()
        }
        return strDe
    }


    /**
     * 得到3-DES的密钥匙
     * 根据根据需要，如密钥匙为24个字节，md5加密出来的是16个字节，因此后面补8个字节的0
     * @param spKey 原始的SPKEY
     * @return byte[] 指定加密方式为md5后的byte[]
     */
    private fun getEnKey(spKey: String): ByteArray? {
        var desKey: ByteArray? = null
        try {
            val desKey1: ByteArray = md5(spKey)!!
            desKey = ByteArray(24)
            var i = 0
            while (i < desKey1.size && i < 24) {
                desKey[i] = desKey1[i]
                i++
            }
            if (i < 24) {
                desKey[i] = 0
                i++
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return desKey
    }


    /**
     * 对字符串进行URLDecoder.decode(strEncoding)解码
     * @param  src 要进行解码的字符串
     *
     * @return String 进行解码后的字符串
     */
    private fun getURLDecoderdecode(src: String): String {
        var requestValue = ""
        try {
            requestValue = URLDecoder.decode(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestValue
    }

    /**
     * 对字符串进行Base64编码
     * @param  src 要进行编码的字符
     *
     * @return String 进行编码后的字符串
     */
    private fun getBase64Encode(src: ByteArray?): String {
        var requestValue = ""
        try {
            requestValue = Base64.encodeToString(src, Base64.DEFAULT) // 此处使用BASE64做转码。
            //System.out.println(requestValue);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestValue
    }

    /**
     * 去掉字符串的换行符号
     * base64编码3-DES的数据时，得到的字符串有换行符号
     * ，一定要去掉，否则uni-wise平台解析票根不会成功，
     * 提示“sp验证失败”。在开发的过程中，因为这个问题让我束手无策，
     * 一个朋友告诉我可以问联通要一段加密后 的文字，然后去和自己生成的字符串比较，
     * 这是个不错的调试方法。我最后比较发现我生成的字符串唯一不同的 是多了换行。
     * 我用c#语言也写了票根请求程序，没有发现这个问题。
     *
     */
    private fun filter(str: String): String {
        var output: String? = null
        val sb = StringBuffer()
        for (i in 0 until str.length) {
            val asc = str[i].code
            if (asc != 10 && asc != 13) sb.append(str.subSequence(i, i + 1))
        }
        output = String(sb)
        return output
    }

    /**
     * 对字符串进行URLDecoder.encode(strEncoding)编码
     * @param  src 要进行编码的字符串
     *
     * @return String 进行编码后的字符串
     */
    private fun getURLEncode(src: String): String? {
        var requestValue: String? = ""
        try {
            requestValue = URLEncoder.encode(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestValue
    }


}