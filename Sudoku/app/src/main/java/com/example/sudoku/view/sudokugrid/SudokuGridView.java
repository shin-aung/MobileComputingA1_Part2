package com.example.sudoku.view.sudokugrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.sudoku.GameEngine;

public class SudokuGridView extends GridView{

    private final Context context;

    public SudokuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        SudokuGridViewAdapter gridViewAdapter = new SudokuGridViewAdapter(context);
        setAdapter(gridViewAdapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int x = position % 9;
                int y = position / 9;
                GameEngine.getInstance().setSelectedPosition(x, y);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
    public void updateGridData(GameGrid newGridData) {
        SudokuGridViewAdapter adapter = (SudokuGridViewAdapter) getAdapter();
        if (adapter != null) {
            adapter.setGridData(newGridData);
            adapter.notifyDataSetChanged();
        }
    }

    class SudokuGridViewAdapter extends BaseAdapter {
        private Context context;
        private GameGrid gridData;

        public SudokuGridViewAdapter(Context context) {
            this.context = context;
            this.gridData = GameEngine.getInstance().getGrid();
        }

        public void setGridData(GameGrid newGridData) {
            this.gridData = newGridData;
        }

        @Override
        public int getCount() {
            return 81;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return gridData.getItem(position);
        }
    }
}
