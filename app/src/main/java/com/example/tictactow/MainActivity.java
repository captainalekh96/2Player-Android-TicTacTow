package com.example.tictactow;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int player=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    boolean gameActive=true;
    int[][] winningPos={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int pointP1=0;
    int pointP2=0;

    public void onPlay(View view)  {
        ImageView counter=(ImageView)view;

        int tapped=Integer.parseInt(counter.getTag().toString());
        if(gameState[tapped]==2 && gameActive) {
            gameState[tapped] = player;
            counter.setTranslationY(-2000);
            if (player == 1) {
                counter.setImageResource(R.drawable.cross);
                player = 0;
                counter.animate().translationYBy(2000).rotation(360).setDuration(500).withEndAction(()->checkForWinner());
            } else {
                counter.setImageResource(R.drawable.circle);
                player = 1;
                counter.animate().translationYBy(2000).rotationY(360).setDuration(500).withEndAction(()->checkForWinner());;
            }

        }

    }
    private void checkForWinner() {
        for (int[] winningpos : winningPos) {
            if (gameState[winningpos[0]] == gameState[winningpos[1]] && gameState[winningpos[1]] == gameState[winningpos[2]] && gameState[winningpos[0]] != 2) {
                String winner = "";
                gameActive = false;

                if (player == 1) {
                    pointP1++;
                    winner = "Player 1";
                    TextView point = (TextView) findViewById(R.id.scoreP1);
                    point.setText("" + pointP1);
                } else {
                    pointP2++;
                    winner = "Player 2";
                    TextView point = (TextView) findViewById(R.id.scoreP2);
                    point.setText("" + pointP2);
                }
                Toast.makeText(this, winner + " has Won! ", Toast.LENGTH_LONG).show();
                TextView text = (TextView) findViewById(R.id.textView);
                text.setText("Winner: " + winner);
                text.animate().alpha(1);
                gamerestart();
            }
        }
        boolean vacantBlocks=false;
        for(int i=0;i<gameState.length;i++){
            if (gameState[i]==2){
                vacantBlocks=true;
                break;
            }
        }
        if(!vacantBlocks){
            Toast.makeText(this,"Tie",Toast.LENGTH_LONG).show();
            pointP1++;
            pointP2++;
            TextView point = (TextView) findViewById(R.id.scoreP1);
            point.setText("" + pointP1);
            point = (TextView) findViewById(R.id.scoreP2);
            point.setText("" + pointP2);
            TextView text = (TextView) findViewById(R.id.textView);
            text.setText("Tie +1 points to both");
            gamerestart();
        }
    }
    public void gamerestart(){
        new Handler().postDelayed(new Runnable() {   //Handler is used to introduce delay,it works similar to Thread.sleep but it does not stop UI and hence is recommed by android to use.
            public void run() {
                for (int i = 0; i < gameState.length; i++) {
                    gameState[i] = 2;
                }
                player = 0;
                gameActive = true;


                GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
                // Loop through all child views of the GridLayout
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    ImageView img = (ImageView) gridLayout.getChildAt(i);
                    img.setImageDrawable(null);
                }
                TextView text = (TextView) findViewById(R.id.textView);
                text.animate().alpha(0);
            }
        },2000);
    }
   public void reset(View view){
        gamerestart();

        pointP1=0;
        pointP2=0;

        TextView score=(TextView) findViewById(R.id.scoreP2);
        score.setText(""+0);
        score=(TextView) findViewById(R.id.scoreP1);
        score.setText(""+0);
        TextView text=(TextView) findViewById(R.id.textView);
        text.animate().alpha(0);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}