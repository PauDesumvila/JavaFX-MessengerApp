package appMissatgeria;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public final class XifratgeAsimetricUtility {

    public static byte[] encriptar(byte[] data, PublicKey pub){
        byte[] encryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            encryptedData = cipher.doFinal(data);
        } catch (Exception e){
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static byte[] desencriptar(byte[] data, PrivateKey priv){
        byte[] decryptedData = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, priv);
            decryptedData = cipher.doFinal(data);
        } catch (Exception e){
            e.printStackTrace();
        }
        return decryptedData;
    }
}
