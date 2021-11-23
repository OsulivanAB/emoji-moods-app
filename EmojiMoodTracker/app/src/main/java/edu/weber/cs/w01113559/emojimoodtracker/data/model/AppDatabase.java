package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppDatabase {

    private final String TAG = "emoji-mood-tracker";

    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userID;
    public List<Record> recordList;
    protected Settings userSettings;

    private DatabaseReference databaseReference;    // General Database Reference
    private DatabaseReference mRecordsRef;          // Reference to the records for the user
    private DatabaseReference mUserSettingsRef;        // Refrence to the user settings

    private graphFragInterface mCallback;

    public interface graphFragInterface
    {
        void updateChart(List<Record> records);
    }

    public void setInterface(graphFragInterface reference){
        mCallback = reference;
    }

    public AppDatabase(Context _context) {

        // Initialize Variables
        this.context = _context;
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        this.userID = currentUser != null ? currentUser.getUid() : null;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        this.mRecordsRef = databaseReference.child("Records").child(userID);
        this.mUserSettingsRef = databaseReference.child("Settings").child(userID);
        recordList = new ArrayList<>();

        userSettingsEventListener();
        addRecordsEventListener();
    }

    /**
     * Add a record for the current user into the database.
     * @param emoji Drawable: emoji to store in record.
     */
    public void writeRecord(@NonNull Drawable emoji) {
        // ToDo: Check if there was a recent record created.
        // Create new record at /records/$userid/$recordid
        Record record = new Record(emoji, context);
        mRecordsRef.push().setValue(record);
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

    public void writeUserSettings(@NonNull List<Drawable> emojis){
        List<String> emojiList = emojiEncoding.getDecodedEmojiList(context, emojis);
        Settings _userSettings = new Settings(emojiList);
        mUserSettingsRef.setValue(_userSettings);
    }

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
}
