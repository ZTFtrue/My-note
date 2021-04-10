# Android KeyStore密钥存储

KeyStore 不需要开发者去维护这个密匙的存储问题，相比起存储在用户的数据空间或者是外部存储器都更加安全. 这个密匙 清除数据或者卸载应用都会被清除掉.

首先用KeyPairGenerator生成非对称(Asymmetric)秘钥对:

```java
/*
 * Generate a new EC key pair entry in the Android Keystore by
 * using the KeyPairGenerator API. The private key can only be
 * used for signing or verification and only with SHA-256 or
 * SHA-512 as the message digest.
 */
KeyPairGenerator kpg = KeyPairGenerator.getInstance(
        KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
kpg.initialize(new KeyGenParameterSpec.Builder(
        alias,
        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
        .setDigests(KeyProperties.DIGEST_SHA256,
            KeyProperties.DIGEST_SHA512)
        //使用秘钥时需要用户授权的设置
        // .setUserAuthenticationRequired(true)
        // .setUserAuthenticationValidityDurationSeconds(10)

        .build());

KeyPair kp = kpg.generateKeyPair();
```

使用私钥签名:

```java
/*
 * Use a PrivateKey in the KeyStore to create a signature over
 * some data.
 */
KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
ks.load(null);
KeyStore.Entry entry = ks.getEntry(alias, null);
if (!(entry instanceof PrivateKeyEntry)) {
    Log.w(TAG, "Not an instance of a PrivateKeyEntry");
    return null;
}
Signature s = Signature.getInstance("SHA256withECDSA");
s.initSign(((PrivateKeyEntry) entry).getPrivateKey());
s.update(data);
byte[] signature = s.sign();
```

使用公钥验证签名:

```java
/*
 * Verify a signature previously made by a PrivateKey in our
 * KeyStore. This uses the X.509 certificate attached to our
 * private key in the KeyStore to validate a previously
 * generated signature.
 */
KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
ks.load(null);
KeyStore.Entry entry = ks.getEntry(alias, null);
if (!(entry instanceof PrivateKeyEntry)) {
    Log.w(TAG, "Not an instance of a PrivateKeyEntry");
    return false;
}
Signature s = Signature.getInstance("SHA256withECDSA");
s.initVerify(((PrivateKeyEntry) entry).getCertificate());
s.update(data);
// Boolean
boolean valid = s.verify(signature);
```

对数据签名验证: <https://developer.android.google.cn/training/articles/keystore.html#SigningAndVerifyingData>
