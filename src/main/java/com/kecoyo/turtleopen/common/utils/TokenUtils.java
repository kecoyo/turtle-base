package com.kecoyo.turtleopen.common.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

public class TokenUtils {

    private static final String ALG_AES_ECB_PKCS5 = "AES/ECB/PKCS5Padding";

    private static final String ALGORITHM = "AES";

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * 加密token
     *
     * @param base64Secret
     */
    public static String generateToken(JSONObject jsonObject, String base64Secret) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALG_AES_ECB_PKCS5);
        // 加密时使用的盐来构造秘钥对象
        SecretKeySpec skeySpec = new SecretKeySpec(getSecretKey(base64Secret), ALGORITHM);
        // 初始化为加密模式的密码器
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        // 加密报文转换为json字符串
        String text = JSON.toJSONString(jsonObject);
        // 加密后的报文数组
        byte[] encryptText = cipher.doFinal(text.getBytes(UTF8));
        // 对加密后的报文进行base64编码
        return Base64.encodeBase64String(encryptText);
    }

    /**
     * 解密token
     */
    public static JSONObject parseToken(String token, String base64Secret) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALG_AES_ECB_PKCS5);
        // 加密时使用的盐来够造秘钥对象
        SecretKeySpec skeySpec = new SecretKeySpec(getSecretKey(base64Secret), ALGORITHM);
        // 初始化为解密模式的密码器
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        // 对加密报文进行base64解码
        byte[] encrypted = Base64.decodeBase64(token);
        // 解密后的报文数组
        byte[] original = cipher.doFinal(encrypted);
        // 输出utf8编码的字符串，输出字符串需要指定编码格式
        String decryptText = new String(original, UTF8);
        // 解密后的报文转换为json对象
        return JSONObject.parseObject(decryptText);
    }

    /**
     * 获取秘钥
     *
     * @param base64Secret
     * @return
     */
    private static byte[] getSecretKey(String base64Secret) {
        // System.out.println("base64Secret: " + base64Secret);
        // 将base64字符串转换为字节数组
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);

        // 将字节数组转换为ByteBuffer
        ByteBuffer buffer = ByteBuffer.wrap(keyBytes);

        // 8个字节交换位置
        for (int i = 0; i < buffer.capacity() / 8; i++) {
            for (int j = 0; j < 4; j++) {
                byte b1 = buffer.get(i * 8 + j);
                byte b2 = buffer.get(i * 8 + 7 - j);
                buffer.put(i * 8 + j, b2);
                buffer.put(i * 8 + 7 - j, b1);
            }
        }

        // 2个字节交换位置
        for (int i = 0; i < buffer.capacity() / 2; i++) {
            byte b1 = buffer.get(i * 2);
            byte b2 = buffer.get(i * 2 + 1);
            buffer.put(i * 2, b2);
            buffer.put(i * 2 + 1, b1);
        }

        // 转base64字符串，截取前32位
        String base64Secret2 = Encoders.BASE64.encode(keyBytes);
        byte[] keyBytes2 = new byte[32];
        System.arraycopy(base64Secret2.getBytes(), 0, keyBytes2, 0, keyBytes2.length);
        // System.out.println("base64Secret: " + new String(keyBytes2));
        return keyBytes2;
    }

    public static void main(String[] args) {
        try {
            String base64Secret = "ZG10b3AxfjQ2MzI3UmZzZCMkc2RmcyNARFY4Z2Y0ZXM=";

            JSONObject encryptObject = new JSONObject();
            encryptObject.put("userId", 1000373);
            encryptObject.put("loginId", "0");
            encryptObject.put("createTime", System.currentTimeMillis());
            encryptObject.put("expireTime", 1000 * 60 * 60 * 24 * 7); // 7天

            System.out.println("被加解密的报文: " + JSON.toJSONString(encryptObject));
            String token = TokenUtils.generateToken(encryptObject, base64Secret);
            System.out.println("AES 加密后的Base64报文: " + token);

            // String token =
            // "XOSvvE29k5BBc7paYCgiUOpXBiaBX1RFP7zQWnYULedPxw2yQJ6IL0lz6kA7lBy/iA0Wu+Y5Z0YqJmeQvz+Aa8deezDML6orGK+JmVOINC6J6f5pyG7T3TY2c4/wLeILwzNB0W685esnlPDvEx2taA==";
            JSONObject decryptObject = TokenUtils.parseToken(token, base64Secret);
            System.out.println("对加密后的报文解密后的明文为: " + JSON.toJSONString(decryptObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
