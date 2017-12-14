package com.example.rosalie.surfingcouch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayRewardActivity extends NavigationDrawerActivity {
    ListView rewardListView;
    ArrayList<String> rewardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_display_reward, null, false);
        drawer.addView(contentView, 0);

        rewardListView = (ListView) findViewById(R.id.reward_list);
        rewardList = new ArrayList<>();
        rewardList.clear();
        if(mCurrentUser.getUnlockedRewards() != null) {
            for (String rew : mCurrentUser.getUnlockedRewards().values()) {
                rewardList.add(rew);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rewardList);
            rewardListView.setAdapter(adapter);
        }else{
            Toast.makeText(getApplicationContext(), R.string.no_reward, Toast.LENGTH_SHORT).show();
        }



    }
}
