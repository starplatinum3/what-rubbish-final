/*
 * Copyright (C) 2013-2021 Federico Iosue (federico@iosue.it)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package en.edu.zucc.pb.loginapplication.util

import android.util.Base64
import android.util.Log
//import it.feio.android.omninotes.helpers.LogDelegate
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.*
import javax.crypto.spec.DESKeySpec

class Security private constructor(){

//kotlin  const
//    const val
//    kotlin 类变量
//    https://blog.csdn.net/wd2014610/article/details/79768069
//    kotlin static
//    final   val  TAG="Security"
    companion object {
        private const   val  TAG="Security"
//        const val LOAN_TYPE = "loanType"
//        const val LOAN_TITLE = "loanTitle"

        @JvmStatic
        fun md5(s: String): String {
            return try {
                val digest = MessageDigest.getInstance("MD5")
                digest.update(s.toByteArray())
                val messageDigest = digest.digest()

                // Creates Hex String
                val hexString = StringBuilder()
                for (b in messageDigest) {
                    hexString.append(Integer.toHexString(0xFF and b.toInt()))
                }
                hexString.toString()
            } catch (e: NoSuchAlgorithmException) {
                Log.w(TAG,"Something is gone wrong calculating MD5",e)
//                LogDelegate.w("Something is gone wrong calculating MD5", e)
                ""
//                catch 最下面一个value kotlin
//                https://blog.csdn.net/beyondforme/article/details/106279549
//                这个值是用来返回的
            }
        }

        @JvmStatic
        fun encrypt(value: String, password: String): String? {
            return try {
                val keySpec = DESKeySpec(password.toByteArray(StandardCharsets.UTF_8))
                val keyFactory = SecretKeyFactory.getInstance("DES")
                val key = keyFactory.generateSecret(keySpec)
                val clearText = value.toByteArray(StandardCharsets.UTF_8)
                // Cipher is not thread safe
                val cipher = Cipher.getInstance("DES")
                cipher.init(Cipher.ENCRYPT_MODE, key)
                Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT)
            } catch (e: Exception) {
//                LogDelegate.e("Something is gone wrong encrypting", e)
                Log.e(TAG,"Something is gone wrong encrypting",e)
                value
            }
        }

        @JvmStatic
        fun decrypt(value: String?, password: String): String? {
            return try {
                val keySpec = DESKeySpec(password.toByteArray(StandardCharsets.UTF_8))
                val keyFactory = SecretKeyFactory.getInstance("DES")
                val key = keyFactory.generateSecret(keySpec)
                val encryptedPwdBytes = Base64.decode(value, Base64.DEFAULT)
                // cipher is not thread safe
                val cipher = Cipher.getInstance("DES")
                cipher.init(Cipher.DECRYPT_MODE, key)
                val decrypedValueBytes = cipher.doFinal(encryptedPwdBytes)
                String(decrypedValueBytes)
            } catch (e: Exception) {
//                LogDelegate.e("Error decrypting", e)
                Log.e(TAG,"Error decrypting",e)
                value
            }
        }

    }
}