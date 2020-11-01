package com.example.demo.util;

/**
 * describe
 *
 * @Auther Mr.Garfield
 * @Date 2020/6/22
 */



import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 依赖jar包
 <dependency>
 <groupId>commons-codec</groupId>
 <artifactId>commons-codec</artifactId>
 <version>1.10</version>
 </dependency>
 */
public class RSAutil {
    private static final String RSA = "RSA";
    private static final String SHA1_WITH_RSA = "SHA1WithRSA";
    private static final String DE_SEDE_ECB_PKCS5_PADDING = "DESede/ECB/PKCS5Padding";
    private static final String UTF_8 = "UTF-8";
    private static final String DE_SEDE = "DESede";

    public static void main(String[] args) throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String phoneNo = "13012345678";
        String cardNo = "4108754623198745636";
//        String key="wekcv34234fg5i3d122023rd";
        String key="5e6c3b3a3b9325f114693uat";

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        Random random = new Random();
        int i = random.nextInt(9999)%10000;
        System.out.println(i);

        //加密
        String enPhoneNo = encrypt(key, phoneNo);
        String enCardNo = encrypt(key, cardNo);

        System.out.println(enPhoneNo);
        System.out.println(enCardNo);

        //解密
        System.out.println(decrypt(key, enPhoneNo));
        System.out.println(decrypt(key, enCardNo));

        System.out.println(encrypt(key,"couponTypeId=mj28&drawDate=20200319135710&certNo=360981198612111656&certType=01"));

        System.out.println(decrypt(key,"kkojxOo+JEjA26AyHG0SBQWviwe5eCOUQWrCK7AOdvyF+2vlFKdTX5vMKSgYWMJDBpwJLZ9RGx0t+doZeKttH913nx8Wl7v01+zI7gw5QD1qJ1juHdzWlg=="));
    }


    /**
     * 3des ecb加密
     *
     * @param key
     * @param data
     * @throws Exception
     */
    public static String encrypt(String key, String data) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DE_SEDE);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DE_SEDE_ECB_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 3des ecb解密
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String data) throws Exception {
        byte[] bytesrc = Base64.decodeBase64(data);
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DE_SEDE);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DE_SEDE_ECB_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }
}

