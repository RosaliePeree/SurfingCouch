package com.example.rosalie.surfingcouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;

public class AddBookingActivity extends AppCompatActivity {

    CalendarView calendar;
    CheckBox laundryService, showerService, sleepService;
    Button addBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        laundryService = (CheckBox) findViewById(R.id.checkBoxLaundryBooking);
        showerService = (CheckBox) findViewById(R.id.checkBoxShowerBooking);
        sleepService = (CheckBox) findViewById(R.id.checkBoxSleepingBooking);

        //String place = getIntent().get

    }
}
