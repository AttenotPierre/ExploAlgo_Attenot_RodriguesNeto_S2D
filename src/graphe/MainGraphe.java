package src.graphe;
public class MainGraphe {
    public static void main(String[] args) {
        // Création du graphe de la Figure 1
        GrapheListe g = new GrapheListe();

        // Ajout des arcs comme dans la figure
        g.ajouterArc("A", "B", 12);
        g.ajouterArc("A", "D", 87);
        g.ajouterArc("B", "E", 11);
        g.ajouterArc("C", "A", 19);
        g.ajouterArc("D", "B", 23);
        g.ajouterArc("D", "C", 10);
        g.ajouterArc("E", "D", 43);

        // Affichage de la liste des nœuds
        System.out.println("Liste des noeuds : " + g.listeNoeuds());

        // Pour chaque nœud, affichage de ses arcs sortants
        for (String noeud : g.listeNoeuds()) {
            System.out.print(noeud + " -> ");
            for (Arc arc : g.suivants(noeud)) {
                System.out.print(arc.getDest() + "(" + arc.getCout() + ") ");
            }
            System.out.println();
        }
    }
}