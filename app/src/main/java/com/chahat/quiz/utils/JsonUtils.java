package com.chahat.quiz.utils;

import android.util.Log;

import com.chahat.quiz.Object.Category;
import com.chahat.quiz.Object.QuestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by chahat on 25/7/17.
 */

public class JsonUtils {


    public static List<Category> getAllCategory(String response){
        try {

            JSONObject responseObject = new JSONObject(response);

            JSONArray jsonArray = responseObject.getJSONArray("trivia_categories");

            List<Category> list = new ArrayList<>();

            for (int i=0;i<jsonArray.length();i++){

                Category category = new Category();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int id = jsonObject.getInt("id");
                category.setId(id);
                category.setName(name);
                list.add(category);
            }

            return list;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<QuestionModel> getAllQuestion(String response){
        try {
            JSONObject responseObject = new JSONObject(response);

            JSONArray jsonArray = responseObject.getJSONArray("results");

            List<QuestionModel> list = new ArrayList<>();

            for (int i=0;i<jsonArray.length();i++){


                QuestionModel questionModel = new QuestionModel();

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                questionModel.setId(i);
                questionModel.setCategory(jsonObject.getString("category"));
                questionModel.setType(jsonObject.getString("type"));
                questionModel.setDifficulty(jsonObject.getString("difficulty"));
                questionModel.setQuestion(jsonObject.getString("question"));
                questionModel.setCorrectAnswer(jsonObject.getString("correct_answer"));

                JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");

                String[] strings = new String[jsonArray1.length()];

                for (int j=0;j<jsonArray1.length();j++){
                    strings[j] = jsonArray1.optString(j);
                }
                questionModel.setIncorrectAnswer(strings);

                List<String> optionList;

                if (questionModel.getType().equals("multiple")){
                    String[] options = new String[] {questionModel.getCorrectAnswer()
                            ,questionModel.getIncorrectAnswer()[0],questionModel.getIncorrectAnswer()[1]
                            ,questionModel.getIncorrectAnswer()[2]};
                    optionList = Arrays.asList(options);
                }else {
                    String[] options = new String[] {questionModel.getCorrectAnswer()
                            ,questionModel.getIncorrectAnswer()[0]};
                    optionList = Arrays.asList(options);
                }
                Collections.shuffle(optionList);

                questionModel.setOptionList(optionList);

                list.add(questionModel);
            }

            return list;

        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
