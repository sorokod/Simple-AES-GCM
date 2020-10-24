package org.xor.simpleaesgcm

open class AesProfile(
        val ivLengthInBytes: Int,
        val tagLengthInBits: Int
)

/**
 * The length of the IV - section 5.2.1.1 of the spec (https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf )
 * recommends 96 (12 bytes) bits.
 *
 * The length of the TAG - the valid sizes are  128,  120,  112,  104 and  96 (section 5.2.1.2). Reducing the TAG size
 * away from 128 impacts the security.
 */
class Base : AesProfile(ivLengthInBytes = 12, tagLengthInBits = 128)