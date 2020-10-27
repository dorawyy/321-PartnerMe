package com.example.myapplication;
import java.io.Serializable;

public class User {
    private String Name;
    private String Class;
    private String Language;
    private String Availability;
    private String Hobbies;

    public String getName () {
        return this.Name;
    }

    public String getAvailability () {
        return this.Availability;
    }

    public String get_Class() { return this.Class;}

    public String getHobbies() {return this.Hobbies;}

    public String getLanguage() {return this.Language;}


}
