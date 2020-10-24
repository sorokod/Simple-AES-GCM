package org.xor.simpleaesgcm

import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * Provides sensible defaults for AES GCM so that encryption / decryption can be done with a single
 * function call
 *
 * https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf
 *
 *
 **/

private const val AES_MODE = "AES/GCM/NoPadding"
private const val AES = "AES"

class AesGCM(val profile: AesProfile = Base()) {

    /**
     * Valid sizes for secret key in bytes: 16, 24, or 32
     */
    fun encrypt(key: ByteArray, data: ByteArray): AesPayload {
        val spec = GCM_parameterSpec()

        val cipher = Cipher.getInstance(AES_MODE).also {
            val sk = SecretKeySpec(key, AES)
            it.init(ENCRYPT_MODE, sk, spec)
        }

        return AesPayload(iv = spec.iv, ciphertext = cipher.doFinal(data))
    }

    fun decrypt(key: ByteArray, data: AesPayload): ByteArray = Cipher.getInstance(AES_MODE).let {
        val sk = SecretKeySpec(key, AES)
        val spec = GCM_parameterSpec(data.iv)
        it.init(DECRYPT_MODE, sk, spec)

        it.doFinal(data.ciphertext)
    }

    /**
     * Regarding IV size = 12 bytes:
     * https://crypto.stackexchange.com/questions/41601/aes-gcm-recommended-iv-size-why-12-bytes
     *
     * Regarding tag size = 16 bytes
     * https://crypto.stackexchange.com/questions/26783/ciphertext-and-tag-size-and-iv-transmission-with-aes-in-gcm-mode
     */
    private fun GCM_parameterSpec(): GCMParameterSpec =
        GCMParameterSpec(profile.tagLengthInBits, randomBytes(profile.ivLengthInBytes))

    private fun GCM_parameterSpec(iv: ByteArray): GCMParameterSpec =
        GCMParameterSpec(profile.tagLengthInBits, iv)
}


/**
 * Wraps the result of an encryption: the iv and the ciphertext.
 */
class AesPayload(val iv: ByteArray, val ciphertext: ByteArray)


