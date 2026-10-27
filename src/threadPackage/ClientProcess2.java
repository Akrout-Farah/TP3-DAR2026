package threadPackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Operation;

public class ClientProcess2 extends Thread {
    private final Socket socket;
    private final int numero;
    private final CompteurGlobal compteur;

    public ClientProcess2(Socket socket, int numero, CompteurGlobal compteur) {
        this.socket = socket;
        this.numero = numero;
        this.compteur = compteur;
    }

    @Override
    public void run() {
        // ObjectOutputStream AVANT ObjectInputStream (evite le blocage)
        try (Socket s = socket;
             ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {

            out.writeObject("Bienvenue, vous etes le client numero " + numero);
            out.flush();

            while (true) {
                Object recu = in.readObject();

                if (recu instanceof String && ((String) recu).equalsIgnoreCase("exit")) {
                    out.writeObject("Au revoir");
                    out.flush();
                    break;
                }

                if (recu instanceof Operation) {
                    Operation op = (Operation) recu;
                    String resultat = calculer(op);
                    compteur.incrementerEtAfficher(numero, op + " = " + resultat);
                    out.writeObject(resultat);
                    out.flush();
                }
            }
        } catch (EOFException e) {
            System.out.println("Client " + numero + " a ferme la connexion sans dire exit");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client " + numero + " : erreur " + e);
        }
        System.out.println("Client numero " + numero + " deconnecte");
    }

    private String calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        switch (op.getOperateur()) {
            case '+': return String.valueOf(a + b);
            case '-': return String.valueOf(a - b);
            case '*': return String.valueOf(a * b);
            case '/':
                return (b == 0) ? "Erreur : division par zero" : String.valueOf(a / b);
            default:
                return "Erreur : operateur invalide";
        }
    }
}