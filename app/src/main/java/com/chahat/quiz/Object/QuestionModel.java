package com.chahat.quiz.Object;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chahat on 26/10/17.
 */

public class QuestionModel implements Parcelable{

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

    public QuestionModel(){

    }

    protected QuestionModel(Parcel in) {
        id = in.readInt();
        category = in.readString();
        type = in.readString();
        difficulty = in.readString();
        question = in.readString();
        correctAnswer = in.readString();
        incorrectAnswer = in.createStringArray();
        givenAnswer = in.readString();
        optionList = in.createStringArrayList();
        checkedId = in.readInt();
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel in) {
            return new QuestionModel(in);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(category);
        parcel.writeString(type);
        parcel.writeString(difficulty);
        parcel.writeString(question);
        parcel.writeString(correctAnswer);
        parcel.writeStringArray(incorrectAnswer);
        parcel.writeString(givenAnswer);
        parcel.writeStringList(optionList);
        parcel.writeInt(checkedId);
    }
}
