package com.example.movieapp.util;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtil {

    public interface OnConfirmListener {
        void onConfirm();
    }

    public static void showConfirmDialog(Context context, String title, String message, OnConfirmListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> listener.onConfirm())
                .setNegativeButton("Cancel", null)
                .show();
    }
}
