package org.xor.simpleaesgcm

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.xor.simpleaesgcm.Conversions.hexToBytes
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Verify the test cases specified in Appendix B of gcm-spec (see in doc).
 * We skip cases that set the Additional authenticated  data  (AAD) or have empty plaintext (P)
 */
const val AAD_SIZE = 16

class AesGcmVectorTest {
    companion object {
        @JvmStatic
        fun testCases() = arrayOf(
            TestCase(
                "Test Case 2",
                k = "00000000000000000000000000000000",
                p = "00000000000000000000000000000000",
                iv = "000000000000000000000000",
                c = "0388dace60b6a392f328c2b971b2fe78",
                t = "ab6e47d42cec13bdf53a67b21257bddf"
            ),
            TestCase(
                "Test Case 3",
                k = "feffe9928665731c6d6a8f9467308308",
                p = "d9313225f88406e5a55909c5aff5269a86a7a9531534f7da2e4c303d8a318a721c3c0c95956809532fcf0e2449a6b525b16aedf5aa0de657ba637b391aafd255",
                iv = "cafebabefacedbaddecaf888",
                c = "42831ec2217774244b7221b784d0d49ce3aa212f2c02a4e035c17e2329aca12e21d514b25466931c7d8f6a5aac84aa051ba30b396a0aac973d58e091473f5985",
                t = "4d5c2af327cd64a62cf35abd2ba6fab4"
            ),
            TestCase(
                "Test Case 8",
                k = "000000000000000000000000000000000000000000000000",
                p = "00000000000000000000000000000000",
                iv = "000000000000000000000000",
                c = "98e7247c07f0fe411c267e4384b0f600",
                t = "2ff58d80033927ab8ef4d4587514f0fb"
            ),
            TestCase(
                "Test Case 9",
                k = "feffe9928665731c6d6a8f9467308308feffe9928665731c",
                p = "d9313225f88406e5a55909c5aff5269a86a7a9531534f7da2e4c303d8a318a721c3c0c95956809532fcf0e2449a6b525b16aedf5aa0de657ba637b391aafd255",
                iv = "cafebabefacedbaddecaf888",
                c = "3980ca0b3c00e841eb06fac4872a2757859e1ceaa6efd984628593b40ca1e19c7d773d00c144c525ac619d18c84a3f4718e2448b2fe324d9ccda2710acade256",
                t = "9924a7c8587336bfb118024db8674a14"
            ),
            TestCase(
                "Test Case 14",
                k = "0000000000000000000000000000000000000000000000000000000000000000",
                p = "00000000000000000000000000000000",
                iv = "000000000000000000000000",
                c = "cea7403d4d606b6e074ec5d3baf39d18",
                t = "d0d1c8a799996bf0265b98b5d48ab919"
            ),
            TestCase(
                "Test Case 15",
                k = "feffe9928665731c6d6a8f9467308308feffe9928665731c6d6a8f9467308308",
                p = "d9313225f88406e5a55909c5aff5269a86a7a9531534f7da2e4c303d8a318a721c3c0c95956809532fcf0e2449a6b525b16aedf5aa0de657ba637b391aafd255",
                iv = "cafebabefacedbaddecaf888",
                c = "522dc1f099567d07f47f37a32a84427d643a8cdcbfe5c0c97598a2bd2555d1aa8cb08e48590dbb3da7b08b1056828838c5f61e6393ba7a0abcc9f662898015ad",
                t = "b094dac5d93471bdec1a502270e3cc6c"
            )
        )
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun `should encrypt as per test cases`(testCase: TestCase) {
        val aesPayload = AesGCM(randomBytes = { testCase.IV }).encrypt(testCase.K, testCase.P)

        assertEquals(testCase.P.size + AAD_SIZE, aesPayload.ciphertext.size)
        assertTrue(testCase.C + testCase.T contentEquals aesPayload.ciphertext)
        assertTrue(testCase.IV contentEquals aesPayload.iv)
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun `should decrypt as per test cases`(testCase: TestCase) {
        val aesPayload = AesPayload(iv = testCase.IV, ciphertext = testCase.C + testCase.T)
        val plaintext = AesGCM().decrypt(testCase.K, aesPayload)

        assertTrue(testCase.P contentEquals plaintext)
    }
}

class TestCase(
    val name: String,
    k: String, // secret key as hex string
    p: String, // plaintext as hex string
    iv: String, // iv as hex string
    c: String, // ciphertext as hex string
    t: String, // tag as hex string
) {
    val K = hexToBytes(k)
    val P = hexToBytes(p)
    val IV = hexToBytes(iv)
    val C = hexToBytes(c)
    val T = hexToBytes(t)
}

