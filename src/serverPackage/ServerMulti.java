package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import threadPackage.ClientProcess;

public class ServerMulti {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        int numero = 0;

        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Serveur multi-thread en ecoute sur le port " + PORT);

            while (true) {
                Socket socket = ss.accept();
                numero++;
                System.out.println("Client numero " + numero
                        + " connecte : " + socket.getRemoteSocketAddress());

                new ClientProcess(socket, numero).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}