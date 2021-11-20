package edu.weber.cs.w01113559.emojimoodtracker;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.FragmentEmojiBinding;

public class EmojiFragment extends Fragment {

    private FragmentEmojiBinding binding;

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmojiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.fab);
        graphFab.show();

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