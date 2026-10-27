package threadPackage;

public class CompteurGlobal {
    private int valeur = 0;

    // synchronized : un seul thread a la fois peut incrementer ET afficher.
    // Les valeurs s'affichent donc toujours dans l'ordre : 1, 2, 3...
    public synchronized int incrementerEtAfficher(int numeroClient, String detail) {
        valeur++;
        System.out.println("Compteur global = " + valeur
                + " (client " + numeroClient + " : " + detail + ")");
        return valeur;
    }

    public synchronized int getValeur() {
        return valeur;
    }
}