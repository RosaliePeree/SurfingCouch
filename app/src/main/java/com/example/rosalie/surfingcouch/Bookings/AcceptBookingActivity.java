package com.example.rosalie.surfingcouch.Bookings;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.Booking;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.database.FirebaseDatabase;

public class AcceptBookingActivity extends NavigationDrawerActivity {
    TextView textThanks;
    Booking book;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_accept_booking, null, false);
        drawer.addView(contentView, 0);

        textThanks = (TextView) findViewById(R.id.accept_text);

        Bundle b = getIntent().getExtras();


        textThanks.setText(getString(R.string.accepted_booking, b.getInt("value")));


        book = (Booking) b.getSerializable("booking");

        FirebaseDatabase.getInstance().getReference().child("Booking/"+ book.getId() + "/bookingEffectued").setValue(true);
        FirebaseDatabase.getInstance().getReference().child("User/"+ book.getUserIDreceiving() + "/numberOfPoints").setValue(b.getInt("value") + b.getInt("beforeValue"));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.cancel(0);



    }
}
