package graphe;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe implémentant l'algorithme de Dijkstra pour trouver le plus court chemin
 */
public class Dijkstra {
    
    /**
     * Algorithme de Dijkstra (version commentée)
     * 
     * Entrées :
     * G un graphe orienté avec une pondération positive des arcs (coût ou poids)
     * A un sommet (départ) de G
     * 
     * Début
     * Q <- {} // utilisation d'une liste de noeuds à traiter
     * Pour chaque sommet v de G faire
     *     v.valeur <- Infini
     *     v.parent <- Indéfini
     *     Q <- Q U {v} // ajouter le sommet v à la liste Q
     * Fin Pour
     * A.valeur <- 0
     * Tant que Q est un ensemble non vide faire
     *     u <- un sommet de Q telle que u.valeur est minimal
     *     // enlever le sommet u de la liste Q
     *     Q <- Q \ {u}
     *     Pour chaque sommet v de Q tel que l'arc (u,v) existe faire
     *         d <- u.valeur + poids(u,v)
     *         Si d < v.valeur
     *             // le chemin est plus interessant
     *         Alors v.valeur <- d
     *             v.parent <- u
     *         Fin Si
     *     Fin Pour
     * Fin Tant que
     * Fin
     */
    
    public Valeurs resoudre(Graphe g, String depart) {
        // Initialisation de l'objet Valeurs pour stocker les résultats
        Valeurs valeurs = new Valeurs();
        
        // Q : liste des nœuds à traiter
        List<String> Q = new ArrayList<>();
        
        // Pour chaque sommet v de G faire
        List<String> noeuds = g.listeNoeuds();
        for (String v : noeuds) {
            // v.valeur <- Infini
            valeurs.setValeur(v, Double.MAX_VALUE);
            // v.parent <- Indéfini
            valeurs.setParent(v, null);
            // Q <- Q U {v} : ajouter le sommet v à la liste Q
            Q.add(v);
        }
        
        // A.valeur <- 0
        valeurs.setValeur(depart, 0);
        
        // Tant que Q est un ensemble non vide faire
        while (!Q.isEmpty()) {
            // u <- un sommet de Q telle que u.valeur est minimal
            String u = trouverNoeudValeurMinimale(Q, valeurs);
            
            // enlever le sommet u de la liste Q
            Q.remove(u);
            
            // Pour chaque sommet v de Q tel que l'arc (u,v) existe faire
            for (Arc arc : g.suivants(u)) {
                String v = arc.getDest();
                
                // Vérifier que v est encore dans Q
                if (Q.contains(v)) {
                    // d <- u.valeur + poids(u,v)
                    double d = valeurs.getValeur(u) + arc.getCout();
                    
                    // Si d < v.valeur
                    if (d < valeurs.getValeur(v)) {
                        // le chemin est plus interessant
                        // v.valeur <- d
                        valeurs.setValeur(v, d);
                        // v.parent <- u
                        valeurs.setParent(v, u);
                    }
                }
            }
        }
        
        return valeurs;
    }
    
    private String trouverNoeudValeurMinimale(List<String> Q, Valeurs valeurs) {
        String noeudMin = Q.get(0);
        double valeurMin = valeurs.getValeur(noeudMin);
        
        for (String noeud : Q) {
            double valeur = valeurs.getValeur(noeud);
            if (valeur < valeurMin) {
                valeurMin = valeur;
                noeudMin = noeud;
            }
        }
        
        return noeudMin;
    }
}