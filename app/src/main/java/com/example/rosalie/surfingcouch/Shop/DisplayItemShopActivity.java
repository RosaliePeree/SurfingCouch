package com.example.rosalie.surfingcouch.Shop;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.Reviews;
import com.example.rosalie.surfingcouch.Database.Rewards;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayItemShopActivity extends NavigationDrawerActivity {
    private ArrayList<Rewards> mRewardList;
    private Rewards mCurrentReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_display_item_shop, null, false);
        drawer.addView(contentView, 0);

        mRewardList = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        getAllRewards(b);
    }


    private void getAllRewards(Bundle b) {
        final String value = b.getString("rewardID");
        mReference = FirebaseDatabase.getInstance().getReference().child("Rewards");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Rewards reward = child.getValue(Rewards.class);
                    if (value.equals(reward.getID()+"")) {
                        mCurrentReward = reward;
                    }
                }
                displayReward(mCurrentReward);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayReward(Rewards reward) {
        TextView name = findViewById(R.id.reward_name);
        name.setText(reward.getName());
        TextView price = findViewById(R.id.reward_price);
        price.setText(reward.getCost() + "pts.");
        if (mCurrentUser.getNumberOfPoints() >= reward.getCost()) {
            findViewById(R.id.reward_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else
            findViewById(R.id.reward_button).setEnabled(false);
    }
}
