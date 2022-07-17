package vip.testops.account.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {

    public static String digest(String content, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] bytes = messageDigest.digest(content.getBytes(StandardCharsets.UTF_8));
        return bytes2string(bytes);
    }

    private static String bytes2string(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            int tmp = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
            String t = Integer.toHexString(tmp);
            sb.append(t.length() < 2 ? "0"+t : t);
        }
        return sb.toString();
    }
}
