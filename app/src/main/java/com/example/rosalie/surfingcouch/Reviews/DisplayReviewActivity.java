package com.example.rosalie.surfingcouch.Reviews;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.HostingPlace;
import com.example.rosalie.surfingcouch.Database.Reviews;
import com.example.rosalie.surfingcouch.Database.Service;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayReviewActivity extends NavigationDrawerActivity {
    private Reviews mCurrentReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_display_review, null, false);
        drawer.addView(contentView, 0);

        Bundle b = getIntent().getExtras();
        getAllReviews(b);
    }


    private void getAllReviews(Bundle b) {
        final String value = b.getString("reviewID");
        mReference = FirebaseDatabase.getInstance().getReference().child("Reviews");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Reviews review = child.getValue(Reviews.class);
                    if (value.equals(review.getId())) {
                        mCurrentReview = review;
                    }
                }
                displayReview(mCurrentReview);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayReview(Reviews review) {
        RatingBar grade = findViewById(R.id.review_grade);
        grade.setRating(review.getGrade());
        TextView title = findViewById(R.id.review_title);
        title.setText(review.getTitle());
        TextView content = findViewById(R.id.review_content);
        content.setText(review.getContent());
        TextView user = findViewById(R.id.review_user);
        user.setText(review.getName());

    }
}
