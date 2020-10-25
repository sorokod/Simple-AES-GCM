package org.xor.simpleaesgcm

import java.util.*

/**
 * Utilities for converting byte arrays to - hex and base 64 strings
 */
object Conversions {

    private const val HEX_CHARS = "0123456789abcdef"

    fun bytesToHex(bytes: ByteArray): String = bytes.joinToString("") { "%02x".format(it) }

    fun bytesToBase64(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)

    fun base64ToBytes(str: String): ByteArray = Base64.getDecoder().decode(str)

    fun hexToBytes(str: String): ByteArray = ByteArray(str.length.shr(1)).also {
        for (i in str.indices step 2) {
            val hi = HEX_CHARS.indexOf(str[i]);
            val lo = HEX_CHARS.indexOf(str[i + 1]);

            it[i.shr(1)] = hi.shl(4).or(lo).toByte()
        }
    }
}
