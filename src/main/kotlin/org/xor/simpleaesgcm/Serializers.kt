package org.xor.simpleaesgcm

import java.util.*

interface Serializer<T> {
    fun serialize(aesPayload: AesPayload): T
    fun deserialize(payload: T): AesPayload
}

object Base64Serializer : Serializer<String> {
    private const val DELIMITER = ':'

    private val enc = Base64.getEncoder()
    private val dec = Base64.getDecoder()

    override fun serialize(aesPayload: AesPayload): String = with(aesPayload) {
        "${enc.encodeToString(iv)}$DELIMITER${enc.encodeToString(ciphertext)}"
    }

    override fun deserialize(payload: String): AesPayload = with(payload.split(DELIMITER)) {
        AesPayload(iv = dec.decode(get(0)), ciphertext = dec.decode(get(1)))
    }
}
