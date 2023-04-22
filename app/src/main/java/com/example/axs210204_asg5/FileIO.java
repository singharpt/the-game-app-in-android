package com.example.axs210204_asg5;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import java.io.*;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: FIleIO does the following: -
 * 1. Instantiates an ArrayList of Object called fileData.
 * 2. Contains method GetFileData which gets the text data from storage, reads it, and adds the content in fileData.
 * 3. Contains method AddFileData which adds more items to the fileData.
 * 4. Contains method WriteFileData which writes the elements of fileData to the text file.
 */
public class FileIO {
    public final static ArrayList<DataSchema> fileData = new ArrayList<DataSchema>();;

    //Contructor for the class FileIO
//    FileIO() {
//        fileData = new ArrayList<DataSchema>();
//    }

    //This method will read the data inside the text file "dataFile.txt" and store it in the arraylist of objects fileData
    public void GetFileData(Context fileContext) {
        File file = fileContext.getFileStreamPath("dataFile.txt");
        if (file.exists())
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine() ) != null) {
                    StringTokenizer tokens = new StringTokenizer(line, "\t");
                    String[] lineData = new String[] {tokens.nextToken(), tokens.nextToken(), tokens.nextToken()};
                    fileData.add(new DataSchema(lineData));
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
        fileData.add(new DataSchema(data));
    }

    //This method will write the data inside the text file "dataFile.txt"
    public void WriteFileData(Context fileContext)
    {
        try {
            FileOutputStream file = fileContext.openFileOutput("dataFile.txt", Activity.MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(file);
            String text;
            List<DataSchema> firstTwenty = fileData.subList(0, Math.min(fileData.size(), 20));
            ArrayList<DataSchema> newList = new ArrayList<>(firstTwenty);
            for (int i = 0; i < newList.size(); i++) {
                text = newList.get(i).name.toString() + "\t" + String.valueOf(newList.get(i).score) + "\t" + newList.get(i).date.toString();
                outputFile.write(text+"\n");
            }
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

