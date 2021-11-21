package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class settings {

    public String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public settings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
