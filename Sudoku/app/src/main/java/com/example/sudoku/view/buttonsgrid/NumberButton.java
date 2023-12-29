package com.example.sudoku.view.buttonsgrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.sudoku.GameEngine;

public class NumberButton extends Button implements OnClickListener{
    private int number;

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        GameEngine.getInstance().setNumber(number);
    }

    public void setNumber(int number){
        this.number = number;
    }
}
