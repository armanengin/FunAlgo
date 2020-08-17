package com.example.creative;
/**
 *CreativeMode class includes layouts and buttons
 *@version 14.05.2020
 *@author Deniz Özal & Remzi Tepe
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;


public class CreativeMode extends AppCompatActivity {

    // properties
    private Button clockwise;
    private Button counterClockwise;
    private Button applyCreative;
    private Button resetCreative;
    private Button returnButton;
    private Button volumeButton;
    private Button settingsButton;
    private Button infoButton;
    private Button tortoiseButton, humanButton,tigerButton;
    private LinearLayout linearLayout, linearLayout2;
    private String[] degrees = {"0", "30", "45","60", "90", "120","150", "180"};
    private String[] distance = {"10", "30", "60", "100","150", "200"};
    private int distanceGo;
    private int degreesTurn;
    private int buttonLimit;



    private int countOfPieces;
    private int background;
    private int delayTime;
    private android.widget.Spinner spinnerDegrees;
    private android.widget.Spinner spinnerDistance;
    private ArrayAdapter<String> dataAdapterForDistance;
    private ArrayAdapter<String> dataAdapterForDegrees;
    private LineView lineView;
    private ConstraintLayout creativeModeLayout;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sets layout creative mode xml
        setContentView(R.layout.activity_creative_mode);

        // Sets orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Find all properties below in xml codes
        creativeModeLayout = findViewById(R.id.creativeMode_layout);
        creativeModeLayout.setBackgroundResource(background);
        returnButton = findViewById(R.id.returnButton_creative);
        volumeButton = findViewById(R.id.volumeButton_creative);
        settingsButton = findViewById(R.id.settingButton_creative);
        infoButton = findViewById(R.id.info_creativeMode);
        clockwise = findViewById(R.id.clockwise_creative);
        counterClockwise = findViewById( R.id.counterClockwise_creative);
        tortoiseButton = findViewById(R.id.tortoise_button);
        humanButton = findViewById(R.id.human_creative);
        tigerButton = findViewById(R.id.tiger_creative);
        applyCreative = findViewById(R.id.applyCreative);
        resetCreative = findViewById(R.id.resetCreative);
        linearLayout = findViewById(R.id.applyLayout);
        linearLayout2 = findViewById(R.id.applyLayout2);
        lineView = findViewById(R.id.lineView);
        spinnerDegrees = findViewById(R.id.degreeSpinner);
        spinnerDistance = findViewById(R.id.distanceSpinner);

        // Setting default delay time
        setDelayTime(75);

        // Takes background image from shared preferences method
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        creativeModeLayout.setBackgroundResource(background);


        // Sets count of pieces to 0
        countOfPieces = 0;

        // Sets button limit to 0
        buttonLimit = 0;

        // Adds data adapter for bot distance and degree options in order to show them as spinner
        dataAdapterForDegrees = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, degrees);
        dataAdapterForDistance = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, distance);

        dataAdapterForDegrees.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDegrees.setAdapter(dataAdapterForDegrees);
        spinnerDistance.setAdapter(dataAdapterForDistance);

        // Sets touch listener for both clockwise and counter clockwise buttons
        clockwise.setOnTouchListener(new MyTouchListener());
        counterClockwise.setOnTouchListener( new MyTouchListener());

        // Sets drag listener to apply drag and drop action on those layouts
        linearLayout.setOnDragListener(new MyDragListener());
        linearLayout2.setOnDragListener(new MyDragListener());

        // if it is pressed, the game resets itself
        resetCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resets the whole layout
                recreate();
            }
        });

        // When all buttons added, the user pressed apply button and it draws the shapes asked user
        applyCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Unable apply button
                applyCreative.setEnabled(false);
                applyCreative.setBackgroundResource(R.drawable.button_design2);

                // Handler object to obtain timer when drawing shapes
                final Handler handler= new Handler();

                // It starts count list starter which is a method from line view class
                lineView.countListStarter();

                // It operates actions with specified time
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // It starts to count methods
                        lineView.countOfMethod();

                        // It starts to draw the shapes from nothing with delaying
                        lineView.draw();

                        // It controls the delay time
                        handler.postDelayed( this, getDelayTime());
                    }
                },getDelayTime());
            }
        });
        // When it is pressed, it works slowest mode
        tortoiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelayTime(100);
            }
        });
        // When it is pressed, it works middle speed mode
        humanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelayTime(75);
            }
        });
        // When it is pressed, it works fastest mode
        tigerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDelayTime(50);
            }
        });


        // When it is pressed, it gives info
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "You can drag and drop buttons to the left by choosing distances and degrees. " +
                                                                           "Also you can arrange the speed from tortoise, human and tiger ☺", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        // When it is pressed, it returns previous page
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // When it is pressed, it goes to setting page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( CreativeMode.this, SettingsPage.class);
                startActivity( intent);
            }
        });
    }
    /**
     * This method gets delayTime
     * @return delayTime
     */
    public int getDelayTime() {
        return delayTime;
    }
    /**
     * This method sets delayTime
     * @param, delayTime
     */
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    /**
     * Method to get Linear Layout
     * @return linearLayout
     */
    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    /**
    set Linear Layout asked
    @param, LinearLayout property
    @return linear layout
     */
    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    /**
     MyTouchListener inner class  operates the action of dragging
     */
    private final class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if ( event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data,shadowBuilder,v,0);
                v.setVisibility(View.VISIBLE);
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     MyDragListener inner class operates the drag and drop event
     */
    class MyDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action){
                case DragEvent.ACTION_DRAG_STARTED:
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    LinearLayout container = (LinearLayout)v;
                    if( view.getId() == R.id.clockwise_creative && v == getLinearLayout()){
                        buttonLimit++;
                        if( buttonLimit == 8) // If limit exceed 8 it goes to second layout

                            // Sets linear layout
                            setLinearLayout(linearLayout2);

                        // Convert spinner distance and degrees  to integer
                        distanceGo = Integer.parseInt((String) spinnerDistance.getSelectedItem());
                        degreesTurn = Integer.parseInt((String) spinnerDegrees.getSelectedItem());

                        // Degrees and distances are added to their list in lineview class
                        lineView.addListDegree( degreesTurn);
                        lineView.addListDistanceArray( distanceGo);
                        lineView.addListDistance( distanceGo);

                        // Then new clockwise button is created and text is set
                        clockwise = new Button(CreativeMode.this);
                        clockwise.setText( "↻" + distanceGo + "u " + degreesTurn + "°");
                        clockwise.setTextColor(Color.BLACK);
                        clockwise.setTextSize(10f);
                        clockwise.setBackgroundResource(R.drawable.button_design);
                        clockwise.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,95));
                        container.addView( clockwise);
                        clockwise.setVisibility(View.VISIBLE);
                    }

                    // It is same with the above but it is the situation if counter clockwise is dragged
                    else if( view.getId() == R.id.counterClockwise_creative && v == getLinearLayout()){
                        buttonLimit++;
                        if( buttonLimit == 8)
                            setLinearLayout(linearLayout2);
                        distanceGo = Integer.parseInt((String) spinnerDistance.getSelectedItem());
                        degreesTurn = 360 - Integer.parseInt((String) spinnerDegrees.getSelectedItem());
                        lineView.addListDegree( degreesTurn);
                        lineView.addListDistanceArray( distanceGo);
                        lineView.addListDistance( distanceGo);
                        counterClockwise = new Button(CreativeMode.this);
                        counterClockwise.setText( "↺" + distanceGo + "u " + ( 360 - degreesTurn)+ "°");
                        counterClockwise.setTextColor(Color.BLACK);
                        counterClockwise.setTextSize(10f);
                        counterClockwise.setBackgroundResource(R.drawable.button_design);
                        counterClockwise.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,95));
                        container.addView( counterClockwise);
                        counterClockwise.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            return true;
        }
    }
}
