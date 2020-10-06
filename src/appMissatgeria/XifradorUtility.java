package appMissatgeria;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

public final class XifradorUtility {

    public static byte[] xifrarSimetric(String missatge){
        SecretKey key = XifratgeSimetricUtility.generarKey("periquito", 128);
        byte[] b = missatge.getBytes();
        return XifratgeSimetricUtility.encriptar(key, b);
    }

    public static String desxifrarSimetric(byte[] b){
        SecretKey key = XifratgeSimetricUtility.generarKey("periquito", 128);
        byte[] bb = XifratgeSimetricUtility.desencriptar(key, b);
        String missatge = new String(bb);
        return missatge;
    }

    public static byte[] xifrarAsimetric(String missatge, PublicKey pub){
        byte[] b = missatge.getBytes();
        return XifratgeAsimetricUtility.encriptar(b, pub);
    }

    public static String desxifrarAsimetric(byte[] b, PrivateKey priv){
        byte[] bb = XifratgeAsimetricUtility.desencriptar(b, priv);
        String missatge = new String(bb);
        return missatge;
    }
}
