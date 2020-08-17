package com.example.educational;
/**
 *DefaultLevelPage class includes basics of movement and win & lose methods
 *@version 14.05.2020
 *@author Alperen Alkan & Fatih Kaplama
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.HomePage;
import com.example.FunAlgo.R;

public class DefaultLevelPage extends AppCompatActivity {
    //properties
    private int starsCount;
    private float x;
    private float y;
    private SharedPreferences sharedP;

    //methods
    /**
     * This level creates an TryAgain pop-up when the bee is out of the way
     * @param context
     */
    public void TryAgain(Context context ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View myView = getLayoutInflater().inflate(R.layout.tryagain, null);
        Button menu = (Button) myView.findViewById(R.id.menubtn);
        Button retry = (Button) myView.findViewById(R.id.retrybtn);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        builder.setView(myView);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method creates finish screen when bee is in the target
     * @param context
     * @param movementsCount
     * @param lower
     * @param upper
     */
    public void finishedScreen(final Context context, int movementsCount, int lower, int upper){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View myView = getLayoutInflater().inflate(R.layout.finishscreen, null);
        TextView message = myView.findViewById(R.id.message);
        ImageView star1 = myView.findViewById(R.id.star1);
        ImageView star2 = myView.findViewById(R.id.star2);
        ImageView star3 = myView.findViewById(R.id.star3);
        if (movementsCount <= lower){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            starsCount = 3;
        }
        else if (movementsCount > lower && movementsCount <= upper){
            star2.setVisibility(View.INVISIBLE);
            starsCount = 2;
        }
        else if (movementsCount > upper){
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.INVISIBLE);
            starsCount = 1;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("starsCount", starsCount);
        editor.commit();
        Button menu = (Button) myView.findViewById(R.id.menubtn);
        Button retry = (Button) myView.findViewById(R.id.retrybtn);
        Button continuebtn = (Button) myView.findViewById(R.id.continuebtn);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        builder.setView(myView);
        AlertDialog dialog = builder.create();
        dialog.show();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HomePage.class);
                startActivity(i);
            }
        });

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, LevelPage.class);
                startActivity(i);
            }
        });

    }

    /**
     * bee goes forward with this method
     * @param bee
     * @param changeX
     * @param changeY
     */
    public void GoForward(ImageView bee, int changeX, int changeY) {

        if (bee.getRotation() == 0) {
            y -= (changeY);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 90) {
            x += (changeX);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }

        if (bee.getRotation() == 180) {
            y += (changeY);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);
        }

        if (bee.getRotation() == -180) {
            y += (changeY);
            bee.setTranslationY(y);
            //bee.animate().translationY(y).setDuration(1000).setStartDelay(500);

        }
        if (bee.getRotation() == 270) {
            x -= (changeX);
            bee.setTranslationX(x);
        }

        if (bee.getRotation() == -90) {
            x -= (changeX);
            bee.setTranslationX(x);
            //bee.animate().translationX(x).setDuration(1000).setStartDelay(500);

        }
        System.out.println(bee.getX());
        System.out.println(bee.getY());
    }

    /**
     * set rotation +90
     * @param bee
     */
    public void TurnRight(ImageView bee) {

        bee.setRotation(bee.getRotation() + (90));
    }

    /**
     * set rotation -90
     * @param bee
     */
    public void TurnLeft(ImageView bee) {

        bee.setRotation(bee.getRotation() - (90));
    }


}
