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
    
     /**
     * Résout le problème du plus court chemin à partir d'un sommet de départ, 
     * en utilisant l'algorithme classique de Dijkstra.
     * 
     * Paramètres :
     *  g : le graphe dans lequel effectuer la recherche
     *  depart : le sommet de départ
     * 
     * Retour :
     * un objet Valeurs contenant les distances minimales depuis le sommet de départ,
     *   ainsi que le parent de chaque sommet sur le chemin le plus court
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
    
    /**
     * Méthode utilitaire pour trouver dans la liste Q le nœud ayant la plus petite distance estimée.
     * 
     * Paramètres :
     *  Q : liste des sommets encore à traiter
     *  valeurs : objet contenant les distances actuelles pour chaque sommet
     * 
     * Retour :
     *  le sommet de Q avec la valeur minimale
     */
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

    /**
    * Variante de l'algorithme de Dijkstra prenant en compte une pénalité
    * lors des changements de ligne (modélisation de transitions avec surcoût, par exemple dans un réseau de transport).
    * 
    * Paramètres :
    *  g : le graphe contenant les arcs avec informations de ligne
    *  depart : le sommet de départ
    * 
    * Retour :
    * un objet Valeurs contenant les distances, parents et lignes optimisées avec pénalité de changement de ligne
    */
    public Valeurs resoudre2(Graphe g, String depart) {
        Valeurs valeurs = new Valeurs();
        List<String> Q = new ArrayList<>();

        List<String> noeuds = g.listeNoeuds();
        for (String v : noeuds) {
            valeurs.setValeur(v, Double.MAX_VALUE);
            valeurs.setParent(v, null);
            valeurs.setLigne(v, null);
            Q.add(v);
        }

        valeurs.setValeur(depart, 0);
        valeurs.setLigne(depart, null);

        while (!Q.isEmpty()) {
            // Trouver le nœud de valeur minimale dans Q
            String u = trouverNoeudValeurMinimale(Q, valeurs);
            Q.remove(u);

            String ligneArrivee = valeurs.getLigne(u);

            for (Arc arc : g.suivants(u)) {
                String v = arc.getDest();

                if (Q.contains(v)) {
                    double coutArc = arc.getCout();
                    String ligneArc = arc.getLigne();

                    double penalite = 0;
                    if (ligneArrivee != null && ligneArc != null && !ligneArrivee.equals(ligneArc)) {
                        penalite = 10;
                    }

                    double d = valeurs.getValeur(u) + coutArc + penalite;

                    if (d < valeurs.getValeur(v)) {
                        valeurs.setValeur(v, d);
                        valeurs.setParent(v, u);
                        valeurs.setLigne(v, ligneArc);
                    }
                }
            }
        }

        return valeurs;
    }

}