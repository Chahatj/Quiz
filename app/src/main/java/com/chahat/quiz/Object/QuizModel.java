package com.chahat.quiz.Object;

import java.io.Serializable;

/**
 * Created by chahat on 25/10/17.
 */

public class QuizModel implements Serializable {

    private int numQuestion;
    private int category;
    private String type;
    private String difficulty;


    public int getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(int numQuestion) {
        this.numQuestion = numQuestion;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
