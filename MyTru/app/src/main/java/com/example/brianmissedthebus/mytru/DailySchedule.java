package com.example.brianmissedthebus.mytru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class DailySchedule extends AppCompatActivity implements View.OnClickListener{

    //Variables
    ImageButton back;
    ListView classList;
    String[] classes;
    MediaPlayer soundPlayer;
    boolean click;
    public static final String DATA = "com.example.brianmissedthebus.mytru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton);
        back.setOnClickListener(this);

        classList = (ListView) findViewById(R.id.dailyList);

        Intent i = getIntent();
        classes = i.getStringArrayExtra("Day");
        Log.d("Test", "Inside of intent.");

        try{
            showClasses();
        }
        catch(Exception e){
            Log.d("Test", e.toString());
            e.printStackTrace();
        }

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

    public void showClasses(){
        String[] listItems = getListItems();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_layout, listItems);
        classList.setAdapter(adapter);
    }

    public String[] getListItems(){
        String[] result = new String[classes.length];
        String[] temp;
        for(int i = 0; i<result.length; i++){
            temp = classes[i].split("\\|");
            result[i] = temp[1] + " " + temp[2] + "\n" + temp[3] +
                    "\n" + temp[6] + " - " + temp[7];
        }
        return result;
    }

    public void onClick(View v){
        if(click){
            soundPlayer.start();//play click sound
        }
        switch(v.getId()){
            //back button case
            case R.id.backButton:
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
