package com.kenning.kcutil.utils.encrypt

import android.util.Base64
import java.io.ByteArrayOutputStream
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


/**
 *Description :
 *@author : KenningChen
 *Date : 2022/5/5
 */
object RSAEncrypt {

    private const val SIGN_ALGORITHMS = "SHA256withRSA"

    /** 使用公钥加密  */
    @Throws(Exception::class)
   private fun encryptByPublicKey(data: ByteArray?, publicKey: String): ByteArray? {

        // base64编码的公钥
        val decoded: ByteArray = Base64.decode(publicKey, Base64.DEFAULT)
        val pubKey = KeyFactory.getInstance("RSA")
            .generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey
        //加密
        val cipher: Cipher = Cipher.getInstance("RSA/None/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)

        return cipher.doFinal(data)
    }


    /**秘钥默认长度 */
    private val DEFAULT_KEY_SIZE = 1024

    /**加密的数据最大的字节数，即117个字节 */
    private val DEFAULT_BUFFERSIZE = DEFAULT_KEY_SIZE / 8 - 11

    /** 使用公钥分段加密  */
    @Throws(java.lang.Exception::class)
    fun encryptByPublicKeyForSpilt(string: String, publicKey: String): ByteArray? {
        val data = string.toByteArray()
        // base64编码的公钥
        val decoded: ByteArray = Base64.decode(publicKey, Base64.DEFAULT)
        val pubKey = KeyFactory.getInstance("RSA")
            .generatePublic(X509EncodedKeySpec(decoded)) as RSAPublicKey
        //加密
        val cipher: Cipher = Cipher.getInstance("RSA/None/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)

        val inputLen: Int = data.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            cache = if (inputLen - offSet > DEFAULT_BUFFERSIZE) {
                cipher.doFinal(data, offSet, DEFAULT_BUFFERSIZE)
            } else {
                cipher.doFinal(data, offSet, inputLen - offSet)
            }
            out.write(cache, 0, cache.size)
            i++
            offSet = i * DEFAULT_BUFFERSIZE
        }
        val encryptedData: ByteArray = out.toByteArray()
        out.close()
        return encryptedData
    }

    fun doCheck(content: String, sign: String?, publicKey: String?): Boolean {
        return try {
            val keyFactory = KeyFactory.getInstance("RSA")
            val encodedKey =
                Base64.decode(publicKey, Base64.DEFAULT)
            val pubKey: PublicKey =
                keyFactory.generatePublic(X509EncodedKeySpec(encodedKey))
            val signature =
                Signature.getInstance(SIGN_ALGORITHMS)
            signature.initVerify(pubKey)
            signature.update(content.toByteArray(charset("UTF-8")))
            signature.verify(Base64.decode(sign, Base64.DEFAULT))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    fun sign(content: String, privateKey: String?, encode: String?): String? {
        try {
            val priPKCS8 = PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT))
            val keyf = KeyFactory.getInstance("RSA")
            val priKey: PrivateKey = keyf.generatePrivate(priPKCS8)
            val signature = Signature.getInstance(SIGN_ALGORITHMS)
            signature.initSign(priKey)
            signature.update(content.toByteArray(charset(encode!!)))
            val signed = signature.sign()
            return Base64.encodeToString(signed, Base64.DEFAULT)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }
}