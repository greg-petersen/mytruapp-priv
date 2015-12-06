package com.example.brianmissedthebus.mytru;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity implements View.OnClickListener{

    //Variables
    ImageButton back;
    ImageView map;
    float zoom = 0.5f;
    boolean zoomedOut = false;
    MediaPlayer soundPlayer;
    boolean click;
    public static final String DATA = "com.example.brianmissedthebus.mytru";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton6);
        back.setOnClickListener(this);
        map = (ImageView) findViewById(R.id.campusMap);
        map.setOnClickListener(this);
        //set up soundPlayer sound
        soundPlayer = MediaPlayer.create(this, R.raw.button_click);

        //access shared prefs and set click
        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        click = sp.getBoolean("clickOn", true);
    }

    protected void onResume()
    {
        super.onResume();
        //when the activity is made visible, show a toast informing the user that they can tap the map to zoom out
        Toast toast = Toast.makeText(getApplicationContext(), "Tap to zoom out", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //reset button backgrounds (it changes colour when clicked)
        back.setImageResource(R.drawable.arrow_blue);
    }

    public void onClick(View v){

        switch(v.getId()){
            //back button case
            case R.id.backButton6:
                if(click){
                    soundPlayer.start();//play click sound
                }
                //change the image colour
                back.setImageResource(R.drawable.arrow_click);
                //go to previous screen
                finish();
                break;
            //case for clicking the map
            case R.id.campusMap:
                //if we are zoomed out
                if(zoomedOut) {
                    //reset scale to 1 (zoom back in)
                    v.setScaleX(1);
                    v.setScaleY(1);
                    zoomedOut = false;
                }
                //if we are zoomed in
                else {
                    //set scale to "zoom" (less than 1), which zooms out
                    v.setScaleX(zoom);
                    v.setScaleY(zoom);
                    zoomedOut = true;
                }
                break;
            default:
                Log.d("app", "How did we get here?");
        }
    }
}
