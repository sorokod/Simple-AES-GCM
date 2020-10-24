[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](/LICENSE)

# Simple Aes GCM

A small library that provides **simple** Aes GCM `encrypt` / `decrypt` functions. Here simple means that:

- Uses sensible initialization vector (IV) size - 12 bytes. 
- Uses sensible tag length - 128 bits 
- Eliminates the possibility of IV reuse by managing them internally. 


## Example usage

```kotlin
val secretKey = "16 byte long key".toByteArray()
val plainText = "some plaintext".toByteArray()

val encrypted: AesPayload = AesGCM().encrypt(secretKey, plainText)
val decrypted: ByteArray = AesGCM().decrypt(secretKey, encrypted)

print(String(decrypted)) // prints: some plaintext
```  

The call to `AesGCM().encrypt(...)` produces output of type `AesPayload`, it is a pair that contains two byte arrays: 
the `iv` and the `ciphertext`. You can convert an instance of `AesPayload` to / from string using a `Base64Serializer`:

```kotlin
val serialized : String = Base64Serializer.serialize(encrypted)
println(serialized) // SCvalYd6/N9keYAu:RiNx5SyJSubAmJdiT7fVpkvPP/qZbEANetiQuFHK

val deserialized: AesPayload = Base64Serializer.deserialize(serialized)
val decrypted2: ByteArray = AesGCM().decrypt(secretKey, deserialized)
print(String(decrypted2)) // prints: some plaintext 
```

---

- Further reading: [Recommendation for Block Cipher Modes of Operation: Galois/Counter Mode (GCM) and GMAC](https://nvlpubs.nist.gov/nistpubs/Legacy/SP/nistspecialpublication800-38d.pdf) (PDF)
