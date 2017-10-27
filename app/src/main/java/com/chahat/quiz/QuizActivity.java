package com.chahat.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chahat.quiz.Object.QuestionModel;
import com.chahat.quiz.Object.QuizModel;
import com.chahat.quiz.adapter.QuestionAdapter;
import com.chahat.quiz.utils.JsonUtils;
import com.chahat.quiz.utils.NetworkConnection;
import com.chahat.quiz.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener{

    private QuizModel quizModel;
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
    @BindView(R.id.rl)
    RelativeLayout submitLayout;
    @BindView(R.id.activityLayout) RelativeLayout activityLayout;
    @BindView(R.id.textViewError)
    TextView textViewError;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        buttonSubmit.setOnClickListener(this);
        imageViewBack.setOnClickListener(this);

        quizModel = (QuizModel) getIntent().getSerializableExtra("QuizModel");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        questionAdapter = new QuestionAdapter(this,null);
        recyclerView.setAdapter(questionAdapter);

        if (savedInstanceState==null){
            fetchQuestion();
        }else {
            List<QuestionModel> list = savedInstanceState.getParcelableArrayList(SAVE_LIST);
            questionAdapter.setQuestionList(list);
            showData();
            mRecyclerState = savedInstanceState.getParcelable(SAVE_RECYLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerState);
        }

    }

    private void fetchQuestion(){
        if (NetworkConnection.isNetworkAvailable(this)){
            new FetchQuestion().execute();
        }else {
            showSnackbar();
            showEmptyView(getString(R.string.sorry_no_internet));
        }
    }

    private void showSnackbar(){
        final Snackbar snackbar = Snackbar
                .make(activityLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchQuestion();
                        view.setVisibility(View.GONE);
                    }
                });

        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
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
                showEmptyView(getString(R.string.sorry_no_question_found));
            }
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.imageViewBack:
                onBackPressed();
                break;
            case R.id.buttonSubmit:
                List<QuestionModel> questionList = questionAdapter.getQuestionList();
                Intent intent = new Intent(this,ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("QuestionList", (ArrayList<? extends Parcelable>) questionList);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;
        }


     }

     private void showProgress(){
         progressBar.setVisibility(View.VISIBLE);
         recyclerView.setVisibility(View.INVISIBLE);
         submitLayout.setVisibility(View.INVISIBLE);
         emptyView.setVisibility(View.GONE);
     }

     private void showData(){
         progressBar.setVisibility(View.GONE);
         recyclerView.setVisibility(View.VISIBLE);
         submitLayout.setVisibility(View.VISIBLE);
         emptyView.setVisibility(View.GONE);
     }

     private void showEmptyView(String error){
         progressBar.setVisibility(View.GONE);
         recyclerView.setVisibility(View.GONE);
         submitLayout.setVisibility(View.INVISIBLE);
         emptyView.setVisibility(View.VISIBLE);
         textViewError.setText(error);
     }
}
