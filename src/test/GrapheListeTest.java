package src.test;
import src.graphe.Arc;
import src.graphe.GrapheListe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GrapheListeTest {

    @Test
    public void testConstructionGraphe() {
        // Création du graphe de test
        GrapheListe g = new GrapheListe();
        g.ajouterArc("A", "B", 12);
        g.ajouterArc("A", "D", 87);
        g.ajouterArc("B", "E", 11);
        g.ajouterArc("C", "A", 19);
        g.ajouterArc("D", "B", 23);
        g.ajouterArc("D", "C", 10);
        g.ajouterArc("E", "D", 43);

        // Vérification des nœuds
        List<String> noeuds = g.listeNoeuds();
        assertEquals(5, noeuds.size());
        assertTrue(noeuds.contains("A"));
        assertTrue(noeuds.contains("B"));
        assertTrue(noeuds.contains("C"));
        assertTrue(noeuds.contains("D"));
        assertTrue(noeuds.contains("E"));

        // Vérification des arcs sortants de A
        List<Arc> arcsA = g.suivants("A");
        assertEquals(2, arcsA.size());
        boolean arcABTrouve = false;
        boolean arcADTrouve = false;

        for (Arc arc : arcsA) {
            if (arc.getDest().equals("B") && arc.getCout() == 12) {
                arcABTrouve = true;
            }
            if (arc.getDest().equals("D") && arc.getCout() == 87) {
                arcADTrouve = true;
            }
        }

        assertTrue(arcABTrouve, "Arc A->B non trouvé ou incorrect");
        assertTrue(arcADTrouve, "Arc A->D non trouvé ou incorrect");

        // Vérification des arcs sortants de D
        List<Arc> arcsD = g.suivants("D");
        assertEquals(2, arcsD.size());
        boolean arcDBTrouve = false;
        boolean arcDCTrouve = false;

        for (Arc arc : arcsD) {
            if (arc.getDest().equals("B") && arc.getCout() == 23) {
                arcDBTrouve = true;
            }
            if (arc.getDest().equals("C") && arc.getCout() == 10) {
                arcDCTrouve = true;
            }
        }

        assertTrue(arcDBTrouve, "Arc D->B non trouvé ou incorrect");
        assertTrue(arcDCTrouve, "Arc D->C non trouvé ou incorrect");
    }
}