package com.example.navdeep.guesstheobject;

import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;

public class GuessItem {
    private Bitmap image;
    private String question;
    private String category;
    private String correctAnswer;
    private String wrongAnswerOne;
    private String wrongAnswerTwo;

    public GuessItem(String question, String correctAnswer, String wrongAnswerOne, String wrongAnswerTwo){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswerOne = wrongAnswerOne;
        this.wrongAnswerTwo = wrongAnswerTwo;
    }

    public GuessItem(Bitmap image, String category, String correctAnswer) {
        this.image = image;
        this.category = category;
        this.correctAnswer = correctAnswer;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswerOne(){ return wrongAnswerOne; }

    public String getWrongAnswerTwo(){ return wrongAnswerTwo; }


}
