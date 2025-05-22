package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe pour lire un réseau de transport depuis un fichier
 */
public class LireReseau {

    /**
     * Méthode statique pour lire un graphe depuis un fichier
     * @param fichier nom du fichier à lire
     * @return Graphe chargé depuis le fichier
     */
    public static Graphe lire(String fichier) {
        GrapheListe graphe = new GrapheListe();

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();

                // Ignorer les lignes vides
                if (ligne.isEmpty()) {
                    continue;
                }

                // Supposons que le fichier est tabulé et que les colonnes sont :
                // nomDepart \t nomArrivee \t cout \t ligneTransport (ligneTransport ignorée ici)
                String[] parties = ligne.split("\t");

                if (parties.length >= 3) {
                    try {
                        String nomDepart = parties[0].trim();
                        String nomArrivee = parties[1].trim();
                        double cout = Double.parseDouble(parties[2].trim());

                        // Appel corrigé : uniquement 3 arguments
                        graphe.ajouterArc(nomDepart, nomArrivee, cout);

                    } catch (NumberFormatException e) {
                        System.err.println("Erreur ligne " + numeroLigne + " : coût non valide '" + parties[2] + "'");
                    }
                } else {
                    System.err.println("Erreur ligne " + numeroLigne + " : format incorrect, attendu au moins 3 colonnes");
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lecture du fichier " + fichier + " : " + e.getMessage());
        }

        System.out.println("Graphe chargé depuis " + fichier + " avec " + graphe.listeNoeuds().size() + " nœuds");
        return graphe;
    }
}
