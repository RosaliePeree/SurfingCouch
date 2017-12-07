package com.example.rosalie.surfingcouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.Message;
import com.example.rosalie.surfingcouch.Database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    ListView listOfChats;
    ArrayList<String> allChats;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        listOfChats = (ListView) findViewById(R.id.listofchat);
        allChats = new ArrayList<>();
        user = new User();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" ,Toast.LENGTH_SHORT).show();
            }
        });

        allChats = user.getConversations();

        if(allChats == null) {
            Toast.makeText(getApplicationContext(), "Nothing to show here" ,Toast.LENGTH_SHORT).show();
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allChats);

            listOfChats.setAdapter(adapter);
        }




    }

}
