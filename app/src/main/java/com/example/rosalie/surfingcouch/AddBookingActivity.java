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

import java.util.ArrayList;

public class AddBookingActivity extends AppCompatActivity {

    CalendarView calendar;
    CheckBox laundryService, showerService, sleepService;
    Button addBooking;
    String curDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        laundryService = (CheckBox) findViewById(R.id.checkBoxLaundryBooking);
        showerService = (CheckBox) findViewById(R.id.checkBoxShowerBooking);
        sleepService = (CheckBox) findViewById(R.id.checkBoxSleepingBooking);

        //String place = getIntent().get
        curDate = null;
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                curDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
            }
        });
    }

        public void addBooking(View view) {

            if ( curDate == null  || (!showerService.isChecked() && !sleepService.isChecked() && !laundryService.isChecked())) {

                Toast.makeText(AddBookingActivity.this, R.string.add_hosting_place_empty, Toast.LENGTH_SHORT).show();

            } else {

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String receivingUser = getIntent().getExtras().getString("receivingUser");
                String actualPlace = getIntent().getExtras().getString("place");
                String choosedPlace = getIntent().getExtras().getString("choosedPlace");

                String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                Booking booking = new Booking(key,curDate,currentUser, receivingUser, actualPlace, false);
                FirebaseDatabase.getInstance().getReference().child("Booking").child(key).setValue(booking);
                FirebaseDatabase.getInstance().getReference().child("Place/"+ choosedPlace +"/booking").child(key).setValue(booking);

                Intent intent = new Intent(this,ProfileActivity.class);
                Toast.makeText(getApplicationContext(),"Booking added, waiting for confirmation", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        }

    }
