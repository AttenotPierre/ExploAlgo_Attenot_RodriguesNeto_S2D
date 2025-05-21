package graphe;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface Graphe utilisant des listes d'adjacence
 */
public class GrapheListe implements Graphe {
    // Attributs privés
    private ArrayList<String> noeuds; // Liste des nœuds du graphe
    private ArrayList<Arcs> adjacence; // Liste des arcs sortant de chaque nœud
    
    /**
     * Constructeur initialisant un graphe vide
     */
    public GrapheListe() {
        this.noeuds = new ArrayList<>();
        this.adjacence = new ArrayList<>();
    }
    
    /**
     * Retourne l'indice d'un nœud dans la liste des nœuds
     * @param n nœud dont on veut l'indice
     * @return indice du nœud, -1 si le nœud n'existe pas
     */
    private int getIndice(String n) {
        return this.noeuds.indexOf(n);
    }
    
    /**
     * Ajoute un nœud au graphe s'il n'existe pas déjà
     * @param n nœud à ajouter
     * @return indice du nœud dans la liste
     */
    private int ajouterNoeud(String n) {
        int indice = getIndice(n);
        if (indice == -1) {
            // Le nœud n'existe pas encore, on l'ajoute
            this.noeuds.add(n);
            this.adjacence.add(new Arcs());
            return this.noeuds.size() - 1;
        }
        return indice;
    }
    
    /**
     * Ajoute un arc entre deux nœuds du graphe
     * @param depart nœud de départ
     * @param destination nœud d'arrivée
     * @param cout coût de l'arc
     */
    public void ajouterArc(String depart, String destination, double cout) {
        // On s'assure que les deux nœuds existent
        int indiceDepart = ajouterNoeud(depart);
        ajouterNoeud(destination);
        
        // On ajoute l'arc à la liste d'adjacence du nœud de départ
        Arc nouveauArc = new Arc(destination, cout);
        this.adjacence.get(indiceDepart).ajouterArc(nouveauArc);
    }
    
    @Override
    public List<String> listeNoeuds() {
        return new ArrayList<>(this.noeuds);
    }
    
    @Override
    public List<Arc> suivants(String n) {
        int indice = getIndice(n);
        if (indice != -1) {
            return this.adjacence.get(indice).getArcs();
        }
        // Si le nœud n'existe pas, on retourne une liste vide
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        // Pour chaque nœud du graphe
        for (int i = 0; i < this.noeuds.size(); i++) {
            String noeud = this.noeuds.get(i);
            sb.append(noeud).append(" -> ");
        
            // Pour chaque arc sortant de ce nœud
            List<Arc> arcsNoeud = this.adjacence.get(i).getArcs();
            for (Arc arc : arcsNoeud) {
                sb.append(arc.getDest()).append("(").append(arc.getCout()).append(") ");
            }
        
            sb.append("\n");
        }
    
        return sb.toString();
    }

    
}