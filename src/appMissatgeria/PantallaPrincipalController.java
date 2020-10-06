package appMissatgeria;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class PantallaPrincipalController implements Initializable {

    @FXML private TextArea taMissatge1, taMissatge2, taMissatgesRebuts1, taMissatgesRebuts2;
    @FXML private Button btEnviaMissatge1, btEnviaMissatge2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taMissatgesRebuts1.setEditable(false);
        taMissatgesRebuts2.setEditable(false);
        taMissatgesRebuts1.setWrapText(true);
        taMissatgesRebuts2.setWrapText(true);
        taMissatge1.setWrapText(true);
        taMissatge2.setWrapText(true);

    }

    @FXML
    private void enviaMissatge1(MouseEvent event){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date data = new Date();
        String missatge = taMissatge1.getText();

        if(!missatge.equals("")){
            // per el visor de missatges propis es mostra directament
            taMissatgesRebuts1.appendText(df.format(data) + " - MISSATGE ENVIAT: " + missatge + '\n' + '\n');

            // per el visor de missatges alie es procesa el missatge encriptant-lo i desencriptant-lo simetricament
            byte[] missatgeEncriptatS =  XifradorUtility.xifrarSimetric(missatge);
            String missatgeDesencriptatS = XifradorUtility.desxifrarSimetric(missatgeEncriptatS);

            try{
                // ara encriptarem asimetricament el missatge desencriptat simetricament
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair keyPair = kpg.generateKeyPair();
                byte[] missatgeEncriptatA = XifradorUtility.xifrarAsimetric(missatgeDesencriptatS, keyPair.getPublic());

                // obtindrem la firma digital del missatge encriptat asimetricament
                byte[] signatura = FirmaDigitalUtility.signData(missatgeEncriptatA, keyPair.getPrivate());

                if(FirmaDigitalUtility.validateSignature(missatgeEncriptatA, signatura, keyPair.getPublic())){
                    // si la signatura es bona procedirem a desencriptarlo asimetricament
                    String missatgeDesencriptatA = XifradorUtility.desxifrarAsimetric(missatgeEncriptatA, keyPair.getPrivate());

                    // enviem el missatgeDesencriptat via socols i el rebem
                    try{
                        Socket socket = new Socket("localhost", 7777);

                        OutputStream outputStream = socket.getOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                        dataOutputStream.writeUTF(missatgeDesencriptatA);
                        dataOutputStream.flush();

                        InputStream inputStream = socket.getInputStream();
                        DataInputStream dataInputStream = new DataInputStream(inputStream);

                        String missatgeDesdelServidor = dataInputStream.readUTF();
                        taMissatgesRebuts2.appendText(df.format(data) + " - MISSATGE REBUT: " + missatgeDesdelServidor + '\n' + '\n');

                        // netejem el text area del missatge un cop ha estat enviat
                        taMissatge1.setText("");

                        socket.close();
                    } catch (IOException e){
                        System.out.println("Conexio amb el servidor perduda...");
                        taMissatgesRebuts1.appendText("**** EL DARRER MISSATGE NO HA ESTAT LLIURAT ****" + '\n' + '\n');
                        taMissatge1.setText("");
                    }
                }
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void enviaMissatge2(MouseEvent event){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date data = new Date();
        String missatge = taMissatge2.getText();

        if(!missatge.equals("")){
            // per el visor de missatges propis es mostra directament
            taMissatgesRebuts2.appendText(df.format(data) + " - MISSATGE ENVIAT: " + missatge + '\n' + '\n');

            // per el visor de missatges alie es procesa el missatge encriptant-lo i desencriptant-lo simetricament
            byte[] missatgeEncriptatS =  XifradorUtility.xifrarSimetric(missatge);
            String missatgeDesencriptatS = XifradorUtility.desxifrarSimetric(missatgeEncriptatS);

            try{
                // ara encriptarem asimetricament el missatge desencriptat simetricament
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair keyPair = kpg.generateKeyPair();
                byte[] missatgeEncriptatA = XifradorUtility.xifrarAsimetric(missatgeDesencriptatS, keyPair.getPublic());

                // obtindrem la firma digital del missatge encriptat asimetricament
                byte[] signatura = FirmaDigitalUtility.signData(missatgeEncriptatA, keyPair.getPrivate());

                if(FirmaDigitalUtility.validateSignature(missatgeEncriptatA, signatura, keyPair.getPublic())) {
                    // si la signatura es bona procedirem a desencriptarlo asimetricament
                    String missatgeDesencriptatA = XifradorUtility.desxifrarAsimetric(missatgeEncriptatA, keyPair.getPrivate());

                    taMissatgesRebuts1.appendText(df.format(data) + " - MISSATGE REBUT: " + missatgeDesencriptatA + '\n' + '\n');

                    // netejem el text area del missatge un cop ha estat enviat
                    taMissatge2.setText("");
                }
            } catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
        }
    }
}


