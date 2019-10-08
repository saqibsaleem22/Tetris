package com.example.tetrispractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class TetrisEngine extends View {

    public TetrisBoard tetrisBoard = new TetrisBoard();

    public TetrisEngine(Context context) {
        super(context);
    }

    public TetrisEngine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TetrisEngine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TetrisEngine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int [][] m = this.tetrisBoard.tetrisMatrix;
        Paint p = new Paint();

        for(int i=0;i<m.length;i++){
            for(int j=0;j<m[j].length;j++){
                if(tetrisBoard.tetrisMatrix[i][j]==-1){
                    p.setColor(Color.BLACK);
                } else {
                    p.setColor(codeToColor(tetrisBoard.tetrisMatrix[i][j]));
                }
                canvas.drawRect(j*42,i*42,j*42+42,i*42+42,p);
            }
        }
    }

    private int codeToColor(int code) {
        int color;
        switch (code){
            case 0:
                color = Color.RED;
                break;
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.MAGENTA;
                break;
            case 5:
                color = Color.CYAN;
                break;
            case 6:
                color = Color.WHITE;
                break;
                default:
                    color = Color.BLACK;
                    break;

        }
        return color;
    }
}


