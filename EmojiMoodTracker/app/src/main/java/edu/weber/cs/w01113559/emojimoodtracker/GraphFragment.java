package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import androidx.core.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.Record;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.emojiEncoding;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.FragmentGraphBinding;

public class GraphFragment extends Fragment implements AppDatabase.graphFragInterface {

    private FragmentGraphBinding binding;
    private PieChart pieChart;
    private AppDatabase mDatabase;
    private Context context;
    private boolean datesUpdateFlag;
    private AppCompatTextView tvDatePicker;
    private Date startDateRange = new Date(System.currentTimeMillis());
    private Date endDateRange = new Date(System.currentTimeMillis());
    private MaterialDatePicker<Pair<Long, Long>> dateRangePicker;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);

        // Setup Action Bar
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Remove Settings Button
        MenuItem settingsButton = menu.findItem(R.id.action_settings);
        settingsButton.setVisible(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*// Hide Graph Button
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.graphFAB);
        graphFab.hide();*/
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateChart(mDatabase.recordList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDatabase.RemoveInterface();
    }

    /**
     * Initializes all variables
     */
    private void initData() {

        context = getContext();

        // Database
        mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
        if (mDatabase == null) mDatabase = GlobalAppDatabase.initializeAppDatabaseInstance();
        mDatabase.setInterface(this);

        // Chart
        pieChart = binding.chart;
        setupPieChart();

        // Views
        tvDatePicker = binding.tvDateRangePicker;

        tvDatePicker.setOnClickListener(view -> {

            dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(new Pair<>(startDateRange.getTime(), endDateRange.getTime()))
                    .setTheme(R.style.Theme_EmojiMoodTracker_GraphDatePicker_Dialog)
                    .build();

            dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                startDateRange = new Date(selection.first + 25200000);
                endDateRange = new Date(selection.second);
                updateDateRange();
                loadPieData();
            });

            dateRangePicker.show(getParentFragmentManager(), "Date Range Picker");
        });
        datesUpdateFlag = false;
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);  // Adds a hole in the center (Donut instead of Pie)
        pieChart.setCenterText("Mood Summary");     // Puts text in the center of the donut!
        pieChart.setCenterTextSize(24f);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
    }

    private void loadPieData() {
        pieChart.clear();

        Map<String, Integer> scores = new HashMap<>();

        // Loop through record list and count each emoji occurance
        for (Record record : mDatabase.recordList) {

            // Check if record is within date range
            Date recordDate = record.getDate();
            if (recordDate.before(startDateRange) || recordDate.after(endDateRange)) { continue; }

            String emoji = record.getEmojiCode();
            boolean flag = false;

            // See if they already have a score in the scores map
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                String key = entry.getKey();
                if (key.equals(emoji)){
                    int newScore = entry.getValue() + 1;
                    entry.setValue(newScore);
                    flag = true;
                    break;
                }
            }

            // If it wasn't already in there, add it and give it a score of 1
            if (!flag) {
                scores.put(emoji, 1);
            }
        }

        // Create Pie Chart Entries Array
        ArrayList<PieEntry> entries = new ArrayList<>();

        // Populate Pie chart entries
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            Drawable smallerDrawable = shrinkEmoji(emojiEncoding.encodeEmoji(entry.getKey(), context));
            entries.add(new PieEntry(entry.getValue(), smallerDrawable));
        }

        // Create Pie Chart Colors Array
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) { colors.add(color); }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) { colors.add(color); }

        // Create Pie Dataset
        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        // Create Pie Data
        PieData data = new PieData(dataSet);
        // ToDo: Add back if we can figure out how to get the emoji to not cover the value
//        data.setDrawValues(true);   // Draw the values of the percentages
//        data.setValueFormatter(new PercentFormatter(pieChart)); // Format the values as percentages.
//        data.setValueTextSize(12f);
//        data.setValueTextColor(Color.BLACK);


        // Start Up Pie Chart
        pieChart.setData(data); // Pass the pie chart the data
//        pieChart.invalidate();  // Tells the pie chart to refresh

        // Add Load Animation
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private Drawable shrinkEmoji(Drawable input_emoji) {
        Bitmap bitmap = convertToBitmap(input_emoji);
        return new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
    }

    private Bitmap convertToBitmap(Drawable input) {
        try {
            Bitmap bitmap;

            bitmap = Bitmap.createBitmap(input.getIntrinsicWidth(), input.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            input.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            input.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("GraphFragment.java/convertToBitmap has encountered an out of memory error.");
        }
    }

    @Override
    public void updateChart(List<Record> records) {
        if (!datesUpdateFlag){
            updateDateRange(records);
        }
        loadPieData();
    }

    private void updateDateRange(List<Record> records) {

        boolean changeflag = false;

        for (Record record : records) {
            if (record.getDate().before(startDateRange)) {
                startDateRange = record.getDate();
                changeflag = true;
            }
        }

        if (changeflag) {
            updateDateRange();
        }
    }

    private void updateDateRange(){
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(startDateRange);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        startDateRange = new Date(cal.getTimeInMillis());

        cal.setTime(endDateRange);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        endDateRange = new Date(cal.getTimeInMillis());

        String newDateString = formatDate.format(startDateRange) + " - " + formatDate.format(endDateRange);
        tvDatePicker.setText(newDateString);
    }
}