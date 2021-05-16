/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author leon
 */
public class UserUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger big = new BigInteger(1, thedigest);
            return big.toString(16);
        } catch (Exception e) {
            return null;
        }
    }
}
