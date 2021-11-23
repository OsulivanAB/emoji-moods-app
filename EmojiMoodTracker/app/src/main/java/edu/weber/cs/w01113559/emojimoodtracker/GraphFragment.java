package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import androidx.core.util.Pair;
import android.view.LayoutInflater;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
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
    private Date startDateRange;
    private Date endDateRange;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Hide Graph Button
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.fab);
        graphFab.hide();
        initData();
    }

    /**
     * Initializes all variables
     */
    private void initData() {

        context = getContext();

        mDatabase = new AppDatabase(getContext());
        mDatabase.setInterface(this);

        pieChart = binding.examplePieChart;
        setupPieChart();

        tvDatePicker = binding.tvDateRangePicker;

        tvDatePicker.setOnClickListener(view -> {

            MaterialDatePicker<Pair<Long, Long>> dateRangePicker =
                    MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(new Pair<>(startDateRange.getTime(), endDateRange.getTime()))
                    .build();

            dateRangePicker.show(getParentFragmentManager(), "Date Range Picker");
            dateRangePicker.addOnPositiveButtonClickListener(selection -> {
                startDateRange = new Date(selection.first + 25200000);
                endDateRange = new Date(selection.second + 25200000);
                updateDateRange();
            });
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
        pieChart.invalidate();  // Tells the pie chart to refresh

        // Add Load Animation
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    private Drawable shrinkEmoji(Drawable input_emoji) {
        Bitmap bitmap = ((BitmapDrawable) input_emoji).getBitmap();
        return new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 100, 100, true));
    }

    @Override
    public void updateChart(List<Record> records) {
        if (!datesUpdateFlag){
            updateDateRange(records);
        } else {
            loadPieData();
        }
    }

    private void updateDateRange(List<Record> records) {

        if (startDateRange == null) {
            startDateRange = records.get(0).getDate();
            endDateRange = records.get(0).getDate();
        }

        for (Record record : records) {
            if (record.getDate().before(startDateRange)) {
                startDateRange = record.getDate();
            } else if (record.getDate().after(endDateRange)) {
                endDateRange = record.getDate();
            }
        }

        updateDateRange();
    }

    private void updateDateRange(){
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        String newDateString = formatDate.format(startDateRange) + " - " + formatDate.format(endDateRange);
        tvDatePicker.setText(newDateString);

        loadPieData();
    }
}