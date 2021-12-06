package edu.weber.cs.w01113559.emojimoodtracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutDialog extends DialogFragment {

    private signOutInterface mCallback;

    interface signOutInterface {
        Boolean signout();
    }

    public LogoutDialog(signOutInterface mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to Logout?")
                .setIcon(R.drawable.ic_baseline_question_mark_24)
                .setNegativeButton("Cancel", (dialogInterface, i) -> dismiss())
                .setPositiveButton("Logout", (dialogInterface, i) -> {
                    try {
                        mCallback.signout();
                    } catch (UnsupportedOperationException e) {
                        throw new UnsupportedOperationException("Must implement the signOutInterface.");
                    }
                });

        return builder.create();
    }
}
