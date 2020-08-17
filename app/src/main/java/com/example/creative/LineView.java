package com.example.creative;
/**
 *LineView class generates all drawing operations
 *@version 14.05.2020
 *@author Deniz Özal & Remzi Tepe
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class LineView extends View {
    // properties
    private Paint paint = new Paint();
    private int[][] distanceArray = new int[ 30][ 40];
    private ArrayList<Integer> distance = new ArrayList<Integer>();
    private ArrayList<Integer> degrees = new ArrayList<Integer>();
    private ArrayList<Integer> countList = new ArrayList<Integer>();
    private ArrayList<Integer> booleanCheck = new ArrayList<Integer>();
    private int starterX;
    private int starterY;
    private int count = 0;
    private int i;
    private int numberOfLines = 0;
    private int countOfPieces = 0;
    private int countOfDistance = 0;
    private int countOfParts;
    private int numberOfMethod =  0;

    // constructors
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    // on draw method that draws the shapes
    protected void onDraw(final Canvas canvas){
        super.onDraw(canvas);
        // sets color and stroke width
        paint.setColor(Color.RED);
        paint.setStrokeWidth( 5);
        // set count of pieces to 0 every time when this method called on creative class
        countOfPieces = 0;

        // set count of parts to 0 every time when this method called on creative class
        countOfParts = 0;

        // sets starters locations to 150, 150
        starterX = 150;
        starterY = 100;

        // sets an integer that indicates how many shapes will be drawn each step
        i = 0;
        while( i <= numberOfLines && numberOfLines < distance.size() && countOfPieces < 1) {

            // sets an integer that indicates how many parts one line has.
            int j;
            j = 0;
            while(  j <= countList.get( i) && countList.get( i) < distance.get( i) / 5) {

                // draw the line with specified locations
                canvas.drawLine(starterX, starterY, starterX + (int)
                                (distanceArray[i][j]
                                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(i))))),
                        starterY + (int) (distanceArray[i][j]
                                * Math.sin(Math.toRadians(Double.valueOf(degrees.get(i))))), paint);

                // increments count of parts because each step one part increments
                countOfParts++;

                // if one line ends and new line should be started this condition provided
                if (j == countList.get(i) && countList.get(i) == distance.get(i) / 5 - 1) {
                    if( isNew( i)) {
                        booleanCheck.set( i, 1);
                        numberOfLines++;
                    }

                    // sets new locations
                    starterX = starterX + (int)
                            (distance.get(i)
                                    * Math.cos(Math.toRadians(Double.valueOf(degrees.get(i)))));
                    starterY = starterY + (int) (distance.get(i)
                            * Math.sin(Math.toRadians(Double.valueOf(degrees.get(i)))));
                }
                // increments j
                j++;
            }
            // if the line is new which means it is drawn for the first time, count list increments the number
            if (j != countList.get(i) && countList.get(i) != distance.get(i) / 5 - 1 && isNew( i) ) {
                countList.set(i, countList.get(i) + 1);
            }
            // then, i increments because the line increments naturally
            i++;

            // then, if count of parts equals to this equation count of pieces increments.
            if( countOfParts == countOfMethod() * ( countOfMethod() + 1) / 2)
                countOfPieces++;
        }

        // this is the method when all lines drawn it draws whole shape again
        if( numberOfLines == distance.size() ) {
            starterX = 150;
            starterY = 100;
            for (int k = 0; k < distance.size(); k++) {
                canvas.drawLine(starterX, starterY, starterX + (int)
                                (distance.get(k)
                                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(k))))),
                        starterY + (int) (distance.get(k)
                                * Math.sin(Math.toRadians(Double.valueOf(degrees.get(k))))), paint);
                starterX = starterX + (int) (distance.get(k)
                        * Math.cos(Math.toRadians(Double.valueOf(degrees.get(k)))));
                starterY = starterY + (int) (distance.get(k)
                        * Math.sin(Math.toRadians(Double.valueOf(degrees.get(k)))));
            }
        }
    }

    /**
     This method calls onDraw method whenever it is called from creativeMode class
     */
    public void draw(){
        invalidate();
        requestLayout();
    }

    /**
    This method adds distance to list distance array
    @param, int distances that the user add
    @return distances
     */
    public int addListDistanceArray( int distances){
        for( int i = 0; i < distances / 5; i++) {
            if( i > 0) {
                distanceArray[countOfDistance][i] = 5 + distanceArray[countOfDistance][i - 1];
            }
            else
                distanceArray[countOfDistance][i] = 5;
        }
        countOfDistance++;
        return distances;
    }

    /**
     This method add distance distances to arrayList
    @param, distances integer value of distance
    @return distances integer value of distance
     */
    public int addListDistance( int distances){
        distance.add( distances);
        return distances;
    }

    /**
    This method add degrees degree to arrayList
    @param, degree integer value of degree
    @return degree integer value of degree
     */
    public int addListDegree( int degree){
        degrees.add( degree);
        return degree;
    }

    /**
     * This method  counts the methods that is operated in creative mode
    @return numberOfMethod
     */
    public int countOfMethod(){
        numberOfMethod++;
        return numberOfMethod;
    }

    /**
     This method it starts the count list with specified values in it.
     */
    public void countListStarter(){
        for( int index = 0; index < distance.size(); index++) {
            countList.add(count);
            booleanCheck.add(0);
        }
    }

    /**
    This method controls if the value is new or not
    @param, int order that is the order of the value
    @return boolean true whether it is new
     */
    public boolean isNew( int order){
        if( countList.get( order) != 0 && booleanCheck.get( order) > 0){
            return false;
        }
        else {
            return true;
        }
    }
}