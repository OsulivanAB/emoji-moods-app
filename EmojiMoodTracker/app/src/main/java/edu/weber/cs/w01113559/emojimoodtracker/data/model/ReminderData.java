package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import java.util.List;
import java.util.UUID;

public final class ReminderData {

    private String ID;
    private int hour;
    private int minute;
    private List<String> days;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<String> getDays() {
        return days;
    }

    public ReminderData() {
    }

    public ReminderData(int hour, int minute, List<String> days) {
        this.ID = UUID.randomUUID().toString();
        this.setHour(hour);
        this.setMinute(minute);
        this.setDays(days);
    }
}
