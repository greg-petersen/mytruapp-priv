package com.example.brianmissedthebus.mytru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashSet;
import java.util.Set;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    //Variables
    ImageButton nav, bus, schedule, back, settings;
    TextView navT, busT, scheduleT, settingsT;
    MediaPlayer soundPlayer;
    String textColour = "#6b9b83", textColour2 = "#052b6a";
    boolean click;

    // FAKE STUDENT DATABASE: Initializing with some fake student numbers and class info.
    public static final String DATA = "com.example.brianmissedthebus.mytru";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home_page);

        // Setting up student and course information.
        initializeData();

        //Connect buttons to layout and set onClick listener
        nav = (ImageButton) findViewById(R.id.navButton);
        nav.setOnClickListener(this);
        bus = (ImageButton) findViewById(R.id.busButton);
        bus.setOnClickListener(this);
        schedule = (ImageButton) findViewById(R.id.scheduleButton);
        schedule.setOnClickListener(this);
        back = (ImageButton) findViewById(R.id.backButton5);
        back.setOnClickListener(this);
        navT = (TextView) findViewById(R.id.mapText);
        navT.setOnClickListener(this);
        busT = (TextView) findViewById(R.id.busText);
        busT.setOnClickListener(this);
        scheduleT = (TextView) findViewById(R.id.courseText);
        scheduleT.setOnClickListener(this);
        settings = (ImageButton) findViewById(R.id.settingsButton);
        settings.setOnClickListener(this);
        settingsT = (TextView) findViewById(R.id.settingsText);
        settingsT.setOnClickListener(this);

        //set up soundPlayer sound
        soundPlayer = MediaPlayer.create(this, R.raw.button_click);



    }

    //when activity becomes visible
    protected void onResume(){
        super.onResume();
        //reset button image (it changes colour when clicked)
        nav.setImageResource(R.drawable.nav_icon_blue);
        navT.setTextColor(Color.parseColor(textColour2));
        bus.setImageResource(R.drawable.bus_icon_blue);
        busT.setTextColor(Color.parseColor(textColour2));
        schedule.setImageResource(R.drawable.schedule_icon_blue);
        scheduleT.setTextColor(Color.parseColor(textColour2));
        settings.setImageResource(R.drawable.settings_icon_blue);
        settingsT.setTextColor(Color.parseColor(textColour2));
        back.setImageResource(R.drawable.arrow_blue);

        //access shared prefs and set click
        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        click = sp.getBoolean("clickOn", true);
    }

    //onClick method
    public void onClick(View v){
        if(click){
            soundPlayer.start();//play click sound
        }
        Log.d("crash test", "entered onClick");
        switch(v.getId()){
            //case for the schedule button/text
            case R.id.scheduleButton:
            case R.id.courseText:
                //change the image colour
                schedule.setImageResource(R.drawable.schedule_icon_click);
                scheduleT.setTextColor(Color.parseColor(textColour));
                SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
                Intent newActivity0;
                if(sp.getBoolean("userLoggedIn", false))
                    newActivity0 = new Intent(HomePage.this, WeeklySchedule.class);
                else
                    newActivity0 = new Intent(HomePage.this, LogIn.class);//set intent for schedule page
                startActivity(newActivity0);//use intent to navigate to schedule page
                break;
            //case for the Navigation button/text
            case R.id.navButton:
            case R.id.mapText:
                //change the image colour
                nav.setImageResource(R.drawable.nav_icon_click);
                navT.setTextColor(Color.parseColor(textColour));
                Intent newActivity1 = new Intent(HomePage.this, MapActivity.class);//set intent for Nav page
                startActivity(newActivity1);//use intent to navigate to nav page
                break;
            //case for the bus schedule button/text
            case R.id.busButton:
            case R.id.busText:
                //change the image colour
                bus.setImageResource(R.drawable.bus_icon_click);
                busT.setTextColor(Color.parseColor(textColour));
                Intent newActivity2 = new Intent(HomePage.this, ComingSoon.class);//set intent for bus page
                startActivity(newActivity2);//use intent to navigate to bus page (Coming Soon page temporarily)
                break;
            //case for the settings button/text
            case R.id.settingsButton:
            case R.id.settingsText:
                //change the image colour
                settings.setImageResource(R.drawable.settings_icon_click);
                settingsT.setTextColor(Color.parseColor(textColour));
                Intent newActivity3 = new Intent(HomePage.this, Settings.class);//set intent for settings page
                startActivity(newActivity3);//use intent to navigate to settings page
                break;
            //case for the back button
            case R.id.backButton5:
                //change the image colour
                back.setImageResource(R.drawable.arrow_click);
                //go to previous screen
                finish();
                break;
            //default case
            default:
                Log.d("app", "How did we get here?");
        }
    }

    public void initializeData() {

        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        // Test to see if Shared Prefs were initialized.
        if(sp.getBoolean("notInitialized", true))
        // Shared prefs have not be declared and initialized yet.
        {
            SharedPreferences.Editor editor = sp.edit();
            Log.d("app", "Creating shared prefs with mock student info.");
            Log.d("app", "Creating shared prefs with mock course info.");

            String[] students = {
                    /* MOCK STUDENT INFO
                       STUDENT # | PW | CLASS CRNS*/
                    "T11111111|testStu1|10148|12080|12081|10386|10387|10394|10395|10403|10404",
                    "T22222222|secret123|10531|10532|10156|10159|10279|10289",
                    "T33333333|mypwtru|10440|10445|11272|11273|10327|10344",
                    "T44444444|happyf33t|10109|10036|10701|10704|10530|10876|10880",
                    "T55555555|impossiblepw|11151|11096|10676|10678|11274|11275|10639",
                    "T00230592|jenny1|10981|10982|10987|10988|10991|10992|10994|10995|11002|11003"
            };

            String[] courses = {
                    /* MOCK CLASS INFORMATION (Creating 20 fake courses)
                       CRN | SUBJECT | COURSE | Title | Section | DAYS | TIME | LOCATION*/
                    "10148|ASTR|1140|Introductory Astronomy: The Solar System|01|TR|11:30-12:45|S 373",
                    "12080|COMP|2160|Mobile Application Development 1|02|MWF|10:30-11:20|OM 1360",
                    "12081|COMP|2160|Mobile Application Development 1|S02|F|8:30-9:20|OM 1360",
                    "12081|COMP|2160|Mobile Application Development 1|S02|F|8:30-9:20|OM 1360",
                    "10386|COMP|3450|Human-Computer Interaction Design|01|MW|12:30-13:20|S 375",
                    "10386|COMP|3450|Human-Computer Interaction Design|01|F|12:30-13:20|OM 1327",
                    "10387|COMP|3450|Human-Computer Interaction Design|S01|R|9:30-10:20|OM 1327",
                    "10394|COMP|4620|Web-based Information Systems|01|TR|14:30-15:45|OM 1327",
                    "10395|COMP|4620|Web-based Information Systems|S01|F|13:30-14:20|OM 1327",
                    "10403|COMP|4980|ST: Ethical Hacking|01|M|15:30-17:20|OM 1365",
                    "10403|COMP|4980|ST: Ethical Hacking|01|F|15:30-16:20|OM 1365",
                    "10404|COMP|4980|ST: Ethical Hacking|S01|F|16:30-17:20|OM 1365",
                    "10531|ENGL|1150|Introduction to Creative Writing|1|T|14:30-17:20|OM 2402",
                    "10532|ENGL|1210|ST: Limits of Empathy|1|MW|12:30-13:45|OM 2402",
                    "10156|BIOL|1110|Principles of Biology 1|1|MWF|11:30-12:20|IB 1020",
                    "10159|BIOL|1110|Principles of Biology 1|L01|M|14:30-17:20|S 360",
                    "10279|CHEM|1500|Chemical Bonding and Organic Chemistry|1|MTWF|8:30-9:20|CT T200",
                    "10289|CHEM|1500|Chemical Bonding and Organic Chemistry|L08|W|15:30-18:20|S 261",
                    "10440|ECON|1220|Introduction to Basic Economics|1|MWF|12:30-13:20|OM 1792",
                    "10445|ECON|1900|Principles of Microeconomics|2|MWF|8:30-9:20|AE 262",
                    "11272|STAT|1200|Introduction to Statistics|1|MWF|11:30-12:20|OM 2761",
                    "11273|STAT|1200|Introduction to Statistics|S01|R|14:30-15:45|OM 2771",
                    "10327|CMNS|1290|Introduction to Professional Writing|1|TR|8:30-9:45|IB 1020",
                    "10344|COMP|1020|Introduction to Spreadsheets|1|F|11:30-12:20|OM 1350",
                    "10109|ARCH|1100|Exploring Archaeology|1|TR|11:30-12:45|AE 108",
                    "10036|ACCT|1000|Financial Accounting|1|MW|8:30-9:20|IB 1014",
                    "10036|ACCT|1000|Financial Accounting|1|F|8:30-9:20|IB 1015",
                    "10701|GEOL|1110|Introduction to Physical Geology|2|MWF|13:30-14:20|S 233",
                    "10704|GEOL|1110|Introduction to Physical Geology|L03|T|14:30-16:20|S 233",
                    "10530|ENGL|1110|Introduction to Fiction|5|TR|8:30-9:45|OM 2216",
                    "10876|MATH|1140|Calculus 1|2|MWF|11:30-12:20|OM 2791",
                    "10880|MATH|1140|Calculus 1|S02|T|13:00-14:15|AE 263",
                    "11151|PSYC|1110|Introduction to Psychology 1|1|TR|11:30-12:45|S 203",
                    "11096|PHIL|1110|Introduction to Critical Thinking|3|TR|13:00-14:15|OM 2621",
                    "10676|GEOG|1000|Planet Earth - An Introduction to Earth System Science|1|MWF|09:30-10:20|IB 1007",
                    "10678|GEOG|1000|Planet Earth - An Introduction to Earth System Science|L01|M|10:30-12:20|AE 151",
                    "11274|STAT|2000|Introduction to Statistics|1|MWF|08:30-09:20|OM 2751",
                    "11275|STAT|2000|Introduction to Statistics|S01|T|08:30-09:45|OM 2202",
                    "10639|FILM|2200|Introduction to Film Studies 1938 - Present|1|F|13:30-16:20|OM 2621",
                    "10981|NRSC|3200|Silviculture|1|MWF|12:30-13:20|OM 2761",
                    "10982|NRSC|3200|Silviculture| L01|W|14:30-16:20|S 364",
                    "10987|NRSC|3260|Limnology|1|TR|8:30-9:45|OM 1732",
                    "10988|NRSC|3260|Limnology| L01|M|9:30-12:20|S 378",
                    "10991|NRSC|4020|Natural Resource Entomology|1|TW|17:30-18:20|S 375",
                    "10992|NRSC|4020|Natural Resource Entomology| L01|T|18:30-20:20|S 364",
                    "10994|NRSC|4030|Natural Resource Pathology|1|F|13:30-15:20|OM 1762",
                    "10995|NRSC|4030|Natural Resource Pathology| L01|F|15:30-17:20|S 364",
                    "11002|NRSC|4130|Fire Ecology and Management|1|MWF|8:30-9:20|OM 1752",
                    "11003|NRSC|4130|Fire Ecology and Management| S01|T|15:30-17:20|OM 1355"
            };


            // Loading data
            Set<String> courseSet = sp.getStringSet("Courses", new HashSet<String>());
            Set<String> studentSet = sp.getStringSet("Students", new HashSet<String>());

            for(int y = 0; y<courses.length; y++)
                courseSet.add(courses[y]);

            for(int x = 0; x<students.length; x++)
                studentSet.add(students[x]);

            editor.putStringSet("Courses", courseSet);
            editor.putStringSet("Students", studentSet);
            //set shared preference clickOn (used for muting click sounds)
            editor.putBoolean("clickOn", true);
            editor.putBoolean("userLoggedIn", false);

            editor.putBoolean("notInitialized", false);
            editor.commit();
        }
        else
            Log.d("app", "Data already initialized");
    }

}
