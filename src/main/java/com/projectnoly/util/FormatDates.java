package com.projectnoly.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class FormatDates {

    public static String formatElapsedTime(LocalDateTime localDateTime){
        LocalDateTime now = LocalDateTime.now();
        Duration elapsedTime = Duration.between(localDateTime,now);
        long minutes = elapsedTime.toMinutes();
        long hours = elapsedTime.toHours();
        long days = ChronoUnit.DAYS.between(localDateTime, now);
        long months = ChronoUnit.MONTHS.between(localDateTime, now);
        long years = ChronoUnit.YEARS.between(localDateTime, now);
        if (years > 0) {
            return "Hace " + years + (years == 1 ? " año" : " años");
        } else if (months > 0) {
            return "Hace " + months + (months == 1 ? " mes" : " meses");
        } else if (days > 0) {
            return "Hace " + days + (days == 1 ? " día" : " días");
        } else if (hours > 0) {
            return "Hace " + hours + (hours == 1 ? " hora" : " horas");
        } else if (minutes > 0) {
            return "Hace " + minutes + (minutes == 1 ? " minuto" : " minutos");
        } else {
            return "Hace unos segundos";
        }
    }
}
