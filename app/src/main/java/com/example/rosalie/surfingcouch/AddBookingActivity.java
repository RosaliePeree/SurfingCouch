package com.example.rosalie.surfingcouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Booking;
import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddBookingActivity extends NavigationDrawerActivity {

    CalendarView calendar;
    CheckBox laundryService, showerService, sleepService;
    Button addBooking;
    String curDate;
    int dayT, monthT, yearT, curDayT, curMonthT, curYearT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        laundryService = (CheckBox) findViewById(R.id.checkBoxLaundryBooking);
        showerService = (CheckBox) findViewById(R.id.checkBoxShowerBooking);
        sleepService = (CheckBox) findViewById(R.id.checkBoxSleepingBooking);
        boolean isShower = getIntent().getExtras().getBoolean("Shower");
        boolean isLaundry = getIntent().getExtras().getBoolean("Shower");
        boolean isSleep = getIntent().getExtras().getBoolean("Sleep");

        if(!isShower){
            showerService.setEnabled(false);
        }

        if(!isLaundry){
            laundryService.setEnabled(false);
        }
        if(!isSleep){
            sleepService.setEnabled(false);
        }
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        String[] tokens = formattedDate.split("-");
        curDayT = Integer.parseInt(tokens[0]);
        curMonthT = Integer.parseInt(tokens[1]);
        curYearT = Integer.parseInt(tokens[2]);

        //String place = getIntent().get
        curDate = null;

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                curDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year);
                dayT = dayOfMonth;
                monthT = month + 1; // I don't know but january is 0 and december is 12
                yearT = year;

            }
        });
    }

        public void addBooking(View view) {

            if ( curDate == null   || (!showerService.isChecked() && !sleepService.isChecked() && !laundryService.isChecked())) {

                Toast.makeText(AddBookingActivity.this, R.string.add_hosting_place_empty, Toast.LENGTH_SHORT).show();

            }else if(yearT < curYearT || (monthT < curMonthT && yearT >= curYearT ) || (dayT < curDayT && monthT >= curMonthT && yearT >= curYearT) ){

                Toast.makeText(AddBookingActivity.this, R.string.calendar, Toast.LENGTH_SHORT).show();

            } else {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String receivingUser = getIntent().getExtras().getString("placeOwnerID");
                String actualPlace = getIntent().getExtras().getString("placeName");
                String choosedPlace = getIntent().getExtras().getString("placeID");

                String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                Booking booking = new Booking(key,curDate,currentUser, receivingUser, actualPlace, choosedPlace, false);
                FirebaseDatabase.getInstance().getReference().child("Booking").child(key).setValue(booking);
                FirebaseDatabase.getInstance().getReference().child("HostingPlace/"+ choosedPlace +"/booking").child(key).setValue(key);
                FirebaseDatabase.getInstance().getReference().child("User/" + currentUser +"/booking").child(key).setValue(key);

                Intent intent = new Intent(this,ProfileActivity.class);
                Toast.makeText(getApplicationContext(),"Booking added, waiting for confirmation", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        }

    }
