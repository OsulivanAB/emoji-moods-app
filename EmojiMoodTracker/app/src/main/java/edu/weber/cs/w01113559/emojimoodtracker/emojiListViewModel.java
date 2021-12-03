package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;

public class emojiListViewModel extends ViewModel {
    private LiveData<List<String>> emojiList;

    public LiveData<List<String>> getEmojiList(Context context) {
//        LiveData<List<String>> emojis = new LiveData<List<String>>() {
//        }
        return emojiList;
    }
}
