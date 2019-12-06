package com.example.navdeep.guesstheobject;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Random;

public class GuessActivity extends AppCompatActivity implements Button.OnClickListener {
    public final static String EXTRA = "category";
    private TextView total_score, play, timeView, scoreCounter, highest, question, guess, next;
    private RelativeLayout topBar;
    private CardView cardView;
    private Button answer_one, answer_two, answer_three;
    private int size, score, seconds,count, highestScore;
    private String correct_answer, wrongAnswerOne, wrongAnswerTwo;
    private GuessItem[] items;
    private Random random;
    private Button[] buttons;
    private boolean running, flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alternative);
        inflateGuessMainLayout();
        runTimer();
        init();
        loadData();
        updateQuestion();

    }
    /* Loads the data file (containing questions and answers) from asset folder */
    private void loadData(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("questions.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String question = mLine;
                String correctAnswer = reader.readLine();
                String wrongAnswerOne = reader.readLine();
                String wrongAnswerTwo = reader.readLine();
                items[count] = new GuessItem(question,correctAnswer, wrongAnswerOne, wrongAnswerTwo);
                ++count;
                reader.readLine();
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }

    /* Stores answer buttons in an array for random access,
    *  initializes collection size and updates question
    */
    private void init(){
        buttons = new Button[3];
        buttons[0] = answer_one;
        buttons[1] = answer_two;
        buttons[2] = answer_three;
        items = new GuessItem[10];
        size = items.length;
        random = new Random();
    }

    /* Inflates a layout */
    private void inflateGuessMainLayout(){
        answer_one = (Button) findViewById(R.id.answer_one);
        answer_two = (Button)findViewById(R.id.answer_two);
        answer_three = (Button)findViewById(R.id.answer_three);
        timeView = (TextView)findViewById(R.id.timer);
        play = (TextView) findViewById(R.id.play);
        question = (TextView)findViewById(R.id.question);
        guess = (TextView)findViewById(R.id.guess);
        next = (TextView)findViewById(R.id.next);
        cardView = (CardView)findViewById(R.id.cardView);
        topBar = findViewById(R.id.topBar);
        total_score = findViewById(R.id.total_score);
        scoreCounter = findViewById(R.id.scoreCounter);
        highest = findViewById(R.id.highest);
        disableAnswerButtons();
    }

    private void updateQuestion(){
        GuessItem item = getQuestion();
        question.setText(item.getQuestion());
        correct_answer = item.getCorrectAnswer();
        updateAnswers();
        if(size>1)
        {
            --size;
        }else{
            if(Integer.parseInt(scoreCounter.getText().toString()) < score){
                scoreCounter.setText(String.valueOf(score));
            }
            reset();
            play.setVisibility(View.VISIBLE);
            timerBlink();
            running = false;
            disableAnswerButtons();
        }
    }

    private void disableAnswerButtons(){
        answer_one.setVisibility(View.INVISIBLE);
        answer_two.setVisibility(View.INVISIBLE);
        answer_three.setVisibility(View.INVISIBLE);
        question.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
    }

    private GuessItem getQuestion(){
        int number = random.nextInt(size);
        GuessItem item = items[number];
        items[number] = items[size-1];
        items[size-1] = item;
        wrongAnswerOne = item.getWrongAnswerOne();
        wrongAnswerTwo = item.getWrongAnswerTwo();

        return item;
    }

    private void updateAnswers(){
        for(int i=buttons.length-1; i>0;--i){
            int number = random.nextInt(i+1);
            Button button = buttons[number];
            buttons[number] = buttons[i];
            buttons[i] = button;
        }
        buttons[0].setText(correct_answer);
        buttons[0].setAllCaps(false);
        buttons[1].setText(wrongAnswerOne);
        buttons[1].setAllCaps(false);
        buttons[2].setText(wrongAnswerTwo);
        buttons[2].setAllCaps(false);
        buttons[0].setTransformationMethod(null);
    }

    private void updateScore(){
        if (score<0)score=0;

        scoreCounter.setText(String.valueOf(score));
    }

    private void reset(){
     score = 0;  size = 0;
     size = items.length;

     updateQuestion();
     updateScore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.answer_one:
                checkAnswer(answer_one.getText().toString());
                running = false;
                disableButtons();
                break;

            case R.id.answer_two:
                checkAnswer(answer_two.getText().toString());
                running = false;
                disableButtons();
                break;

            case R.id.answer_three:
                checkAnswer(answer_three.getText().toString());
                running = false;
                disableButtons();
                break;

            case R.id.play:
                play.setVisibility(View.INVISIBLE);
                timerBlink();
                setAllVisible();
                next.setEnabled(false);
                running = true;
                reset();
                break;

            case R.id.next:
                updateQuestion();
                updateTime();
                enableButtons();
                guess.setText("Guess");
                guess.setBackgroundColor(Color.parseColor("#00d598"));
                break;

            case R.id.playAgain:
                updateQuestion();
                updateTime();
                enableButtons();
                guess.setText("Guess");
                running = false;
                guess.setBackgroundColor(Color.parseColor("#00d598"));
                play.setVisibility(View.VISIBLE);
                topBar.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.INVISIBLE);
                reset();
                break;

            case R.id.quit:
                finish();
                break;

                default:

        }
    }

    private void disableButtons(){
        answer_one.setEnabled(false);
        answer_two.setEnabled(false);
        answer_three.setEnabled(false);
        next.setEnabled(true);
    }

    private void enableButtons(){
        answer_one.setEnabled(true);
        answer_two.setEnabled(true);
        answer_three.setEnabled(true);
        next.setEnabled(false);
    }

    private void timerBlink(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
               /* if(flag == true){
                    timeView.animate().alpha(0.5f);

                   flag = false;
                }else{
                    if(running) {
                        timeView.animate().alpha(1f);

                    }
                    flag = true;
                }*/
                handler.postDelayed(this, 500);
            }
        });
    }
    private void setAllVisible(){
        answer_one.setVisibility(View.VISIBLE);
        answer_two.setVisibility(View.VISIBLE);
        answer_three.setVisibility(View.VISIBLE);
        timeView.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
    }

    private void updateTime(){
        if(seconds<0){
            running = false;
        }else {
            timerReset();
            running = true;
        }
    }

    private void checkAnswer(String answer){
        if(answer.equalsIgnoreCase(correct_answer)){

           switch (seconds){
               case 1:
                   score+=5;break;
               case 2:
                   score+=4;break;
               case 3:
                   score+=3;break;
               case 4:
                   score+=2;break;
               case 5:
                   score+=1;break;
               default:
                   score--;
           }
            guess.setText("Correct");
            guess.setBackgroundColor(Color.parseColor("#8dc400"));
            updateScore();
        }else{
            guess.setText("Wrong");
            total_score.setText("Score: "+score);
            disableAnswerButtons();
            timerReset();
            play.setVisibility(View.INVISIBLE);
            topBar.setVisibility(View.INVISIBLE);
            timeView.setText("0");
            if(score > highestScore){
                highestScore = score;
            }
            highest.setText("Highest: "+highestScore);
            cardView.setVisibility(View.VISIBLE);
            guess.setBackgroundColor(Color.parseColor("#ee4d02"));
        }


    }
    /*This method will create a thread in background that will run until app main activity is destroyed*/
    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int sec = 5 - (seconds%60);
                String time = String.format("%d",sec);

                if(running){

                    timeView.setText(time);
                    seconds++;
                    if(seconds == 6)timerReset();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void timerReset(){
        seconds = 0;
        running = false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
