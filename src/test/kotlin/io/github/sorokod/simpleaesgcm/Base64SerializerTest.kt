package io.github.sorokod.simpleaesgcm

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import io.github.sorokod.simpleaesgcm.Base64Serializer.deserialize
import io.github.sorokod.simpleaesgcm.Base64Serializer.serialize
import io.github.sorokod.simpleaesgcm.SecureRandomBytes.generate

internal class Base64SerializerTest {

    companion object {
        @JvmStatic
        fun aesPayloads() = arrayOf(
                arrayOf(AesPayload(iv = generate(12), ciphertext = generate(12))),
                arrayOf(AesPayload(iv = generate(16), ciphertext = generate(16))),
                arrayOf(AesPayload(iv = generate(1), ciphertext = generate(1)))
        )
    }

    @ParameterizedTest
    @MethodSource("aesPayloads")
    fun `should serialize deserialize`(aesPayload: AesPayload) {
        val actual = deserialize(serialize(aesPayload))

        assertTrue(actual.iv contentEquals aesPayload.iv)
        assertTrue(actual.ciphertext contentEquals aesPayload.ciphertext)
    }
}