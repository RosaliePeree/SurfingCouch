package com.example.rosalie.surfingcouch.Messages;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rosalie.surfingcouch.Database.Message;
import com.example.rosalie.surfingcouch.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

/**
 * Created by infer on 06/12/2017.
 */

public class MessageAdapter extends FirebaseListAdapter<Message>{

    private ChatActivity activity;

    public MessageAdapter(Activity activity, Class<Message> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.activity = (ChatActivity) activity;
    }

    @Override
    protected void populateView(View v, Message model, int position) {

            TextView messageText = (TextView) v.findViewById(R.id.message_text);
            TextView messageUser = (TextView) v.findViewById(R.id.message_username);
            TextView messageTime = (TextView) v.findViewById(R.id.message_timestamp);

            messageText.setText(model.getText());
            messageUser.setText(model.getSenderName());

            // Format the date before showing it
            messageTime.setText(model.getTimeStamp());
        }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Message message = getItem(position);
        if (message.getSenderID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            view = activity.getLayoutInflater().inflate(R.layout.item_message_sender2, viewGroup, false);
        else
            view = activity.getLayoutInflater().inflate(R.layout.item_message_sender, viewGroup, false);

        //generating view
        populateView(view, message, position);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }
}
