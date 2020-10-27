package com.example.myapplication;
import java.io.Serializable;

public class User {
    private String name;
    private String _class;
    private String language;
    private String availability;
    private String[] hobbies;
    private String userId;

    public String getName () {
        return this.name;
    }

    public String getAvailability () {
        return this.availability;
    }
}
