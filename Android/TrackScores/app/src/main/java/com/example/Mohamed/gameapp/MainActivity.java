package com.example.Mohamed.gameapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Mohamed.gameapp.utils.SoundHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
    implements Balloon.BalloonListener{

    private static final int MIN_ANIMATION_DELAY = 500; // delay mellem lauched ballon
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;// skelne mellem lav level og høj level
    private static final int MAX_ANIMATION_DURATION = 8000;
    private static final int NUMBER_OF_PINS = 5; // antal liv
    private static final int BALLOONS_PER_LEVEL = 3;

    private ViewGroup mContentView;
    private int[] mBalloonColors = new int[3]; // forskellige farver
    private int mNextColor, mScreenWidth, mScreenHeight; //
    private int mLevel, mScore, mPinsUsed;// holder styr på score og liv
    TextView mScoreDisplay, mLevelDisplay; // holder styr på score
    private List<ImageView> mPinImages = new ArrayList<>();
    private List<Balloon> mBalloons = new ArrayList<>();
    private Button mGoButton;
    private boolean mPlaying;
    private boolean mGameStopped = true;
    private int mBalloonsPopped;
    private SoundHelper mSoundHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // tager fra activity main

        mBalloonColors[0] = Color.argb(255, 255, 0, 0); // de forskellige farver
        mBalloonColors[1] = Color.argb(255, 0, 255, 0);
        mBalloonColors[2] = Color.argb(255, 0, 0, 255);

        getWindow().setBackgroundDrawableResource(R.drawable.modern_background); // baggrund

        mContentView = (ViewGroup) findViewById(R.id.activity_main);
        setToFullScreen();

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver(); //for at finde ud af min screen width og height  + A view tree observer is used to register listeners that can be notified of global changes in the view tree.
        if (viewTreeObserver.isAlive()) { // tjekker om den er live så det ikke sker hele tiden
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToFullScreen();
            }
        });

        mScoreDisplay = (TextView) findViewById(R.id.score_display); // sætter de forskellige ting
        mLevelDisplay = (TextView) findViewById(R.id.level_display);// ind i variabler så
        mPinImages.add((ImageView) findViewById(R.id.pushpin1));  // der kan holdes styr på det
        mPinImages.add((ImageView) findViewById(R.id.pushpin2)); // eks alle pins ind i et array
        mPinImages.add((ImageView) findViewById(R.id.pushpin3));// af pins
        mPinImages.add((ImageView) findViewById(R.id.pushpin4));
        mPinImages.add((ImageView) findViewById(R.id.pushpin5));
        mGoButton = (Button) findViewById(R.id.go_button);

        updateDisplay();

        mSoundHelper = new SoundHelper();
        mSoundHelper.prepareMusicPlayer(this);


    }

    private void setToFullScreen() { // for at sætte det til fullscreen, bliver kaldt i start game og on resume
        ViewGroup rootLayout = (ViewGroup) findViewById(R.id.activity_main);
        rootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override// en af de 5 lifecycle events
    protected void onResume() { // on resume er når den er aktiv kommer typisk efter onCreate og Onstart, OnPause on Onstop har jeg ikke brugt i min app
        super.onResume();
        setToFullScreen();
    }

    private void startGame() { // starter spil, bliver kaldt af goButtonHandler(PLay knappen)
        setToFullScreen();
        mScore = 0;
        mLevel = 0;
        mPinsUsed = 0;
        for (ImageView pin : //  nulstiller den pins
                mPinImages) {
            pin.setImageResource(R.drawable.pin);
        }
        mGameStopped = false;
        startLevel();
        mSoundHelper.playMusic();
    }

    private void startLevel() {
        mLevel++;
        updateDisplay();
        BalloonLauncher launcher = new BalloonLauncher();//laver en klasse med ballonlauncer "tråden"
        launcher.execute(mLevel);//starter tråd og går in i DoInbackground()
        mPlaying = true;
        mBalloonsPopped = 0;
        mGoButton.setText("Stop game"); // ændre teksten på knappen
    }

    private void finishLevel() { // ændre navn på knap og giver en popup besked om at level r done
        Toast.makeText(this, String.format("You finished level %d", mLevel),
                Toast.LENGTH_SHORT).show();
        mPlaying = false;
        mGoButton.setText(String.format("Start level %d", mLevel + 1));
    }

    public void goButtonClickHandler(View view) { // start knap

        if (mPlaying) { // hvis den bliver trykke mens jeg spiller = game over
            gameOver(false);
        } else if (mGameStopped) { // hvis gamet endnu ikke er startet så starter den på ny
            startGame();
        } else {
            startLevel(); // går videre til næste lvl
        }
    }

    @Override
    public void popBalloon(Balloon balloon, boolean userTouch) {

        mBalloonsPopped++;

        mContentView.removeView(balloon);
        mBalloons.remove(balloon);

        if (userTouch) {
            mScore++;
        } else {
            mPinsUsed++; // tabt liv
            if (mPinsUsed <= mPinImages.size()) { // tjekker om du død
                mPinImages.get(mPinsUsed - 1)
                        .setImageResource(R.drawable.pin_off); // viser tabt live
            }
            if (mPinsUsed == NUMBER_OF_PINS) { // hvis alle pins used så det gameover
                gameOver(true);
                return;
            } else {
                Toast.makeText(this, "Missed that one!", Toast.LENGTH_SHORT).show();
            }
        }
        updateDisplay();

        if (mBalloonsPopped == BALLOONS_PER_LEVEL) {
            finishLevel();
        }
    }

    private void gameOver(boolean b) {
        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
        mSoundHelper.pauseMusic();

        for (Balloon balloon :// fjerne
                mBalloons) {
            mContentView.removeView(balloon);
            balloon.setPopped(true); // sætter  balloner til staus popped og canceler animation
        }
        mBalloons.clear(); // sletter balloner fra array
        mPlaying = false;
        mGameStopped = true;
        mGoButton.setText("Start game");
    }

    private void updateDisplay() { // opdatere i henhold til lvl og score
        mScoreDisplay.setText(String.valueOf(mScore));
        mLevelDisplay.setText(String.valueOf(mLevel));
    }

    private class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {// hvilke parameter for klassen, f.eks. integer til DOiNbackGround metode, integer til onpregessUpdate men den retunere void
        // https://www.youtube.com/watch?v=dVwR5Gpw1_E cirka min 4
        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];
            int maxDelay = Math.max(MIN_ANIMATION_DELAY,
                    (MAX_ANIMATION_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int balloonsLaunched = 0;
            while (mPlaying && balloonsLaunched < BALLOONS_PER_LEVEL) {

//              Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(mScreenWidth - 200);
                publishProgress(xPosition); // kalder automatisk onProgessUpdate som opdatere main tråden !Fra video https://www.youtube.com/watch?v=V4q0sTIntsk
                balloonsLaunched++;

//              Wait a random number of milliseconds before looping
                int delay = random.nextInt(minDelay) + minDelay; //Delay for balloner
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) { // bliver kaldt af publishprogess i baggrunden og opdatere main tråd
            super.onProgressUpdate(values);
            int xPosition = values[0]; // metoden forventer et array men du giver kun en int(linje 216) derfor tager man den første værdi a value-arrayet(magisk omdaning)
            launchBalloon(xPosition); // launcer ballon
        }

    }

    private void launchBalloon(int x) { //parameter er x position for ballon

        Balloon balloon = new Balloon(this, mBalloonColors[mNextColor], 150); //laver ballon og farve
        mBalloons.add(balloon);

        if (mNextColor + 1 == mBalloonColors.length) {
            mNextColor = 0;
        } else {
            mNextColor++;
        }

//      Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(mScreenHeight + balloon.getHeight());
        mContentView.addView(balloon);

//      Let 'er fly
        int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000)); // hastighed ifh level
        balloon.releaseBalloon(mScreenHeight, duration); //ballon sendes afsted med hastihed og

    }
}
