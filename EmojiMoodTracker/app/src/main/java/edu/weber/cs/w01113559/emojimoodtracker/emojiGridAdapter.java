package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.Settings;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.emojiEncoding;

public class emojiGridAdapter extends RecyclerView.Adapter<emojiGridAdapter.EmojiViewHolder> {

    private List<String> emojiList;
    private Context context;

    /**
     * Viewholder Class that will hold each emoji object
     */
    public class EmojiViewHolder extends RecyclerView.ViewHolder {

        protected final View itemRoot;
        protected final AppCompatImageButton emojiImage;
        protected String tag;

        public EmojiViewHolder(@NonNull View emojiButtonFragment) {
            super(emojiButtonFragment);

            this.itemRoot = emojiButtonFragment;
            this.emojiImage = emojiButtonFragment.findViewById(R.id.ibEmoji);
        }
    }

    /**
     * Initializes the Adapter
     * @param emojiList {@link List<String>} of emojis from the user {@link Settings}.
     */
    public emojiGridAdapter(Context context, List<String> emojiList) {
        this.context = context;
        this.emojiList = emojiList;
    }

    /**
     * Updates the emoji list.
     * @param list {@link List<String>} of emojis from user {@link Settings}.
     */
    public void setEmojiList(List<String> list) {
        this.emojiList.clear();
        this.emojiList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * Getter for the emoji list
     * @return {@link List<String>} the current list of emojis that the adapter is using.
     */
    public List<String> getEmojiList() {
        return emojiList;
    }

    /**
     * {@link RecyclerView} calls this method whenever it needs to create a new {@link emojiGridAdapter.EmojiViewHolder}.
     * The method creates and initializes the {@link emojiGridAdapter.EmojiViewHolder}. and its associated View,
     * but does not fill in the view's contents. The {@link emojiGridAdapter.EmojiViewHolder} has not yet been bound to
     * specific data.
     * @param parent {@link ViewGroup}
     * @param viewType {@link Integer}
     * @return {@link emojiGridAdapter.EmojiViewHolder} View to hold an emoji from the list.
     */
    @NonNull
    @Override
    public emojiGridAdapter.EmojiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create the view to hold the emoji button
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_emoji_button, parent, false);

        return new EmojiViewHolder(view);
    }

    /**
     * {@link RecyclerView} calls this method to associate a {@link emojiGridAdapter.EmojiViewHolder} with data.
     * The method fetches the appropriate data and uses the data to fill in the {@link emojiGridAdapter.EmojiViewHolder}'s layout.
     * @param holder {@link emojiGridAdapter.EmojiViewHolder} The view created in onCreateViewHolder()
     * @param position {@link Integer} The index in the emoji list that we're working with.
     */
    @Override
    public void onBindViewHolder(@NonNull emojiGridAdapter.EmojiViewHolder holder, int position) {
        String emojiCode = emojiList.get(position);
        Drawable emojiDrawable = emojiEncoding.encodeEmoji(emojiCode, context);

        if (emojiDrawable != null) {
            holder.emojiImage.setImageDrawable(emojiDrawable);
            holder.emojiImage.setTag(emojiCode);

            holder.emojiImage.setOnClickListener(view -> {
                String tag = (String) view.getTag();
                String message = AppDatabase.writeRecord(tag) ? "New Entry Recorded!" : "Recent entry updated to " + emojiEncoding.getDescription(context, tag);
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            });
        }
    }

    /**
     * {@link RecyclerView} calls this method to get the size of the data set.
     * @return {@link Integer} the number of emojis in the list.
     */
    @Override
    public int getItemCount() {
        return emojiList.size();
    }
}
