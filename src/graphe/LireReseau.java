package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour lire le réseau du métro parisien depuis un fichier texte
 * (format particulier avec stations et connexions)
 */
public class LireReseau {

    /**
     * Méthode pour lire le fichier du réseau du métro parisien
     * @param fichier le fichier texte contenant le réseau
     * @return Graphe correspondant au réseau
     */
    public static Graphe lire(String fichier) {
        GrapheListe graphe = new GrapheListe();
        Map<String, String> idToNom = new HashMap<>();
        boolean lectureConnexions = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();

                // ignorer les lignes vides ou commentaires
                if (ligne.isEmpty() || ligne.startsWith("%")) continue;

                if (ligne.startsWith("%% Connexions")) {
                    lectureConnexions = true;
                    continue;
                }

                if (!lectureConnexions) {
                    // Traitement des stations
                    String[] parts = ligne.split(":");
                    if (parts.length >= 2) {
                        String id = parts[0].trim();
                        String nom = parts[1].trim();
                        idToNom.put(id, nom);
                        graphe.ajouterArc(nom, nom, 0); // ajoute le noeud
                    }
                } else {
                    // Traitement des connexions
                    String[] parts = ligne.split(":");
                    if (parts.length >= 4) {
                        String idDepart = parts[0].trim();
                        String idArrivee = parts[1].trim();
                        double cout = Double.parseDouble(parts[2].trim());
                        String ligneMetro = parts[3].trim();

                        String nomDepart = idToNom.get(idDepart);
                        String nomArrivee = idToNom.get(idArrivee);

                        if (nomDepart != null && nomArrivee != null) {
                            // arcs bidirectionnels
                            graphe.ajouterArc(nomDepart, nomArrivee, cout, ligneMetro);
                            graphe.ajouterArc(nomArrivee, nomDepart, cout, ligneMetro);
                        } else {
                            System.err.println("Erreur : station inconnue pour ID " +
                                    idDepart + " ou " + idArrivee);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

        System.out.println("Chargement terminé. Nombre de stations : " + graphe.listeNoeuds().size());
        return graphe;
    }
}
