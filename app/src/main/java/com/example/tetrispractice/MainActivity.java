package com.example.tetrispractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {
    private TetrisEngine tetrisView;

    private TextView scoreBox;
    private ImageView nextPieceView;
    SecureRandom random;
    private int randomColorCode;
    private Handler handler  = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            gameLoop();

            handler.postDelayed(this, 200);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new SecureRandom();
        this.tetrisView = findViewById(R.id.tetrisView);
        this.scoreBox = findViewById(R.id.scoreBoxContent);
        this.nextPieceView = findViewById(R.id.nextPieceView);
        this.randomColorCode = this.randomGenerator();
        this.setNextPieceImage(this.randomColorCode);
        tetrisView.tetrisBoard.throwNewShape(0,(this.randomColorCode));
        tetrisView.invalidate();
        handler.postDelayed(runnable, 200);



    }

    public void gameLoop() {
       if(!buttonActions(1)){
           if(tetrisView.tetrisBoard.removeLine()){
               scoreBox.setText((Integer.parseInt(scoreBox.getText().toString()))+30+"");
           }

           if(tetrisView.tetrisBoard.gameOver(new Shape(this.randomColorCode))){
               /*finish game code here*/
               finish();

           }
           tetrisView.tetrisBoard.throwNewShape(0,this.randomColorCode);
           tetrisView.invalidate();
           tetrisView.tetrisBoard.previousShape = new Shape(this.tetrisView.tetrisBoard.currentshape);
           tetrisView.tetrisBoard.currentshape.moveDown();
           tetrisView.tetrisBoard.resetEmptyPoints();
           this.randomColorCode = randomGenerator();
           this.setNextPieceImage(this.randomColorCode);
       }

    }


    public void moveLeft(View view) {
        buttonActions(3);
    }

    public void moveRight(View view) {
        buttonActions(2);
    }

    public void rotateAction(View view) {
        buttonActions(4);
    }

    private boolean buttonActions(int moveCode){
        boolean possible = false;
        if(tetrisView.tetrisBoard.moveIsPossible(moveCode)){
            possible = true;
            tetrisView.tetrisBoard.previousShape = new Shape(this.tetrisView.tetrisBoard.currentshape);
            switch (moveCode){
                case 1:
                    tetrisView.tetrisBoard.currentshape.moveDown();
                    break;
                case 2:
                    tetrisView.tetrisBoard.currentshape.moveRight();
                    break;
                case 3:
                    tetrisView.tetrisBoard.currentshape.moveLeft();
                    break;
                case 4:
                    tetrisView.tetrisBoard.currentshape.rotateShape();
                    break;
            }

            tetrisView.tetrisBoard.resetEmptyPoints();
            tetrisView.tetrisBoard.throwNewShape(1,null);
            tetrisView.invalidate();

        }
        return possible;
    }

    public int randomGenerator(){
        return this.random.nextInt(7);
    }

    private void setNextPieceImage(int shapeCode) {
        int imageCode = 0;
        switch (shapeCode){
            case 0:
                imageCode = R.drawable.o;
                break;
            case 1:
                imageCode = R.drawable.i;
                break;
            case 2:
                imageCode = R.drawable.s;
                break;
            case 3:
                imageCode = R.drawable.z;
                break;
            case 4:
                imageCode = R.drawable.j;
                break;
            case 5:
                imageCode = R.drawable.l;
                break;
            case 6:
                imageCode = R.drawable.t;
                break;

        }
        this.nextPieceView.setImageResource(imageCode);
    }
}
