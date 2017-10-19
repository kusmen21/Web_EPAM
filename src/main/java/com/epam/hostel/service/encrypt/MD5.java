package com.epam.hostel.service.encrypt;



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * Class for MD5 encryption
 */
public class MD5 {
	private static Logger log = LogManager.getLogger(MD5.class);
    private static final String MD5 = "MD5";
    private static final String ALGORITHM_NOT_RECOGNIZED = "Algorithm wasn't recognized.";

    /**
     * @param word String to encrypt
     * @return String that contains MD5 encryption of the given word
     */
    public static String encrypt(String word) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance(MD5);
            messageDigest.reset();
            messageDigest.update(word.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error(ALGORITHM_NOT_RECOGNIZED, e);
        }
        BigInteger bigIntPass = new BigInteger(1, digest);
        return bigIntPass.toString(16);
    }
}
