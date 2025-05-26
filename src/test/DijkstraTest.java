package test;
import graphe.GrapheListe;
import graphe.Dijkstra;
import graphe.Valeurs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests unitaires pour l'algorithme de Dijkstra
 */
public class DijkstraTest {
    
    private GrapheListe grapheTest;
    private Dijkstra dijkstra;
    
    @BeforeEach
    public void setUp() {
        // Création du graphe de test (Figure 1 du sujet)
        grapheTest = new GrapheListe();
        grapheTest.ajouterArc("A", "B", 12);
        grapheTest.ajouterArc("A", "D", 87);
        grapheTest.ajouterArc("B", "E", 11);
        grapheTest.ajouterArc("C", "A", 19);
        grapheTest.ajouterArc("D", "B", 23);
        grapheTest.ajouterArc("D", "C", 10);
        grapheTest.ajouterArc("E", "D", 43);
        
        dijkstra = new Dijkstra();
    }
    
    @Test
    public void testDistancesDepuisA() {
        Valeurs valeurs = dijkstra.resoudre(grapheTest, "A");
        
        // Vérification des distances calculées
        assertEquals(0.0, valeurs.getValeur("A"), 0.001, "Distance A->A incorrecte");
        assertEquals(12.0, valeurs.getValeur("B"), 0.001, "Distance A->B incorrecte");
        assertEquals(76.0, valeurs.getValeur("C"), 0.001, "Distance A->C incorrecte");
        assertEquals(66.0, valeurs.getValeur("D"), 0.001, "Distance A->D incorrecte");
        assertEquals(23.0, valeurs.getValeur("E"), 0.001, "Distance A->E incorrecte");
    }
    
    @Test
    public void testParentsDepuisA() {
        Valeurs valeurs = dijkstra.resoudre(grapheTest, "A");
        
        // Vérification des parents
        assertNull(valeurs.getParent("A"), "Parent de A devrait être null");
        assertEquals("A", valeurs.getParent("B"), "Parent de B incorrect");
        assertEquals("D", valeurs.getParent("C"), "Parent de C incorrect");
        assertEquals("E", valeurs.getParent("D"), "Parent de D incorrect");
        assertEquals("B", valeurs.getParent("E"), "Parent de E incorrect");
    }
    
    @Test
    public void testCheminVersC() {
        Valeurs valeurs = dijkstra.resoudre(grapheTest, "A");
        List<String> chemin = valeurs.calculerChemin("C");
        
        // Le chemin optimal de A vers C devrait être [A, B, E, D, C]
        List<String> cheminAttendu = List.of("A", "B", "E", "D", "C");
        assertEquals(cheminAttendu, chemin, "Chemin A->C incorrect");
    }
    
    @Test
    public void testAutreNoeudDepart() {
        Valeurs valeurs = dijkstra.resoudre(grapheTest, "B");
        
        // Test depuis B
        assertEquals(0.0, valeurs.getValeur("B"), 0.001, "Distance B->B incorrecte");
        assertEquals(11.0, valeurs.getValeur("E"), 0.001, "Distance B->E incorrecte");
        assertEquals(54.0, valeurs.getValeur("D"), 0.001, "Distance B->D incorrecte");
    }
    
    @Test
    public void testGrapheSimple() {
        // Test sur un graphe plus simple
        GrapheListe grapheSimple = new GrapheListe();
        grapheSimple.ajouterArc("X", "Y", 5);
        grapheSimple.ajouterArc("Y", "Z", 3);
        grapheSimple.ajouterArc("X", "Z", 10);
        
        Valeurs valeurs = dijkstra.resoudre(grapheSimple, "X");
        
        assertEquals(0.0, valeurs.getValeur("X"), 0.001);
        assertEquals(5.0, valeurs.getValeur("Y"), 0.001);
        assertEquals(8.0, valeurs.getValeur("Z"), 0.001); // Via Y : 5+3=8 < 10
        assertEquals("Y", valeurs.getParent("Z"));
    }
}