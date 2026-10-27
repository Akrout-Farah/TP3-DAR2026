package threadPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {
    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try (Socket s = socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            // c) envoi du numero d'ordre au client
            out.println("Bienvenue, vous etes le client numero " + numero);

            String ligne;
            while ((ligne = in.readLine()) != null) {
                ligne = ligne.trim();
                System.out.println("Client " + numero + " envoie : " + ligne);
                out.println("Echo : " + ligne);
                if (ligne.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Client " + numero + " : erreur " + e.getMessage());
        }
        System.out.println("Client numero " + numero + " deconnecte");
    }
}