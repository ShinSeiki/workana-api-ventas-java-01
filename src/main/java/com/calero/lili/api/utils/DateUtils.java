package com.calero.lili.api.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    //ZonedDateTime zonedDateTime = ZonedDateTime.parse("2015-05-05 10:15:30 Europe/Paris", formatter);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDate toLocalDate(String date) {
        //System.out.println("recibido");
        //System.out.println(date);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDateTime toLocalDateTime(String date) {
        //System.out.println("recibido");
        //System.out.println(date);
        return LocalDateTime.parse(date, formatterTime);
    }

    public static String toString(LocalDate date) {
        return date.format(formatter);
    }

    public static String toString(LocalDateTime date) {
        return date.format(formatterTime);
    }

}
