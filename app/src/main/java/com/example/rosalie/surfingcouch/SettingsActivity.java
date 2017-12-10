package com.example.rosalie.surfingcouch;

/**
 * Created by Rose on 07-11-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends NavigationDrawerActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_settings, null, false);
        drawer.addView(contentView, 0);
        SettingsActivity.this.getSharedPreferences(SettingsActivity.this.getApplicationContext());
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private SharedPreferences sharedPreferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.user_settings);

            mReference = FirebaseDatabase.getInstance().getReference().child("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User use = dataSnapshot.getValue(User.class);
                    //Log.i(use.getName(), " user");
                    mCurrentUser = use;
                    updateFromPref(mCurrentUser);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("username")) {
                mReference = FirebaseDatabase.getInstance().getReference().child("User/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
                mReference.child("username").setValue(sharedPreferences.getString("username",""));
            }
        }

        public void updateFromPref(User user){
            sharedPreferences = getActivity().getBaseContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user.getUsername()); //This is just an example, you could also put boolean, long, int or floats
            editor.putString("location", user.getComefrom());
            editor.putString("email", user.getEmail());
            editor.commit();

            Preference etp = findPreference("username");
            etp.setSummary(user.getUsername());
            Preference etp2 = findPreference("email");
            etp2.setSummary(user.getEmail());
            Preference etp3 = findPreference("location");
            etp3.setSummary(user.getComefrom());
            onSharedPreferenceChanged(null, "");
        }
    }
}
