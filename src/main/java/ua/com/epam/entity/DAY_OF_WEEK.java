package ua.com.epam.entity;

import java.util.Arrays;
import java.util.Optional;

public enum DAY_OF_WEEK {
    MON("Понеділок", 1),
    TUE("Вівторок", 2),
    WED("Середа", 3),
    THU("Четвер", 4),
    FRI("П'ятниця", 5),
    SAT("Субота",6),
    SUN("Неділя", 7);

    private String weekday;
    private int dayNumber;
    DAY_OF_WEEK(String weekday, int dayNumber){
        this.weekday = weekday;
        this.dayNumber = dayNumber;
    }

    public static Optional<DAY_OF_WEEK> getWeekdayByNumber(int dayNumber) {
        return Arrays.stream(DAY_OF_WEEK.values()).filter(s->s.getDayNumber() == dayNumber).findFirst();
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getWeekday() {
        return weekday;
    }
}