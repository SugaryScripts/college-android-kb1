package com.college.kb1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.college.kb1.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Random rand;

    private int p1Score = 0, p2Score = 0;
    private int leftQuiz, rightQuiz;

    // 0 = easy, 1 = med, 2 = hard
    private int difficulty = 0;

    // 0 = +, 1 = -, 2 = *
    private int operator = 0;

    private int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rand = new Random();

        initUI();
        generateQuiz();
    }

    private void initUI() {
        binding.radioEasy.setChecked(true);

        binding.tvP1Score.setText(String.valueOf(p1Score));
        binding.tvP2Score.setText(String.valueOf(p2Score));

        binding.radioEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDifficulty(true, false, false);
            }
        });

        binding.radioMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDifficulty(false, true, false);
            }
        });

        binding.radioDifficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDifficulty(false, false, true);
            }
        });

        /*binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateQuiz();
            }
        });*/

    }

    private void generateQuiz() {
        randomizeQuestion();

        switch (operator){
            case 0:
                binding.tvOpQuiz.setText("+");
                key = leftQuiz + rightQuiz;
                break;
            case 1:
                binding.tvOpQuiz.setText("-");
                key = leftQuiz - rightQuiz;
                break;
            case 2:
                binding.tvOpQuiz.setText("x");
                key = leftQuiz * rightQuiz;
                break;
        }

        binding.tvLeftQuiz.setText(String.valueOf(leftQuiz));
        binding.tvRightQuiz.setText(String.valueOf(rightQuiz));

        randomizeOption();
    }

    private void randomizeOption() {
        ArrayList<Integer> optionForP1 = new ArrayList<>();
        ArrayList<Integer> optionForP2 = new ArrayList<>();

        optionForP1.add(key);
        optionForP2.add(key);
        for (int i = 0; i < 2; i++) {
            int p1 = rand.nextInt(201) - 100;
            int p2 = rand.nextInt(201) - 100;

            while (p1 == key)
                p1 = rand.nextInt(201) - 100;

            while (p2 == key)
                p2 = rand.nextInt(201) - 100;

            optionForP1.add(p1);
            optionForP2.add(p2);
        }

        Collections.shuffle(optionForP1);
        Collections.shuffle(optionForP2);

        binding.btnP1Top.setText(String.valueOf(optionForP1.get(0)));
        binding.btnP1Mid.setText(String.valueOf(optionForP1.get(1)));
        binding.btnP1Bot.setText(String.valueOf(optionForP1.get(2)));

        binding.btnP2Top.setText(String.valueOf(optionForP2.get(0)));
        binding.btnP2Mid.setText(String.valueOf(optionForP2.get(1)));
        binding.btnP2Bot.setText(String.valueOf(optionForP2.get(2)));

    }

    private void randomizeQuestion(){
        if (difficulty == 0) {
            leftQuiz = rand.nextInt(100);
            rightQuiz = rand.nextInt(100);
            operator = rand.nextInt(2);
        } else if (difficulty == 1){
            leftQuiz = rand.nextInt(101) - 50;
            rightQuiz = rand.nextInt(101) - 50;
            operator = rand.nextInt(2);
        } else {
            leftQuiz = rand.nextInt(101) - 50;
            rightQuiz = rand.nextInt(101) - 50;
            operator = rand.nextInt(3);
        }
    }

    private void toggleDifficulty(boolean easy, boolean med, boolean difficult){
        if(easy)
            difficulty = 0;
        else if(med)
            difficulty = 1;
        else if(difficult)
            difficulty = 2;
        binding.radioEasy.setChecked(easy);
        binding.radioMedium.setChecked(med);
        binding.radioDifficult.setChecked(difficult);

        generateQuiz();
        Toast.makeText(MainActivity.this, "Tingkat kesulitan telah dirubah", Toast.LENGTH_SHORT).show();
    }



}