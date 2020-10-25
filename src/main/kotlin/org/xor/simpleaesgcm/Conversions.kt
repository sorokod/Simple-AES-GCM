package org.xor.simpleaesgcm

import java.util.*

object Conversions {

    inline fun bytesToHex(bytes: ByteArray): String = bytes.joinToString("") { "%02x".format(it) }

    inline fun bytesToBase64(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)

    inline fun base64ToBytes(str: String): ByteArray = Base64.getDecoder().decode(str)
}