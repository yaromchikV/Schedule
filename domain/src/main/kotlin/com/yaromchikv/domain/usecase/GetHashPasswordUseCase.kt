package com.yaromchikv.domain.usecase

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class GetHashPasswordUseCase {
    operator fun invoke(password: String): String {
        return try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(password.toByteArray())

            val hexString = StringBuilder()
            for (messageDigest in digest.digest()) {
                var h = Integer.toHexString(0xFF and messageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            ""
        }
    }
}