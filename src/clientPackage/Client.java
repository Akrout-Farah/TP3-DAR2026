package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Usage : java clientPackage.Client [IP du serveur] [port]
        String hote = (args.length > 0) ? args[0] : "127.0.0.1";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;

        try (Socket s = new Socket(); Scanner sc = new Scanner(System.in)) {
            s.connect(new InetSocketAddress(InetAddress.getByName(hote), port), 5000);
            System.out.println("Connecte au serveur " + hote + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            System.out.println("Serveur : " + in.readLine());   // numero d'ordre

            while (true) {
                System.out.print("Entrez votre message (exit pour quitter) : ");
                String ligne = sc.nextLine().trim();
                out.println(ligne);

                String reponse = in.readLine();
                if (reponse == null) {
                    System.out.println("Le serveur a ferme la connexion");
                    break;
                }
                System.out.println("Serveur : " + reponse);
                if (ligne.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur client : " + e);
        }
    }
}