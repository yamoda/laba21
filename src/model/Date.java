package model;

import java.util.Calendar;

public class Date {
    int year;
    int month;
    int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(java.util.Date anotherDateFormat) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(anotherDateFormat);

        this.year = dateCalendar.get(Calendar.YEAR);
        this.month = dateCalendar.get(Calendar.MONTH) + 1;
        this.day = dateCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public String toString() { return day+"/"+month+"/"+year; }
}
