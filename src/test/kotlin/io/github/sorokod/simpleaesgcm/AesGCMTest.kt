package io.github.sorokod.simpleaesgcm

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import io.github.sorokod.simpleaesgcm.SecureRandomBytes.generate

internal class AesGCMTest {

    companion object {
        private val key16 = generate(16)
        private val key24 = generate(24)
        private val key32 = generate(32)

        private val data16 = (15..17).map { i -> generate(i) }
        private val data32 = (31..33).map { i -> generate(i) }
        private val data48 = (47..49).map { i -> generate(i) }

        @JvmStatic
        fun keysAndItems() = arrayOf(
            arrayOf(key16, data16 + data32 + data48),
            arrayOf(key24, data16 + data32 + data48),
            arrayOf(key32, data16 + data32 + data48)
        )
    }

    @ParameterizedTest
    @MethodSource("keysAndItems")
    fun `should enc and dec`(key: ByteArray, items: List<ByteArray>) {
        for (item in items) {
            val encrypted = AesGCM().encrypt(key, item)
            val decrypted = AesGCM().decrypt(key, encrypted)

            assertTrue(item contentEquals decrypted)
        }
    }
}