package edu.weber.cs.w01113559.emojimoodtracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.Record;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.FragmentEmojiBinding;

public class EmojiFragment extends Fragment {

    private final String TAG = "emoji-mood-tracker";
    private FragmentEmojiBinding binding;
    private AppDatabase mDatabase;

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmojiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initData();
        return root;
    }

    private void initData() {

        // Set Toolbar
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Record Mood");

        // Show FAB
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.fab);
        graphFab.show();

        if (GlobalAppDatabase.getAppDatabaseInstance() == null) {
            GlobalAppDatabase.setAppDatabaseInstance(new AppDatabase(getContext()));
        }
        mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        // ToDo: This should be in a "Populate page" function
        // Loop through Rows in the Table
        for (int i = 0; i < binding.emojiTable.getChildCount(); i++) {
            if (binding.emojiTable.getChildAt(i) instanceof TableRow) {
                TableRow row = (TableRow) binding.emojiTable.getChildAt(i);
                // Loop Through Items in the row
                for (int j = 0; j < row.getChildCount(); j++) {
                    if (row.getChildAt(j) instanceof AppCompatImageButton) {
                        // Analyze the image
                        AppCompatImageButton image = (AppCompatImageButton) row.getChildAt(j);
                        image.setOnClickListener(emojiButtonListener);
                    }
                }
            }
        }
        // ToDo: End ToDo
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Listener for almost all buttons
     */
    private View.OnClickListener emojiButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(binding.getRoot(), "You chose: " + v.getTag().toString() + ".", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show();
        }
    };
}