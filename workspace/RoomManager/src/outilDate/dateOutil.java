package outilDate;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class dateOutil {
    public static int getWorkingDaysBetween(LocalDate start, LocalDate end) {

        int workingDays = 0;

        // Parcourir chaque jour entre les deux dates
        LocalDate current = start;
        while (!current.isAfter(end)) {
            DayOfWeek day = current.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            current = current.plusDays(1);
        }

        return workingDays;
    }
    public static String convertDoubleToStr(double time) {
        // Extraire les heures entières
        int hours = (int) time;

        // Extraire les minutes (partie décimale convertie en minutes)
        int minutes = (int) Math.round((time - hours) * 60);

        // Construire la chaîne de format "XX h XX min"
        return hours + " h " + minutes + " min";
    }
}
