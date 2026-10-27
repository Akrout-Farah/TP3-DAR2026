package clientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import model.Operation;

public class Client2 {
    public static void main(String[] args) {
        // Usage : java clientPackage.Client2 [IP du serveur] [port]
        String hote = (args.length > 0) ? args[0] : "127.0.0.1";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;

        try (Socket s = new Socket(); Scanner sc = new Scanner(System.in)) {
            s.connect(new InetSocketAddress(InetAddress.getByName(hote), port), 5000);
            System.out.println("Connecte au serveur " + hote + ":" + port);

            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            System.out.println("Serveur : " + in.readObject());

            while (true) {
                System.out.print("Operande 1 (exit pour quitter) : ");
                String saisie = sc.nextLine().trim();

                if (saisie.equalsIgnoreCase("exit")) {
                    out.writeObject("exit");
                    out.flush();
                    System.out.println("Serveur : " + in.readObject());
                    break;
                }

                double a;
                try {
                    a = Double.parseDouble(saisie);
                } catch (NumberFormatException e) {
                    System.out.println("Ce n'est pas un nombre.");
                    continue;
                }

                System.out.print("Operateur (+, -, *, /) : ");
                String o = sc.nextLine().trim();
                if (!o.matches("[+\\-*/]")) {
                    System.out.println("Operateur invalide.");
                    continue;
                }

                System.out.print("Operande 2 : ");
                double b;
                try {
                    b = Double.parseDouble(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ce n'est pas un nombre.");
                    continue;
                }

                out.writeObject(new Operation(a, o.charAt(0), b));
                out.flush();
                System.out.println("Resultat : " + in.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur client : " + e);
        }
    }
}