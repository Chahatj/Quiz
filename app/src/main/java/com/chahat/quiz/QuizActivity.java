package com.chahat.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chahat.quiz.Object.QuestionModel;
import com.chahat.quiz.Object.QuizModel;
import com.chahat.quiz.adapter.QuestionAdapter;
import com.chahat.quiz.utils.JsonUtils;
import com.chahat.quiz.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private QuizModel quizModel;
    private final int LOADER_ID = 2;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private QuestionAdapter questionAdapter;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;
    private final String SAVE_LIST = "saveList";
    private final String SAVE_RECYLER_STATE = "saveRecyclerState";
    private Parcelable mRecyclerState;

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

        if (savedInstanceState==null){
            new FetchQuestion().execute();
        }else {
            List<QuestionModel> list = savedInstanceState.getParcelableArrayList(SAVE_LIST);
            questionAdapter.setQuestionList(list);
            showData();
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SAVE_RECYLER_STATE,mRecyclerState);
        outState.putParcelableArrayList(SAVE_LIST, (ArrayList<? extends Parcelable>) questionAdapter.getQuestionList());
    }

    public class FetchQuestion extends AsyncTask<Void,Void,List<QuestionModel>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected List<QuestionModel> doInBackground(Void... voids) {
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

        @Override
        protected void onPostExecute(List<QuestionModel> questionModels) {
            if (questionModels!=null && questionModels.size()>0){
                questionAdapter.setQuestionList(questionModels);
                showData();
            }else {
                showEmptyView();
            }
        }
    }

    @Override
    public void onClick(View view) {

        List<QuestionModel> questionList = questionAdapter.getQuestionList();
        Intent intent = new Intent(this,ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("QuestionList", (ArrayList<? extends Parcelable>) questionList);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
     }

     private void showProgress(){
         progressBar.setVisibility(View.VISIBLE);
         recyclerView.setVisibility(View.INVISIBLE);
         buttonSubmit.setEnabled(false);
         emptyView.setVisibility(View.GONE);
     }

     private void showData(){
         progressBar.setVisibility(View.GONE);
         recyclerView.setVisibility(View.VISIBLE);
         buttonSubmit.setEnabled(true);
         emptyView.setVisibility(View.GONE);
     }

     private void showEmptyView(){
         progressBar.setVisibility(View.GONE);
         recyclerView.setVisibility(View.GONE);
         buttonSubmit.setEnabled(false);
         emptyView.setVisibility(View.VISIBLE);
     }
}
