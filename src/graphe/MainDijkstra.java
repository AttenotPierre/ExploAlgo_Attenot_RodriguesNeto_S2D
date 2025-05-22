package src.graphe;
import java.util.List;

/**
 * Programme principal pour tester l'algorithme de Dijkstra
 */
public class MainDijkstra {
    
    public static void main(String[] args) {
        // Utilisation du graphe par défaut (Figure 1 du sujet)
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12);
        g.ajouterArc("A", "D", 87);
        g.ajouterArc("B", "E", 11);
        g.ajouterArc("C", "A", 19);
        g.ajouterArc("D", "B", 23);
        g.ajouterArc("D", "C", 10);
        g.ajouterArc("E", "D", 43);
        
        System.out.println("=== Graphe utilisé ===");
        System.out.println(g);
        
        // Création de l'algorithme de Dijkstra
        Dijkstra dijkstra = new Dijkstra();
        
        // Test depuis le nœud A
        System.out.println("=== Calcul des plus courts chemins depuis A avec Dijkstra ===");
        Valeurs valeursA = dijkstra.resoudre(g, "A");
        System.out.println(valeursA);
        
        // Affichage des chemins pour des nœuds donnés
        String[] destinations = {"B", "C", "D", "E"};
        
        System.out.println("=== Chemins les plus courts depuis A ===");
        for (String dest : destinations) {
            List<String> chemin = valeursA.calculerChemin(dest);
            double distance = valeursA.getValeur(dest);
            System.out.println("A -> " + dest + " : " + chemin + 
                             " (distance: " + distance + ")");
        }
        
        System.out.println("\n=== Comparaison avec d'autres points de départ ===");
        
        // Test depuis le nœud B
        System.out.println("\nDepuis B :");
        Valeurs valeursB = dijkstra.resoudre(g, "B");
        for (String dest : new String[]{"A", "C", "D", "E"}) {
            List<String> chemin = valeursB.calculerChemin(dest);
            double distance = valeursB.getValeur(dest);
            if (distance != Double.MAX_VALUE) {
                System.out.println("B -> " + dest + " : " + chemin + 
                                 " (distance: " + distance + ")");
            } else {
                System.out.println("B -> " + dest + " : inaccessible");
            }
        }
        
        // Test depuis le nœud C
        System.out.println("\nDepuis C :");
        Valeurs valeursC = dijkstra.resoudre(g, "C");
        for (String dest : new String[]{"A", "B", "D", "E"}) {
            List<String> chemin = valeursC.calculerChemin(dest);
            double distance = valeursC.getValeur(dest);
            if (distance != Double.MAX_VALUE) {
                System.out.println("C -> " + dest + " : " + chemin + 
                                 " (distance: " + distance + ")");
            } else {
                System.out.println("C -> " + dest + " : inaccessible");
            }
        }
        
        System.out.println("\n=== Vérification du chemin optimal A->C ===");
        List<String> cheminOptimal = valeursA.calculerChemin("C");
        System.out.println("Chemin: " + cheminOptimal);
        System.out.println("Distance totale: " + valeursA.getValeur("C"));
        System.out.println("Détail: A->B(12) + B->E(11) + E->D(43) + D->C(10) = " + 
                          (12 + 11 + 43 + 10) + " = 76");
    }
}