package com.potato.library.util.encrypt;


import android.text.TextUtils;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 安全相关工具类
 *
 * @author jyz
 */
public class SecurityUtil {

    public static final String DEF_C = "s9L6ttTCGZ1c2HuVNZvu6JULwW29LVlXQobfeat1f1k=";
    public static final String DEF_K = "EN7evXIdQcrUH01jg8lTdJJJKNgtI/gty7uDxjSJW1/x/NbxQNN3+g3xtdZwxy+twFgvPZPS59vKNvvolsdZ6A==";

    private static final String KEY_SHA = "SHA";

    private static final String KEY_MD5 = "MD5";

    /**
     * BASE64解密
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     */
    public static String encryptBASE64(byte[] bts) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(bts);
    }

    /**
     * MD5加密
     */
    public static String encryptMD5(String data) throws Exception {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes());
        byte s[] = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length; i++) {
            sb.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
                    .substring(6));
        }
        return sb.toString();
    }

    /**
     * SHA加密
     */
    public static String encryptSHA(String data) throws Exception {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data.getBytes());
        return new String(sha.digest());
    }

    /**
     * 初始化HMAC密钥
     */
    public static String initMacKey(String type) throws Exception {
        if (TextUtils.isEmpty(type)) {
            type = KEY_MAC.MD5.getValue();
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     */
    public static String encryptHMAC(String data, String key, String type)
            throws Exception {
        if (TextUtils.isEmpty(type)) {
            type = KEY_MAC.MD5.getValue();
        }
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), type);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return new String(mac.doFinal(data.getBytes()));
    }

    /**
     * DES加密
     *
     * @param encryptString 需要加密的字符串
     * @param encryptKey    密钥
     * @return 加密之后的内容
     * @throws Exception
     */
    public static String desEncode(String encryptString, String encryptKey)
            throws Exception {
        // 针对des加密使用nopading类型，数据长度必须是8的倍数
        int count = encryptString.length() % 8;
        if (count > 0) {
            for (int i = 0; i < 8 - count; i++) {
                encryptString = encryptString.concat(" ");
            }
        }
        String se = DESCoder.encryptDES(encryptString, encryptKey);
        return se;
    }

    /**
     * DES解密
     *
     * @param decryptString 需要解密的字符串
     * @param decryptKey    密钥
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String desDecode(String decryptString, String decryptKey)
            throws Exception {
        return DESCoder.decryptDES(decryptString, decryptKey);
    }

    /**
     * rsa公钥加密
     *
     * @param str
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String rsaEncode(String str, String publicKey)
            throws Exception {
        byte[] data = str.getBytes();
        byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);
        return RSACoder.encryptBASE64(encodedData);
    }

    /**
     * rsa私钥解密
     *
     * @param str
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String rsaDecode(String str, String privateKey)
            throws Exception {
        byte[] decodedData = RSACoder.decryptByPrivateKey(
                RSACoder.decryptBASE64(str), privateKey);
        return new String(decodedData);
    }

    public static enum KEY_MAC { // MAC算法可选以下多种算法
        MD5("HmacMD5"), SHA1("HmacSHA1"), SHA256("HmacSHA256"), SHA384(
                "HmacSHA384"), SHA512("HmacSHA512");
        private final String key;

        private KEY_MAC(String key) {
            this.key = key;
        }

        public String getValue() {
            return key;
        }
    }
}
