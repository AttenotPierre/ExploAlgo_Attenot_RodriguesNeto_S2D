package graphe;

/**
 * Classe représentant un arc sortant d'un nœud
 */
public class Arc {
    // Attributs privés
    private String dest; // Nœud de destination
    private double cout; // Coût (ou poids) de l'arc
    
    /**
     * Constructeur d'un arc
     * @param dest chaîne représentant le nœud de destination
     * @param cout valeur réelle positive représentant le coût de l'arc
     */
    public Arc(String dest, double cout) {
        this.dest = dest;
        this.cout = cout;
    }
    
    /**
     * Retourne le nœud de destination
     * @return le nœud de destination
     */
    public String getDest() {
        return dest;
    }
    
    /**
     * Retourne le coût de l'arc
     * @return le coût de l'arc
     */
    public double getCout() {
        return cout;
    }
}