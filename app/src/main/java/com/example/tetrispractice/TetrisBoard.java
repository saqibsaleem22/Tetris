package com.example.tetrispractice;

import android.graphics.Point;
import android.view.View;

import androidx.annotation.Nullable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TetrisBoard {

    public final int BOARD_WIDTH = 16;
    public final int BOARD_HEIGHT = 30;
    public int[][] tetrisMatrix;
    public Shape currentshape;
    public Shape previousShape;

    public TetrisBoard(){
        this.tetrisMatrix = new int[BOARD_HEIGHT][BOARD_WIDTH];
        startfill();

    }

    public void throwNewShape(int randomOrShape,@Nullable Integer randomShape){

        //creates the shape
        if(randomOrShape == 0) {
            this.currentshape= new Shape(randomShape);
        }

        //gets points of shape
        float [] points = this.currentshape.getPoints();
        //places shape color code on corresponding points
        for(int i=0,j=1; j<points.length;i+=2,j+=2) {
            int x = (int)points[i], y = (int)points[j];
            //color codes the points on our board
            this.tetrisMatrix[x][y] = this.currentshape.getColor();
        }
    }

    private void startfill() {
        for(int i=0;i<tetrisMatrix.length;i++){
            for(int j=0;j<tetrisMatrix[j].length;j++){
                this.tetrisMatrix[i][j]=-1;
            }
        }
    }

    /**
     * Checks if the move is possible
     * @param moveCode
     * @return boolean
     */
    public boolean moveIsPossible(int moveCode){
        try {
            float [] currentPoints = this.currentshape.getPoints();
            //creates a copy to test the move
            Shape testShape = new Shape(this.currentshape);
            boolean isPossible = true;
            //down move
            if(moveCode == 1){
                testShape.moveDown();
            //move right
            } else if(moveCode == 2) {
                testShape.moveRight();
            //move left
            } else if(moveCode == 3) {
                testShape.moveLeft();
            } else {
                testShape.rotateShape();
            }
            //gets points of test shape after move
            float [] testPoints = testShape.getPoints();
            ArrayList<Integer> differentPoints = new ArrayList<>();
            boolean isSimilar = false;
            for (int i = 0 ,j=1; j < testPoints.length; i+=2,j+=2) {
                int x=(int)testPoints[i]; int y=(int)testPoints[j];
                for (int i1 = 0, j1=1; j1 < currentPoints.length; i1+=2,j1+=2) {
                    int x1 = (int)currentPoints[i1]; int y1 = (int)currentPoints[j1];
                    if(x == x1 && y == y1){
                        isSimilar=true;
                        break;
                    }
                }
                if(!isSimilar){
                    differentPoints.add(x);
                    differentPoints.add(y);
                }
                isSimilar=false;
            }
            for (int i = 0, j = 1; j < differentPoints.size(); i+=2,j+=2) {
                int x = differentPoints.get(i); int y = differentPoints.get(j);
                if(tetrisMatrix[x][y] != -1){
                    isPossible =false;
                    break;
                }

            }
            return isPossible;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }


    /**
     * resets the points on board after shape is moved
     */
    public void resetEmptyPoints(){
        boolean isPresent = false;
        //TODO: Get current shape points
        //Get the new points and compare them with older ones
        //convert all older points to -1 except those which are there in new points
        float [] newPoints = this.currentshape.getPoints();
        float [] oldPoints = this.previousShape.getPoints();
        for(int i=0,j=1; j<oldPoints.length;i+=2,j+=2) {
            int x = (int)oldPoints[i], y = (int)oldPoints[j];
            for(int k=0,l=1; l<newPoints.length;k+=2,l+=2) {
                int o = (int)newPoints[k], p = (int)newPoints[l];
                if(x==o && y==p){
                    isPresent = true;
                    break;
                }

            }
            if(!isPresent){
                this.tetrisMatrix[x][y] = -1;
            }
            isPresent = false;
        }

    }

    public boolean removeLine() {
        boolean removed = false;
        /*
        Todo: Itertate over tetris array
        Check if any row 16 !-1s
        -convert all to -1
        -Bring everything down
         */
        int notOnes = 0;
        for (int i = 0; i < 30; i++) {
            notOnes = 0 ;
            for (int j = 0; j < 16; j++) {
                if(this.tetrisMatrix[i][j] != -1){
                    notOnes++;
                }

            }
            //i = 3
            if(notOnes == 16){
                removed = true;
                if(i>0){
                    int copyArray [][] = new int[i][16];
                    for (int g = 0; g < i; g++) {
                        copyArray[g] = Arrays.copyOfRange(tetrisMatrix[g],0,16);

                    }
                    for(int k=1;k<i+1;k++){
                        tetrisMatrix[k] = copyArray[k-1];
                    }
                }
                Arrays.fill(tetrisMatrix[0], -1);
                removeLine();
            }

        }
        return removed;

    }
    /**
     * Checks if game is over by investigating if it is
     * possible to throw a new shape
     * @return boolean
     */
    public boolean gameOver(Shape newShape){
        boolean gameOver = false;
        //creates a copy of points
        float [] newShapePoints = newShape.getPoints().clone();
        for (int i = 0,j=1; j < newShapePoints.length; i+=2,j+=2) {
            if(tetrisMatrix[(int)newShapePoints[i]][(int)newShapePoints[j]]!=-1){
                gameOver = true;
                break;
            }
        }
        return gameOver || gameOver2();

    }

    /**
     * Checks if the game is over by checking the last and
     * first rows. If both rows have atleast one element returns true.
     * @return boolean
     */
    public boolean gameOver2() {
        boolean gameOver = false, top = false, bottom = false;
        int counter=0;
        for(int i = 0;i<16;i++){
            if(tetrisMatrix[0][i]!=-1 && !top){
                counter++;
                top = true;
            }
            if(tetrisMatrix[29][i]!=-1 && !bottom){
                counter++;
                bottom = true;
            }

        }

        return counter == 2 ? true:false;
    }

}
