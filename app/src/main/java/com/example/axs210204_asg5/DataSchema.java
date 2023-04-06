package com.example.axs210204_asg5;

import java.sql.Timestamp;
import java.util.Comparator;

public class DataSchema {
    public String name;
    public int score;
    public Timestamp date;

    DataSchema(String[] dataArray) {
        this.name = dataArray[0];
        this.score = Integer.parseInt(dataArray[1]);
        this.date = Timestamp.valueOf(dataArray[2]);
    }
}

class DataComparator implements Comparator<DataSchema> {
    public int compare(DataSchema obj1, DataSchema obj2) {
        // first, compare by the int value in descending order
        int result = Integer.compare(obj2.score, obj1.score);

        // if the int values are equal, compare by the datetime value in ascending order
        if (result == 0) {
            result = obj2.date.compareTo(obj1.date);
        }
        return result;
    }
}
