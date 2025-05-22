package graphe;


public class MainBellmanFord {
    public static void main(String[] args) {
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12);
        g.ajouterArc("A", "D", 87);
        g.ajouterArc("B", "E", 11);
        g.ajouterArc("C", "A", 19);
        g.ajouterArc("D", "B", 23);
        g.ajouterArc("D", "C", 10);
        g.ajouterArc("E", "D", 43);
        
        System.out.println("Graphe :");
        System.out.println(g);
        
        BellmanFord bf = new BellmanFord();
        Valeurs valeurs = bf.resoudre(g, "A");
        
        System.out.println("\nRésultats de l'algorithme du point fixe à partir de A :");
        System.out.println(valeurs);
        
        System.out.println("\nVérification des distances :");
        System.out.println("A -> A : " + valeurs.getValeur("A") + " (attendu: 0)");
        System.out.println("A -> B : " + valeurs.getValeur("B") + " (attendu: 12)");
        System.out.println("A -> C : " + valeurs.getValeur("C") + " (attendu: 76)");
        System.out.println("A -> D : " + valeurs.getValeur("D") + " (attendu: 66)");
        System.out.println("A -> E : " + valeurs.getValeur("E") + " (attendu: 23)");
    }
}
