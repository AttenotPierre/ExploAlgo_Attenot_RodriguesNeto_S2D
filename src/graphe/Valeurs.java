package graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe permettant de stocker :
 * - une valeur (distance) pour chaque nœud,
 * - un parent (prédécesseur) pour chaque nœud,
 * - la ligne utilisée pour atteindre chaque nœud (pour gérer les changements de ligne).
 */
public class Valeurs {

    private Map<String, Double> valeur;
    private Map<String, String> parent;
    private Map<String, String> ligne; // ligne utilisée pour atteindre le nœud

    /**
     * Constructeur vide initialisant les structures de données.
     */
    public Valeurs() {
        this.valeur = new TreeMap<>();
        this.parent = new TreeMap<>();
        this.ligne = new TreeMap<>();
    }

    /**
     * Associe une valeur à un nœud.
     * @param nom Nom du nœud
     * @param valeur Valeur associée (distance)
     */
    public void setValeur(String nom, double valeur) {
        this.valeur.put(nom, valeur);
    }

    /**
     * Accède à la valeur associée à un nœud.
     * @param nom Nom du nœud
     * @return Valeur stockée (distance)
     */
    public double getValeur(String nom) {
        return this.valeur.getOrDefault(nom, Double.MAX_VALUE);
    }

    /**
     * Associe un parent à un nœud.
     * @param nom Nom du nœud
     * @param parent Nom du nœud parent
     */
    public void setParent(String nom, String parent) {
        this.parent.put(nom, parent);
    }

    /**
     * Accède au parent d'un nœud.
     * @param nom Nom du nœud
     * @return Nom du parent
     */
    public String getParent(String nom) {
        return this.parent.get(nom);
    }

    /**
     * Associe une ligne utilisée pour atteindre un nœud.
     * @param nom Nom du nœud
     * @param ligne Nom ou numéro de la ligne
     */
    public void setLigne(String nom, String ligne) {
        this.ligne.put(nom, ligne);
    }

    /**
     * Accède à la ligne utilisée pour atteindre un nœud.
     * @param nom Nom du nœud
     * @return Ligne associée
     */
    public String getLigne(String nom) {
        return this.ligne.get(nom);
    }

    /**
     * Affiche toutes les valeurs, parents et lignes utilisées.
     * @return Chaîne formatée
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (String s : this.valeur.keySet()) {
            Double valeurNoeud = valeur.get(s);
            String noeudParent = parent.get(s);
            String ligneUtilisee = ligne.get(s);
            res.append(s)
               .append(" -> V:").append(valeurNoeud)
               .append(" p:").append(noeudParent)
               .append(" l:").append(ligneUtilisee)
               .append("\n");
        }
        return res.toString();
    }

    /**
     * Calcule le chemin (liste des nœuds) depuis le départ jusqu'à la destination.
     * @param destination Nœud d'arrivée
     * @return Liste ordonnée des nœuds à parcourir
     */
    public List<String> calculerChemin(String destination) {
        List<String> chemin = new ArrayList<>();
        String courant = destination;

        while (courant != null) {
            chemin.add(0, courant); // ajout en tête
            courant = this.getParent(courant);
        }

        return chemin;
    }
}
