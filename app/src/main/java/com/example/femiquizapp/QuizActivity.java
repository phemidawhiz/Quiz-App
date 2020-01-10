package com.example.femiquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
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
    private int questionCountTotal;
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

        textColorDeafaultRb = option1.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNexQuestion();

        confirmNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!answered) {
                    if(option1.isChecked() || option2.isChecked() || option3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNexQuestion();
                }
            }
        });
    }

    private void showNexQuestion() {
        option1.setTextColor(textColorDeafaultRb);
        option2.setTextColor(textColorDeafaultRb);
        option3.setTextColor(textColorDeafaultRb);

        optionsRadioGroup.clearCheck();

        if(questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            questionView.setText(currentQuestion.getQuestion());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());

            questionCounter++;
            questionView.setText("Question: " + questionCounter + "/" + questionCountTotal);

            answered = false;
            confirmNextBtn.setText("Confirm");
        } else {
            finishQuiz();
        }
    }

    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(optionsRadioGroup.getCheckedRadioButtonId());
        int answerNum = optionsRadioGroup.indexOfChild(rbSelected) + 1;

        if(answerNum == currentQuestion.getAnswerNumber()) {
            score++;
            scoreView.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        option1.setTextColor(Color.RED);
        option2.setTextColor(Color.RED);
        option3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNumber()) {
            case 1:
                option1.setTextColor(Color.GREEN);
                questionView.setText("Answer 1 is correct");
                break;
            case 2:
                option2.setTextColor(Color.GREEN);
                questionView.setText("Answer 2 is correct");
                break;
            case 3:
                option3.setTextColor(Color.GREEN);
                questionView.setText("Answer 3 is correct");
                break;
        }

        if(questionCounter < questionCountTotal) {
            confirmNextBtn.setText("Next");
        } else {
            confirmNextBtn.setText("Finish");
        }
    }





    private void finishQuiz() {
        finish();;
    }
}
