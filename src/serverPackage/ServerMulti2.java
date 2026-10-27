package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import threadPackage.ClientProcess2;
import threadPackage.CompteurGlobal;

public class ServerMulti2 {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        // Un seul compteur, partage par tous les threads
        CompteurGlobal compteur = new CompteurGlobal();
        int numero = 0;

        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Serveur multi-thread (objets) en ecoute sur le port " + PORT);

            while (true) {
                Socket socket = ss.accept();
                numero++;
                System.out.println("Client numero " + numero
                        + " connecte : " + socket.getRemoteSocketAddress());

                new ClientProcess2(socket, numero, compteur).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}