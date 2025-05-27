package graphe;

import java.util.List;

public class MainMetro {
    public static void main(String[] args) {
        Graphe g = LireReseau.lire("src/graphe/Grp/plan-reseau.txt");

        String[][] trajets = {
            {"Châtelet", "Nation"},
            {"Porte de Clignancourt", "Gare de Lyon"},
            {"Place d'Italie", "République"},
            {"Odéon", "Gare du Nord"},
            {"Montparnasse Bienvenue", "Bastille"}
        };

        System.out.println("\n✅ Vérification des noms de stations chargées :");
        for (String s : g.listeNoeuds()) {
            if (s.contains("Châtelet") || s.contains("Nation") || s.contains("Gare de Lyon")) {
                System.out.println("> " + s);
            }
        }

        System.out.printf("%-30s | %-30s | %-40s | %-20s | %-20s\n",
                          "Départ", "Arrivée", "Chemin (Bellman)", "Temps Bellman (ms)", "Temps Dijkstra (ms)");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");

        for (String[] trajet : trajets) {
            String depart = trajet[0];
            String arrivee = trajet[1];

            // Bellman-Ford
            BellmanFord bf = new BellmanFord();
            long startBF = System.nanoTime();
            Valeurs resBF = bf.resoudre(g, depart);
            long endBF = System.nanoTime();
            double tempsBF = (endBF - startBF) / 1e6;
            List<String> cheminBF = resBF.calculerChemin(arrivee);

            // Dijkstra
            Dijkstra dj = new Dijkstra();
            long startD = System.nanoTime();
            Valeurs resD = dj.resoudre(g, depart);
            long endD = System.nanoTime();
            double tempsD = (endD - startD) / 1e6;
            List<String> cheminD = resD.calculerChemin(arrivee);
            
            System.out.printf("%-30s | %-30s | %-40s | %-20.2f | %-20.2f\n",
                              depart, arrivee, cheminBF, tempsBF, tempsD);
        }

        
        System.out.println("\n--- Avec pénalité de changement de ligne (Bellman-Ford v2) ---");
        System.out.printf("%-30s | %-30s | %-40s | %-20s\n",
                          "Départ", "Arrivée", "Chemin (BF2)", "Temps Bellman2 (ms)");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (String[] trajet : trajets) {
            String depart = trajet[0];
            String arrivee = trajet[1];

            BellmanFord bf = new BellmanFord();
            long start = System.nanoTime();
            Valeurs res = bf.resoudre2(g, depart);
            long end = System.nanoTime();
            double temps = (end - start) / 1e6;

            List<String> chemin = res.calculerChemin(arrivee);
            System.out.printf("%-30s | %-30s | %-40s | %-20.2f\n",
                              depart, arrivee, chemin, temps);
        }

        
        System.out.println("\n--- Avec pénalité de changement de ligne (Dijkstra v2) ---");
        System.out.printf("%-30s | %-30s | %-40s | %-20s\n",
                          "Départ", "Arrivée", "Chemin (Dijkstra2)", "Temps Dijkstra2 (ms)");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (String[] trajet : trajets) {
            String depart = trajet[0];
            String arrivee = trajet[1];

            Dijkstra dj = new Dijkstra();
            long start = System.nanoTime();
            Valeurs res = dj.resoudre2(g, depart);
            long end = System.nanoTime();
            double temps = (end - start) / 1e6;

            List<String> chemin = res.calculerChemin(arrivee);
            System.out.printf("%-30s | %-30s | %-40s | %-20.2f\n",
                              depart, arrivee, chemin, temps);
        }
    }
}
