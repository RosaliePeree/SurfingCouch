package com.example.rosalie.surfingcouch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.example.rosalie.surfingcouch.Database.Service;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.google.android.gms.location.places.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayPlaceActivity extends NavigationDrawerActivity {

    private ListView mServiceListView;
    private ArrayList<HostingPlace> mPlacesList;
    private HostingPlace mDisplayedPlace;

    public DisplayPlaceActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_display_place, null, false);
        drawer.addView(contentView, 0);

        mServiceListView = findViewById(R.id.list_services);

        mPlacesList = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        getAllPlaces(b);
    }


    private void getAllPlaces(Bundle b){
        final String value = b.getString("placeID");
        mReferenceUser = FirebaseDatabase.getInstance().getReference().child("HostingPlace");
        mReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mPlacesList.add(child.getValue(HostingPlace.class));
                    Log.d("userlist",child.toString());
                }
                for(HostingPlace place :mPlacesList)
                    if(value.equals(place.getId())) {
                        mDisplayedPlace =
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getServicesLinked(){
        mReferenceUser = FirebaseDatabase.getInstance().getReference().child("HostingPlace");
        mReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    HostingPlace place = child.getValue(HostingPlace.class);
                    if(place.getUserID().equals(displayedUser.getId()))
                        placeList.add(place);
                }
                //Log.i(use.getName(), " user");

                ProfileActivity.PlacesAdapter myAdapter = new ProfileActivity.PlacesAdapter(getApplicationContext(),R.layout.list_view_places,placeList);
                placesListView.setAdapter(myAdapter);

                displayUser(mCurrentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



    private void displayPlace(HostingPlace place){
        TextView placeName = findViewById(R.id.place_name);
        placeName.setText(place.getPlacename());
        TextView location = findViewById(R.id.place_location);
        location.setText(place.getLocation());
        TextView numberPeople = findViewById(R.id.place_number_people);
        numberPeople.setText(place.getNumberOfPossiblePeople());;
    }
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
        TextView textView = v.findViewById(R.id.list_services);
        String servicesProvided = "";
        for(Service service : serviceArrayList)
            servicesProvided += service.getName() + " \n";
        textView.setText(servicesProvided);
        return v;
    }
}
