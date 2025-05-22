package src.graphe;
import java.io.IOException;
import java.util.List;

/**
 * Programme de comparaison des algorithmes de Bellman-Ford et Dijkstra
 */
public class ComparaisonAlgorithmes {
    
    public static void main(String[] args) {
        // Création de plusieurs graphes pour les tests
        GrapheListe[] graphes = new GrapheListe[5];
        String[] nomsGraphes = {"Graphe 1 (Figure 1)", "Graphe 2 (Linéaire)", 
                               "Graphe 3 (Étoile)", "Graphe 4 (Cycle)", "Graphe 5 (Complexe)"};
        
        // Graphe 1 : le graphe de la Figure 1
        graphes[0] = new GrapheListe();
        graphes[0].ajouterArc("A", "B", 12);
        graphes[0].ajouterArc("A", "D", 87);
        graphes[0].ajouterArc("B", "E", 11);
        graphes[0].ajouterArc("C", "A", 19);
        graphes[0].ajouterArc("D", "B", 23);
        graphes[0].ajouterArc("D", "C", 10);
        graphes[0].ajouterArc("E", "D", 43);
        
        // Graphe 2 : graphe linéaire
        graphes[1] = new GrapheListe();
        graphes[1].ajouterArc("A", "B", 5);
        graphes[1].ajouterArc("B", "C", 3);
        graphes[1].ajouterArc("C", "D", 7);
        graphes[1].ajouterArc("D", "E", 2);
        graphes[1].ajouterArc("E", "F", 4);
        
        // Graphe 3 : graphe en étoile
        graphes[2] = new GrapheListe();
        graphes[2].ajouterArc("Centre", "A", 10);
        graphes[2].ajouterArc("Centre", "B", 15);
        graphes[2].ajouterArc("Centre", "C", 20);
        graphes[2].ajouterArc("Centre", "D", 25);
        graphes[2].ajouterArc("A", "Centre", 10);
        graphes[2].ajouterArc("B", "Centre", 15);
        graphes[2].ajouterArc("C", "Centre", 20);
        graphes[2].ajouterArc("D", "Centre", 25);
        
        // Graphe 4 : graphe avec cycle
        graphes[3] = new GrapheListe();
        graphes[3].ajouterArc("A", "B", 8);
        graphes[3].ajouterArc("B", "C", 12);
        graphes[3].ajouterArc("C", "D", 6);
        graphes[3].ajouterArc("D", "A", 9);
        graphes[3].ajouterArc("A", "C", 25);
        graphes[3].ajouterArc("B", "D", 15);
        
        // Graphe 5 : graphe plus complexe
        graphes[4] = new GrapheListe();
        graphes[4].ajouterArc("S", "A", 4);
        graphes[4].ajouterArc("S", "B", 2);
        graphes[4].ajouterArc("A", "B", 1);
        graphes[4].ajouterArc("A", "C", 5);
        graphes[4].ajouterArc("B", "C", 8);
        graphes[4].ajouterArc("B", "D", 10);
        graphes[4].ajouterArc("C", "D", 2);
        graphes[4].ajouterArc("C", "T", 6);
        graphes[4].ajouterArc("D", "T", 3);
        
        // Algorithmes à comparer
        BellmanFord bellmanFord = new BellmanFord();
        Dijkstra dijkstra = new Dijkstra();
        
        System.out.println("=== COMPARAISON DES ALGORITHMES ===\n");
        System.out.printf("%-20s %-15s %-15s %-20s %-20s%n", 
                         "Graphe", "Nœuds", "Arcs", "Temps BF (ms)", "Temps Dijkstra (ms)");
        System.out.println("-".repeat(90));
        
        // Test sur chaque graphe
        for (int i = 0; i < graphes.length; i++) {
            GrapheListe g = graphes[i];
            String nomGraphe = nomsGraphes[i];
            
            // Statistiques du graphe
            int nbNoeuds = g.listeNoeuds().size();
            int nbArcs = compterArcs(g);
            
            // Choisir un nœud de départ (le premier de la liste)
            String depart = g.listeNoeuds().get(0);
            
            // Test Bellman-Ford
            long debutBF = System.nanoTime();
            Valeurs resultBF = bellmanFord.resoudre(g, depart);
            long finBF = System.nanoTime();
            double tempsBF = (finBF - debutBF) / 1_000_000.0; // Conversion en millisecondes
            
            // Test Dijkstra
            long debutDijkstra = System.nanoTime();
            Valeurs resultDijkstra = dijkstra.resoudre(g, depart);
            long finDijkstra = System.nanoTime();
            double tempsDijkstra = (finDijkstra - debutDijkstra) / 1_000_000.0;
            
            // Affichage des résultats
            System.out.printf("%-20s %-15d %-15d %-20.3f %-20.3f%n", 
                             nomGraphe, nbNoeuds, nbArcs, tempsBF, tempsDijkstra);
            
            // Vérification de la cohérence des résultats
            if (!resultatsIdentiques(resultBF, resultDijkstra, g.listeNoeuds())) {
                System.out.println("ATTENTION: Résultats différents entre les algorithmes!");
            }
        }
        
        System.out.println("\n=== ANALYSE DÉTAILLÉE ===");
        
        // Test plus approfondi sur le graphe 1
        GrapheListe g1 = graphes[0];
        System.out.println("\nTest détaillé sur " + nomsGraphes[0] + " depuis A:");
        
        Valeurs valeursB = bellmanFord.resoudre(g1, "A");
        Valeurs valeursD = dijkstra.resoudre(g1, "A");
        
        System.out.println("\nRésultats Bellman-Ford:");
        System.out.println(valeursB);
        
        System.out.println("Résultats Dijkstra:");
        System.out.println(valeursD);
        
        // Comparaison des chemins
        System.out.println("Chemins trouvés:");
        for (String dest : g1.listeNoeuds()) {
            if (!dest.equals("A")) {
                List<String> cheminBF = valeursB.calculerChemin(dest);
                List<String> cheminD = valeursD.calculerChemin(dest);
                System.out.println("Vers " + dest + " - BF: " + cheminBF + 
                                 " - Dijkstra: " + cheminD);
            }
        }
        
        System.out.println("\n=== CONCLUSION ===");
        System.out.println("En général, Dijkstra est plus efficace que Bellman-Ford car:");
        System.out.println("1. Dijkstra traite chaque nœud une seule fois");
        System.out.println("2. Bellman-Ford peut nécessiter plusieurs itérations sur tous les nœuds");
        System.out.println("3. Pour des graphes avec des poids positifs, Dijkstra est optimal");
        System.out.println("4. La complexité de Dijkstra est O((V+E)logV) vs O(VE) pour Bellman-Ford");
    }
    
    /**
     * Compte le nombre total d'arcs dans le graphe
     */
    private static int compterArcs(GrapheListe g) {
        int count = 0;
        for (String noeud : g.listeNoeuds()) {
            count += g.suivants(noeud).size();
        }
        return count;
    }
    
    /**
     * Vérifie si les résultats des deux algorithmes sont identiques
     */
    private static boolean resultatsIdentiques(Valeurs v1, Valeurs v2, List<String> noeuds) {
        for (String noeud : noeuds) {
            double val1 = v1.getValeur(noeud);
            double val2 = v2.getValeur(noeud);
            if (Math.abs(val1 - val2) > 0.001) {
                return false;
            }
        }
        return true;
    }
}