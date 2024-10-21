package tests.items;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modeles.items.Reservation;

public class TestReservation {

    private ArrayList<Reservation> reservations;

    @BeforeEach
    public void setUp() {
        // Création des objets Reservation à partir des données du CSV
        reservations = new ArrayList<>();

        // Réservations basées sur le CSV
        reservations.add(new Reservation("R000001", "7/10/2024", "17h00", "19h00", "club gym", "Legendre", "Noémie", 0600000000, "reunion", "E000001", "prêt", 1));
        reservations.add(new Reservation("R000002", "7/10/2024", "15h00", "18h00", "réunion avec client", "", "", 0, "réunion", "E000001", "réunion", 4));
        reservations.add(new Reservation("R000003", "7/10/2024", "10h00", "11h00", "Préparation réunion client", "", "", 0, "réunion", "E000005", "réunion", 4));
        reservations.add(new Reservation("R000004", "8/10/2024", "09h00", "11h00", "", "", "", 0, "réunion", "E000002", "réunion", 4));
        reservations.add(new Reservation("R000005", "8/10/2024", "17h00", "19h00", "club gym", "Legendre", "Noémie", 0600000000, "AG", "E000003", "prêt", 1));
        reservations.add(new Reservation("R000006", "9/10/2024", "09h00", "12h00", "tests candidats", "", "", 0, "autre", "E000007", "autre", 8));
        reservations.add(new Reservation("R000007", "7/10/2024", "15h00", "18h00", "présentation maquette", "", "", 0, "réunion", "E000007", "réunion", 3));
        reservations.add(new Reservation("R000008", "10/10/2024", "08h00", "18h00", "Bureautique", "Leroux", "Jacques", 0600000001, "formation", "E000003", "formation", 1));
        reservations.add(new Reservation("R000009", "11/10/2024", "08h00", "18h00", "Bureautique", "Leroux", "Jacques", 0600000001, "formation", "E000003", "formation", 1));
        reservations.add(new Reservation("R000010", "7/10/2024", "10h00", "12h00", "accueil nouveau membre", "", "", 0, "réunion", "E000008", "réunion", 3));
        reservations.add(new Reservation("R000011", "10/10/2024", "09h00", "12h00", "tests candidats", "", "", 0, "autre", "E000001", "autre", 8));
        reservations.add(new Reservation("R000012", "15/10/2024", "09h00", "10h00", "point avec stagiaire", "", "", 0, "réunion", "E000007", "réunion", 6));
        reservations.add(new Reservation("R000013", "11/10/2024", "08h00", "17h00", "mise à jour logiciels", "", "", 0, "entretien", "E000003", "entretien", 8));
        reservations.add(new Reservation("R000014", "11/10/2024", "08h00", "17h00", "mise à jour logiciels", "", "", 0, "entretien", "E000007", "entretien", 9));
        reservations.add(new Reservation("R000015", "16/10/2024", "10h00", "11h00", "visite tuteur IUT", "", "", 0, "réunion", "E000007", "réunion", 6));
        reservations.add(new Reservation("R000016", "17/10/2024", "14h00", "15h30", "validation maquette", "", "", 0, "réunion", "E000005", "réunion", 6));
        reservations.add(new Reservation("R000017", "18/10/2024", "08h00", "13h00", "Mairie", "Marin", "Hector", 0666666666, "location", "E000008", "location", 1));
        reservations.add(new Reservation("R000018", "18/10/2024", "13h00", "19h00", "Département", "Tournefeuille", "Michel", 0655555555, "location", "E000005", "location", 1));
    }

    @Test
    public void testGetters() {
        System.out.println("/********** Test des getters de la classe Reservation **********/");

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
