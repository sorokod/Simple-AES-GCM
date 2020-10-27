package io.github.sorokod.simpleaesgcm

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ExampleTest {
    @Test
    fun `should run example encrypt decrypt`() {
        val secretKey = "16 byte long key".toByteArray()
        val plainText = "some plaintext".toByteArray()

        val encrypted: AesPayload = AesGCM().encrypt(secretKey, plainText)
        val decrypted: ByteArray = AesGCM().decrypt(secretKey, encrypted)

        assertTrue(plainText contentEquals decrypted)

        val serialized : String = Base64Serializer.serialize(encrypted)
        val deserialized: AesPayload = Base64Serializer.deserialize(serialized)
        val decrypted2: ByteArray = AesGCM().decrypt(secretKey, deserialized)

        assertTrue(plainText contentEquals decrypted2)
    }

}