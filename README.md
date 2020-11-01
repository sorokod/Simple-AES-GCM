[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](/LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-1.4.10-blue.svg?logo=kotlin)](http://kotlinlang.org)

# Simple Aes GCM

A small Kotlin library that provides **simple** Aes GCM `encrypt` / `decrypt` functions. Simple means that:

- Sensible default initialization vector (IV) size - 12 bytes. 
- Sensible default tag length - 128 bits 
- IVs are managed internally, eliminating the possibility of IV reuse. 


## Install

Maven:
```xml
<dependency>
    <groupId>io.github.sorokod</groupId>
    <artifactId>simpleaesgcm</artifactId>
    <version>1.0.1</version>
</dependency>
```
Gradle:
```groovy
implementation 'io.github.sorokod:simpleaesgcm:1.0.1'
```

## Example usage

```kotlin
import io.github.sorokod.simpleaesgcm.AesGCM
import io.github.sorokod.simpleaesgcm.AesPayload
import io.github.sorokod.simpleaesgcm.Base64Serializer

fun main() {
    val secretKey = "16 byte long key".toByteArray()
    val plainText = "some plaintext".toByteArray()

    val encrypted: AesPayload = AesGCM().encrypt(secretKey, plainText)
    val decrypted: ByteArray = AesGCM().decrypt(secretKey, encrypted)

    println(String(decrypted)) // prints: some plaintext
}
```  

The call to `AesGCM().encrypt(...)` produces output of type `AesPayload` - it is a pair that contains  the `iv` and the 
`ciphertext` byte arrays. You can convert an instance of `AesPayload` to / from `String` using a `Base64Serializer`:

```kotlin
    val serialized : String = Base64Serializer.serialize(encrypted)
    println(serialized) // SCvalYd6/N9keYAu:RiNx5SyJSubAmJdiT7fVpkvPP/qZbEANetiQuFHK
    
    val deserialized: AesPayload = Base64Serializer.deserialize(serialized)
    val decrypted2: ByteArray = AesGCM().decrypt(secretKey, deserialized)
    print(String(decrypted2)) // prints: some plaintext 
```

---

- Further reading: [Recommendation for Block Cipher Modes of Operation: Galois/Counter Mode (GCM) and GMAC](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf) (PDF)
