package com.example.sudoku.view.sudokugrid;

import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudoku.DataBase;
import com.example.sudoku.GameEngine;
import com.example.sudoku.MainActivity;
import com.example.sudoku.R;
import com.example.sudoku.SudokuChecker;
public class GameGrid {

    private AlertDialog.Builder builder;
    private DataBase DB; // Move the declaration here

    private SudokuGridView sudokuGridView;

    private SudokuCell[][] Sudoku = new SudokuCell[9][9];

    private long startTime;
    private Context context;

    private void restartGame() {
        setGrid(new int[9][9]);
        startTimer();
    }
    public GameGrid(MainActivity context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        DB = new DataBase(context); // Initialize the database here

        startTimer();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Sudoku[x][y] = new SudokuCell(context);
            }
        }
    }

    public void setGrid( int[][] grid ){
        for( int x = 0 ; x < 9 ; x++ ){
            for( int y = 0 ; y < 9 ; y++){
                Sudoku[x][y].setInitValue(grid[x][y]);
                if( grid[x][y] != 0 ){
                    Sudoku[x][y].setNotModifiable();
                }
            }
        }
    }

    public SudokuCell[][] getGrid(){
        return Sudoku;
    }

    public SudokuCell getItem(int x , int y ){
        return Sudoku[x][y];
    }

    public SudokuCell getItem( int position ){
        int x = position % 9;
        int y = position / 9;

        return Sudoku[x][y];
    }

    public void setItem( int x , int y , int number ){
        Sudoku[x][y].setValue(number);
    }

    public void checkGame(){
        int [][] sudGrid = new int[9][9];
        for( int x = 0 ; x < 9 ; x++ ){
            for( int y = 0 ; y < 9 ; y++ ){
                sudGrid[x][y] = getItem(x,y).getValue();
            }
        }

        if( SudokuChecker.getInstance().checkSudoku(sudGrid)){
            Toast.makeText(context, "You solved the sudoku.", Toast.LENGTH_LONG).show();
            onGameWon();
        }
    }

    private void onGameWon() {
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        showEndGameDialog(true, timeTaken);
    }

    public void showEndGameDialog(boolean gameWon, long timeTaken) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.end_game_dialog, null);
        builder.setView(dialogView);

        TextView timeTakenTextView = dialogView.findViewById(R.id.timeTakenTextView);
        timeTakenTextView.setText("Time Taken: " + (timeTaken / 1000) + " seconds");

        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
                String timeTXT = String.valueOf(timeTaken / 1000); // Convert to seconds
                Boolean checkInsertData = DB.insertuserdata(timeTXT);
                if (checkInsertData) {
                    Toast.makeText(context, "New Entry", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }

                // Open the ViewRecordsActivity to display inserted records
                openRecordsActivity();
            }
        });

        builder.show();
    }



    private void openRecordsActivity() {
        context.startActivity(new Intent(context, com.example.sudoku.RecordActivity.class));
    }

    private void updateSudokuGrid() {
        // Update the existing SudokuGridView with the new data
        sudokuGridView.updateGridData(GameEngine.getInstance().getGrid());
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }
}

