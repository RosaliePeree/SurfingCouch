package com.example.rosalie.surfingcouch.Messages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.User;
import com.example.rosalie.surfingcouch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CheckConversationActivity extends AppCompatActivity {

    ArrayList<String> allChats;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_conversation);

        allChats = new ArrayList<>();

        user = new User();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("User/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+"/conversations");
        DatabaseReference ref2 = reference.child("User/" + getIntent().getExtras().getString("userID")  +"/conversations" );

        Intent intent = getIntent();

        User currentUser = (User) intent.getSerializableExtra("currentUser");
        User displayedUser = (User) intent.getSerializableExtra("displayedUser");
        intent = new Intent(getApplicationContext(), ChatActivity.class);

        if(currentUser.getConversations() != null || displayedUser.getConversations() != null) {
            loop:
            {
                for (String conv : currentUser.getConversations().values()) {
                    for (String conv2 : displayedUser.getConversations().values()) {
                        if (Objects.equals(conv, conv2) && !Objects.equals(conv, "General Chat")) {

                            intent.putExtra("chatName", conv2);
                            startActivity(intent);
                            finish();
                            break loop;
                        }
                    }
                }

                ref.child(currentUser.getUsername() + " - " + displayedUser.getUsername()).setValue(currentUser.getUsername() + " - " + displayedUser.getUsername());
                ref2.child(currentUser.getUsername() + " - " + displayedUser.getUsername()).setValue(currentUser.getUsername() + " - " + displayedUser.getUsername());
                intent.putExtra("chatName", currentUser.getUsername() + " - " + displayedUser.getUsername());
                startActivity(intent);
                finish();
            }

        }else {
            ref.child(currentUser.getUsername() + " - " + displayedUser.getUsername()).setValue(currentUser.getUsername() + " - " + displayedUser.getUsername());
            ref2.child(currentUser.getUsername() + " - " + displayedUser.getUsername()).setValue(currentUser.getUsername() + " - " + displayedUser.getUsername());
            intent.putExtra("chatName", currentUser.getUsername() + " - " + displayedUser.getUsername() );
            startActivity(intent);
            finish();
        }




    }

}
