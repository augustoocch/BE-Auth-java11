package com.augustoocc.demo.globant.security;

import com.augustoocc.demo.globant.domain.exceptions.GlobantException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import static com.augustoocc.demo.globant.domain.constants.ErrorCode.DECRIPTION_ERROR;

@Configuration
public class EncriptionConfig<T>{

    @Value("${secret.key}")
    private String SECRET_KEY;
    private static final String ALGORITHM = "AES";

    @Bean
    public BCryptPasswordEncoder passowrdEncoder() {
        return new BCryptPasswordEncoder();
    }


    public T decryptObject(String encryptedData, Class<T> valueType) {
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
            throw new GlobantException(DECRIPTION_ERROR.getMessage(), DECRIPTION_ERROR.getCode());
        }
    }

    private static SecretKeySpec getSecretKeySpec(String key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 32);
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String passowrdEncrypt(String passw) {
        String passwordE = null;
        try {
            passwordE = new String(passowrdEncoder().encode(passw).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passwordE;
    }

    public boolean comparePassword(String password, String encodedPassword) {
        return passowrdEncoder().matches(password, encodedPassword);
    }
}