package com.vagnermartins.marcatento.entity;

/**
 * Created by vagnnermartins on 19/03/15.
 */
public class Game {

    private long id;
    private String weName;
    private int weScore;
    private String theyName;
    private int theyScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWeName() {
        return weName.equals("") ? "Nós" :  weName;
    }

    public void setWeName(String weName) {
        this.weName = weName;
    }

    public int getWeScore() {
        return weScore;
    }

    public void setWeScore(int weScore) {
        this.weScore = weScore;
    }

    public String getTheyName() {
        return theyName.equals("") ? "Nós" :  theyName;
    }

    public void setTheyName(String theyName) {
        this.theyName = theyName;
    }

    public int getTheyScore() {
        return theyScore;
    }

    public void setTheyScore(int theyScore) {
        this.theyScore = theyScore;
    }
}
