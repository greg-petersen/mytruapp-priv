package com.example.brianmissedthebus.mytru;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ComingSoon extends AppCompatActivity implements View.OnClickListener{

    //Variables
    ImageButton back;
    MediaPlayer soundPlayer;
    boolean click;
    public static final String DATA = "com.example.brianmissedthebus.mytru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton2);
        back.setOnClickListener(this);
        //set up soundPlayer sound
        soundPlayer = MediaPlayer.create(this, R.raw.button_click);

        //access shared prefs and set click
        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        click = sp.getBoolean("clickOn", true);
    }

    //when activity becomes visible
    protected void onResume(){
        super.onResume();
        //reset button backgrounds (it changes colour when clicked)
        back.setImageResource(R.drawable.arrow_blue);
    }


    public void onClick(View v){
        if(click){
            soundPlayer.start();//play click sound
        }
        switch(v.getId()){
            //back button case
            case R.id.backButton2:
                //change the image colour
                back.setImageResource(R.drawable.arrow_click);
                //go to previous screen
                finish();
                break;
            default:
                Log.d("app", "How did we get here?");
        }

    }
}
