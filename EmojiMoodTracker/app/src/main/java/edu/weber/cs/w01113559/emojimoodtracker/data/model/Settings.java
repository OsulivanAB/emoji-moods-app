package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Settings {

    private List<String> emojiList;

    public List<String> getEmojiList() {
        return emojiList;
    }

    public void setEmojiList(List<String> emojiList) {
        this.emojiList = emojiList;
    }

    @Exclude
    public void addEmoji(@NonNull String emoji) {
        this.emojiList.add(emoji);
    }

    @Exclude
    public void removeEmoji(@NonNull String emoji) {
        this.emojiList.remove(emoji);
    }

    public Settings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Settings(List<String> emojiList) {
        this.emojiList = emojiList;
    }
}
