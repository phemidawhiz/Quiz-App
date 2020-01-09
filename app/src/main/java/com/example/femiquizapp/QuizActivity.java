package com.example.femiquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionCount;
    private TextView scoreView;
    private TextView countDownView;
    private TextView questionView;
    private RadioGroup optionsRadioGroup;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private Button confirmNextBtn;

    private ColorStateList textColorDeafaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int getQuestionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionCount = findViewById(R.id.questionCount);
        scoreView = findViewById(R.id.scoreView);
        countDownView = findViewById(R.id.countdownView);
        questionView = findViewById(R.id.questionView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        confirmNextBtn = findViewById(R.id.confirmNextBtn);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
    }
}
