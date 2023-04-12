package com.example.axs210204_asg5;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: DataSchema does the following: -
 * 1. It has 3 data members: Name, Score & Date
 * 2. It has a parameterized constructor that takes in an array of string and instantiates all 3 data members of this class.
 */
public class DataSchema {
    public String name;
    public int score;
    public String date;
    DataSchema(String[] dataArray) {
        this.name = dataArray[0];
        this.score = Integer.parseInt(dataArray[1]);
        this.date = dataArray[2];
    }
}

/**
 * This class is used as a custom made that sorts the ArrayList of objects based upon two conditions.
 * 1. It first compares the two objects's score, and returns them in the order of higher score first.
 * 2. If two scores are equal then it compares the datetime of both objects and return them in the order of earlier datatime first.
 */
class DataComparator implements Comparator<DataSchema> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:m a");
    public int compare(DataSchema obj1, DataSchema obj2) {
        // first, compare by the int value in descending order
        int result = Integer.compare(obj2.score, obj1.score);
        // if the int values are equal, compare by the datetime value in descending order
        if (result == 0) {
            LocalDateTime date1 = LocalDateTime.parse(obj1.date, formatter);
            LocalDateTime date2 = LocalDateTime.parse(obj2.date, formatter);
            result = date2.compareTo(date1);
        }
        return result;
    }
}
