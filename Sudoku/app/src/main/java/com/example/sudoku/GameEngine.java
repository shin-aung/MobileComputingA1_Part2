package com.example.sudoku;

import android.content.Context;

import com.example.sudoku.view.sudokugrid.GameGrid;

public class GameEngine {
    private static GameEngine instance;

    private GameGrid grid = null;
    private GameEngine(){}
    private int selectedPosX = -1, selectedPosY = -1;

    public static GameEngine getInstance(){
        if( instance == null ){
            instance = new GameEngine();
        }
        return instance;
    }

    public void createGrid(Context context){
        // Reset game state or any other necessary actions
        // ...

        // Remove the old grid
        grid = null;

        // Generate a new Sudoku grid
        int[][] sudokuGrid = SudokuGenerator.getInstance().generateGrid();
        sudokuGrid = SudokuGenerator.getInstance().removeElements(sudokuGrid);

        // Set the new grid in the GameGrid
        grid = new GameGrid((MainActivity) context);
        grid.setGrid(sudokuGrid);
    }

    public GameGrid getGrid(){
        return grid;
    }

    public void setSelectedPosition( int x , int y ){
        selectedPosX = x;
        selectedPosY = y;
    }

    public void setNumber( int number ){
        if( selectedPosX != -1 && selectedPosY != -1 ){
            grid.setItem(selectedPosX,selectedPosY,number);
        }
        grid.checkGame();
    }
}
