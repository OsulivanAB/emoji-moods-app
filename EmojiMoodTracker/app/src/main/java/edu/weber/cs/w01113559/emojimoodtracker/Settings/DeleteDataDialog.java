package edu.weber.cs.w01113559.emojimoodtracker.Settings;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.R;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;

public class DeleteDataDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
                .setTitle("Delete Data?")
                .setMessage("Are you sure you want to delete all your data? It will not be recoverable.")
                .setPositiveButton("Cancel", (dialogInterface, i) -> dismiss())
                .setNegativeButton("Delete Forever", (dialogInterface, i) -> {
                    AppDatabase mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
                    if (mDatabase == null) {
                        mDatabase = GlobalAppDatabase.initializeAppDatabaseInstance();
                    }
                    mDatabase.removeAllRecords();
                });

        return builder.create();
    }
}
