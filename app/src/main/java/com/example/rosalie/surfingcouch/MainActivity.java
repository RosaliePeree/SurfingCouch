package com.example.rosalie.surfingcouch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rosalie.surfingcouch.Database.User;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView mStatusTextView;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mUsernameField;
    private RadioGroup mGenderField;
    private Place mPlaceField;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        mStatusTextView = findViewById(R.id.status);
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        mUsernameField = findViewById(R.id.field_username);
        mGenderField = findViewById(R.id.field_gender);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.verify_email_button).setOnClickListener(this);
        findViewById(R.id.save_data_new_user).setOnClickListener(this);
        findViewById(R.id.updateIU_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                mPlaceField = place;
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                            saveNewUser(mUsernameField.getText().toString(), mEmailField.getText().toString(), mGenderField.getCheckedRadioButtonId(), mPlaceField.getName().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "signInWithEmail:failure" + task.getException(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "signInWithEmail:success", Toast.LENGTH_SHORT).show();
                            updateUI(mAuth.getCurrentUser());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "signInWithEmail:failure" + task.getException(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText(R.string.main_failed);
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.verify_email_button).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.verify_email_button).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getApplicationContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            if (!user.isEmailVerified()) {
                mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                        user.getEmail(), user.isEmailVerified()));

                mStatusTextView.setText(user.getEmail() + " - Please verify your email to continue");

                findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
                findViewById(R.id.email_password_fields).setVisibility(View.GONE);
                findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
                findViewById(R.id.create_account_update_data).setVisibility(View.GONE);

                findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
            } else {
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            }
        } else {
            mStatusTextView.setText(R.string.main_signed_out);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            displayCreateAccount();
        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.sign_out_button) {
            signOut();
        } else if (i == R.id.verify_email_button) {
            sendEmailVerification();
            //Intent intent = new Intent(this, ProfileActivity.class); // Debug purpose
            //startActivity(intent);
        } else if (i == R.id.save_data_new_user) {
            if (mUsernameField.getText().toString().trim().length() == 0
                    || mGenderField.getCheckedRadioButtonId() == -1
                    || mPlaceField.getName().toString().trim().length() == 0)
                Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            else
            {
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        } else if (i == R.id.updateIU_button) {
            mAuth.getCurrentUser().reload();
            if (mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                findViewById(R.id.updateIU_button).setEnabled(false);
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, mAuth.getCurrentUser().getEmail() + " _ " + mAuth.getCurrentUser().isEmailVerified(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void displayCreateAccount() {
        findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
        findViewById(R.id.email_password_fields).setVisibility(View.GONE);
        findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        findViewById(R.id.create_account_update_data).setVisibility(View.VISIBLE);
    }

    public void saveNewUser(String username, String email, int radiobuttonId, String placeName) {
        FirebaseUser user = mAuth.getCurrentUser();
        HashMap<String,String> dummyList = new HashMap<>();
        dummyList.put("undefined","undefined");
        User newUser = new User(placeName, mEmailField.getText().toString(), null, user.getUid(), 0, dummyList, dummyList, dummyList, dummyList, username, dummyList);
        if (radiobuttonId == R.id.gender_male)
            newUser.setGender("Male");
        else
            newUser.setGender("Female");

        FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).setValue(newUser);
        user.sendEmailVerification();
        updateUI(user);
    }
}
