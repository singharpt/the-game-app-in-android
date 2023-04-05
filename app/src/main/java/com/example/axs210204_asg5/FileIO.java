package com.example.axs210204_asg5;

import android.app.Activity;
import android.content.Context;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

class Data{
    public String name;
    public int score;
    public Timestamp date;

    Data(String[] dataArray){
        this.name = dataArray[0];
        this.score = Integer.parseInt(dataArray[1]);
        this.date = Timestamp.valueOf(dataArray[2]);
    }
}

public class FileIO {
    //Constructor for the FileIO Class
    //Creates a file name "IOFile.txt" @ current project directory inside folder names IO
    File path;
    String filename;
    File file;
    ArrayList<Data> fileData;
    FileIO() {
        fileData = new ArrayList<Data>();
    }

    //This method will read the data inside the file and store in the arraylist of objects of class data.
    public void GetFileData(Context fileContext) {
        File file = fileContext.getFileStreamPath("A1.txt");
        if (file.exists())
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine() ) != null) {
                    StringTokenizer tokens = new StringTokenizer(line, "\t");
                    String[] lineData = new String[] {tokens.nextToken(), tokens.nextToken(), tokens.nextToken()};
                    fileData.add(new Data(lineData));
                }
                br.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //This method will add an object to the arraylist filedata
    public void AddData(String[] data){
        fileData.add(new Data(data));
    }

    //This method will write the data inside the file
    public void WriteFileData(Context fileContext)
    {
        try {
            FileOutputStream file = fileContext.openFileOutput("A1.txt", Activity.MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);
            String text;
            for (int i = 0; i < fileData.size(); i++) {
                text = fileData.get(i).name.toString() + "\t" + String.valueOf(fileData.get(i).score) + "\t" + fileData.get(i).date.toString();
                outputFile.write(text+"\n");
            }
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

