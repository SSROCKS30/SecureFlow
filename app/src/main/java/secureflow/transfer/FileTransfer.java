package secureflow.transfer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransfer {

    public static void sendFile(String filePath, String host, int port) throws Exception {
        Socket socket = new Socket(host, port);
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            socket.getOutputStream().write(buffer, 0, bytesRead);
        }
        fis.close();
        socket.close();
    }

    public static void receiveFile(String filePath, int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        FileOutputStream fos = new FileOutputStream(filePath);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = socket.getInputStream().read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        socket.close();
        serverSocket.close();
    }
}
