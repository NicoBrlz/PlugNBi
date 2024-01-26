package fr.an.exprlib.dto.specific;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateDTO {

    // PK
    int dateId;

    // other
    LocalDate calDate;
    String day;
    int week;
    String month;
    String qtr;
    int year;
    boolean holiday;

}
