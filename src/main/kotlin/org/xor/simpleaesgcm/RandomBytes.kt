package org.xor.simpleaesgcm

import java.security.SecureRandom

fun interface RandomBytes {
    fun generate(byteCount: Int): ByteArray
}

object SecureRandomBytes : RandomBytes {
    private val random = SecureRandom()

    override fun generate(byteCount: Int): ByteArray = ByteArray(byteCount).also { random.nextBytes(it) }
}