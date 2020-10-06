package appMissatgeria;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public final class FirmaDigitalUtility {

    public static byte[] signData(byte[] data, PrivateKey priv){
        byte[] signature = null;
        try{
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priv);
            signer.update(data);
            signature = signer.sign();
        } catch (Exception e){
            e.printStackTrace();
        }
        return signature;
    }

    public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub){
        boolean isValid = false;
        try{
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(pub);
            signer.update(data);
            isValid = signer.verify(signature);
        } catch (Exception e){
            e.printStackTrace();
        }
        return isValid;
    }
}
