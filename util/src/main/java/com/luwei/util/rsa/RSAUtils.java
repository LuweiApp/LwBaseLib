package com.luwei.util.rsa;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @Author: chenjianrun
 * @Time: 2019/2/21
 * @Description:
 */
public class RSAUtils {
    // 秘钥的类型
    public enum KeyType {
        publicKey, privateKey
    }

    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String BC = "BC";// 非对称加密密钥算法
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式


    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br>
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 用(公钥/秘钥)加密，每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data    需加密数据的byte数据
     * @param keyStr  秘钥字符串
     * @param keyType 加密类型：privateKey：私钥加密、Publick_Key：公钥加密
     * @return 加密后的 byte 型数据（避免出现换行符，在使用 Base64 时需要 Base64.NO_WRAP）
     */
    public static String encryptData(String data, String keyStr, KeyType keyType) {
        return Base64.encodeToString(encryptData(data.getBytes(), keyStr, keyType), Base64.NO_WRAP);
    }

    /**
     * 用(公钥/秘钥)加密，每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data    需加密数据的byte数据
     * @param keyStr  秘钥字符串
     * @param keyType 加密类型：privateKey：私钥加密、Publick_Key：公钥加密
     * @return 加密后的 byte 型数据（避免出现换行符，在使用 Base64 时需要 Base64.NO_WRAP）
     */
    public static byte[] encryptData(byte[] data, String keyStr, KeyType keyType) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING, BC);
            // 编码前设定编码方式及密钥
            if (keyType == KeyType.publicKey) {
                PublicKey publicKey = RSAUtils.loadPublicKey(keyStr);
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            } else {
                PrivateKey privateKey = RSAUtils.loadPrivateKey(keyStr);
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            }
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用(公钥/秘钥)加密，每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data          需加密数据的byte数据
     * @param inputStream   公钥或者私钥数据流
     * @param keyType       加密类型：privateKey：私钥加密、Publick_Key：公钥加密
     * @return 加密后的 byte 型数据（避免出现换行符，在使用 Base64 时需要 Base64.NO_WRAP）
     */
    public static String encryptData(String data, InputStream inputStream, KeyType keyType) {
        return Base64.encodeToString(encryptData(data.getBytes(), inputStream, keyType), Base64.NO_WRAP);
    }

    /**
     * 用(公钥/秘钥)加密，每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data          需加密数据的byte数据
     * @param inputStream   公钥或者私钥数据流
     * @param keyType       加密类型：privateKey：私钥加密、Publick_Key：公钥加密
     * @return 加密后的 byte 型数据（避免出现换行符，在使用 Base64 时需要 Base64.NO_WRAP）
     */
    public static byte[] encryptData(byte[] data, InputStream inputStream, KeyType keyType) {
        try {
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING, BC);
            // 编码前设定编码方式及密钥
            if (keyType == KeyType.publicKey) {
                PublicKey publicKey = RSAUtils.loadPublicKey(inputStream);
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            } else {
                PrivateKey privateKey = RSAUtils.loadPrivateKey(inputStream);
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            }
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥或密钥解密
     * @param cipherData    解密内容
     * @param keyStr        私钥或密钥字符串
     * @param keyType       解密类型：privateKey：私钥；publickey：公钥
     * @return
     * @throws Exception
     */
    public static String decryptData(String cipherData, String keyStr, KeyType keyType) throws Exception {
        return new String(decryptData(Base64.decode(cipherData.getBytes(), Base64.NO_WRAP), keyStr, keyType), "utf-8");
    }


    /**
     * 公钥或密钥解密
     * @param cipherData    解密内容
     * @param keyStr        私钥或密钥字符串
     * @param keyType       解密类型：privateKey：私钥；publickey：公钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptData(byte[] cipherData, String keyStr, KeyType keyType)
            throws Exception {
        if (keyStr == null) {
            throw new Exception("解密公钥或私钥为空, 请设置");
        }
        try {
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            if (keyType == KeyType.publicKey) {
                PublicKey publicKey = loadPublicKey(keyStr);
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
            }else{
                PrivateKey privateKey = loadPrivateKey(keyStr);
                cipher.init(Cipher.DECRYPT_MODE,privateKey);
            }
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥或私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 公钥或密钥解密
     * @param cipherData            解密内容
     * @param inputStream           私钥或密钥数据流
     * @param keyType               解密类型：privateKey：私钥；publickey：公钥
     * @return
     * @throws Exception
     */
    public static String decryptData(String cipherData, InputStream inputStream, KeyType keyType) throws Exception {
        return new String(decryptData(Base64.decode(cipherData.getBytes(), Base64.NO_WRAP), inputStream, keyType), "utf-8");
    }


    /**
     * 公钥或密钥解密
     * @param cipherData            解密内容
     * @param inputStream           私钥或密钥数据流
     * @param keyType               解密类型：privateKey：私钥；publickey：公钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptData(byte[] cipherData, InputStream inputStream, KeyType keyType)
            throws Exception {
        if (inputStream == null) {
            throw new Exception("解密公钥或私钥为空, 请设置");
        }
        try {
            // 使用默认RSA
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            if (keyType == KeyType.publicKey) {
                PublicKey publicKey = loadPublicKey(inputStream);
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
            }else{
                PrivateKey privateKey = loadPrivateKey(inputStream);
                cipher.init(Cipher.DECRYPT_MODE,privateKey);
            }
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥或私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 通过公钥byte[](Publick_Key.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchProviderException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA, BC);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr, Base64.DEFAULT));
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是 PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA, BC);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }

}
