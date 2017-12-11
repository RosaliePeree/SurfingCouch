package com.example.rosalie.surfingcouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Booking;
import com.example.rosalie.surfingcouch.Database.User;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends NavigationDrawerActivity {

    ArrayList<Booking>  listBooking;
    ListView bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_trip, null, false);
        drawer.addView(contentView, 0);
        listBooking = new ArrayList<>();
        bookingList = (ListView) findViewById(R.id.list_bookings);

        getAllOfUserBooking();
    }

    public void getAllOfUserBooking(){

        mReference = FirebaseDatabase.getInstance().getReference().child("Booking");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listBooking.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Booking book = child.getValue(Booking.class);
                    if(book.getUserIDbooking().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        listBooking.add(book);
                        Log.d("bookingList", child.toString());
                    }
                }
                if(listBooking == null){
                    Toast.makeText(getApplicationContext(),"No booking to display", Toast.LENGTH_SHORT).show();
                }
                ListAdapter myAdapter = new BookingAdapter(getApplicationContext(), R.layout.list_view_users, listBooking);
                bookingList.setAdapter(myAdapter);

                bookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), BookingDisplayActivity.class);
                        Booking book = (Booking) adapterView.getItemAtPosition(i);
                        intent.putExtra("booking", book);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

            class BookingAdapter extends ArrayAdapter<Booking> {
                ArrayList<Booking> bookingArrayList;

                public BookingAdapter(Context context, int textViewResourceId, ArrayList<Booking> objects) {
                    super(context,textViewResourceId,objects);

                    bookingArrayList = new ArrayList<>();
                    bookingArrayList = objects;
                }

                @Override
                public int getCount() {
                    return super.getCount();
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View v = convertView;
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = inflater.inflate(R.layout.list_view_booking, null);
                    TextView text = (TextView) v.findViewById(R.id.list_booking_text);
                    text.setText("Date :" + bookingArrayList.get(position).getDate() + " Place :" + bookingArrayList.get(position).getPlace());
                    return v;
                }
            }
        }
