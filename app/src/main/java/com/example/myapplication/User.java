package com.example.myapplication;

public class User {
    private String Name;
    private String Class;
    private String Language;
    private String Availability;
    private String Hobbies;
    private String Email;

    public User() {
    }

    public User(String name, String classes, String language, String hobbies) {
        this.Name = name;
        this.Class = classes;
        this.Language = language;
        this.Hobbies = hobbies;
    }

    public String getName () {
        return this.Name;
    }

    public String getAvailability () {
        return this.Availability;
    }

    public String get_Class() { return this.Class;}

    public String getHobbies() {return this.Hobbies;}

    public String getLanguage() {return this.Language;}

    public String getEmail() {return this.Email;}


}
