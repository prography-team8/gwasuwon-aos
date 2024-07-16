package com.prography.utils.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * Created by MyeongKi.
 */
abstract class AndroidCryptoHelper : CryptoHelper {
    abstract val context: Context
    abstract val key: String
    abstract val keyEncryptionIv: String
    abstract val fileName: String
    abstract val keyAlias: String

    override fun encryptContentsAndStore(contents: String) {
        // 1. 암호화 키 생성 및 KeyStore에 저장
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, PROVIDER)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()

        // 2. 데이터 암호화
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        val keyStore = KeyStore.getInstance(PROVIDER).apply {
            load(null) // KeyStore를 초기화합니다.
        }
        val secretKey = keyStore.getKey(keyAlias, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptionIV = cipher.iv
        val encryptedToken = cipher.doFinal(contents.toByteArray(Charsets.UTF_8))

        // 3. 암호화된 데이터와 IV 저장 (여기서는 EncryptedSharedPreferences 사용)
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            fileName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        // 암호화된 데이터와 IV 저장
        with(sharedPreferences.edit()) {
            putString(key, Base64.encodeToString(encryptedToken, Base64.DEFAULT))
            putString(keyEncryptionIv, Base64.encodeToString(encryptionIV, Base64.DEFAULT))
            apply()
        }
    }

    override fun decryptContents(): String? {
        try {
            // 1. 암호화된 데이터와 IV 불러오기
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val sharedPreferences = EncryptedSharedPreferences.create(
                fileName,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val encryptedToken = sharedPreferences.getString(key, null)
            val encryptionIV = sharedPreferences.getString(keyEncryptionIv, null)
            if (encryptedToken == null || encryptionIV == null) {
                return null
            }
            val keyStore = KeyStore.getInstance(PROVIDER).apply {
                load(null) // KeyStore를 초기화합니다.
            }
            // 2. 복호화 키 가져오기
            val secretKey = keyStore.getKey(keyAlias, null)

            // 3. 데이터 복호화
            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(Base64.decode(encryptionIV, Base64.DEFAULT)))
            val decryptedBytes = cipher.doFinal(Base64.decode(encryptedToken, Base64.DEFAULT))

            return String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    companion object {
        private const val PROVIDER = "AndroidKeyStore"
        private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding"
    }
}