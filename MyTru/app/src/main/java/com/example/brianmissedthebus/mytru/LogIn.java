package com.example.brianmissedthebus.mytru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LogIn extends AppCompatActivity implements View.OnClickListener{


    /*
        Using shared preferences for the prototype, this can be changed in the future.
        Shared preferences make sense because we are storing a very small amount of information.
     */

    //Variables
    public static final String STUDENT_SCHEDULE = "com.example.brianmissedthebus.mytru";
    public static final String DATA = "com.example.brianmissedthebus.mytru";

    ImageButton back;
    String studentNumber, password;
    EditText numberIn, passIn;
    Button signIn;
    MediaPlayer soundPlayer;
    boolean click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Connect buttons to layout and set onClick listener
        back = (ImageButton) findViewById(R.id.backButton3);
        back.setOnClickListener(this);
        numberIn = (EditText) findViewById(R.id.studentNumberIn);
        passIn = (EditText) findViewById(R.id.passwordIn);
        signIn = (Button) findViewById(R.id.saveButton);
        signIn.setOnClickListener(this);
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
        signIn.setBackgroundColor(Color.parseColor("#7ca88f"));
        signIn.setTextColor(Color.parseColor("#032969"));
    }

    public void onClick(View v){
        if(click){
            soundPlayer.start();//play click sound
        }
        switch(v.getId()){
            //back button case
            case R.id.backButton3:
                //change the image colour
                back.setImageResource(R.drawable.arrow_click);
                //go to previous screen
                finish();
                break;
            //case for log in button
            case R.id.saveButton:
                signIn.setBackgroundColor(Color.parseColor("#032969"));
                signIn.setTextColor(Color.parseColor("#7ca88f"));
                //retrieve the student number
                studentNumber = numberIn.getText().toString().toUpperCase();
                //retrieve the password
                password = passIn.getText().toString();
                Log.i("Test", "strings set");

                //test username and password, display schedule if valid
                if(logIn(studentNumber,password))//if the username and password match
                {
                    //go to weekly schedule
                    Intent newActivity0 = new Intent(LogIn.this, WeeklySchedule.class);
                    startActivity(newActivity0);
                    finish();
                    Log.i("Test", "Intent passed");
                }
                break;
            default:
                Log.d("app", "How did we get here?");
        }

    }

    private boolean logIn(String studentID, String pw){
        // In here, this is where we would do a pull request to the TRU servers for the student account validation and then pull schedule.
        // Going to pretend it is being validated.
        // Validating with stored preferences.
        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        Set<String> students = sp.getStringSet("Students", new HashSet<String>());

        //Log.d("app", students.toString());
        //Log.d("app", "Size: " + students.size());

        Boolean retVal = false;

        if(students.size()>0){
            String[] studentArray = students.toArray(new String[students.size()]);

            // Testing for each student.
            for(int x = 0; x<studentArray.length; x++){
                //Log.d("app", studentArray[x].toString());
                String[] info = studentArray[x].split("\\|");

                //Log.d("app", "Length: " + info.length);
                //Log.d("app", "ID: " + info[0]);
                //Log.d("app", "PW: " + info[1]);

                if(info[0].equals(studentID) && info[1].equals(pw)){
                    retVal = true;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("userLoggedIn", true);
                    editor.commit();
                    Log.d("app", "Login valid for student: " + studentID);

                    // Syncing courses here.
                    String[] studentArrayNPW = new String[info.length-1];
                    for(int y = 1; y<studentArrayNPW.length; y++)
                        studentArrayNPW[y]=info[y+1];
                    studentArrayNPW[0]=info[0];
                    syncCourses(studentArrayNPW);
                    break;
                }
            }
        }
        else
            Log.d("app", "Student Size less then 0");

        if(!retVal){
            Log.d("app", "Login unsuccessful with: " + studentID + " PW: " + pw);
            //show toast message for the user to let them know the username and password combination is not valid
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            signIn.setBackgroundColor(Color.parseColor("#7ca88f"));
            signIn.setTextColor(Color.parseColor("#032969"));
        }


        return retVal;
    }

    private void syncCourses(String[] studentInfo){
        SharedPreferences sp = getSharedPreferences(DATA, MODE_PRIVATE);
        Set<String> courseSet = sp.getStringSet("Courses", new HashSet<String>());

        if(courseSet.size()>0){

            String[] courses = courseSet.toArray(new String[courseSet.size()]);
            SharedPreferences thisStudentSP = getSharedPreferences(STUDENT_SCHEDULE, MODE_PRIVATE);
            SharedPreferences.Editor editor = thisStudentSP.edit();

            editor.remove("MyCourses");
            editor.remove("Student");
            Set<String> thisStudentSet = new HashSet<>();

            // Storing student ID for easy access later.
            editor.putString("Student", studentInfo[0]);
            Log.d("app", Arrays.toString(studentInfo));

            for(int x = 1; x<studentInfo.length; x++){
                // Populating MyCourses set. In here we will lookup each course CRN and then save it to another shared pref.
                for(int y = 0; y<courses.length; y++){
                    String courseCRN = courses[y].substring(0,courses[y].indexOf('|'));
                    //Log.d("app", courseCRN);
                    if(studentInfo[x].equals(courseCRN))
                    {
                        Log.d("app", studentInfo[x] + " == " + courseCRN);
                        Log.d("app", "Student has: " + courseCRN);
                        thisStudentSet.add(courses[y]);
                    }

                }
            }
            editor.putStringSet("MyCourses", thisStudentSet);
            editor.commit();
            Log.d("app", thisStudentSP.getString("Student", "DNE"));
            Log.d("app", thisStudentSP.getStringSet("MyCourses",new HashSet<String>()).toString());
        }
        else
            Log.d("app", "CourseSet size is less then 0");
    }
}
