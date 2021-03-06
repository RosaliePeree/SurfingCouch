package com.example.rosalie.surfingcouch.Places;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.example.rosalie.surfingcouch.Database.Service;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.ProfileActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddHostingPlaceActivity extends NavigationDrawerActivity {
    private Place mPlaceField;
    private EditText numberOfPeople, placenameText;
    private CheckBox showerService, laundryService, sleepService;
    private String TAG = "AddHostingPlaceActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_add_hosting_place, null, false);
        drawer.addView(contentView, 0);


        numberOfPeople = findViewById(R.id.numberpeople);
        placenameText = findViewById(R.id.input_placename);
        showerService = findViewById(R.id.checkBoxShower);
        laundryService = findViewById(R.id.checkBoxLaundry);
        sleepService = findViewById(R.id.checkBoxSleeping);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.addplace_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, getString(R.string.place_name,place.getName()));
                mPlaceField = place;
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG,  getString(R.string.error, status));
            }
        });
    }

    public void addPlace(View view) {

        if (numberOfPeople.getText().toString().trim().length() == 0 || placenameText.getText().toString().trim().length() == 0 || (!showerService.isChecked() && !sleepService.isChecked() && !laundryService.isChecked()) || mPlaceField == null) {

            Toast.makeText(AddHostingPlaceActivity.this, R.string.add_hosting_place_empty, Toast.LENGTH_SHORT).show();

        } else {

            ArrayList<Service> services = new ArrayList<>();
            if (showerService.isChecked()) {
                services.add(new Service("Shower", 50));
            }
            if (sleepService.isChecked()) {
                services.add(new Service("Sleep", 200));
            }
            if (laundryService.isChecked()) {
                services.add(new Service("Laundry", 50));
            }
            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


            String key = FirebaseDatabase.getInstance().getReference().push().getKey();
            HostingPlace place = new HostingPlace( services, mPlaceField.getName().toString(),Integer.parseInt(numberOfPeople.getText().toString()), placenameText.getText().toString(), currentUser, key );
            FirebaseDatabase.getInstance().getReference().child("HostingPlace").child(key).setValue(place);
            FirebaseDatabase.getInstance().getReference().child("User/"+currentUser+"/places").child(key).setValue(placenameText.getText().toString());

            Toast.makeText(getApplicationContext(),R.string.place_added, Toast.LENGTH_LONG).show();
            finish();

        }
    }

}
