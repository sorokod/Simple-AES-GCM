package org.xor.simpleaesgcm

import java.security.SecureRandom


private val secRandom = SecureRandom()

fun ByteArray.toHex() = this.joinToString("") { "%02x".format(it) }

fun randomBytes(byteCount: Int) = ByteArray(byteCount).also { secRandom.nextBytes(it) }