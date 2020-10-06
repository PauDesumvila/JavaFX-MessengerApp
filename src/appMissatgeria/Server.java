package appMissatgeria;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = ss.accept();
        System.out.println("Connection from " + socket + "!");

        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        String message = dataInputStream.readUTF();
        System.out.println("The message sent from the socket was: " + message);

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

        System.out.println("Closing sockets.");
        ss.close();
        socket.close();
    }
}