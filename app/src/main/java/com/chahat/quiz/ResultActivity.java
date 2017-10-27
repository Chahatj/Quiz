package com.chahat.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chahat.quiz.Object.QuestionModel;
import com.chahat.quiz.adapter.ResultAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvAttempt) TextView tvAttempt;
    private List<QuestionModel> questionList;
    @BindView(R.id.buttonFinish)
    Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        buttonFinish.setOnClickListener(this);

        questionList = getIntent().getExtras().getParcelableArrayList("QuestionList");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        int correctAnswer = 0;
        int attempt =0;

        for (int i=0;i<questionList.size();i++){

            QuestionModel questionModel = questionList.get(i);
            if (questionModel.getGivenAnswer()!=null){
                if (questionModel.getCorrectAnswer().equals(questionModel.getGivenAnswer())){
                    correctAnswer += 1;
                }
                attempt +=1;
            }

        }

        tvResult.setText(correctAnswer+"/"+questionList.size());
        tvAttempt.setText(String.valueOf(attempt));

        ResultAdapter resultAdapter = new ResultAdapter(this,questionList);
        recyclerView.setAdapter(resultAdapter);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
