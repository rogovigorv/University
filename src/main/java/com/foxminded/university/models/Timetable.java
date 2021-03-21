package com.foxminded.university.models;

import java.time.LocalDate;
import java.util.Objects;

public class Timetable {
    private int id;
    private LocalDate start;
    private LocalDate duration;

    public Timetable(int id, LocalDate start, LocalDate duration) {
        this.id = id;
        this.start = start;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getDuration() {
        return duration;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object otherTimetable) {
        if (this == otherTimetable) {
            return true;
        }
        if (otherTimetable == null || getClass() != otherTimetable.getClass()) {
            return false;
        }
        Timetable timetable = (Timetable) otherTimetable;
        return id == timetable.id &&
                start == timetable.start &&
                duration == timetable.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, duration);
    }

    @Override
    public String toString() {
        return "Timetable id: " + id +
                " Lecture start: " + start +
                " Lecture duration: " + duration;
    }
}
