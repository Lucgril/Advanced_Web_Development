package org.sharechef.data.impl;

import java.security.SecureRandom;


public class Utility {
    
    protected static SecureRandom random = new SecureRandom();

    public static String generateToken() {
        long longToken = Math.abs(random.nextInt());
        return Long.toString(longToken, 200);

    }
    
}
