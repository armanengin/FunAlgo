package com.example.educational;
/**
 *Level1Page class includes button methods and this class is a super class of all other level pages
 *@version 06.05.2020
 *@author Alperen Alkan & Fatih Kaplama & Arman Engin Sucu
 */
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FunAlgo.R;
import com.example.menu.SettingsPage;
import com.example.interfaces.ShowCodeI;

import java.util.ArrayList;

public class Level1Page extends DefaultLevelPage implements ShowCodeI {
    //properties
    final private int[] targetArea = {600, 184};
    final private int[] nonForbiddenAreaX = {200, 400, 600};
    final private int[] nonForbiddenAreaY = {184, 184, 184};
    final static private int changeX = 200;
    final static private int changeY = 180;
    private int volumeoffID;
    private int volumeonID;
    private int count = 0;
    private int timesForward;
    private int movementsCount;
    private int timesLeft;
    private int timesRight;
    private int background;
    private boolean isSelected;
    private boolean isSelected2;
    private boolean heroHasKey;
    private boolean isVolumeOn;
    private boolean isTryAgain;
    private boolean isGameOver;
    private int starsCount;
    private TextView nu;
    private TextView movements;
    private Spinner spinnerForward;
    private Spinner spinnerLeft;
    private Spinner spinnerRight;
    private Integer[] times = {1, 2, 3};
    private ArrayAdapter<Integer> timesAdapter;
    private ArrayList<String> list;
    private ImageView bee;
    private ImageView honey;
    private Button goForward;
    private Button turnRight;
    private Button turnLeft;
    private Button settings;
    private Button volume;
    private Button back;
    private Button info;
    private Button apply;
    private Button reset;
    private Button show;
    private ConstraintLayout level1Page;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout.LayoutParams params;
    private Drawable volumeoff;
    private Drawable volumeon;
    private String code;
    private int buttonID;
    private Drawable button;

    //sharedPreferences to update and save levels
    private SharedPreferences sp;
    private SharedPreferences.Editor et;

    // sharedPreferences for transport data to AchievementsPage
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //sharedPreferences to move accordingly
    SharedPreferences sharedP;
    SharedPreferences.Editor etS;

    @SuppressLint({"RestrictedApi", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //starting activity
        Intent i = getIntent();
        level1Page = findViewById(R.id.level1_page_layout);
        background = getSharedPreferences("ShareTheme",MODE_PRIVATE).getInt("theme",0);
        level1Page.setBackgroundResource(background);

        //defining the variables
        reset = findViewById(R.id.reset);
        apply = findViewById(R.id.apply);
        bee = findViewById(R.id.bee);
        honey = findViewById(R.id.honey);
        goForward = findViewById(R.id.goForward);
        turnLeft = findViewById(R.id.turnLeft);
        turnRight = findViewById(R.id.turnRight);
        settings = findViewById(R.id.settings);
        volume = findViewById(R.id.volume);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        layout1 = findViewById(R.id.leftLayout1);
        layout2 = findViewById(R.id.leftLayout2);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        movements = findViewById(R.id.movements);
        show = findViewById(R.id.showCode_button);
        code = "";

        //volume
        isVolumeOn = true;
        volumeonID = R.drawable.volumeon;
        volumeoffID = R.drawable.volumeoff;
        volumeon = AppCompatDrawableManager.get().getDrawable(this, volumeonID);
        volumeoff = AppCompatDrawableManager.get().getDrawable(this, volumeoffID);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.daybreaker);

        //spinner
        timesAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, times);
        timesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForward.setAdapter(timesAdapter);
        spinnerRight.setAdapter(timesAdapter);
        spinnerLeft.setAdapter(timesAdapter);
        spinnerForward = findViewById(R.id.spinnerForward);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);

        list = new ArrayList<String>();
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 80);

        isGameOver = false;
        isTryAgain = false;
        isSelected = false;
        movementsCount = 0;
        buttonID = R.drawable.button_design;
        button = AppCompatDrawableManager.get().getDrawable(this, buttonID);
        //SharedPreferences to save Level
        sp = getSharedPreferences("isFinishedBooleans", MODE_PRIVATE);
        et = sp.edit();

        sharedP = getSharedPreferences("isThis", MODE_PRIVATE);
        etS = sharedP.edit();
        etS.clear();
        isTryAgain = getSharedPreferences("isThis", MODE_PRIVATE).getBoolean("isTry", false);
        isGameOver = getSharedPreferences("isThis", MODE_PRIVATE).getBoolean("isOver", false);
        movementsCount = getSharedPreferences("isThis", MODE_PRIVATE).getInt("movements", 0);

        //to finish the level
        isFinished(Level1Page.this, "1", 20 , 30);

        //if the user goes out of the way
        if (isTryAgain) {
            TryAgain(Level1Page.this);
            etS.putBoolean("isTry", false);
            etS.commit();
        }

        //when the user presses the back button, he switches to Level Page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level1Page.this, LevelPage.class);
                startActivity(i);
            }
        });

        //when the user presses the settings, he switches to Settings
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Level1Page.this, SettingsPage.class);
                startActivity(i);
            }
        });
        //the user can turn on or off the sound when pressing the sound icon
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVolumeOn) {
                    volume.setBackground(volumeoff);
                    isVolumeOn = false;
                    mediaPlayer.pause();
                } else {
                    volume.setBackground(volumeon);
                    isVolumeOn = true;
                    mediaPlayer.start();
                    ;
                }
            }
        });

        //when the user presses the info, he can get information about level
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Bee needs to reach to hive. Help it with your algorithm!", Toast.LENGTH_LONG);
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        //to show the current codes
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                if (code == "") {
                    toast = Toast.makeText(getApplicationContext(), "No code here yet.", Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG);
                }
                View view = toast.getView();
                view.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();
            }
        });

        //to reset the game
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
                code = "";
            }
        });

        //to apply the activities selected by the user
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply.setEnabled(false);
                ApplyMove applyMove = new ApplyMove(bee, list, changeX, changeY, targetArea, nonForbiddenAreaX, nonForbiddenAreaY,0, 0, 0, 0, null, null,null,0,0, movementsCount);
                Thread t1 = new Thread(applyMove);
                t1.start();
            }
        });

        //when the user click the GO FORWARD
        goForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = goForwardButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerForward);
            }
        });

        //when the user click the TURN LEFT
        turnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnLeftButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerLeft);
            }
        });

        //when the user click the TURN RIGHT
        turnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movementsCount = turnRightButton(timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerRight);
            }
        });
    }

    //methods
    /** This method adds the GO FORWARD activity selected by the user to the layout
     * @param, timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerForward
     * @return number of movements
     **/
    public int goForwardButton(int timesForward, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerForward) {
        String codeMessage;
        timesForward = (Integer) spinnerForward.getSelectedItem();
        if (timesForward == 1) {
            codeMessage = "goForward();";
        } else {
            codeMessage = "for(int i = 0 ; i < " + timesForward + " ; i++){\n" +
                    "goForward()\n}";
        }
        SaveData(codeMessage);
        setCodeMessage();
        if (movementsCount >= 9) {
            list.add("forward" + timesForward);
            Button forward = new Button(Level1Page.this);
            forward.setTextSize(10);
            forward.setText(timesForward + " " + "GO FORWARD");
            forward.setBackground(button);
            layout2.addView(forward, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }

        if (movementsCount < 9) {
            list.add("forward" + timesForward);
            Button forward = new Button(Level1Page.this);
            forward.setTextSize(10);
            forward.setText(timesForward + " " + "GO FORWARD");
            forward.setBackground(button);
            layout1.addView(forward, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    /** This method adds the TURN LEFT activity selected by the user to the layout
     * @param, timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerLeft
     * @return number of movements
     **/
    public int turnLeftButton(int timesLeft, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerLeft) {
        String codeMessage;
        timesLeft = (Integer) spinnerLeft.getSelectedItem();
        if (timesLeft == 1) {
            codeMessage = "turnLeft();";
        } else {
            codeMessage = "for(int i = 0 ; i < " + timesLeft + " ; i++){\n" +
                    "turnLeft()\n}";
        }
        SaveData(codeMessage);
        setCodeMessage();
        if (movementsCount >= 9) {
            list.add("left" + timesLeft);
            Button left = new Button(Level1Page.this);
            left.setTextSize(10);
            left.setText(timesLeft + " " + "TURN LEFT");
            left.setBackground(button);
            layout2.addView(left, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        if (movementsCount < 9) {
            list.add("left" + timesLeft);
            Button left = new Button(Level1Page.this);
            left.setTextSize(10);
            left.setText(timesLeft + " " + "TURN LEFT");
            left.setBackground(button);
            layout1.addView(left, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    /** This method adds the TURN RIGHT activity selected by the user to the layout
     * @param, timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerRight
     * @return number of movements
     **/
    @SuppressLint("SetTextI18n")
    public int turnRightButton(int timesRight, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int count, int movementsCount, TextView movements, Spinner spinnerRight) {
        String codeMessage;
        timesRight = (Integer) spinnerRight.getSelectedItem();
        if (timesRight == 1) {
            codeMessage = "turnRight();";
        } else {
            codeMessage = "for(int i = 0 ; i < " + timesRight + " ; i++){\n" +
                    "turnRight()\n}";
        }
        SaveData(codeMessage);
        setCodeMessage();
        if (movementsCount >= 9) {
            list.add("right" + timesRight);
            Button right = new Button(Level1Page.this);
            right.setTextSize(10);
            right.setText(timesRight + " " + "TURN RIGHT");
            right.setBackground(button);
            layout2.addView(right, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        if (movementsCount < 9) {
            list.add("right" + timesRight);
            Button right = new Button(Level1Page.this);
            right.setTextSize(10);
            right.setText(timesRight + " " + "TURN RIGHT");
            right.setBackground(button);
            layout1.addView(right, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    /** This method adds the GET ITEM activity selected by the user to the layout
     * @param, timesForward, layout1, layout2, list, count, movementsCount, movements, spinnerNectar, object
     * @return number of movements
     **/
    public int getNectarButton(int timesNectar, LinearLayout layout1, LinearLayout layout2, ArrayList<String> list, int movementsCount, TextView movements, Spinner spinnerNectar, String object) {
        String codeMessage;
        timesNectar = (Integer) spinnerNectar.getSelectedItem();
        if (timesNectar == 1) {
            codeMessage = "getNectar();";
        } else {
            codeMessage = "for(int i = 0 ; i < " + timesNectar + " ; i++){\n" +
                    "getNectar()\n}";
        }
        SaveData(codeMessage);
        setCodeMessage();
        if (movementsCount >= 9) {
            System.out.println("burası çalışıyor");
            list.add(object + timesNectar);
            Button nectar = new Button(Level1Page.this);
            nectar.setTextSize(10);
            nectar.setText(timesNectar + " " + "GET " + object);
            nectar.setBackground(button);
            layout2.addView(nectar, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        if (movementsCount < 9) {
            list.add(object + timesNectar);
            System.out.println(list.toString());
            Button nectar = new Button(Level1Page.this);
            nectar.setTextSize(10);
            nectar.setText(timesNectar + " " + "GET "+ object);
            nectar.setBackground(button);
            layout1.addView(nectar, params);
            count++;
            movementsCount++;
            movements.setText("Movements : " + movementsCount);
        }
        return movementsCount;
    }

    /** This method allows the user to get the nectar
     * if the user comes to the square which there is the nectar
     * @param, bee, valueX1, valueX2, valueY1, valueY2, nu, nu2, nu3, valueX3, valueY3
     * @return
     **/
    public void GetNectar(ImageView bee, int valueX1, int valueX2, int valueY1, int valueY2, final TextView nu, final TextView nu2,final ImageView nu3, int valueX3, int valueY3) {
        System.out.println("workkkkk");
        if (bee.getX() == valueX1 && bee.getY() == valueY1) {
            isSelected = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nu.setText("0");
                }
            });
        }
        if (bee.getX() == valueX2 && bee.getY() == valueY2) {
            isSelected2 = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nu2.setText("0");
                }
            });
        }
        if (bee.getX() == valueX3 && bee.getY() == valueY3) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("çalıştımm");
                    nu3.setVisibility(View.INVISIBLE);
                    heroHasKey = true;
                }
            });
        }
    }

    /** This method saves the data
     * @param, codeMessage
     * @return
     **/
    public void SaveData(String codeMessage) {
        SharedPreferences sharedPref = Level1Page.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CODEMESSAGE", codeMessage);
        editor.commit();
    }

    /** This method sets the code message
     * @param
     * @return
     **/
    public void setCodeMessage() {
        SharedPreferences sharedPref = Level1Page.this.getPreferences(Context.MODE_PRIVATE);
        code += sharedPref.getString("CODEMESSAGE", "") + "\n";
    }

    /** This method checks if the user finished the level
     * @param, context, level, lower, upper
     * @return
     **/
    public void isFinished(Context context, String level, int lower, int upper){
        if (isGameOver) {
            System.out.println("Movements : " + movementsCount);
            et.putBoolean("finished" + level, true);
            //for finish screen
            finishedScreen(context, movementsCount, lower, upper);
            sharedPreferences = getSharedPreferences("starsData", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            starsCount = sharedPreferences.getInt("starsCount", 3);
            editor.putInt("starsCountLevel" + level, starsCount);
            editor.commit();
            et.commit();
            etS.putBoolean("isOver", false);
            etS.commit();
        }
    }

    //inner class
    /**this class applies the actions selected by the user one by one
     *
     */
    public class ApplyMove implements Runnable {
        //variables
        private ArrayList<String> list;
        private ImageView bee;
        private ImageView nu3;
        private int movements;
        private int changeX;
        private int changeY;
        private int valueX1;
        private int valueX2;
        private int valueY1;
        private int valueY2;
        private int valueX3;
        private int valueY3;
        private int[] target;
        private int[] nonForbiddenAreaX;
        private int[] nonForbiddenAreaY;
        private boolean isForbidden;
        private TextView nu;
        private TextView nu2;

        //constructor
        public ApplyMove(ImageView bee, ArrayList<String> list, int changeX, int changeY, int[] target, int[] nonForbiddenAreaX, int[] nonForbiddenAreaY,
                         int valueX1, int valueX2, int valueY1, int valueY2, TextView nu, TextView nu2, ImageView nu3, int valueX3,int valueY3, int movements) {
            this.bee = bee;
            this.list = list;
            this.changeX = changeX;
            this.changeY = changeY;
            this.target = target;
            this.nonForbiddenAreaX = nonForbiddenAreaX;
            this.nonForbiddenAreaY = nonForbiddenAreaY;
            isForbidden = true;
            this.valueX1 = valueX1;
            this.valueX2 = valueX2;
            this.valueY1 = valueY1;
            this.valueY2 = valueY2;
            this.nu = nu;
            this.nu2 = nu2;
            this.nu3 = nu3;
            this.valueX3 = valueX3;
            this.valueY3 = valueY3;
            this.movements = movements;
        }

        //this method moves the user according to the number of activities
        public void run() {
            try {
                for (int i = 0; i < list.size(); i++) {
                    isForbidden = true;
                    Thread.sleep(1000);
                    if (list.get(i).equals("forward1")) {
                        GoForward(bee, changeX, changeY);
                    }
                    else if (list.get(i).equals("forward2")) {
                        for (int k = 0; k < 2; k++) {
                            GoForward(bee, changeX, changeY);
                        }
                    }
                    else if (list.get(i).equals("forward3")) {
                        for (int k = 0; k < 3; k++) {
                            GoForward(bee, changeX, changeY);
                        }
                    }
                    else if (list.get(i).equals("left1")) {

                        TurnLeft(bee);
                    }
                    else if (list.get(i).equals("left2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnLeft(bee);
                        }
                    }
                    else if (list.get(i).equals("left3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnLeft(bee);
                        }
                    }
                    else if (list.get(i).equals("right1")) {
                        TurnRight(bee);
                    }
                    else if (list.get(i).equals("right2")) {
                        for (int k = 0; k < 2; k++) {
                            TurnRight(bee);
                        }
                    }
                    else if (list.get(i).equals("right3")) {
                        for (int k = 0; k < 3; k++) {
                            TurnRight(bee);
                        }
                    }
                    else if (list.get(i).equals("NECTAR1")) {
                        System.out.println("çalıştım");
                        GetNectar(bee, valueX1, valueX2, valueY1, valueY2, nu, nu2, nu3, valueX3, valueY3);
                    }
                    else if (list.get(i).equals("NECTAR2")) {
                        for (int k = 0; k < 2; k++) {
                            GetNectar(bee, valueX1, valueX2, valueY1, valueY2, nu, nu2, nu3, valueX3, valueY3);
                        }
                    }
                    else if (list.get(i).equals("NECTAR3")) {
                        for (int k = 0; k < 3; k++) {
                            GetNectar(bee, valueX1, valueX2, valueY1, valueY2, nu, nu2, nu3, valueX3, valueY3);
                        }
                    }
                    else if (list.get(i).equals("KEY1")) {
                        for (int k = 0; k < 3; k++) {
                            GetNectar(bee, valueX1, valueX2, valueY1, valueY2, nu, nu2, nu3, valueX3, valueY3);
                        }
                    }


                    //for the level where a key available
                    if(nu3 != null) {
                        if (heroHasKey && (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }

                    //for the level where no item is available
                    else if (nu2 == null && nu == null) {
                        if ((bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }

                    //for the level where a nectar is available
                    else if (nu2 == null && nu != null) {
                        if (isSelected && (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }

                    //for the level where two nectar are available
                    else if (nu2 != null && nu != null) {
                        if (isSelected2 && isSelected&& (bee.getX() == target[0]) && (bee.getY() == target[1])) {
                            etS.putBoolean("isOver", true);
                            etS.commit();
                            Intent j = getIntent();
                            finish();
                            startActivity(j);
                        }
                    }
                    for (int t = 0; t < nonForbiddenAreaY.length; t++) {
                        if ((bee.getX() == nonForbiddenAreaX[t] && bee.getY() == nonForbiddenAreaY[t]))
                            isForbidden = false;
                    }

                    //if the user goes out of the way
                    if (isForbidden) {
                        etS.putBoolean("isTry", true);
                        etS.commit();
                        Intent j = getIntent();
                        finish();
                        startActivity(j);
                        break;
                    }
                }
            } catch (InterruptedException e) {
            }
            etS.putInt("movements" , movements);
            etS.commit();
        }
    }
}
