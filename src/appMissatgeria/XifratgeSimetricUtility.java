package appMissatgeria;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public final class XifratgeSimetricUtility {

    public static SecretKey generarKey(String text, int keySize){
        SecretKey sKey = null;
        if ((keySize == 128) || (keySize == 192) || (keySize == 256)){
            try{
                byte[] data = text.getBytes(StandardCharsets.UTF_16);
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return sKey;
    }

    public static byte[] encriptar(SecretKey sKey, byte[] data){
        byte[] encryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception e){
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static byte[] desencriptar(SecretKey sKey, byte[] data){
        byte[] decryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            decryptedData = cipher.doFinal(data);
        } catch (Exception e){
            e.printStackTrace();
        }
        return decryptedData;
    }
}
