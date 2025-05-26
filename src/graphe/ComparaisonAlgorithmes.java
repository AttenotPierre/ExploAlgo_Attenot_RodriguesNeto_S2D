package graphe;

import graphe.GrapheListe;
import graphe.BellmanFord;
import graphe.Dijkstra;
import graphe.Valeurs;

import java.io.BufferedReader;
import java.io.FileReader;

public class ComparaisonAlgorithmes {
    
    public static void main(String[] args) throws Exception {
        String fichier = args[0];
        String depart = args[1];

        GrapheListe graphe = new GrapheListe();

        // Lecture du graphe depuis le fichier
        BufferedReader br = new BufferedReader(new FileReader(fichier));
        String ligne;
        while ((ligne = br.readLine()) != null) {
            String[] parties = ligne.trim().split("\\s+");
            String origine = parties[0];
            String destination = parties[1];
            double poids = Double.parseDouble(parties[2]);
            graphe.ajouterArc(origine, destination, poids);
        }
        br.close();

        // Résolution par Bellman-Ford
        BellmanFord bf = new BellmanFord();
        long debutBF = System.nanoTime();
        Valeurs resBF = bf.resoudre(graphe, depart);
        long finBF = System.nanoTime();

        // Résolution par Dijkstra
        Dijkstra dijkstra = new Dijkstra();
        long debutDij = System.nanoTime();
        Valeurs resDij = dijkstra.resoudre(graphe, depart);
        long finDij = System.nanoTime();

        // Comparaison des résultats
        boolean identiques = true;
        for (String n : graphe.listeNoeuds()) {
            double distBF = resBF.getValeur(n);
            double distDij = resDij.getValeur(n);
            String p1 = resBF.getParent(n), p2 = resDij.getParent(n);
            if (Math.abs(distBF - distDij) > 0.001) identiques = false;
            if ((p1 == null && p2 != null) || (p1 != null && !p1.equals(p2))) identiques = false;
        }

        // Affichage des temps
        System.out.printf("Temps Bellman-Ford : %.4f ms\n", (finBF - debutBF) / 1e6);
        System.out.printf("Temps Dijkstra     : %.4f ms\n", (finDij - debutDij) / 1e6);
        System.out.println("Résultats identiques ? " + (identiques ? "OUI" : "NON"));

        // Affichage détaillé des résultats
        System.out.println("\n--- Résultats détaillés ---");
        System.out.printf("%-10s | %-8s | %-8s | %-8s | %-8s\n", "Sommet", "DistBF", "DistDij", "PèreBF", "PèreDij");
        System.out.println("--------------------------------------------------------");
        for (String n : graphe.listeNoeuds()) {
            double distBF = resBF.getValeur(n);
            double distDij = resDij.getValeur(n);
            String p1 = resBF.getParent(n);
            String p2 = resDij.getParent(n);
            System.out.printf("%-10s | %-8.2f | %-8.2f | %-8s | %-8s\n", n, distBF, distDij,
                    (p1 == null ? "-" : p1), (p2 == null ? "-" : p2));
        }
    }
}
