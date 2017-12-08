package com.example.rosalie.surfingcouch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ListOfUsersActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_list_ofusers, null, false);
        drawer.addView(contentView, 0);

        //Intent tu use when we have to display a users profile (when the Users will be created and displayed)
        Intent intent = new Intent(this,ProfileActivity.class);
        Bundle b = new Bundle();
        b.putString ("userID", "yPBmQOC7GEN6h4uhkaWa0SMydto2"); //Test id, to be replaced by the id of the user that is clicked
        intent.putExtras(b); //Put your id to your next Intent*/
        startActivity(intent);
    }
}
