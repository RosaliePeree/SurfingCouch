package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Reviews {
    private String content;
    private int grade;
    private String id;
    private String posterID;
    private String receivingID;
    private String title;
    private String name;



    public Reviews(String content, int grade, String id, String posterID, String receivingID, String title, String name) {

        this.content = content;
        this.grade = grade;
        this.id = id;
        this.posterID = posterID;
        this.receivingID = receivingID;
        this.title = title;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public String getReceivingID() {
        return receivingID;
    }

    public void setReceivingID(String receivingID) {
        this.receivingID = receivingID;
    }
}
