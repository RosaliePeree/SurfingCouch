package com.example.rosalie.surfingcouch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.example.rosalie.surfingcouch.Database.Reviews;
import com.example.rosalie.surfingcouch.Database.Service;
import com.example.rosalie.surfingcouch.Database.User;
import com.example.rosalie.surfingcouch.Messages.CheckConversationActivity;
import com.example.rosalie.surfingcouch.Messages.MessagesActivity;
import com.google.android.gms.location.places.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends NavigationDrawerActivity {
    private ListView placesListView;
    private ListView reviewsListView;
    private ArrayList<HostingPlace> placeList;
    private ArrayList<Reviews> reviewsList;
    private ArrayList<User> mUserList;
    private User displayedUser;
    private float gradeAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        drawer.addView(contentView, 0);

        gradeAverage = 0;

        placesListView = findViewById(R.id.profile_places_list);
        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),DisplayPlaceActivity.class);
                Bundle b = new Bundle();
                HostingPlace place = (HostingPlace) adapterView.getItemAtPosition(i);
                b.putString("placeID", place.getPlaceID());
                intent.putExtras(b); //Put your id to your next Intent*/
                startActivity(intent);
            }
        });

        reviewsListView = findViewById(R.id.profile_reviews_list);
        reviewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"test reviews",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),DisplayReviewActivity.class);
                Bundle b = new Bundle();
                Reviews review = (Reviews) adapterView.getItemAtPosition(i);
                b.putString("reviewID", review.getId());
                intent.putExtras(b); //Put your id to your next Intent*/
                startActivity(intent);
            }
        });

        placeList = new ArrayList<>();
        mUserList = new ArrayList<>();
        reviewsList = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        if(b != null) {
            getOtherUserProfile(b);
        } else
            getLoggedUserProfile();
    }

    private void getLoggedUserProfile(){
        mReference = FirebaseDatabase.getInstance().getReference().child("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User use = dataSnapshot.getValue(User.class);
                //Log.i(use.getName(), " user");
                displayedUser = mCurrentUser = use;
                if(mCurrentUser.getPlaces().size() >= 1) {
                    getPlacesLinked();
                    if (mCurrentUser.getReviews() != null)
                        getReviewsLinked();
                }
                displayUser(displayedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getOtherUserProfile(Bundle b){
        final String value = b.getString("userID");
        mReference = FirebaseDatabase.getInstance().getReference().child("User");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    mUserList.add(child.getValue(User.class));
                    Log.d("userlist",child.toString());
                }
                for(User id : mUserList)
                    if(value.equals(id.getId())) {
                        displayedUser = id;
                    }
                if(displayedUser.getPlaces().size() >= 1) {
                    getPlacesLinked();
                    if (displayedUser.getReviews() != null)
                        getReviewsLinked();
                }
                else
                    displayUser(displayedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPlacesLinked(){
        mReference = FirebaseDatabase.getInstance().getReference().child("HostingPlace");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                placesListView.invalidateViews();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    HostingPlace place = child.getValue(HostingPlace.class);
                    if(place.getUserID().equals(displayedUser.getId()))
                        placeList.add(place);
                }
                //Log.i(use.getName(), " user");

                ProfileActivity.PlacesAdapter myAdapter = new ProfileActivity.PlacesAdapter(getApplicationContext(),R.layout.list_view_places,placeList);
                placesListView.setAdapter(myAdapter);

                if (displayedUser.getReviews() == null)
                    displayUser(displayedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getReviewsLinked(){
        mReference = FirebaseDatabase.getInstance().getReference().child("Reviews");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reviewsListView.invalidateViews();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Reviews review = child.getValue(Reviews.class);
                    if(review.getReceivingID().equals(displayedUser.getId())) {
                        reviewsList.add(review);
                        gradeAverage += review.getGrade();
                    }
                }
                gradeAverage = gradeAverage / reviewsList.size();

                ProfileActivity.ReviewsAdapter myAdapter = new ReviewsAdapter(getApplicationContext(), R.layout.list_view_reviews, reviewsList);
                reviewsListView.setAdapter(myAdapter);

                RatingBar rating = findViewById(R.id.profile_rating_bar);
                rating.setRating(gradeAverage);

                displayUser(displayedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void displayUser(User user){
        TextView username = findViewById(R.id.profile_username);
        username.setText(user.getUsername());
        TextView city = findViewById(R.id.profile_city);
        city.setText(user.getComefrom());
        TextView gender = findViewById(R.id.profile_gender);
        gender.setText(user.getGender());
        Button button = findViewById(R.id.profile_button);
        if(displayedUser == mCurrentUser || displayedUser.getId().equals(mCurrentUser.getId())){
            button.setText("Add place");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),AddHostingPlaceActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            button.setText("Send a message");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CheckConversationActivity.class);
                    intent.putExtra("currentUser", mCurrentUser);
                    intent.putExtra("displayedUser", displayedUser);
                    intent.putExtra("userID", displayedUser.getId());
                    startActivity(intent);
                }
            });
        }
    }

    class PlacesAdapter extends ArrayAdapter<HostingPlace> {
        ArrayList<HostingPlace> placeArrayList;

        public PlacesAdapter(Context context, int textViewResourceId, ArrayList<HostingPlace> objects) {
            super(context, textViewResourceId, objects);
            placeArrayList = new ArrayList<>();
            placeArrayList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_places, parent, false);
            TextView textView = v.findViewById(R.id.list_places);
            String servicesProvided = "";
            for(Service service : placeArrayList.get(position).getListService())
                servicesProvided += service.getName() + " ";
            textView.setText("Property name: " + placeArrayList.get(position).getPlacename() + " " +
                    "(can host " + placeArrayList.get(position).getNumberOfPossiblePeople() + "people) \n" +
                    "Services provided: " +  servicesProvided);
            return v;
        }
    }

    class ReviewsAdapter extends ArrayAdapter<Reviews> {
        ArrayList<Reviews> reviewsArrayList;

        public ReviewsAdapter(Context context, int textViewResourceId, ArrayList<Reviews> objects) {
            super(context, textViewResourceId, objects);
            reviewsArrayList = new ArrayList<>();
            reviewsArrayList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_reviews, parent, false);
            TextView textView = v.findViewById(R.id.list_reviews_text);
            textView.setText("Grade: " + reviewsArrayList.get(position).getGrade() + " \n " +
                    "Message title: " +  reviewsArrayList.get(position).getTitle());
            return v;
        }
    }
}