package io.github.sorokod.simpleaesgcm

import java.security.SecureRandom

/**
 * The purpose of [RandomBytes] interface is to abstract the usage of a particular source of randomness.
 */
fun interface RandomBytes {
    fun generate(byteCount: Int): ByteArray
}

/**
 * An instance of [RandomBytes] interface that uses the default [SecureRandom].
 */
object SecureRandomBytes : RandomBytes {
    private val random = SecureRandom()

    override fun generate(byteCount: Int): ByteArray = ByteArray(byteCount).also { random.nextBytes(it) }
}