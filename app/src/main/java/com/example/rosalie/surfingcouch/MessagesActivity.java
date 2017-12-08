package com.example.rosalie.surfingcouch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

public class MessagesActivity extends NavigationDrawerActivity {

    ListView listOfChats;
    ArrayList<String> allChats;
    User user;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_messages, null, false);
        drawer.addView(contentView, 0);

        listOfChats = (ListView) findViewById(R.id.listofchat);
        allChats = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allChats);
        user = new User();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = reference.child("User/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        //FirebaseDatabase.getInstance().getReference().keepSynced(true); <- This cache all of the database so not good

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    user = dataSnapshot.getValue(User.class);
                    allChats = user.getConversations();
                    for(String object: allChats) {
                        adapter.add(object);
                    }

                    if(allChats == null) {
                        Toast.makeText(getApplicationContext(), R.string.message_empty ,Toast.LENGTH_SHORT).show();
                    }else{
                        listOfChats.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), R.string.message_error, Toast.LENGTH_SHORT).show();
                }
            });

            listOfChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),ChatActivity.class);
                    intent.putExtra("position", i);

                    intent.putExtra("chatName", listOfChats.getItemAtPosition(i).toString());
                    startActivity(intent);
                }
            });
        }

}

