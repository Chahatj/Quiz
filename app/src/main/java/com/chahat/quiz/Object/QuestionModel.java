package com.chahat.quiz.Object;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chahat on 26/10/17.
 */

public class QuestionModel implements Serializable {

    private int id;
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private String[] incorrectAnswer;
    private String givenAnswer = null;
    private List<String> optionList;

    private int checkedId= -1;

    public int getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(int checkedId) {
        this.checkedId = checkedId;
    }

    public List<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
    }

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
