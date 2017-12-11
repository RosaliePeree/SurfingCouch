package com.example.rosalie.surfingcouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.Booking;
import com.example.rosalie.surfingcouch.Places.DisplayPlaceActivity;

public class BookingDisplayActivity extends AppCompatActivity {
    Booking actualBooking;
    TextView placeName, date, isApproved;
    Button goToPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_display);

        actualBooking = (Booking) getIntent().getExtras().getSerializable("booking");
        placeName = (TextView) findViewById(R.id.booking_placename);
        date = (TextView) findViewById(R.id.booking_date);
        isApproved = (TextView) findViewById(R.id.booking_approveholder);
        placeName.setText(actualBooking.getPlace());
        date.setText(actualBooking.getDate());

        goToPlace = (Button) findViewById(R.id.booking_button);

        if(actualBooking.isBookingEffectued()){
            isApproved.setText(R.string.booking_approveYes);
        }else{
            isApproved.setText(R.string.booking_approveNo);
        }


        goToPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DisplayPlaceActivity.class);
                intent.putExtra("placeID", actualBooking.getPlaceID());
                startActivity(intent);
            }
        });


    }
}
