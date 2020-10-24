package org.xor.simpleaesgcm

/**
 * [AesProfile] is used to parametrise instances of [AesGCM]. Generally the (default) [Base] class should be used.
 *
 * @param ivLengthInBytes
 * @param tagLengthInBits
 *
 * @author David Soroko
 */
open class AesProfile(
        val ivLengthInBytes: Int,
        val tagLengthInBits: Int
)

/**
 * [Base] is the standard set of parameters for [AesGCM]. The parameter values are:
 * - `ivLengthInBytes` 12 bytes see [section 5.2.1.1](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf)
 * - `tagLengthInBits` 128 bits. Valid values are 128,  120,  112,  104 and 96. See [section 5.2.1.2](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf)
 *
 * @author David Soroko
 */
class Base : AesProfile(ivLengthInBytes = 12, tagLengthInBits = 128)