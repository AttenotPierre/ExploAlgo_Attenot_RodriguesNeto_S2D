package src.graphe;
import java.util.List;

/**
 * Interface définissant le comportement d'un graphe
 */
public interface Graphe {
    /**
     * Retourne la liste de tous les nœuds du graphe
     * @return liste des nœuds (sous forme de chaînes de caractères)
     */
    public List<String> listeNoeuds();
    
    /**
     * Retourne la liste des arcs sortant d'un nœud donné
     * @param n nœud dont on veut les arcs sortants
     * @return liste des arcs sortant du nœud n
     */
    public List<Arc> suivants(String n);
}