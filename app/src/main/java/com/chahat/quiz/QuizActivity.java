package com.chahat.quiz;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chahat.quiz.Object.Category;
import com.chahat.quiz.Object.QuestionModel;
import com.chahat.quiz.Object.QuizModel;
import com.chahat.quiz.adapter.QuestionAdapter;
import com.chahat.quiz.utils.JsonUtils;
import com.chahat.quiz.utils.NetworkUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuestionModel>> ,View.OnClickListener{

    private QuizModel quizModel;
    private final int LOADER_ID = 2;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        buttonSubmit.setOnClickListener(this);

        quizModel = (QuizModel) getIntent().getSerializableExtra("QuizModel");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        questionAdapter = new QuestionAdapter(this,null);
        recyclerView.setAdapter(questionAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().restartLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<List<QuestionModel>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<QuestionModel>>(this) {

            List<QuestionModel> mList = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mList!=null){
                    deliverResult(mList);
                }else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(List<QuestionModel> data) {
                super.deliverResult(data);
                mList = data;
            }

            @Override
            public List<QuestionModel> loadInBackground() {



                try {
                    URL url = NetworkUtils.builtURLQuiz(quizModel.getNumQuestion(),
                            quizModel.getCategory(),quizModel.getDifficulty(),quizModel.getType());
                    String response = NetworkUtils.getResponseFromHttpURL(url);
                    return JsonUtils.getAllQuestion(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<QuestionModel>> loader, List<QuestionModel> data) {
        questionAdapter.setQuestionList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<QuestionModel>> loader) {

    }

    @Override
    public void onClick(View view) {

        List<QuestionModel> questionList = questionAdapter.getQuestionList();
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("QuestionList", (Serializable) questionList);
        startActivity(intent);
        finish();
     }
}
