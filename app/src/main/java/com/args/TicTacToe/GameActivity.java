package com.args.TicTacToe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons =new Button[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1points;
    private int player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewPlayer1= findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for(int i =0; i<3;i++){
            for(int j=0;j<3;j++){
                String buttonID ="button_"+ i + j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        Button buttonReset = findViewById(R.id.button_reset);// for reset button on click
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(!((Button) v).getText().toString().equals("")){
            return;

    }
        if(player1Turn){
            ((Button) v).setText("x");
            ((Button)v).setTextColor(Color.BLUE);// color for x is blue
        }else{
            ((Button) v).setText("0");
            ((Button)v).setTextColor(Color.RED);//colour for 0 is red
        }

        roundCount++;

        if(checkForWin()) {// if someone wins
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        }else if(roundCount == 9) {
            draw();
        }else {
            player1Turn = !player1Turn;
        }


    }

    private boolean checkForWin(){// if someone wins the round
        String[][] field= new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3; j++){
                field[i][j]= buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i<3;i++){
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return  true;
            }
        }

        for (int i = 0; i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return  true;
            }
        }

        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return  true;
        }
        //for second diagonal
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return  true;
        }
        return  false;

    }


    //for wins and draws
    private void player1Wins(){
        player1points++;
        Toast.makeText(this,"player 1 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }
    private void player2Wins(){
        player2points++;
        Toast.makeText(this,"player 2 wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }
    private void draw(){
        Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        resetBoard();
    }



    //after winning changes
    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1points);
        textViewPlayer2.setText("Player 2: " + player2points);
    }
    private void resetBoard(){
        for(int i=0; i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount= 0;
        player1Turn = true;
    }
    //when the player clicks the reset button
    private void resetGame(){
        player1points= 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }
    //protect data during screen rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1points",player1points);
        outState.putInt("player2points",player2points);
        outState.putBoolean("player1Turn",player1Turn);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1points=savedInstanceState.getInt("player1points");
        player2points=savedInstanceState.getInt("player2points");
        player1Turn= savedInstanceState.getBoolean("player1Turn");

    }
}


