package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    TextView resultText,pointsTextView,textView,timerText;
    Button startButton,but0,but1,but2,but3,playAgainButton;
    ArrayList<Integer> answers = new ArrayList<>();
    int locCorrect,noOfQues=0;
    int score = 0;

    public void playAgain(View view){
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            child.setEnabled(true);
        }
        score = 0;
        noOfQues = 0;
        timerText.setText("30s");
        pointsTextView.setText("0/0");
        resultText.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestions();
        new CountDownTimer(30500L, 1000L) {
            @Override
            public void onTick(long l) {
                timerText.setText((l / 1000)+"s");
            }

            @Override
            public void onFinish() {
                GridLayout gridLayout = findViewById(R.id.gridLayout);
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    View child = gridLayout.getChildAt(i);
                    child.setEnabled(false);
                }
                playAgainButton.setVisibility(View.VISIBLE);
                timerText.setText("0s");
                resultText.setText("Your Score: "+score + "/" + noOfQues);
            }
        }.start();
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        ConstraintLayout constraintLayout = findViewById(R.id.startScreen);
        constraintLayout.setVisibility(ConstraintLayout.VISIBLE);
        playAgain(findViewById(R.id.button4));  //does not matter what view is sent. Need to send a view, that's all.
    }

    public void generateQuestions(){
        but0 = findViewById(R.id.button0);
        but1 = findViewById(R.id.button1);
        but2 = findViewById(R.id.button2);
        but3 = findViewById(R.id.button3);
        Random rand = new Random();
        int a = rand.nextInt(30)+1;
        int b = rand.nextInt(30)+1;
        String dispStr = a+" + "+b;
        textView.setText(dispStr);
        locCorrect = rand.nextInt(4);
        int locCorrectClose = (locCorrect+1)%4;
        int incorrectAns;
        for(int i=0;i<4;++i){
            if(i==locCorrect)
                answers.add(a+b);
            else if(i==locCorrectClose)
                answers.add(a+b+rand.nextInt(4)+1);
            else{
                incorrectAns = rand.nextInt(70)+1;
                while(incorrectAns == a+b)
                    incorrectAns = rand.nextInt(70)+1;
                answers.add(incorrectAns);
            }
        }
        but0.setText(Integer.toString(answers.get(0)));
        but1.setText(Integer.toString(answers.get(1)));
        but2.setText(Integer.toString(answers.get(2)));
        but3.setText(Integer.toString(answers.get(3)));
        answers.clear();
    }

    public void chooseAns(View view){
        Log.i("Tag: ",(String)view.getTag());
        ++noOfQues;
        if(view.getTag().toString().equals(Integer.toString(locCorrect))){
            ++score;
            resultText.setText("Correct!");
        }
        else{
            resultText.setText("Wrong!");
        }
        pointsTextView.setText(score+"/"+noOfQues);
        generateQuestions();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button);
        textView = findViewById(R.id.textView3);
        pointsTextView = findViewById(R.id.textView2);
        resultText = findViewById(R.id.textView4);
        timerText = findViewById(R.id.textView);
        playAgainButton = findViewById(R.id.button4);

    }
}
