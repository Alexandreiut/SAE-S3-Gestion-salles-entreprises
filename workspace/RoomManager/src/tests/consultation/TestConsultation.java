package tests.consultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lanceur.RoomManager;
import modeles.consultation.Consultation;
import modeles.items.Salle;
import modeles.stockage.Stockage;
import tests.items.JeuDonne;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTest {

    private Consultation consultation;
    private JeuDonne jeuDonne;
    private Stockage stockage;

    @BeforeEach
    void setUp() {
        consultation = new Consultation();
        jeuDonne = new JeuDonne();

        // Initialisation de données réelles
        stockage = new Stockage(null,null,null,null);
        stockage.setListeActivite(jeuDonne.setUpActivites());
        stockage.setListeEmploye(jeuDonne.setUpEmployes());
        stockage.setListeReservation(jeuDonne.setUpReservations());
        stockage.setListeSalle(jeuDonne.setUpSalles());

        // Simulation de l'intégration avec RoomManager
        RoomManager.stockage = stockage;
    }

    @Test
    void testFetchDonneesBrutes_withData() {
        HashMap<String, ArrayList<? extends Object>> donnees = consultation.fetchDonneesBrutes();

        assertNotNull(donnees);
        assertEquals(4, donnees.size());

        assertTrue(donnees.containsKey("Employés"));
        assertTrue(donnees.containsKey("Salles"));
        assertTrue(donnees.containsKey("Activitées"));
        assertTrue(donnees.containsKey("Réservations"));

        assertEquals(jeuDonne.setUpEmployes().size(), donnees.get("Employés").size());
        assertEquals(jeuDonne.setUpSalles().size(), donnees.get("Salles").size());
        assertEquals(jeuDonne.setUpActivites().size(), donnees.get("Activitées").size());
        assertEquals(jeuDonne.setUpReservations().size(), donnees.get("Réservations").size());
    }

    @Test
    void testFetchDonneesBrutes_withNoData() {
        RoomManager.stockage = null; // Simule un stockage null
        HashMap<String, ArrayList<? extends Object>> donnees = consultation.fetchDonneesBrutes();

        assertNotNull(donnees);
        assertEquals(1, donnees.size());
        assertTrue(donnees.containsKey("pas de données"));
        assertEquals("Aucune données enregistré", donnees.get("pas de données").get(0));
    }

    @Test
    void testGetSalleDisponible() {
        LocalDate dateDebut = LocalDate.of(2024, 10, 7);
        LocalDate dateFin = LocalDate.of(2024, 10, 7);
        String heureDebut = "08h00";
        String heureFin = "18h00";
        String chaineSalle = "salle";

        ArrayList<Object> sallesDisponibles = consultation.getSalleDisponible(dateDebut, dateFin, heureDebut, heureFin, chaineSalle);

        assertNotNull(sallesDisponibles);
        assertTrue(sallesDisponibles.size() > 0);

        // Vérifie que les salles disponibles ne contiennent pas celles déjà réservées
        for (Object salle : sallesDisponibles) {
            assertFalse(((Salle) salle).getNom().contains("salle Picasso")); // Exemple spécifique pour test
        }
    }

    @Test
    void testGetSalleFound() {
        LocalDate dateDebut = LocalDate.of(2024, 10, 7);
        LocalDate dateFin = LocalDate.of(2024, 10, 7);
        String heureDebut = "08h00";
        String heureFin = "18h00";
        String chaineSalle = "salle";
        String chaineEmploye = "Dupont";
        String chaineActivite = "formation";
        String itemRecherche = "Employés";

        ArrayList<ArrayList<Object>> resultats = consultation.getItemCritere(
                dateDebut, dateFin, heureDebut, heureFin, chaineSalle, chaineEmploye, chaineActivite, itemRecherche
        );

        assertNotNull(resultats);
        assertEquals(2, resultats.size());

        ArrayList<Object> salles = resultats.get(0);
        ArrayList<Object> heuresCritere = resultats.get(1);

        assertEquals(salles.size(), heuresCritere.size());
    }

    @Test
    void testOrderList() {
        ArrayList<Object> items = new ArrayList<>();
        items.add("Salle A");
        items.add("Salle B");
        items.add("Salle C");

        ArrayList<Object> heures = new ArrayList<>();
        heures.add(5.0);
        heures.add(2.0);
        heures.add(3.0);

        ArrayList<ArrayList<Object>> listeAOrdonnee = new ArrayList<>();
        listeAOrdonnee.add(items);
        listeAOrdonnee.add(heures);

        ArrayList<ArrayList<Object>> result = consultation.orderList(listeAOrdonnee, true);

        assertNotNull(result);
        assertEquals("Salle B", result.get(0).get(0));
        assertEquals("Salle C", result.get(0).get(1));
        assertEquals("Salle A", result.get(0).get(2));
    }
}
