package com.example.rosalie.surfingcouch;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.rosalie.surfingcouch.Database.Message;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    String loggedInUserName;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = (ListView) findViewById(R.id.list);

        showAllOldMessages();

    }

    private void showAllOldMessages() {
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Main", "user id: " + loggedInUserName);


        ListAdapter adapter = new MessageAdapter(this, Message.class, R.layout.item_message_sender, FirebaseDatabase.getInstance().getReference().getRoot().child("Conversation/Conversation1/listOfMessages"));

        listView.setAdapter(adapter);
    }

}
