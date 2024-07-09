package com.hris.HRIS.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${jasypt.encryptor.password}")
    private String encryptorPassword;

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private SecretKeySpec getKeySpec(String key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, 0, 16, ALGORITHM); // use 16 bytes for AES-128
    }

    private IvParameterSpec getIvSpec(String key) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] ivBytes = md5.digest(key.getBytes(StandardCharsets.UTF_8));
        return new IvParameterSpec(ivBytes);
    }

    public String encryptPassword(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec keySpec = getKeySpec(encryptorPassword);
        IvParameterSpec ivSpec = getIvSpec(encryptorPassword);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decryptPassword(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec keySpec = getKeySpec(encryptorPassword);
        IvParameterSpec ivSpec = getIvSpec(encryptorPassword);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
