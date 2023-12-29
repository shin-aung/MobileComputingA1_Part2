package com.example.sudoku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.example.sudoku.view.sudokugrid.SudokuGridView;
import com.example.sudoku.view.buttonsgrid.ButtonsGridView;

public class MainActivity extends Activity {


    private SudokuGridView sudokuGridView;
    private ButtonsGridView buttonsGridView;

    private long startTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Sudoku and Buttons grids
        sudokuGridView = findViewById(R.id.sudokuGridView);
        buttonsGridView = findViewById(R.id.buttonsgridview);

        // Set up restart button click listener
        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartSudoku();

            }
        });


        // Create the initial Sudoku grid
        GameEngine.getInstance().createGrid(this);
        updateSudokuGrid();
    }

    private void restartSudoku() {
        // Recreate the Sudoku grid for a new game
        GameEngine.getInstance().createGrid(this);
        startTimer();
        updateSudokuGrid();
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    private void updateSudokuGrid() {
        // Update the existing SudokuGridView with the new data
        sudokuGridView.updateGridData(GameEngine.getInstance().getGrid());
    }

    private void printSudoku(int Sudoku[][]) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                System.out.print(Sudoku[x][y] + "|");
            }
            System.out.println();
        }
    }
}
