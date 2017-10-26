package com.chahat.quiz.Object;

import java.util.List;

/**
 * Created by chahat on 26/10/17.
 */

public class QuestionModel {

    private int id;
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private String[] incorrectAnswer;
    private String givenAnswer = null;

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getIncorrectAnswer() {
        return incorrectAnswer;
    }

    public void setIncorrectAnswer(String[] incorrectAnswer) {
        this.incorrectAnswer = incorrectAnswer;
    }
}
