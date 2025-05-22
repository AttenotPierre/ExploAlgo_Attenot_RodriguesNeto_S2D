package src.graphe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe fournie, permet de stocker des valeurs associées au noeud et 
 * des parents.
 * - un noeud est représenté par un String (son nom)
 * - on accède avec des get (getValeur et getParent)
 * - on modifie avec des set (setValeur et setParent)
 */
public class Valeurs {

    /**
     * Attributs pour stocker les informations 
     * (type Table = Dictionnaire)
     */
    Map<String, Double> valeur;
    Map<String, String> parent;

    /**
     * Constructeur vide (initialise la possibilité de stocker 
     * des valeurs)
     */
    public Valeurs() {
        this.valeur = new TreeMap<>();
        this.parent = new TreeMap<>();
    }

    /**
     * Associe une valeur à un nom de noeud (L(X))
     *
     * @param nom    le nom du noeud
     * @param valeur la valeur associée
     */
    public void setValeur(String nom, double valeur) {
        this.valeur.put(nom, valeur);
    }

    /**
     * Associe un parent à un nom de noeud (parent(X))
     *
     * @param nom    nom du noeud
     * @param parent nom du noeud parent associé
     */
    public void setParent(String nom, String parent) {
        this.parent.put(nom, parent);
    }

    /**
     * Accède au parent stocké associé au noeud passé en paramètre
     *
     * @param nom nom du noeud
     * @return le nom du noeud parent
     */
    public String getParent(String nom) {
        return this.parent.get(nom);
    }

    /**
     * Accède à la valeur associée au noeud passé en paramètre
     *
     * @param nom nom du noeud
     * @return la valeur stockée
     */
    public double getValeur(String nom) {
        return this.valeur.get(nom);
    }

    /**
     * Retourne une chaîne qui affiche le contenu :
     * - par noeud stocké
     * - pour chaque noeud, affiche la valeur puis le parent
     *
     * @return descriptif des noeuds
     */
    public String toString() {
        String res = "";
        for (String s : this.valeur.keySet()) {
            Double valeurNoeud = valeur.get(s);
            String noeudParent = parent.get(s);
            res += s + " ->  V:" + valeurNoeud + " p:" + noeudParent + "\n";
        }
        return res;
    }

    public List<String> calculerChemin(String destination) {
        List<String> chemin = new ArrayList<>();
        String courant = destination;

        // Remonter jusqu'à la racine (noeud sans parent)
        while (courant != null) {
            chemin.add(0, courant); // ajoute en début de liste
            courant = this.getParent(courant);
        }

        return chemin;
    }
}

