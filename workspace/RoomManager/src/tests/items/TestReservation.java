/*
 * TestReservation.java				24/10/2024
 * BUT Info2, 2024/2025, pas de copyright
 */

package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modeles.items.Reservation;

/**
 * Classe de tests de la classe Reservation.java
 */
public class TestReservation extends JeuDonne {

    private ArrayList<Reservation> reservations;

    @BeforeEach
    public void setUp() {
        // Création des objets Reservation à partir des données du CSV
        reservations = setUpReservations();
    }

    @Test
    public void testGetters() {

        // Valeurs attendues (tirées du CSV)
        String[] identifiantsAttendus = {"R000001", "R000002", "R000003", "R000004", "R000005", "R000006", "R000007", "R000008", "R000009", "R000010", 
                                         "R000011", "R000012", "R000013", "R000014", "R000015", "R000016", "R000017", "R000018"};
        String[] datesAttendues = {"7/10/2024", "7/10/2024", "7/10/2024", "8/10/2024", "8/10/2024", "9/10/2024", "7/10/2024", "10/10/2024", "11/10/2024", 
                                   "7/10/2024", "10/10/2024", "15/10/2024", "11/10/2024", "11/10/2024", "16/10/2024", "17/10/2024", "18/10/2024", "18/10/2024"};
        String[] heuresDebutAttendues = {"17h00", "15h00", "10h00", "09h00", "17h00", "09h00", "15h00", "08h00", "08h00", "10h00", 
                                         "09h00", "09h00", "08h00", "08h00", "10h00", "14h00", "08h00", "13h00"};
        String[] heuresFinAttendues = {"19h00", "18h00", "11h00", "11h00", "19h00", "12h00", "18h00", "18h00", "18h00", "12h00", 
                                       "12h00", "10h00", "17h00", "17h00", "11h00", "15h30", "13h00", "19h00"};
        String[] objetsReservationAttendus = {"club gym", "réunion avec client", "Préparation réunion client", "", "club gym", "tests candidats", 
                                              "présentation maquette", "Bureautique", "Bureautique", "accueil nouveau membre", 
                                              "tests candidats", "point avec stagiaire", "mise à jour logiciels", "mise à jour logiciels", 
                                              "visite tuteur IUT", "validation maquette", "Mairie", "Département"};

        // Test des getters
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);

            assertEquals(identifiantsAttendus[i], reservation.getIdentifiant());
            assertEquals(datesAttendues[i], reservation.getDate());
            assertEquals(heuresDebutAttendues[i], reservation.getHeureDebut());
            assertEquals(heuresFinAttendues[i], reservation.getHeureFin());
            assertEquals(objetsReservationAttendus[i], reservation.getObjetReservation());
        }
    }
}
