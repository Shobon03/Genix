package com.pjiproject.genix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsAdapter extends ArrayAdapter<Question> {

    /* ATTRIBUTES */
    private Context context;
    private ArrayList<Question> questions;


    /* CONSTRUCTOR */
    public QuestionsAdapter(Context context, ArrayList<Question> questions) {

        super(context, R.layout.question_list_item, questions);
        this.context = context;
        this.questions = questions;

    }


    // As a custom adapter, create a list item that has the question, the right answer and the user answer
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View questionView = li.inflate(R.layout.question_list_item, parent, false);

        TextView lblQuestionString = questionView.findViewById(R.id.lblQuestionString);
        TextView lblCorrectAnswer = questionView.findViewById(R.id.lblCorrectAnswer);
        TextView lblUserAnswer = questionView.findViewById(R.id.lblUserAnswer);

        lblQuestionString.setText(questions.get(position).getQuestion());
        lblCorrectAnswer.setText(questions.get(position).getCorrectAnswer());
        lblUserAnswer.setText(questions.get(position).getUserAnswer());

        return questionView;

    }

}
