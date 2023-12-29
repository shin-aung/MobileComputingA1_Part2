package com.example.sudoku;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {

    private Button btnView;
    private Button btnDelete;

    private Button btnExit;
    private Button btn;
    private DataBase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        DB = new DataBase(this);

        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
        btnExit = findViewById(R.id.exitButton);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecords();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecords();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecordActivity.this, StartActivity.class));
            }
        });
    }

    private void viewRecords() {
        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(RecordActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display records in AlertDialog
        StringBuilder data = new StringBuilder();
        while (res.moveToNext()) {
            data.append("Time Taken: ").append(res.getString(0)).append("\n");
        }

        showRecordsDialog(data.toString());
    }

    private void deleteRecords() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
        builder.setTitle("Delete Records");
        builder.setMessage("Are you sure you want to delete all records?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean result = DB.deleteAllData();
                if (result) {
                    Toast.makeText(RecordActivity.this, "Records Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecordActivity.this, "Error Deleting Records", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void showRecordsDialog(String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
        builder.setTitle("User Entries");
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }
}
