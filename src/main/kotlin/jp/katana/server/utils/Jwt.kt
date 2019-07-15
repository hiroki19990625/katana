package jp.katana.server.utils

import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*


/**
 * Minecraftで使用するJwtを容易に利用できる機能を提供します。
 */
class Jwt {
    companion object {
        /**
         * MojangのBase64でエンコードされた公開鍵
         */
        private const val MOJANG_PUBLIC_KEY_BASE64 =
            "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V"

        /**
         * Mojangの公開鍵
         */
        val mojangPublicKey = genECKey(MOJANG_PUBLIC_KEY_BASE64)

        /**
         * 公開鍵を使用してJwtを検証します。
         * @param publicKey 公開鍵
         * @param jwsObject Jwt
         * @return Boolean
         */
        fun verify(publicKey: PublicKey, jwsObject: JWSObject): Boolean {
            val verifier = DefaultJWSVerifierFactory().createJWSVerifier(jwsObject.header, publicKey)
            return jwsObject.verify(verifier)
        }

        /**
         * Base64から公開鍵を生成します。
         * @param key Base64の公開鍵
         * @return 公開鍵
         */
        fun genECKey(key: String): PublicKey {
            return KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(key)))
        }
    }
}