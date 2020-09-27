package com.teamer.teapot.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密工具
 *
 * @author : tanzj
 * @date : 2020/1/19.
 */
@Slf4j
public class AESUtil {

    private static final String AES = "AES";
    private static final String CIPHER_INSTANCE = "AES/ECB/PKCS5Padding";

    private static final String KEY = "com.teamer.teapot.AESKEY";

    public AESUtil() {
    }

    /**
     * <b>用于身份证等数据库敏感信息加密</b><br/><br/>
     * 密钥：配置文件 (32位)<br/>
     * 字符集：utf-8<br/>
     * 填充方式：AES/ECB/PKCS5Padding<br/>
     * 输出方式：base64<br/>
     * 数据块：128位<br/>
     *
     * @param sourceString 源信息
     * @return String - 加密后数据
     */
    public String encrypt(String sourceString) {

        try {
            if (sourceString == null) {
                return null;
            } else {
                byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
                Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                byte[] encrypted = cipher.doFinal(sourceString.getBytes());
                return Base64Util.encodeBase64(encrypted);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <b>用于身份证等数据库敏感信息解密</b><br/><br/>
     * 密钥：配置文件 (32位)<br/>
     * 字符集：utf-8<br/>
     * 填充方式：AES/ECB/PKCS5Padding<br/>
     * 输出方式：base64<br/>
     * 数据块：128位<br/>
     *
     * @param encryptSource 解密数据信息
     * @return String - 解密后数据
     */
    public String decrypt(String encryptSource) {
        try {
            byte[] encryptSourceByte = Base64Util.decodeBase64(encryptSource);
            if (encryptSourceByte != null && encryptSourceByte.length != 0) {
                byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, AES);
                Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] original = cipher.doFinal(encryptSourceByte);
                return new String(original);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /**
     * base64 包装
     */
    public static class Base64Util {

        static String encodeBase64(byte[] src) {
            Base64 base64 = new Base64();
            return new String(base64.encode(src));
        }

        public static String encodeBase64(String src) {
            return encodeBase64(src.getBytes());
        }

        static byte[] decodeBase64(String src) {
            Base64 base64 = new Base64();
            return base64.decode(src);
        }

        public static byte[] decodeBase64(byte[] src) {
            Base64 base64 = new Base64();
            return base64.decode(src);
        }
    }
}

