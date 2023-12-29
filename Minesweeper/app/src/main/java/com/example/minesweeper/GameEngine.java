package com.example.minesweeper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.minesweeper.util.Generator;
import com.example.minesweeper.util.PrintGrid;
import com.example.minesweeper.views.grid.Cell;

public class GameEngine {
    private static GameEngine instance;

    public static final int BOMB_NUMBER = 10;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 16;

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];

    public static GameEngine getInstance() {
        if( instance == null ){
            instance = new GameEngine();
        }
        return instance;
    }

    private GameEngine(){ }

    public void createGrid(Context context){
        Log.e("GameEngine","createGrid is working");
        this.context = context;

        // create the grid and store it
        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER,WIDTH, HEIGHT);
        PrintGrid.print(GeneratedGrid,WIDTH,HEIGHT);
        setGrid(context,GeneratedGrid);
    }

    private void setGrid( final Context context, final int[][] grid ){
        for( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( MinesweeperGrid[x][y] == null ){
                    MinesweeperGrid[x][y] = new Cell( context , x,y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt( int x , int y ){
        return MinesweeperGrid[x][y];
    }

    public void click(int x, int y) {
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x, y).isClicked()) {
            getCellAt(x, y).setClicked();

            if (getCellAt(x, y).getValue() == 0) {
                for (int xt = -1; xt <= 1; xt++) {
                    for (int yt = -1; yt <= 1; yt++) {
                        if (xt != yt) {
                            click(x + xt, y + yt);
                        }
                    }
                }
            }

            if (getCellAt(x, y).isBomb()) {
                // Player clicked on a mine, game over
                onGameLost();
            }
        }

        checkEnd();
    }

    public void flag(int x, int y) {
        Cell cell = getCellAt(x, y);
        boolean isFlagged = cell.isFlagged();
        cell.setFlagged(!isFlagged);
        cell.invalidate();

        checkEnd();
    }

    private boolean checkEnd() {
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        int correctlyFlagged = 0;

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (getCellAt(x, y).isRevealed() || getCellAt(x, y).isFlagged()) {
                    notRevealed--;
                }

                if (getCellAt(x, y).isFlagged() && getCellAt(x, y).isBomb()) {
                    bombNotFound--;
                    correctlyFlagged++;
                }
            }
        }

        if (bombNotFound == 0 && notRevealed == 0) {
            // All bombs flagged correctly, player wins
            Toast.makeText(context, "Congratulations! You win!", Toast.LENGTH_SHORT).show();
            // Implement logic to restart or go back to the main menu
            showWinDialog();
        } else if (correctlyFlagged == BOMB_NUMBER) {
            // All mines have been identified with flags, player wins
            Toast.makeText(context, "Congratulations! You win!", Toast.LENGTH_SHORT).show();
            // Implement logic to restart or go back to the main menu
            showWinDialog();
        }

        return false;
    }

    private void showWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Congratulation!!");
        builder.setMessage("Do you want to restart the game?");
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
            }
        });
        builder.show();
    }

    public void revealNeighbors(int x, int y) {
        for (int xt = -1; xt <= 1; xt++) {
            for (int yt = -1; yt <= 1; yt++) {
                int newX = x + xt;
                int newY = y + yt;

                // Check bounds to avoid ArrayIndexOutOfBoundsException
                if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT) {
                    Cell neighbor = getCellAt(newX, newY);

                    // Only reveal unrevealed neighbors
                    if (!neighbor.isRevealed()) {
                        neighbor.setRevealed();
                    }
                }
            }
        }
    }

    private void onGameWon() {
        Toast.makeText(context, "Game won", Toast.LENGTH_SHORT).show();
        // Perform any actions needed when the game is won

    }

    public void onGameLost() {
        // Handle lost game
        Toast.makeText(context, "Game lost", Toast.LENGTH_SHORT).show();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                getCellAt(x, y).setRevealed();
            }
        }

        // Show a dialog or message to ask the player if they want to restart
        showRestartDialog();
    }

    private void showRestartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Game Over");
        builder.setMessage("Do you want to restart the game?");
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
            }
        });
        builder.show();
    }

    private void showGameCompletionDialog() {
        // Implement the logic to show a dialog asking the user to restart the game
        // You can use AlertDialog or any other UI component for this purpose
        // Example:
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Game Completed")
                .setMessage("Congratulations! You completed the game.")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Implement the logic to restart the game
                        for (int x = 0; x < WIDTH; x++) {
                            for (int y = 0; y < HEIGHT; y++) {
                                getCellAt(x, y).setRevealed();
                            }
                        }

                        // Show a dialog or message to ask the player if they want to restart
                        showRestartDialog();
                    }
                })
                .show();
    }

    private void restartGame() {
        // Implement the logic to restart the game
        // You may need to reset the game state, clear the grid, and start a new game
        // Example:
        createGrid(context);
    }

}
