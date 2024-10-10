package encryption;

import android.util.Log;

import org.bouncycastle.util.encoders.Base64;

import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Kapil Rathee on 11/1/18.
 */

public class AESEncreption {

    private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LEN = 16; // 128 bits

public static String encrypt(String key, String data) {
    String iv = getIV();
    try {
        byte[] sha = generateSha512Hash(key.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
// converting byte[] type SHA into Hexadecimal representation string
        for (int i = 0; i < sha.length; i++) {
            sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16).substring(1));
        }
        String shaKey = sb.substring(0, 16);
        //Log.e("AES Key",shaKey)
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        SecretKeySpec secretKey = new SecretKeySpec(fixKey(shaKey).getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(AESEncreption.CIPHER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedData = cipher.doFinal((data.getBytes()));
        String encryptedDataInBase64 = new String (Base64.encode(encryptedData));
        String ivInBase64 = new String (Base64.encode(iv.getBytes("UTF-8")));
        return encryptedDataInBase64 + ":" + ivInBase64;
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
}

    private static String fixKey(String key) {
        if (key.length() < AESEncreption.CIPHER_KEY_LEN) {
            int numPad = AESEncreption.CIPHER_KEY_LEN - key.length();for (int i = 0; i < numPad; i++) {
                key += "0"; // 0 pad to len 16 bytes
            }
            return key;
        }
        if (key.length() > AESEncreption.CIPHER_KEY_LEN) {
            return key.substring(0, CIPHER_KEY_LEN); // truncate to 16 bytes
        }
        return key;
    }


    public static String decrypt(String key, String data) {
        try {
            byte sha[] = generateSha512Hash(key.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < sha.length; i++) {
                sb.append(Integer.toString((sha[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            String shaKey = sb.substring(0, 16);
            String[] parts = data.split(":");
            //IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]))
            IvParameterSpec iv = new IvParameterSpec(Base64.decode(parts[1]));
            SecretKeySpec secretKey = new SecretKeySpec(
                    shaKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance(AESEncreption.CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] decodedEncryptedData = Base64.decode(parts[0]);
            byte[] original = cipher.doFinal(decodedEncryptedData);
            return new String(original);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    /*
* This method return IV random values of 16 bit (Init Vector values) which
* must be 16 bit long and random each time encrypting the data.
*/
    protected static String getIV() {
        String ivCHARS = "1234567890";
        StringBuilder iv = new StringBuilder();
        Random rnd = new Random();
        while (iv.length() < 16) { // length of the random string.
            int index = (int) (rnd.nextFloat() * ivCHARS.length());
            iv.append(ivCHARS.charAt(index));
        }
        return iv.toString();
    }

    /*** Generate SHA512 Hashes of given data
     *
     * @param data
     *
    - data to use should be in bytes type array form
     * @return decrypted SHA512 hashes in form of byte type array
     */

    public static byte[] generateSha512Hash(byte[] data) {
        String algorithm = "SHA-512";
        byte[] hash = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.reset();
            hash = digest.digest(data);
        } catch (Exception e) {
            //e.printStackTrace()
        }
        return hash;
    }

}
