package hr.dante_ri.kviz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    String[] questions = {
            "King Henry VIII was the second monarch of which European royal house?",
            "What was the game 'Galaga' was a sequel to?",
            "The song 'Little April Shower' features in which Disney cartoon film?",
            "Which Shakespeare play inspired the musical 'West Side Story'?",
            "Which of these games was the earliest known first-person shooter with a known time of publication?"
    };
    String[][] answers = {
            {"Tudor", "York", "Stuart", "Lancaster"},
            {"Galaxian", "Galactica", "Space Invaders", "Galactic Wars"},
            {"Bambi", "Cinderella", "Pinocchio", "The Jungle Book"},
            {"Romeo and Juliet", "Hamlet", "Macbeth", "Othello"},
            {"Spasim", "Doom", "Wolfenstein", "Quake"}
    };
    String[] correct = {"Tudor", "Galaxian", "Bambi", "Romeo and Juliet", "Spasim"};

    TextView title, txtQuestion, ans1, ans2, ans3, ans4;
    Button btnAnswer;
    int currentQuestion = 0, points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Button btnQuit = findViewById(R.id.btnBackQuestion);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnAnswer = findViewById(R.id.btnAnswer);
        title = findViewById(R.id.txtQuestionNum);
        txtQuestion = findViewById(R.id.txtQuestion);
        ans1 = findViewById(R.id.txtAnswer1);
        ans2 = findViewById(R.id.txtAnswer2);
        ans3 = findViewById(R.id.txtAnswer3);
        ans4 = findViewById(R.id.txtAnswer4);

        setupQuestion(currentQuestion);

        View.OnClickListener answerClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans1.setTypeface(null, Typeface.NORMAL);
                ans1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                ans2.setTypeface(null, Typeface.NORMAL);
                ans2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                ans3.setTypeface(null, Typeface.NORMAL);
                ans3.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                ans4.setTypeface(null, Typeface.NORMAL);
                ans4.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                TextView currentAnswer = (TextView) view;
                currentAnswer.setTypeface(null, Typeface.BOLD);
                currentAnswer.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.dante_main));
            }
        };
        ans1.setOnClickListener(answerClick);
        ans2.setOnClickListener(answerClick);
        ans3.setOnClickListener(answerClick);
        ans4.setOnClickListener(answerClick);

        Button btnAnswer = findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = "";
                if(ans1.getTypeface() != null && ans1.getTypeface().getStyle() == Typeface.BOLD) {
                    answer = ans1.getText().toString();
                } else if(ans2.getTypeface() != null && ans2.getTypeface().getStyle() == Typeface.BOLD) {
                    answer = ans2.getText().toString();
                } else if(ans3.getTypeface() != null && ans3.getTypeface().getStyle() == Typeface.BOLD) {
                    answer = ans3.getText().toString();
                } else if(ans4.getTypeface() != null && ans4.getTypeface().getStyle() == Typeface.BOLD) {
                    answer = ans4.getText().toString();
                }
                if(answer.length() == 0) {
                    //Toast.makeText(getApplicationContext(), "Potrebno je odabrati odgovor!", Toast.LENGTH_SHORT).show();
                    return;
                }
                answer = answer.substring(3);

                if(answer.equals(correct[currentQuestion])) {
                    //Toast.makeText(getApplicationContext(), "Točno :)", Toast.LENGTH_SHORT).show();
                    points++;
                } else {
                    //Toast.makeText(getApplicationContext(), "Netočno :(", Toast.LENGTH_SHORT).show();
                }

                currentQuestion++;
                if(currentQuestion >= questions.length) {
                    // prošli smo pitanja - prikaži rezultat
                    //Toast.makeText(getApplicationContext(), "Br. bodova: " + points + "/" + questions.length, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("points", points);
                    intent.putExtra("maxPoints", questions.length);
                    startActivity(intent);
                } else {
                    // sljedeće pitanje
                    setupQuestion(currentQuestion);
                }
            }
        });

    }

    void setupQuestion(int idx) {
        title.setText("Pitanje " + (idx+1) + "/" + questions.length);
        txtQuestion.setText(questions[idx]);
        String[] tmpAnswers = answers[idx];
        List<String> list = Arrays.asList(tmpAnswers);
        Collections.shuffle(list);
        list.toArray(tmpAnswers);
        ans1.setText("A. " + tmpAnswers[0]);
        ans2.setText("B. " + tmpAnswers[1]);
        ans3.setText("C. " + tmpAnswers[2]);
        ans4.setText("D. " + tmpAnswers[3]);

        ans1.setTypeface(null, Typeface.NORMAL);
        ans1.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        ans2.setTypeface(null, Typeface.NORMAL);
        ans2.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        ans3.setTypeface(null, Typeface.NORMAL);
        ans3.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        ans4.setTypeface(null, Typeface.NORMAL);
        ans4.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
    }
}