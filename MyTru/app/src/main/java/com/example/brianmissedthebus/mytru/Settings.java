package com.example.brianmissedthebus.mytru;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    //variables
    ImageButton back;
    MediaPlayer soundPlayer;
    boolean click;
    Button clickB;
    Button logoutBtn;
    public static final String DATA = "com.example.brianmissedthebus.mytru";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton7);
        back.setOnClickListener(this);
        clickB = (Button) findViewById(R.id.clickButton);
        clickB.setOnClickListener(this);
        logoutBtn = (Button)findViewById(R.id.logOutButton);
        logoutBtn.setOnClickListener(this);
        //set up soundPlayer sound
        soundPlayer = MediaPlayer.create(this, R.raw.button_click);

        //access shared preferences
        sp = getSharedPreferences(DATA, MODE_PRIVATE);

        if(sp.getBoolean("userLoggedIn", false))
            ((TextView)findViewById(R.id.studentLoggedIn)).setText(sp.getString("Student", "DNE") + " Logged In.");
        else
            ((TextView)findViewById(R.id.studentLoggedIn)).setText("Not Currently Logged In.");
    }

    //when activity becomes visible
    protected void onResume(){
        super.onResume();
        //reset button backgrounds (it changes colour when clicked)
        back.setImageResource(R.drawable.arrow_blue);

        click = sp.getBoolean("clickOn", true);
        //set log out button colours + visibility
        //TODO set code

        //set button click sound button colour + text
        setButton(click);

    }//end of onResume()

    public void setButton(boolean check){
        if(check == false){
            clickB.setText("Off");
            clickB.setBackgroundColor(Color.parseColor("#052b6a"));
            clickB.setTextColor(Color.parseColor("#6b9b83"));
        }
        else{
            clickB.setText("On");
            clickB.setBackgroundColor(Color.parseColor("#6b9b83"));
            clickB.setTextColor(Color.parseColor("#052b6a"));
        }
    }

    public void onClick(View v){
        switch(v.getId()){
            //back button case
            case R.id.backButton7:
                //change the image colour
                back.setImageResource(R.drawable.arrow_click);
                //go to previous screen
                finish();
                break;
            case R.id.clickButton:
                SharedPreferences.Editor editor = sp.edit();
                if(click == true) {
                    click = false;
                }
                else{
                    click=true;
                }
                setButton(click);
                editor.putBoolean("clickOn", click);
                editor.commit();
                break;
            case R.id.logOutButton:
                if(sp.getBoolean("userLoggedIn", false)){
                    SharedPreferences.Editor editor1 = sp.edit();
                    ((TextView)findViewById(R.id.studentLoggedIn)).setText("Not Currently Logged In.");
                    // Toast message to indicate user was logged out.
                    Toast toast = Toast.makeText(getApplicationContext(), sp.getString("Student", "DNE") + " was logged out.", Toast.LENGTH_LONG);
                    editor1.remove("Student");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    editor1.putBoolean("userLoggedIn", false);
                    editor1.commit();
                }
            default:
                Log.d("app", "How did we get here?");
        }
        if(click){
            soundPlayer.start();//play click sound
        }

    }//end of onClick


}//end of class
