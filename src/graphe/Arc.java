package graphe;

/**
 * Classe représentant un arc sortant d'un nœud avec numéro de ligne
 */
public class Arc {
    // Attributs privés
    private String dest; // Nœud de destination
    private double cout; // Coût (ou poids) de l'arc
    private String ligne; // Numéro de ligne (pour le métro)
    
    /**
     * Constructeur d'un arc avec ligne
     * @param dest chaîne représentant le nœud de destination
     * @param cout valeur réelle positive représentant le coût de l'arc
     * @param ligne numéro de ligne (peut contenir "bis", "ter", etc.)
     */
    public Arc(String dest, double cout, String ligne) {
        this.dest = dest;
        this.cout = cout;
        this.ligne = ligne;
    }
    
    /**
     * Constructeur d'un arc sans ligne (pour compatibilité avec le code existant)
     * @param dest chaîne représentant le nœud de destination
     * @param cout valeur réelle positive représentant le coût de l'arc
     */
    public Arc(String dest, double cout) {
        this(dest, cout, null); // Ligne non définie
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
    
    /**
     * Retourne le numéro de ligne
     * @return le numéro de ligne, null si non défini
     */
    public String getLigne() {
        return ligne;
    }
    
    /**
     * Définit le numéro de ligne
     * @param ligne numéro de ligne
     */
    public void setLigne(String ligne) {
        this.ligne = ligne;
    }
    
    /**
     * Représentation textuelle de l'arc
     * @return chaîne décrivant l'arc
     */
    @Override
    public String toString() {
        if (ligne != null) {
            return dest + "(" + cout + ", ligne " + ligne + ")";
        } else {
            return dest + "(" + cout + ")";
        }
    }
}