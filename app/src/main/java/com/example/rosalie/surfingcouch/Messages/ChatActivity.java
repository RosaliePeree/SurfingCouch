package com.example.rosalie.surfingcouch.Messages;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Message;
import com.example.rosalie.surfingcouch.Database.User;
import com.example.rosalie.surfingcouch.NavigationDrawerActivity;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_chat, null, false);
        drawer.addView(contentView, 0);
        listView = (ListView) findViewById(R.id.list);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendText = (EditText) findViewById(R.id.messageArea);

        fab.setVisibility(View.GONE);

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
