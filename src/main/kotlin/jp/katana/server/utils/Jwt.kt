package jp.katana.server.utils

import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*


class Jwt {
    companion object {
        const val mojangPublicKeyBase64 =
            "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V"

        val mojangPublicKey = genECKey(mojangPublicKeyBase64)

        fun verify(publicKey: PublicKey, jwsObject: JWSObject): Boolean {
            val verifier = DefaultJWSVerifierFactory().createJWSVerifier(jwsObject.header, publicKey)
            return jwsObject.verify(verifier)
        }

        fun genECKey(key: String): PublicKey {
            return KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(key)))
        }
    }
}