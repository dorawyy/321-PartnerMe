package com.example.myapplication;
import java.io.Serializable;

public class MatchResult {
    private Float similarity;
    private User userList;

    public Float getSimilarity(){ return this.similarity;}
    public User getUser(){ return this.userList; }
}
