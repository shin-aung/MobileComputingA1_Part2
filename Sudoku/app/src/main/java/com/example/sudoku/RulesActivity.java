package com.example.sudoku;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView rulesTextView = findViewById(R.id.rulesTextView);

        // Set the rules dynamically
        String rulesText = getString(R.string.sudoku_rules); // Assuming you have a string resource
        rulesTextView.setText(rulesText);
    }
}
