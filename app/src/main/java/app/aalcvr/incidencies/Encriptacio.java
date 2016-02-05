package app.aalcvr.incidencies;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alcvr on 28/01/2016.
 */

public class Encriptacio {

    //algoritmos de encriptacio
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";

    /***
     * Converteix un array de bytes a String usant valors hexadecimals
     * @param digest arrays de bytes a convertir
     * @return String creat a partir de <code>digest</code>
     */
    private static String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /***
     * Encripta un missatge de text mitjançant algorisme de resum de missatge.
     * @param message text a encriptar
     * @param algorithm algorisme d'encriptació, pot ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
     * @return missatge encriptat
     */
    public static String getStringMessageDigest(String message, String algorithm){
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creant Digest");
        }
        return toHexadecimal(digest);
    }
}
