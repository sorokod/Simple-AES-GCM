package org.xor.simpleaesgcm

import org.junit.jupiter.api.Assertions
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
        println(serialized) // SCvalYd6/N9keYAu:RiNx5SyJSubAmJdiT7fVpkvPP/qZbEANetiQuFHK

        val deserialized: AesPayload = Base64Serializer.deserialize(serialized)
        val decrypted2: ByteArray = AesGCM().decrypt(secretKey, deserialized)

        println(String(decrypted2)) // SCvalYd6/N9keYAu:RiNx5SyJSubAmJdiT7fVpkvPP/qZbEANetiQuFHK
        assertTrue(plainText contentEquals decrypted2)
    }

}