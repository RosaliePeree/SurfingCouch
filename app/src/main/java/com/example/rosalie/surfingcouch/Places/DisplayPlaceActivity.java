package com.example.rosalie.surfingcouch.Places;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Bookings.AddBookingActivity;
import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.example.rosalie.surfingcouch.Database.Service;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayPlaceActivity extends NavigationDrawerActivity {
    private ListView mServiceListView;
    private ArrayList<Service> mServiceList;
    private HostingPlace mCurrentPlace;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_display_place, null, false);
        drawer.addView(contentView, 0);

        mServiceListView = findViewById(R.id.list_services);

        mServiceList = new ArrayList<>();
        button = findViewById(R.id.place_booking_button);
        Bundle b = getIntent().getExtras();
        getAllPlaces(b);




    }


    private void getAllPlaces(Bundle b) {
        final String value = b.getString("placeID");
        mReference = FirebaseDatabase.getInstance().getReference().child("HostingPlace");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mServiceList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    HostingPlace place = child.getValue(HostingPlace.class);
                    if (value.equals(place.getPlaceID())) {
                        mCurrentPlace = place;
                        for(Service serv : mCurrentPlace.getListService())
                            mServiceList.add(serv);
                    }
                    displayPlace(place);
                }
                DisplayPlaceActivity.ServicesAdapter myAdapter = new DisplayPlaceActivity.ServicesAdapter(getApplicationContext(), R.layout.list_view_services, mServiceList);
                mServiceListView.setAdapter(myAdapter);

                buttonHandler();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayPlace(HostingPlace place) {
        TextView placeName = findViewById(R.id.place_name);
        placeName.setText(place.getPlacename());
        TextView location = findViewById(R.id.place_location);
        location.setText(place.getLocation());
        TextView numberPeople = findViewById(R.id.place_number_people);
        numberPeople.setText(place.getNumberOfPossiblePeople() + "");
    }

    class ServicesAdapter extends ArrayAdapter<Service> {
        ArrayList<Service> serviceArrayList;

        public ServicesAdapter(Context context, int textViewResourceId, ArrayList<Service> objects) {
            super(context, textViewResourceId, objects);
            serviceArrayList = new ArrayList<>();
            serviceArrayList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_services, parent, false);
            TextView textView = v.findViewById(R.id.list_services_text);
            textView.setText(mServiceList.get(position).getName());
            return v;
        }
    }

    public void buttonHandler(){
        if(Objects.equals(mCurrentUser.getId(), mCurrentPlace.getUserID()))
            button.setVisibility(View.GONE);
        else
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddBookingActivity.class);
                    intent.putExtra("placeOwnerID", mCurrentPlace.getUserID());
                    intent.putExtra("placeName", mCurrentPlace.getPlacename());
                    intent.putExtra("placeID", mCurrentPlace.getPlaceID());
                    intent.putExtra("Shower", false);
                    intent.putExtra("Laundry", false);
                    intent.putExtra("Sleep", false);
                    for(Service serv : mCurrentPlace.getListService()){
                        if(Objects.equals(serv.getName(), "Shower")){
                            intent.putExtra("Shower", true);
                        }
                        if(Objects.equals(serv.getName(), "Laundry")){
                            intent.putExtra("Laundry", true);
                        }
                        if(Objects.equals(serv.getName(), "Sleep")){
                            intent.putExtra("Sleep", true);
                        }

                    }
                    startActivity(intent);
                }
            });
    }
}
