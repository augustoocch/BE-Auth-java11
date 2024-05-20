package com.augustoocc.demo.globant.security;

import com.augustoocc.demo.globant.domain.exceptions.GlobantException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static com.augustoocc.demo.globant.domain.constants.ErrorCode.*;

@Configuration
@Getter
public class EncryptionConfig{

    @Value("${secret.key}")
    private String SECRET_KEY;

    private final BCryptPasswordEncoder passwordEncoder;

    public EncryptionConfig() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public <T> T decryptObject(String encryptedData, Class<T> valueType) {
        try {
            String[] parts = encryptedData.split(":");
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

            SecretKeySpec secretKeySpec = getSecretKeySpec(SECRET_KEY);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(decryptedString, valueType);
        } catch (Exception e) {
            throw new GlobantException(DECRYPTION_ERROR.getMessage(), DECRYPTION_ERROR.getCode());
        }
    }

    private static SecretKeySpec getSecretKeySpec(String key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 32);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String passwordEncrypt(String passw) {
        String passwordE = null;
        try {
            passwordE = new String(getPasswordEncoder().encode(passw).toString());
            return passwordE;
        } catch (Exception e) {
            throw new GlobantException(ENCRYPTION_ERROR.getMessage(), ENCRYPTION_ERROR.getCode());
        }
    }

    public boolean comparePassword(String password, String encodedPassword) {
       return passwordEncoder.matches(password, encodedPassword);
    }

}