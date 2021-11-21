package edu.weber.cs.w01113559.emojimoodtracker;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
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

import java.util.ArrayList;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.FragmentGraphBinding;

public class GraphFragment extends Fragment {

    private FragmentGraphBinding binding;
    private PieChart pieChart;

    public GraphFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraphBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
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
        pieChart = binding.examplePieChart;
        setupPieChart();
        loadPieData();

        AppCompatTextView tvDatePicker = binding.tvDateRangePicker;
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker dateRangePicker =
                        MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select dates")
                        .setSelection(new Pair<Long, Long>(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                        .build();

                dateRangePicker.show(getParentFragmentManager(), "Date Range Picker");
            }
        });
    }

    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);  // Adds a hole in the center (Donut instead of Pie)
//        pieChart.setUsePercentValues(true); // Let the pie chart know we're passing in percentage values.
//        pieChart.setEntryLabelTextSize(12f);
//        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Mood Summary");     // Puts text in the center of the donut!
        pieChart.setCenterTextSize(24f);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
    }

    private void loadPieData(){

        // Create Pie Chart Entries Array
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(1, shrinkEmoji(ContextCompat.getDrawable(getActivity(), R.drawable.emoji_u1f600))));
        entries.add(new PieEntry(5, shrinkEmoji(ContextCompat.getDrawable(getActivity(), R.drawable.emoji_u1f607))));
        entries.add(new PieEntry(6, shrinkEmoji(ContextCompat.getDrawable(getActivity(), R.drawable.emoji_u1f608))));
        entries.add(new PieEntry(3, shrinkEmoji(ContextCompat.getDrawable(getActivity(), R.drawable.emoji_u1f621))));

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
}