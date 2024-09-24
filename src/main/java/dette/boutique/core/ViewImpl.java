package dette.boutique.core;

import java.util.List;
import java.util.Scanner;

import dette.boutique.Main;

public abstract class ViewImpl<T> implements View {
    @Override
    public int obtenirChoixUtilisateur(int min, int max) {
        Scanner scanner = Main.getScanner();
        while (true) {
            System.out.println("Veuillez entrer votre choix (Entre " + min + " et " + max + ") : ");
            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix >= min && choix <= max) {
                    return choix;
                } else {
                    System.out.println("Choix invalide. Veuillez entrer un nombre entre " + min + " et " + max + ".");
                }
            } else {
                System.out.println("Entrée invalide. Veuillez saisir un nombre entier.");
                scanner.next();
            }
        }
    }

    public void afficherList(List<T> elements) {
        for (int i = 0; i < elements.size(); i++) {
            System.out.println((i + 1) + ". " + elements.get(i));
        }
    }

}
