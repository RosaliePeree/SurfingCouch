package com.example.rosalie.surfingcouch.Shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.Rewards;
import com.example.rosalie.surfingcouch.Database.User;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopActivity extends NavigationDrawerActivity {

    private ListView shopListView;
    private ArrayList<Rewards> rewardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_shop, null, false);
        drawer.addView(contentView, 0);

        shopListView = findViewById(R.id.list_rewards);
        rewardsList = new ArrayList<>();

        getAllRewards();
    }

    public void getAllRewards(){
        mReference = FirebaseDatabase.getInstance().getReference().child("Rewards");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rewardsList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    rewardsList.add(child.getValue(Rewards.class));
                }

                ShopActivity.RewardsAdapter myAdapter=new ShopActivity.RewardsAdapter(getApplicationContext(),R.layout.list_view_rewards,rewardsList);
                shopListView.setAdapter(myAdapter);

                shopListView.setOnItemClickListener(    new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(view.isEnabled()) {
                            Intent intent = new Intent(getApplicationContext(), DisplayItemShopActivity.class);
                            Bundle b = new Bundle();
                            Rewards rew = (Rewards) adapterView.getItemAtPosition(i);
                            b.putString("rewardID", rew.getID() + "");
                            intent.putExtras(b); //Put your id to your next Intent*/
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class RewardsAdapter extends ArrayAdapter<Rewards> {
        ArrayList<Rewards> rewardsArrayList;

        public RewardsAdapter(Context context, int textViewResourceId, ArrayList<Rewards> objects) {
            super(context, textViewResourceId, objects);
            rewardsArrayList = new ArrayList<>();
            rewardsArrayList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_rewards, null);
            TextView textView = (TextView)v.findViewById(R.id.list_rewards_text);
            ImageView imageView = (ImageView)v.findViewById(R.id.list_rewards_image);
            if(rewardsArrayList.get(position).isAvailability()) {
                textView.setText(getString(R.string.reward_available,rewardsArrayList.get(position).getName() ,rewardsArrayList.get(position).getCost()));
            }else{
                textView.setText(getString(R.string.reward_notavailable,rewardsArrayList.get(position).getName() ,rewardsArrayList.get(position).getCost()));
            }
            imageView.setImageResource(R.mipmap.ic_gift);
            if(rewardsArrayList.get(position).isAvailability() == false) {
                v.setEnabled(false);
                v.setBackgroundColor(getResources().getColor(R.color.colorGrey2));
            }
            return v;
        }
    }
}
