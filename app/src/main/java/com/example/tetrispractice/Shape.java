package com.example.tetrispractice;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;

import androidx.core.content.res.TypedArrayUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


/**
 * This class creates different shapes for tetris game.It uses points on
 * xy plain to define them. Also defines methods which are needed to move or
 * rotate our shape.Every shape has unique color which is defined by an int
 * which can be between 0 and 6 (6 inclusive)
 * @author saqib
 * @version 1
 */


public class Shape {

    private float[] points; //contains points on xy plain of the shape
    private int color;  //color of the shape

    /**
     * This constructor creates shape depending on the passed
     * color code. The color code can be between 0 and 6 (6 inclusive)
     * @pram int color
     */
    public Shape(int color) {
        this.points = new float[8];
        this.color = color;
        shapePoints(color);
    }

    /**
     * This constructor creates a clone of the passed shape.
     * @param shape
     */
    public Shape(Shape shape){
        this.points = shape.points.clone();
        this.color  = color;

    }


    /**
     * This method sets the points of the shape depending
     * on color code.
     * @param color
     */
    private void shapePoints(int color) {
        int colorCode = color;


        switch (color) {
            case 0: //square shape
                setPoints(0,7,0,8,1,7,1,8);
                break;

            case 1: //I shape
                setPoints(0,6,0,7,0,9,0,8);
                break;

            case 2: //S shape
                setPoints(0,7,0,8,1,6,1,7);
                break;

            case 3: //Z shape
                setPoints(0,7,0,8,1,9,1,8);
                break;

            case 4: //J shape
                setPoints(0,9,1,7,1,9,1,8);
                break;
            case 5: //L shape
                setPoints(0,7,1,7,1,9,1,8);
                break;

            case 6: //T shape
                setPoints(0,7,0,8,0,9,1,8);
                break;

        }
    }

    /**
     * Sets the points of the shape
     * @param points
     */
    private void setPoints(float...points){
        this.points = points;
    }

    /**
     * Rotates our shape 90º
     */
    public void rotateShape() {

        float px = this.points[6];
        float py = this.points[7];
        Matrix matrix = new Matrix();
        matrix.setRotate(-90,px,py);
        matrix.mapPoints(this.points);

    }

    /**
     * Moves the shape to right
     */
    public void moveRight() {
        int lenght = this.points.length;
        for(int i=0;i<lenght;i++){
            //move only on x axis
            if(i%2.0 != 0){
                this.points[i]++;
            }

        }
    }

    /**
     * Moves the shape to the left
     */
    public void moveLeft() {
        int lenght = this.points.length;
        for(int i=0;i<lenght;i++){
            //move only on x axis
            if(i%2.0 != 0){
                this.points[i]--;
            }

        }
    }

    /**
     * Moves the shape down
     */
    public void moveDown(){
        int lenght = this.points.length;
        for(int i=0;i<lenght;i++){
            //move only on y axis
            if(i%2.0 == 0){
                this.points[i]++;
            }

        }
    }

    public float[] getPoints() {
        return this.points;
    }

    public int getColor(){
        return this.color;
    }

}
