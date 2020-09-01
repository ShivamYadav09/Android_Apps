package com.example.eggtimer;

/*
    TO-DO: when the timer stops reset the timer automatically
*/

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button timerButton;
    boolean timerRunning = false;
    CountDownTimer countDownTimer;
    MediaPlayer mplayer;

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft%60;
        String minuteStr = String.format("%02d",minutes);
        String secondString = String.format("%02d",seconds);
        timerTextView.setText(minuteStr+":"+secondString);
    }

    public void controlTimer(View view){
        if(!timerRunning) {
            timerRunning = true;
            timerSeekBar.setEnabled(false);
            timerButton.setText(R.string.button1);
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 500, 1000) { //adding 500 milliseconds refer to video

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    mplayer = MediaPlayer.create(MainActivity.this, R.raw.nani);
                    mplayer.start();
                }
            }.start();
        }
        else{
            Log.i("info","stopping");
            timerTextView.setText(R.string.start);
            timerSeekBar.setProgress(10);
            countDownTimer.cancel();
            if(mplayer.isPlaying())
                mplayer.stop();
            timerButton.setText(R.string.button);
            timerSeekBar.setEnabled(true);
            timerRunning = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerButton = findViewById(R.id.timerButton);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.textView2);
        timerSeekBar.setMax(1800);
        timerSeekBar.setProgress(10);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
