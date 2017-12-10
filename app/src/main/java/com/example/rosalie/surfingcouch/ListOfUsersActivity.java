package com.example.rosalie.surfingcouch;

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

import com.example.rosalie.surfingcouch.Database.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfUsersActivity extends NavigationDrawerActivity {

    private ListView userListView;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_list_ofusers, null, false);
        drawer.addView(contentView, 0);

        userListView = findViewById(R.id.list_of_users_view);
        userList = new ArrayList<>();

        getAllUsersProfile();
    }

    public void getAllUsersProfile(){
        mReference = FirebaseDatabase.getInstance().getReference().child("User");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    userList.add(child.getValue(User.class));
                    Log.d("userlist",child.toString());
                }
                UserAdapter myAdapter=new UserAdapter(getApplicationContext(),R.layout.list_view_users,userList);
                userListView.setAdapter(myAdapter);

                userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        Bundle b = new Bundle();
                        User use = (User)adapterView.getItemAtPosition(i);
                        b.putString("userID", use.getId());
                        intent.putExtras(b); //Put your id to your next Intent*/
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class UserAdapter extends ArrayAdapter<User> {
        ArrayList<User> userArrayList;

        public UserAdapter(Context context, int textViewResourceId, ArrayList<User> objects) {
            super(context, textViewResourceId, objects);
            userArrayList = new ArrayList<>();
            userArrayList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_users, null);
            TextView textView = (TextView) v.findViewById(R.id.list_user_item_text);
            ImageView imageView = (ImageView) v.findViewById(R.id.list_user_item_image);
            textView.setText(userArrayList.get(position).getUsername() + " (" + userArrayList.get(position).getComefrom() + ")");
            imageView.setImageResource(R.mipmap.profile_user_image);
            return v;
        }
    }
}
