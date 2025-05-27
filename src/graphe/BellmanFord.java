    package graphe;
    import java.util.List;
    /**
     * 
     * Algorithme pointFixe(Graphe g, Noeud depart)
     *
     *  Lexique:
     *      g : Graphe                 // Le graphe sur lequel on travaille
     *      depart : Noeud             // Nœud de départ pour calculer les plus courts chemins
     *      L : Table[Noeud -> Réel]   // Table associant à chaque nœud sa distance au nœud de départ
     *      parent : Table[Noeud -> Nœud]  // Table associant à chaque nœud son parent dans le plus court chemin
     *      modifie : Booléen          // Indicateur de modification lors d'une itération
     *      noeudsCourants : Liste[Noeud]  // Liste des nœuds du graphe
     *
     *  Début
     *      // Initialisation
     *      noeudsCourants ← g.listeNoeuds()
     *      Pour chaque noeud dans noeudsCourants faire
     *          L(noeud) ← +∞
     *          parent(noeud) ← null
     *      Fin Pour
     *      
     *      // Le nœud de départ a une distance de 0 à lui-même
     *      L(depart) ← 0
     *      
     *      // Boucle principale pour atteindre le point fixe
     *      modifie ← vrai
     *      Tant que modifie faire
     *          modifie ← faux
     *          Pour chaque noeud dans noeudsCourants faire
     *              Pour chaque arc dans g.suivants(noeud) faire
     *                  // Calcul de la nouvelle distance potentielle
     *                  nouvDistance ← L(noeud) + arc.cout
     *                  
     *                  // Si la nouvelle distance est meilleure, on met à jour
     *                  Si nouvDistance < L(arc.dest) alors
     *                      L(arc.dest) ← nouvDistance
     *                      parent(arc.dest) ← noeud
     *                      modifie ← vrai
     *                  Fin Si
     *              Fin Pour
     *          Fin Pour
     *      Fin Tant que
     *      
     *      // Retourner les valeurs calculées (distances et parents)
     *      Retourner (L, parent)
     *  Fin
     */



    /**
     * Classe implémentant l'algorithme de Bellman-Ford pour trouver le plus court chemin
     */
    public class BellmanFord {
        
        /**
         * Résout le problème du plus court chemin à partir d'un nœud de départ
         * @param g graphe sur lequel appliquer l'algorithme
         * @param depart nœud de départ
         * @return objet Valeurs contenant les distances et les parents de chaque nœud
         */
        public Valeurs resoudre(Graphe g, String depart) {
            // Initialisation de l'objet Valeurs pour stocker les résultats
            Valeurs valeurs = new Valeurs();
            
            // Initialisation des distances et parents
            List<String> noeuds = g.listeNoeuds();
            for (String noeud : noeuds) {
                valeurs.setValeur(noeud, Double.MAX_VALUE);
                valeurs.setParent(noeud, null);
            }
            
            // La distance du nœud de départ à lui-même est 0
            valeurs.setValeur(depart, 0);
            
            // Boucle principale pour atteindre le point fixe
            boolean modifie = true;
            while (modifie) {
                modifie = false;
                
                // Pour chaque nœud du graphe
                for (String noeud : noeuds) {
                    // On ne traite le nœud que si sa distance n'est pas infinie
                    double distanceNoeud = valeurs.getValeur(noeud);
                    if (distanceNoeud != Double.MAX_VALUE) {
                        
                        // Pour chaque arc sortant du nœud
                        for (Arc arc : g.suivants(noeud)) {
                            String destination = arc.getDest();
                            double cout = arc.getCout();
                            
                            // Calcul de la nouvelle distance potentielle
                            double nouvDistance = distanceNoeud + cout;
                            
                            // Si la nouvelle distance est meilleure
                            if (nouvDistance < valeurs.getValeur(destination)) {
                                // Mise à jour de la distance et du parent
                                valeurs.setValeur(destination, nouvDistance);
                                valeurs.setParent(destination, noeud);
                                modifie = true;
                            }
                        }
                    }
                }
            }
            
            return valeurs;
        }

        /**
         * Variante de l'algorithme de Bellman-Ford qui prend en compte une pénalité
         * lors des changements de ligne (utile pour les graphes modélisant un réseau de transport).
         *
         * @param g graphe pondéré avec informations de lignes
         * @param depart nom du nœud de départ
         * @return objet contenant les distances, parents et lignes associées à chaque nœud
         */
        public Valeurs resoudre2(Graphe g, String depart) {
            Valeurs valeurs = new Valeurs();

            List<String> noeuds = g.listeNoeuds();
            for (String noeud : noeuds) {
                valeurs.setValeur(noeud, Double.MAX_VALUE);
                valeurs.setParent(noeud, null);
                valeurs.setLigne(noeud, null); // init ligne
            }

            valeurs.setValeur(depart, 0);
            valeurs.setLigne(depart, null);

            boolean modifie = true;

            while (modifie) {
                modifie = false;

                for (String noeud : noeuds) {
                    double distanceNoeud = valeurs.getValeur(noeud);
                    if (distanceNoeud == Double.MAX_VALUE) continue;

                    String ligneActuelle = valeurs.getLigne(noeud);

                    for (Arc arc : g.suivants(noeud)) {
                        String dest = arc.getDest();
                        double coutArc = arc.getCout();
                        String ligneArc = arc.getLigne();

                        double penalite = (ligneActuelle != null && ligneArc != null && !ligneArc.equals(ligneActuelle)) ? 10 : 0;

                        double nouvelleDistance = distanceNoeud + coutArc + penalite;

                        if (nouvelleDistance < valeurs.getValeur(dest)) {
                            valeurs.setValeur(dest, nouvelleDistance);
                            valeurs.setParent(dest, noeud);
                            valeurs.setLigne(dest, ligneArc);
                            modifie = true;
                        }
                    }
                }
            }

            return valeurs;
        }


    }