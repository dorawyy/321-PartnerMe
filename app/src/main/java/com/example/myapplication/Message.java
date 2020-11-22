package com.example.myapplication;

public class Message {
    private String text;
    private User memberData;
    private boolean belongsToCurrentUser;

    public Message(String text, User data, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public User getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}