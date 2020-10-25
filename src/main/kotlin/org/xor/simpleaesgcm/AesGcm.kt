package org.xor.simpleaesgcm

import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * [AesGCM] provides simple ergonomics for AES GCM so that encryption and decryption can be done with a single
 * function call.
 *
 *
 * See also: [Recommendation for Block Cipher Modes of Operation GCM and GMAC](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf)
 *
 *
 * @author David Soroko
 */
private const val AES_MODE = "AES/GCM/NoPadding"
private const val AES = "AES"

class AesGCM(val profile: AesProfile = Base(), private val randomBytes: RandomBytes = SecureRandomBytes) {

    /**
     * Encrypts using AES GCM
     *
     * @param secretKey  The secret key to use for encryption, only valid sizes are: 16, 24 and 32 bytes
     * @param plaintext  The content to be encrypted
     *
     * @return - an instance of [AesPayload]
     */
    fun encrypt(secretKey: ByteArray, plaintext: ByteArray): AesPayload {
        val spec = GCM_parameterSpec()

        val cipher = Cipher.getInstance(AES_MODE).also {
            val sk = SecretKeySpec(secretKey, AES)
            it.init(ENCRYPT_MODE, sk, spec)
        }

        return AesPayload(iv = spec.iv, ciphertext = cipher.doFinal(plaintext))
    }

    /**
     * Decrypts using AES GCM
     *
     * @param secretKey  The secret key that was used to encrypt the payload
     * @param payload  The payload to be decrypted.
     *
     * @return - the plaintext as a [ByteArray]
     */
    fun decrypt(secretKey: ByteArray, payload: AesPayload): ByteArray = Cipher.getInstance(AES_MODE).let {
        val sk = SecretKeySpec(secretKey, AES)
        val spec = GCM_parameterSpec(payload.iv)
        it.init(DECRYPT_MODE, sk, spec)

        it.doFinal(payload.ciphertext)
    }

    /**
     * Regarding IV size = 12 bytes:
     * https://crypto.stackexchange.com/questions/41601/aes-gcm-recommended-iv-size-why-12-bytes
     *
     * Regarding tag size = 16 bytes
     * https://crypto.stackexchange.com/questions/26783/ciphertext-and-tag-size-and-iv-transmission-with-aes-in-gcm-mode
     */
    private fun GCM_parameterSpec(): GCMParameterSpec =
        GCMParameterSpec(profile.tagLengthInBits, randomBytes.generate(profile.ivLengthInBytes))

    private fun GCM_parameterSpec(iv: ByteArray): GCMParameterSpec =
        GCMParameterSpec(profile.tagLengthInBits, iv)
}


/**
 * [AesPayload] is a wrapper for the output of [AesGCM.encrypt]. It wraps the resulting iv and ciphertext.
 *
 * @param iv The IV that was used for the encryption
 * @param ciphertext The encrypted ciphertext
 *
 * @author David Soroko
 */
class AesPayload(val iv: ByteArray, val ciphertext: ByteArray)


