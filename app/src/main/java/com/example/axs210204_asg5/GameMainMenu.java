package com.example.axs210204_asg5;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * --> Written by Arpit Singh for class CS 6326, assignment 5 - The Android App. Net Id - AXS210204 <--
 * The following class: GameMainMenu does the following: -
 * 1. This is the parent activity of the app.
 * 2. It displays the activity_main layout which gives two options to the user.
 * 3. Option 1 - Play game. If a user clicks on this button a new game starts.
 * 4. Option 2 - View Leaderboard. If a user clicks on this button leaderboard is displayed.
 */

public class GameMainMenu extends AppCompatActivity {

    private final FileIO iobj = new FileIO();

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This if condition prevents data from being read multiple times from the text file.
        if (FileIO.fileData.size() < 1) {
            //The line triggers the read file method from the FileIO class
            iobj.GetFileData(this.getApplicationContext());
            System.out.println(FileIO.fileData.size());
        }

        //When user presses the start game button it starts the GamePlayActivity which initiates the custome game view.
        findViewById(R.id.startGameBtn).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GamePlayActivity.class);
            startActivity(intent);
            finish();
        });

        //When user presses the start game button it starts the GameScoreDisplay activity which displays the leaderboard.
        findViewById(R.id.viewLeaderboardBtn).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameScoreDisplay.class);
            startActivity(intent);
            finish();
        });
    }
}
