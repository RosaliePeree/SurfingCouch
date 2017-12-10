package com.example.rosalie.surfingcouch.Reviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Reviews;
import com.example.rosalie.surfingcouch.ProfileActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddingReviewActivity extends AppCompatActivity {

    private EditText reviewTitle, reviewText;
    private Button addReview;
    private RatingBar rate;
    private String TAG = "AddingReviewPlaceActivity";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_review);

        reviewText = (EditText) findViewById(R.id.review_edittext);
        reviewTitle = (EditText) findViewById(R.id.review_edittitle);
        addReview = (Button) findViewById(R.id.button_adding_review);
        rate = (RatingBar) findViewById(R.id.ratingBar);
        username = getIntent().getExtras().getString("username");

    }

    public void addReview(View view) {
        Intent intent = getIntent();
        String receivingUser; //= intent.getStringExtra("receiverID");
        receivingUser = "tOeyT5B5hMeDqZz7K2We6tGb1Ie2";
        if (reviewTitle.getText().toString().trim().length() == 0 ||
                reviewText.getText().toString().trim().length() == 0 ||
                rate.getNumStars() == 0 || receivingUser == null) {
            Toast.makeText(AddingReviewActivity.this, R.string.add_hosting_place_empty, Toast.LENGTH_SHORT).show();
        } else {

            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            String key = FirebaseDatabase.getInstance().getReference().push().getKey();
            Reviews review = new Reviews(reviewText.getText().toString(),rate.getNumStars(), key, currentUser, receivingUser, reviewTitle.getText().toString(), username );
            FirebaseDatabase.getInstance().getReference().child("Reviews").child(key).setValue(review);
            FirebaseDatabase.getInstance().getReference().child("User/"+receivingUser+"/reviews").child(key).setValue(key);

            Intent returnback = new Intent(this,ProfileActivity.class);
            Toast.makeText(getApplicationContext(),"review added", Toast.LENGTH_LONG).show();
            startActivity(returnback);

        }
    }
}
