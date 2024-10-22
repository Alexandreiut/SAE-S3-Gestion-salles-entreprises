package tests.items;

import java.util.ArrayList;
import java.util.List;

import modeles.items.Activite;
import modeles.items.Employe;
import modeles.items.Reservation;
import modeles.items.Salle;

public class JeuDonne {
    
    public ArrayList<Activite> setUpActivites() {
        ArrayList<Activite> activites = new ArrayList<>();
        activites.add(new Activite("A0000001", "réunion"));
        activites.add(new Activite("A0000002", "formation"));
        activites.add(new Activite("A0000003", "entretien de la salle"));
        activites.add(new Activite("A0000004", "prêt"));
        activites.add(new Activite("A0000005", "location"));
        activites.add(new Activite("A0000006", "autre"));
        return activites;
    }

    public ArrayList<Employe> setUpEmployes() {
        ArrayList<Employe> employes = new ArrayList<>();
        employes.add(new Employe("E0000001", "Dupont", "Pierre", 2614));
        employes.add(new Employe("E0000002", "Lexpert", "Noemie", 2614));
        employes.add(new Employe("E0000003", "Dujardin", "Océane", 2633));
        employes.add(new Employe("E0000004", "Durand", "Bill", 2696));
        employes.add(new Employe("E0000005", "Dupont", "Max", -1));
        employes.add(new Employe("E0000006", "Martin", "Martin", 2678));
        employes.add(new Employe("E0000007", "Legrand", "Jean-Pierre", 2689));
        employes.add(new Employe("E0000008", "Deneuve", "Zoé", 2626));
        return employes;
    }

    public ArrayList<Reservation> setUpReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
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
        return reservations;
    }

    public ArrayList<Salle> setUpSalles() {
        ArrayList<Salle> salles = new ArrayList<>();
        salles.add(new Salle(1, "A6", 15, true, false, 4, "PC portable", new ArrayList<>(), false));
        salles.add(new Salle(2, "salle bleue", 18, true, true, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(3, "salle ronde", 14, true, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(4, "salle Picasso", 15, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(5, "petite salle", 7, true, true, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(6, "A7", 4, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(7, "salle patio", 6, false, false, 0, "", new ArrayList<>(), false));
        salles.add(new Salle(8, "salle Sydney", 20, true, false, 16, "PC Windows", new ArrayList<>(List.of("bureautique", "java", "Intellij")), false));
        salles.add(new Salle(9, "salle Brisbane", 22, true, false, 18, "PC Windows", new ArrayList<>(List.of("bureautique", "java", "Intellij", "photoshop")), true));
        return salles;
    }
}
