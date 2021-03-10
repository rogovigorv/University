package com.foxminded.university;

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

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getDuration() {
        return duration;
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
