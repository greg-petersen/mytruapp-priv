package com.example.brianmissedthebus.mytru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.HashSet;
import java.util.Set;

public class WeeklySchedule extends AppCompatActivity implements View.OnClickListener{

    //Variables
    public static final String STUDENT_SCHEDULE = "com.example.brianmissedthebus.mytru";
    ImageButton back;
    String[][] days = new String[5][10];
    ListView display;
    MediaPlayer soundPlayer;
    boolean click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton4);
        back.setOnClickListener(this);

        display = (ListView) findViewById(R.id.weeklyList);
        showSchedule();
        //set up soundPlayer sound
        soundPlayer = MediaPlayer.create(this, R.raw.button_click);

        //access shared prefs and set click
        SharedPreferences sp = getSharedPreferences(STUDENT_SCHEDULE, MODE_PRIVATE);
        click = sp.getBoolean("clickOn", true);
    }

    //when activity becomes visible
    protected void onResume(){
        super.onResume();
        //reset button backgrounds (it changes colour when clicked)
        back.setImageResource(R.drawable.arrow_blue);
    }

    public void showSchedule(){
        separateDays();
        populateList();
    }

    public void populateList(){
        String[] listItems = getListItems();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_layout, listItems);
        display.setAdapter(adapter);

        setListeners(display);
    }

    public void setListeners(ListView v) {
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Boolean hasClass = true;
                Intent newActivityDaily = new Intent(WeeklySchedule.this, DailySchedule.class);
                if(click){
                    soundPlayer.start();//play click sound
                }
                switch (position) {
                    case 0:
                        Log.d("Test", "Classes: " + days[position].length);
                        Log.d("Test", "Contents: " + days[position][0]);
                        if(days[position][0]==null)// No classes, won't start intent.
                            hasClass = false;
                        else// We know we have a day with classes.
                            newActivityDaily.putExtra("Day", days[position]);
                        break;
                    case 1:
                        Log.d("Test", "Classes: " + days[position].length);
                        Log.d("Test", "Contents: " + days[position][0]);
                        if(days[position][0]==null)// No classes, won't start intent.
                            hasClass = false;
                        else// We know we have a day with classes.
                            newActivityDaily.putExtra("Day", days[position]);
                        break;
                    case 2:
                        Log.d("Test", "Classes: " + days[position].length);
                        Log.d("Test", "Contents: " + days[position][0]);
                        if(days[position][0]==null)// No classes, won't start intent.
                            hasClass = false;
                        else// We know we have a day with classes.
                            newActivityDaily.putExtra("Day", days[position]);
                        break;
                    case 3:
                        Log.d("Test", "Classes: " + days[position].length);
                        Log.d("Test", "Contents: " + days[position][0]);
                        if(days[position][0]==null)// No classes, won't start intent.
                            hasClass = false;
                        else// We know we have a day with classes.
                            newActivityDaily.putExtra("Day", days[position]);
                        break;
                    case 4:
                        Log.d("Test", "Classes: " + days[position].length);
                        Log.d("Test", "Contents: " + days[position][0]);
                        if(days[position][0]==null)// No classes, won't start intent.
                            hasClass = false;
                        else// We know we have a day with classes.
                            newActivityDaily.putExtra("Day", days[position]);
                        break;
                    default:
                        Log.d("app", "How did we get here?");
                }
                if(hasClass)
                    startActivity(newActivityDaily);
                Log.i("Test", "Intent passed");
            }
        });
    }

    public String[] getListItems() {
        String[] result = new String[5];
        String[] names = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String temp;
        String startTime, endTime;
        for(int i = 0; i<days.length; i++) {
            int classCount = 0;
            if (days[i][0] == null) {
                result[i] = names[i] + ": 0 classes";
            } else {
                classCount = days[i].length;
                temp = days[i][0].split("\\|")[6];
                startTime = temp.split("-")[0];
                temp = days[i][days[i].length-1].split("\\|")[6];
                endTime = temp.split("-")[1];
                result[i] = names[i] + ": " + classCount + " classes\n" +
                            "Start: " + startTime + "\nEnd: " + endTime;
                Log.d("app", "Day: " +names[i]);
                Log.d("app", "Class count: " + classCount);
                Log.d("app", "Start Time: " + startTime);
                Log.d("app", "End Time: " + endTime);
            }
        }
        return result;

    }
    public void separateDays() {
        SharedPreferences sp = getSharedPreferences(STUDENT_SCHEDULE, MODE_PRIVATE);
        Set<String> courses = sp.getStringSet("MyCourses", new HashSet<String>());

        if(courses.size()>0){
            String[] courseArray = courses.toArray(new String[courses.size()]);

            Set<String> mon = new HashSet<>();
            Set<String> tues = new HashSet<>();
            Set<String> wed = new HashSet<>();
            Set<String> thurs = new HashSet<>();
            Set<String> fri = new HashSet<>();

            for(int i = 0; i<courseArray.length; i++){
                String[] course = courseArray[i].split("\\|");

                if(course[5].contains("M")) {
                    mon.add(courseArray[i]);
                    Log.d("app", courseArray[i] + " added to monday set.");
                }
                if(course[5].contains("T")) {
                    tues.add(courseArray[i]);
                    Log.d("app", courseArray[i] + " added to tuesday set.");
                }
                if(course[5].contains("W")) {
                    wed.add(courseArray[i]);
                    Log.d("app", courseArray[i] + " added to wednesday set.");
                }
                if(course[5].contains("R")) {
                    thurs.add(courseArray[i]);
                    Log.d("app", courseArray[i] + " added to thursday set.");
                }
                if(course[5].contains("F")) {
                    fri.add(courseArray[i]);
                    Log.d("app", courseArray[i] + " added to friday set.");
                }
            }

            days[0] = timeSort(mon);
            days[1] = timeSort(tues);
            days[2] = timeSort(wed);
            days[3] = timeSort(thurs);
            days[4] = timeSort(fri);
        }
    }

    public String[] timeSort(Set<String> data){

        if(data.size()>0) {
            String[] day = data.toArray(new String[data.size()]);

            int index;
            int t1 = 0;
            int t2 = 0;
            String s, temp1, temp2;
            String time1, time2;

            for (int x = 0; x < day.length - 1; x++) {
                index = x;
                for (int y = x + 1; y < day.length; y++) {
                    temp1 = day[index].split("\\|")[6];
                    time1 = temp1.split(":")[0];
                    temp2 = day[y].split("\\|")[6];
                    time2 = temp2.split(":")[0];
                    try {
                        t1 = Integer.parseInt(time1);
                        t2 = Integer.parseInt(time2);
                    } catch (NumberFormatException e) {
                        Log.d("app", "time sort parse exception");
                    }
                    if (t2 < t1) {
                        index = y;
                    }
                }
                if (index != x) {
                    s = day[x];
                    day[x] = day[index];
                    day[index] = s;
                }
            }
            /*for (int i = 0; i < day.length; i++) {
                Log.d("app", "Array contents: " + day[i]);
            }*/
            return day;
        } else {
            return new String[1];
        }
    }

    public void onClick(View v){
        if(click){
            soundPlayer.start();//play click sound
        }
        switch(v.getId()){
            //back button case
            case R.id.backButton4:
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
