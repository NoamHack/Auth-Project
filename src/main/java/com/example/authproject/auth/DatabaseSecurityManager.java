package com.example.authproject.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class DatabaseSecurityManager {
    private static final String SECRET_KEY_FILE = "secret.key";
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            File keyFile = new File(SECRET_KEY_FILE);
            if (keyFile.exists()) {
                secretKey = loadSecretKey();
            } else {
                secretKey = generateSecretKey();
                saveSecretKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }

    private void saveSecretKey() throws IOException {
        byte[] keyBytes = secretKey.getEncoded();
        FileOutputStream fos = new FileOutputStream(SECRET_KEY_FILE);
        fos.write(keyBytes);
        fos.close();
    }

    private SecretKey loadSecretKey() throws IOException, NoSuchAlgorithmException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(SECRET_KEY_FILE));
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
