package com.theah64.gpix.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.theah64.gpix.R;


/**
 * Created by shifar on 23/7/16.
 */
public class DialogUtils {
    private final Context context;
    private LayoutInflater inflater;

    public DialogUtils(final Context context) {
        this.context = context;
    }


    /**
     * Shows a error dialog with a single 'OK' button and the given error message.
     *
     * @param errorMessage error message explaining the error.
     */
    public void showErrorDialog(final String errorMessage) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.Error)
                .setMessage(errorMessage)
                .setNeutralButton(context.getString(android.R.string.ok), null)
                .show();
    }

    /**
     * Used to show a simple message along with a title.
     *
     * @param title
     * @param message
     */
    public void showSimpleMessage(final @StringRes int title, final String message) {
        showSimpleMessage(title, message, null);
    }

    public void showSimpleMessage(final @StringRes int title, final String message, DialogInterface.OnDismissListener dismissListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .setOnDismissListener(dismissListener)
                .create()
                .show();
    }


    public void showErrorDialog(final @StringRes int errorMessage) {
        showErrorDialog(context.getString(errorMessage));
    }

    /**
     * To show a loading dialog.
     *
     * @param message message to be displayed on the dialog.
     * @return an instance of the showing dialog.
     */
    public AlertDialog getLoadingDialog(final @StringRes int message) {
        return getLoadingDialog(false, context.getString(message));
    }

    public AlertDialog getLoadingDialog(final boolean isCancellable, String message) {

        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }

        @SuppressLint("InflateParams")
        final View loadingDialogue = inflater.inflate(R.layout.loading_dialog, null);

        //Setting message
        ((TextView) loadingDialogue.findViewById(R.id.tvMessage))
                .setText(message);

        return new AlertDialog.Builder(context)
                .setView(loadingDialogue)
                .setCancelable(isCancellable)
                .create();
    }


    /**
     * Shows a closed question confirmation dialogue.
     */
    public void showConfirmDialog(@StringRes final int title, final String message, final String specialButtonText, final ClosedQuestionCallback callback) {

        final AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onYes();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNo();
                    }
                });


        //Checking if there's a special button.
        if (specialButtonText != null && callback instanceof AdvancedClosedQuestionCallback) {

            final AdvancedClosedQuestionCallback advCallback = (AdvancedClosedQuestionCallback) callback;
            confirmDialogBuilder.setNeutralButton(specialButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    advCallback.onSpecialButtonClicked();
                }
            });
        }


        confirmDialogBuilder.create()
                .show();
    }


    public void showConfirmDialog(@StringRes final int title, @StringRes final int message, final ClosedQuestionCallback callback) {
        showConfirmDialog(title, context.getString(message), null, callback);
    }

    public void showConfirmDialog(@StringRes final int title, @StringRes final int message, @StringRes final int spButtonText, final ClosedQuestionCallback callback) {
        showConfirmDialog(title, context.getString(message), context.getString(spButtonText), callback);
    }


    public interface ClosedQuestionCallback {
        void onYes();

        void onNo();
    }

    public interface AdvancedClosedQuestionCallback extends ClosedQuestionCallback {
        void onSpecialButtonClicked();
    }
}