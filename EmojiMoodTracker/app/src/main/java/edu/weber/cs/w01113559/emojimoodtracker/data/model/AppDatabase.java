package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppDatabase {

    //region Variables
    private final String TAG = "emoji-mood-tracker";

    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userID;
    public static List<Record> recordList;
    public static Settings userSettings;

    private static DatabaseReference databaseReference;    // General Database Reference
    private static DatabaseReference mRecordsRef;          // Reference to the records for the user
    private static DatabaseReference mUserSettingsRef;        // Refrence to the user settings

    private graphFragInterface mCallback;
    //endregion

    //region Interface
    public interface graphFragInterface {
        void updateChart(List<Record> records);
    }

    public void setInterface(graphFragInterface reference){
        mCallback = reference;
    }

    public void RemoveInterface(){
        mCallback = null;
    }
    //endregion

    //region Constructors
    public AppDatabase(Context _context) {

        // Initialize Variables
        this.context = _context;
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        this.userID = currentUser != null ? currentUser.getUid() : null;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mRecordsRef = databaseReference.child("Records").child(userID);
        mUserSettingsRef = databaseReference.child("Settings").child(userID);
        recordList = new ArrayList<>();

        userSettingsEventListener();
        addRecordsEventListener();
    }
    //endregion

    //region Getters
    public String getUserID() {
        return userID;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public Settings getUserSettings() {
        return userSettings;
    }
    //endregion

    //region Event Listeners
    /**
     * Gets record list from database and assigns it to recordList. Listens for updates.
     */
    private void addRecordsEventListener() {
        mRecordsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Record record = snapshot.getValue(Record.class);
                String key = snapshot.getKey();
                if (record != null) { record.setKey(key); }
                recordList.add(record);
                if (mCallback != null) {
                    mCallback.updateChart(recordList);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Record record = snapshot.getValue(Record.class);
                String key = snapshot.getKey();
                if (record != null) { record.setKey(key); }

                // Find old version of record and replace it
                for (Record r : recordList) {
                    if (record != null && r.getKey().equals(record.getKey())) {
                        recordList.set(recordList.indexOf(r), record);
                        break;
                    }
                }
                if (mCallback != null) {
                    mCallback.updateChart(recordList);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Record record = snapshot.getValue(Record.class);
                if (record != null) {
                    record.setKey(snapshot.getKey());
                }

                // Find old version of record and replace it
                for (Record r : recordList) {
                    if (record != null && r.getKey().equals(record.getKey())) {
                        recordList.remove(r);
                        break;
                    }
                }
                if (mCallback != null) {
                    mCallback.updateChart(recordList);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void userSettingsEventListener() {
        mUserSettingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userSettings = snapshot.getValue(Settings.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //endregion

    //region Writers
    public static boolean writeRecord(@NonNull String emojiCode) {
        Record record = new Record(emojiCode);
        boolean newFlag = checkForRecentRecord(record);
        mRecordsRef.push().setValue(record);
        return newFlag;
    }

    private static void removeRecord(String key) {
        mRecordsRef.child(key).removeValue();
    }

    /**
     * Clears all Records for the current user.
     */
    public void removeAllRecords(){
        mRecordsRef.removeValue();
    }

    /**
     * Add a new user record to the database.
     * @param uID String: user ID provided by Firebase Authentication.
     * @param email String: user email used to log in.
     */
    public void writeNewUser(@NonNull String uID, @NonNull String email) {
        // 1 - Create User
        // 2 - Save User in /Users/$userid/
        User user = new User("Anthony.R.Bahl@Gmail.com");
        databaseReference.child("Users").child(uID).setValue(user);
    }

    public static void writeUserSettings(Context context, @NonNull List<String> emojis, List<ReminderData> reminders){
        userSettings.deleteAlarmsForReminders(context);
        Settings _userSettings = new Settings(emojis, reminders);
        _userSettings.scheduleAlarmsForReminders(context);
        mUserSettingsRef.setValue(_userSettings);
    }

    public void writeEmojiList(Context context, List<String> emojiList) {
        if (emojiEncoding.validateList(context, emojiList)){
            DatabaseReference reference = databaseReference.child("Settings").child(userID).child("emojiList");
            reference.setValue(emojiList);
        }
    }
    //endregion

    //region Private Functions

    /**
     * Checks to see if there has been a recent {@link Record} created already.
     * @param newRecord {@link Record} new record to be added.
     * @return {@link Boolean} true: there is not a recent record, false: there is a recent record.
     */
    private static boolean checkForRecentRecord(Record newRecord) {
        // ToDo: Add user preference to control this number
        int CREATE_REACORD_TIME_FRAME_MIN = 15;
        for (Record record: recordList) {
            if (TimeUnit.MILLISECONDS.toMinutes(newRecord.getTimestamp() - record.getTimestamp()) <= CREATE_REACORD_TIME_FRAME_MIN) {
                removeRecord(record.getKey());
                return false;
            }
        }
        return true;
    }
    //endregion
}
