package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Reviews {
    private String content;
    private int grade;
    private int id;
    private int posterID;
    private int receivingID;
    private String title;

    public Reviews(String content, int grade, int id, int posterID, int receivingID, String title) {
        this.content = content;
        this.grade = grade;
        this.id = id;
        this.posterID = posterID;
        this.receivingID = receivingID;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosterID() {
        return posterID;
    }

    public void setPosterID(int posterID) {
        this.posterID = posterID;
    }

    public int getReceivingID() {
        return receivingID;
    }

    public void setReceivingID(int receivingID) {
        this.receivingID = receivingID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
