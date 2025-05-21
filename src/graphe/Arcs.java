package graphe;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un ensemble d'arcs sortant d'un nœud
 */
public class Arcs {
    // Attribut privé
    private List<Arc> arcs; // Liste des arcs
    
    /**
     * Constructeur qui initialise une liste d'arcs vide
     */
    public Arcs() {
        this.arcs = new ArrayList<>();
    }
    
    /**
     * Ajoute un arc à la liste
     * @param a arc à ajouter
     */
    public void ajouterArc(Arc a) {
        this.arcs.add(a);
    }
    
    /**
     * Retourne la liste des arcs
     * @return liste des arcs
     */
    public List<Arc> getArcs() {
        return this.arcs;
    }
}