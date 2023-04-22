package com.example.axs210204_asg5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameMainMenu extends AppCompatActivity {

    private final FileIO iobj = new FileIO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This if condition prevents data from being read mutilple times from the text file
        if (FileIO.fileData.size() < 1) {
            //The line triggers the read file method from the FileIO class
            iobj.GetFileData(this.getApplicationContext());
            System.out.println(FileIO.fileData.size());
        }

        findViewById(R.id.startGameBtn).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GamePlayActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.viewLeaderboardBtn).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameScoreDisplay.class);
            startActivity(intent);
        });
    }
}
