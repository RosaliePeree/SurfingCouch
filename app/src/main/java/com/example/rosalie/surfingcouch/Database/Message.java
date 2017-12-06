package com.example.rosalie.surfingcouch.Database;


import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by infer on 06/12/2017.
 */

public class Message {
    private int id;
    protected String senderID;
    private String senderName;
    private String text;
    private String timestamp;

    public Message(int id, String senderID, String senderName, String text, String timestamp) {
        this.id = id;
        this.senderID = senderID;
        this.senderName = senderName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timestamp = timeStamp;
    }
}
