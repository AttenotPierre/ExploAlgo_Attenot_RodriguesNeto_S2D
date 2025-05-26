package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe pour lire le réseau du métro parisien depuis un fichier texte
 * (format avec : liste de stations puis connexions)
 */
public class LireReseau {

    /**
     * Lit un fichier de métro et construit le graphe associé.
     * @param fichier Chemin du fichier texte
     * @return Graphe du réseau
     */
    public static Graphe lire(String fichier) {
        GrapheListe graphe = new GrapheListe();
        Map<Integer, String> idVersNom = new HashMap<>();
        boolean lectureStations = false;
        boolean lectureConnexions = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();



                // Détection des sections
                if (ligne.toLowerCase().contains("stations")) {
                    System.out.println("Section STATIONS détectée.");
                    lectureStations = true;
                    lectureConnexions = false;
                    continue;
                }


               if (ligne.toLowerCase().contains("connex")) {
                    System.out.println(" Section CONNEXIONS détectée.");
                    lectureStations = false;
                    lectureConnexions = true;
                    continue;
                }


                // Traitement des stations
                if (lectureStations) {
                    // Format : id:nom:x:y:lignes
                    String[] parts = ligne.split(":", 5);
                    if (parts.length >= 2) {
                        try {
                            int id = Integer.parseInt(parts[0].trim());
                            String nom = parts[1].trim();
                            idVersNom.put(id, nom);
                            graphe.ajouterNoeud(nom); // ajout simple du nœud
                        } catch (NumberFormatException e) {
                            System.err.println("Erreur ID station invalide : " + ligne);
                        }
                    }
                }

                // Traitement des connexions
                else if (lectureConnexions) {
                    // Format : id1:id2:temps:ligne
                    String[] parts = ligne.split(":", 4);
                    if (parts.length >= 4) {
                        try {
                            int id1 = Integer.parseInt(parts[0].trim());
                            int id2 = Integer.parseInt(parts[1].trim());
                            double cout = Double.parseDouble(parts[2].trim());
                            String ligneMetro = parts[3].trim();

                            String nom1 = idVersNom.get(id1);
                            String nom2 = idVersNom.get(id2);

                            if (nom1 != null && nom2 != null) {
                                // arcs bidirectionnels avec info ligne
                                graphe.ajouterArc(nom1, nom2, cout, ligneMetro);
                                graphe.ajouterArc(nom2, nom1, cout, ligneMetro);
                            } else {
                                System.err.println("Connexion ignorée : station inconnue (id " + id1 + " ou " + id2 + ")");
                            }

                        } catch (NumberFormatException e) {
                            System.err.println("Erreur de format (connexion) : " + ligne);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }

        System.out.println("✅ Chargement terminé. Nombre de stations : " + graphe.listeNoeuds().size());
        return graphe;
    }
}
