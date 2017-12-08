package com.example.rosalie.surfingcouch;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Message;
import com.example.rosalie.surfingcouch.Database.User;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ChatActivity extends NavigationDrawerActivity {

    String loggedInUserName;
    ListView listView;
    ImageView sendButton;
    EditText sendText;
    User lul;
    String chatName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = (ListView) findViewById(R.id.list);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendText = (EditText) findViewById(R.id.messageArea);

        Intent intent = getIntent();
        chatName = intent.getStringExtra("chatName");

        showAllOldMessages();

        lul = new User();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usernameRef = reference.child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

        usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lul = dataSnapshot.getValue(User.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendText.getText().toString().trim().length() == 0) {
                    Toast.makeText(ChatActivity.this, R.string.chat_empty_text, Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.FRANCE);
                    String currentDateAndTime = sdf.format(new Date());
                    FirebaseDatabase.getInstance().getReference().child("Conversation/" + chatName + "/listOfMessages").push().setValue(new Message
                            (
                            FirebaseAuth.getInstance().getCurrentUser().getUid()
                            , lul.getUsername()
                            , sendText.getText().toString()
                            , currentDateAndTime
                            )
                    );
                    sendText.setText("");
                }
            }
        });
    }

    private void showAllOldMessages() {
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Main", "user id: " + loggedInUserName);


        ListAdapter adapter = new MessageAdapter(this, Message.class, R.layout.item_message_sender, FirebaseDatabase.getInstance().getReference().getRoot().child("Conversation/" + chatName +"/listOfMessages"));

        listView.setAdapter(adapter);
    }



}
