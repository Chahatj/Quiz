package com.chahat.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.chahat.quiz.Object.Category;
import com.chahat.quiz.Object.QuizModel;
import com.chahat.quiz.utils.JsonUtils;
import com.chahat.quiz.utils.NetworkConnection;
import com.chahat.quiz.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<List<Category>> {

    @BindView(R.id.spinnerCategory) Spinner spinnerCategory;
    private final int LOADER_ID = 1;
    @BindView(R.id.buttonTakeQuiz) Button buttonTakeQuiz;
    @BindView(R.id.buttonRandomQuiz) Button buttonRandomQuiz;
    @BindView(R.id.editTextQuestion) EditText editTextQuestion;
    @BindView(R.id.spinnerDifficulty) Spinner spinnerDifficulty;
    @BindView(R.id.spinnerType) Spinner spinnerType;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.activityLayout)
    LinearLayout activityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        buttonTakeQuiz.setOnClickListener(this);
        buttonRandomQuiz.setOnClickListener(this);

        initLoader();
    }

    private void initLoader(){
        if (NetworkConnection.isNetworkAvailable(this)){
            getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        }else {
            showSnackbar();
        }
    }

    @Override
    public Loader<List<Category>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Category>>(this) {


            List<Category> mList = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mList!=null){
                    deliverResult(mList);
                }else {
                    forceLoad();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void deliverResult(List<Category> data) {
                mList = data;
                super.deliverResult(data);
            }


            @Override
            public List<Category> loadInBackground() {
                URL url = NetworkUtils.builtURLCategory();
                try {
                    String response = NetworkUtils.getResponseFromHttpURL(url);
                    if (response!=null){
                        return JsonUtils.getAllCategory(response);
                    }else {
                        return null;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Category>> loader, List<Category> data) {
        if (data!=null && data.size()>0){
            ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_dropdown_item,data);
            spinnerCategory.setAdapter(adapter);
            buttonTakeQuiz.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Category>> loader) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.buttonTakeQuiz:
                checkData();
                break;
            case R.id.buttonRandomQuiz:
                generateRandomQuiz();
                break;
        }
    }

    private void generateRandomQuiz(){

        QuizModel quizModel = new QuizModel();
        quizModel.setNumQuestion(10);
        quizModel.setCategory(-1);
        quizModel.setDifficulty(null);
        quizModel.setType(null);

        goToQuizActivity(quizModel);

    }

    private void checkData(){
        if (!editTextQuestion.getText().toString().trim().isEmpty()){

            QuizModel quizModel = new QuizModel();

            int numQuestion = Integer.valueOf(editTextQuestion.getText().toString());
            quizModel.setNumQuestion(numQuestion);

            int categoryId = ((Category)spinnerCategory.getSelectedItem()).getId();
            quizModel.setCategory(categoryId);

            int difficultyPosition = spinnerDifficulty.getSelectedItemPosition();
            if (difficultyPosition==0){
                quizModel.setDifficulty(null);
            }else {
                String difficulty = getResources().getStringArray(R.array.difficulty_list_value)[difficultyPosition];
                quizModel.setDifficulty(difficulty);
            }

            int typePosition = spinnerType.getSelectedItemPosition();
            if (typePosition==0){
                quizModel.setType(null);
            }else {
                String type = getResources().getStringArray(R.array.type_list_value)[typePosition];
                quizModel.setType(type);
            }

            goToQuizActivity(quizModel);

        }else {
            editTextQuestion.setError("required");
        }
    }

    private void goToQuizActivity(QuizModel quizModel){
        Intent intent = new Intent(this,QuizActivity.class);
        intent.putExtra("QuizModel",quizModel);
        startActivity(intent);
    }

    private void showSnackbar(){
        final Snackbar snackbar = Snackbar
                .make(activityLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initLoader();
                        view.setVisibility(View.GONE);
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

}
