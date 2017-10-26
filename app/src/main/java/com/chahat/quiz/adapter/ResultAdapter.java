package com.chahat.quiz.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chahat.quiz.Object.QuestionModel;
import com.chahat.quiz.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chahat on 26/10/17.
 */

public class ResultAdapter extends RecyclerView.Adapter {

    List<QuestionModel> questionList;
    Context context;

    public ResultAdapter(Context context, List<QuestionModel> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_question, parent, false);
                return new McTypeViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tf_question, parent, false);
                return new TF_TypeViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        QuestionModel questionModel = questionList.get(position);

        if (holder.getItemViewType() == 0) {

            ((McTypeViewHolder) holder).tvQuestion.setText(questionModel.getQuestion());
            ((McTypeViewHolder) holder).radioOption1.setText(questionModel.getCorrectAnswer());
            ((McTypeViewHolder) holder).radioOption1.setChecked(true);
            ((McTypeViewHolder) holder).radioOption2.setText(questionModel.getIncorrectAnswer()[0]);
            ((McTypeViewHolder) holder).radioOption3.setText(questionModel.getIncorrectAnswer()[1]);
            ((McTypeViewHolder) holder).radioOption4.setText(questionModel.getIncorrectAnswer()[2]);

        } else {

            ((TF_TypeViewHolder) holder).TF_tvQuestion.setText(questionModel.getQuestion());
            ((TF_TypeViewHolder) holder).TF_radioOption1.setText(questionModel.getCorrectAnswer());
            ((TF_TypeViewHolder) holder).TF_radioOption1.setChecked(true);
            ((TF_TypeViewHolder) holder).TF_radioOption2.setText(questionModel.getIncorrectAnswer()[0]);
        }

    }

    public void setQuestionList(List<QuestionModel> questionList) {
        this.questionList = questionList;
        notifyDataSetChanged();
    }

    public List<QuestionModel> getQuestionList() {
        return questionList;
    }

    @Override
    public int getItemViewType(int position) {

        String type = questionList.get(position).getType();

        if (type.equals("multiple")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if (questionList != null)
            return questionList.size();
        else return 0;
    }

    public class McTypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvQuestion)
        TextView tvQuestion;
        @BindView(R.id.radioOption1)
        RadioButton radioOption1;
        @BindView(R.id.radioOption2)
        RadioButton radioOption2;
        @BindView(R.id.radioOption3)
        RadioButton radioOption3;
        @BindView(R.id.radioOption4)
        RadioButton radioOption4;
        @BindView(R.id.radioOptions)
        RadioGroup radioGroup;

        public McTypeViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            radioGroup.setEnabled(false);
        }
    }

    public class TF_TypeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.TF_tvQuestion)
        TextView TF_tvQuestion;
        @BindView(R.id.TF_radioOption1)
        RadioButton TF_radioOption1;
        @BindView(R.id.TF_radioOption2)
        RadioButton TF_radioOption2;
        @BindView(R.id.radioOptions)
        RadioGroup TF_radioOptions;

        public TF_TypeViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            TF_radioOptions.setEnabled(false);
        }

    }
}