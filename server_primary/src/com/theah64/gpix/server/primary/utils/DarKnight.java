package com.theah64.gpix.server.primary.utils;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/*
 * Used to simple text enc and dec
 * Created by Shifar Shifz on 6/29/2015.
 */
public class DarKnight {

    private static final String ALGORITHM = "AES";

    private static final byte[] SALT = new byte[]{'t', 'H', 'e', 'A', 'p', 'A', 'c', 'H', 'e', '6', '4', '1', '0', '1', '1', '1'};
    private static final String X = DarKnight.class.getSimpleName();

    public static String getEncrypted(String plainText) {

        Key salt = getSalt();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, salt);
            byte[] encodedValue = cipher.doFinal(plainText.getBytes());
            return Base64.encode(encodedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Failed to encrypt data");
    }

    public static String getDecrypted(String encodedText) {

        Key salt = getSalt();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, salt);
            byte[] decodedValue = Base64.decode(encodedText);
            byte[] decValue = cipher.doFinal(decodedValue);
            return new String(decValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Key getSalt() {
        return new SecretKeySpec(SALT, ALGORITHM);
    }

}