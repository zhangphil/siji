package com.wlqq.huodi.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * author: fangzw
 * date: 13-5-15
 * time: 下午1:08
 */
public class DESUtils {

    public static final long SID_OF_NO_SESSION = -1;
    public static final String TOKEN_OF_NO_SESSION = "C86EA4369B61AE5E";
    public static final String PUBLIC_KEY = "A891167560B398D8";
    public static final String DOWNLOAD_HOSTS_KEY = "ABDF45847FC9837C";
    private static final String ALGORITHM_PADDING = "DES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "DES";
    private static final String ENCODING = "UTF-8";
    private static final String CONNECTOR = "|";

    /**
     * 加密（两次）,若为登录，注册，session id用SID_OF_NO_SESSION,session token 用TOKEN_OF_NO_SESSION
     *
     * @param text  明文
     * @param sid   session id
     * @param token session token
     * @return 密文
     * @throws Exception
     */
    public static String encrypt(String text, long sid, String token) throws Exception {

        //第一次用token加密,加密结果和SID放一起准备第二次加密
        String firstCipherText = doEncrypt(text, token);
        String textForSecondEncrypt = firstCipherText.concat(CONNECTOR).concat(String.valueOf(sid));

        //第二次用public key 加密
        String finalCipherText = doEncrypt(textForSecondEncrypt, PUBLIC_KEY);

        return finalCipherText;

    }

    /**
     * 解密（两次）,若为登录，注册，session id用SID_OF_NO_SESSION,session token 用TOKEN_OF_NO_SESSION
     *
     * @param cipherText 密文
     * @param sid        session id
     * @param token      session token
     * @return 明文
     * @throws Exception
     */
    public static String decrypt(String cipherText, long sid, String token) throws Exception {

        //第一次用public key解密,解密结果用‘|’分解 ,并检查sid
        String firstText = doDecrypt(cipherText, PUBLIC_KEY);
        String[] firstResult = firstText.split("\\|"); //必须用“\\|”,用“|”无法得到正确结果

        if (new Long(firstResult[1]).longValue() != sid) {
            throw new Exception("Not matched session ID !");
        }

        //第二次用token解密
        String cipherTextForSecondDecrypt = firstResult[0];
        String finalText = doDecrypt(cipherTextForSecondDecrypt, token);

        return finalText;
    }

    /**
     * 实际加密处理
     *
     * @param text     明文
     * @param keyValue 密钥
     * @return 密文
     * @throws Exception
     */
    public static String doEncrypt(String text, String keyValue) throws Exception {

        byte keyBytes[] = HexTransfer.hexStr2ByteArr(keyValue);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        byte[] inputBytes = text.getBytes(ENCODING);

        Cipher cipher = Cipher.getInstance(ALGORITHM_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptBytes = cipher.doFinal(inputBytes);

        return HexTransfer.byteArr2HexStr(encryptBytes);

    }

    /**
     * 实际解密处理
     *
     * @param cipherText 密文
     * @param keyValue   密钥
     * @return 明文
     * @throws Exception
     */
    public static String doDecrypt(String cipherText, String keyValue) throws Exception {

        byte keyBytes[] = HexTransfer.hexStr2ByteArr(keyValue);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        byte[] cipherTextBytes = HexTransfer.hexStr2ByteArr(cipherText);

        Cipher cipher = Cipher.getInstance(ALGORITHM_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptBytes = cipher.doFinal(cipherTextBytes);

        return new String(decryptBytes, ENCODING);

    }

}
