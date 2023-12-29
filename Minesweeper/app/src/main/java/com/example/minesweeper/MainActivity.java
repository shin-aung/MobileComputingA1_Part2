package com.example.minesweeper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.textView)).setText("ASDASDASDASD");

        Log.e("MainActivity","onCreate");
        GameEngine.getInstance().createGrid(this);

        Button quitButton = findViewById(R.id.quitButton);

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });
    }
}


