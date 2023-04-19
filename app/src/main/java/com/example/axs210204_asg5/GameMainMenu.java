package com.example.axs210204_asg5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GameMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
