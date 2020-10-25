package org.xor.simpleaesgcm

import java.security.SecureRandom


private val secRandom = SecureRandom()

fun randomBytes(byteCount: Int) = ByteArray(byteCount).also { secRandom.nextBytes(it) }