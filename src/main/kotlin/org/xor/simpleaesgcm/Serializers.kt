package org.xor.simpleaesgcm

import org.xor.simpleaesgcm.Conversions.base64ToBytes
import org.xor.simpleaesgcm.Conversions.bytesToBase64

interface Serializer<T> {
    fun serialize(aesPayload: AesPayload): T
    fun deserialize(payload: T): AesPayload
}

object Base64Serializer : Serializer<String> {
    private const val DELIMITER = ':'

    override fun serialize(aesPayload: AesPayload): String = with(aesPayload) {
        "${bytesToBase64(iv)}$DELIMITER${bytesToBase64(ciphertext)}"
    }

    override fun deserialize(payload: String): AesPayload = with(payload.split(DELIMITER)) {
        AesPayload(iv = base64ToBytes(get(0)), ciphertext = base64ToBytes(get(1)))
    }
}
