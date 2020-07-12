package com.hack3atx.covidcaution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Alert-Page
public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button notificationButton = (Button) findViewById(R.id.button11);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView t1 = findViewById(R.id.notification);
                String answer = calculation("3:57", "4:57", "Monday",
                        "Wednesday", "QR-Code-12-Jersey-Junction");
                t1.setText(answer);
            }
        });
    }

    public static String calculation(String timePositiveCheckedIn, String timeYouWereThere, String dayPositive,
                              String dayYou, String location) {

        //Start Notification Analysis//

        int dayPositiveInt = 0;
        int dayYouInt = 0;

        switch(dayPositive) {
            case "Sunday":
                dayPositiveInt = 1;
                break;
            case "Monday":
                dayPositiveInt = 2;
                break;
            case "Tuesday":
                dayPositiveInt = 3;
                break;
            case "Wednesday":
                dayPositiveInt = 4;
                break;
            case "Thursday":
                dayPositiveInt = 5;
                break;
            case "Friday":
                dayPositiveInt = 6;
                break;
            case "Saturday":
                dayPositiveInt = 7;
                break;
        }

        switch(dayYou) {
            case "Sunday":
                dayYouInt = 1;
                break;
            case "Monday":
                dayYouInt = 2;
                break;
            case "Tuesday":
                dayYouInt = 3;
                break;
            case "Wednesday":
                dayYouInt = 4;
                break;
            case "Thursday":
                dayYouInt = 5;
                break;
            case "Friday":
                dayYouInt = 6;
                break;
            case "Saturday":
                dayYouInt = 7;
                break;
        }

        String result = "";
        int timePositiveCheckedInInteger = toMinutes(timePositiveCheckedIn);
        int timeYouWereThereInteger = toMinutes(timeYouWereThere);

        if (Math.abs(dayYouInt - dayPositiveInt) == 1) {
            timeYouWereThereInteger += 1440;
        } else if (Math.abs(dayYouInt - dayPositiveInt) == 2) {
            timeYouWereThereInteger += (1440 * 2);
        } else if (Math.abs(dayYouInt - dayPositiveInt) == 3) {
            timeYouWereThereInteger += (1440 * 3);
        } else if (Math.abs(dayYouInt - dayPositiveInt) == 4) {
            timeYouWereThereInteger += (1440 * 4);
        } else if (Math.abs(dayYouInt - dayPositiveInt) == 5) {
            timeYouWereThereInteger += (1440 * 5);
        } else if (Math.abs(dayYouInt - dayPositiveInt) == 6) {
            timeYouWereThereInteger += (1440 * 6);
        }

        if (timeYouWereThereInteger >= timePositiveCheckedInInteger &&
                timeYouWereThereInteger <= (1440 + timePositiveCheckedInInteger)) {

            result = "You are at EXTREMELY HIGH RISK of having COVID-19. You were at " + location + " within 1 day " +
                    "of someone " +
                    "who tested positive for the virus." + "\n" + "For your information, the time was " + timeYouWereThere +
                    " and the day was " + dayYou + "." +
                    " Please seek help immediately. If this is not possible, please self-quarantine.";

        } else if (timeYouWereThereInteger > (1440 + timePositiveCheckedInInteger) &&
                timeYouWereThereInteger <= (2880 + timePositiveCheckedInInteger)) {

            result = "You are at HIGH RISK of having COVID-19. You were at " + location + " within 1-2 days " +
                    "of someone " +
                    "who tested positive for the virus." + "\n" + "For your information, the time was " + timeYouWereThere +
                    " and the day was " + dayYou + "." +
                    " Please seek help immediately. If this is not possible, please self-quarantine.";

        } else if (timeYouWereThereInteger > (2880 + timePositiveCheckedInInteger) &&
                timeYouWereThereInteger <= (4320 + timePositiveCheckedInInteger)) {

            result = "You are at MEDIUM RISK of having COVID-19. You were at " + location + " within 2-3 days " +
                    "of someone " +
                    "who tested positive for the virus." + "\n" + "For your information, the time was " + timeYouWereThere +
                    " and the day was " + dayYou + "." +
                    " Please seek help immediately. If this is not possible, please self-quarantine.";

        } else if (timeYouWereThereInteger > (4320 + timePositiveCheckedInInteger) &&
                timeYouWereThereInteger <= (10080 + timePositiveCheckedInInteger)) {

            result = "You are at LOW RISK of having COVID-19. You were at " + location + " within 3-7 " +
                    "as someone " +
                    "who tested positive for the virus." + "\n" + "For your information, the time was " + timeYouWereThere +
                    " and the day was " + dayYou + "." +
                    " Please seek help immediately. If this is not possible, please self-quarantine.";

        }

        return result;
    }

    public static int toMinutes(String s) {
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }
}

