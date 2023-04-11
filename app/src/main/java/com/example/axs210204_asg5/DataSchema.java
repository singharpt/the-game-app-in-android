package com.example.axs210204_asg5;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

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

class DataComparator implements Comparator<DataSchema> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy H:m");
    public int compare(DataSchema obj1, DataSchema obj2) {
        // first, compare by the int value in descending order
        int result = Integer.compare(obj2.score, obj1.score);
        // if the int values are equal, compare by the datetime value in ascending order
        if (result == 0) {
            LocalDateTime date1 = LocalDateTime.parse(obj1.date, formatter);
            LocalDateTime date2 = LocalDateTime.parse(obj2.date, formatter);
            result = date2.compareTo(date1);
        }
        return result;
    }
}
