package com.moviebuff.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.moviebuff.R;
import com.moviebuff.interfaces.AlertDialog_OnClickInterface;

/**
 * Created by Admin on 19-02-2017.
 */

public class AppUtil {

    /**
     * Checks if android device is connected to network or not for Internet connection.
     *
     * @param context Context object
     * @return true if device is connected to any network else false
     */
    public static boolean isInternetConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showAlertDialogWith1Button(Context context, String messageToShowOnAlert, final AlertDialog_OnClickInterface
            mAlertDialog_OnClickListener, String buttonText, final String strTAG, boolean isCancellable) {

        if (context == null) {
            return;
        }
        AlertDialog.Builder alertDialog_builder = new AlertDialog.Builder(context);
        alertDialog_builder.setCancelable(isCancellable);
        alertDialog_builder.setMessage(messageToShowOnAlert);

        if (buttonText == null) {
            buttonText = context.getString(android.R.string.ok);
        }
        final String finalButtonText = buttonText;

        alertDialog_builder.setPositiveButton(buttonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalButtonText, strTAG);
                        }
                    }
                });
        if (!((Activity) context).isFinishing()) // show dialog only if activity is not finishing
            alertDialog_builder.show();
    }

    public static void showAlertDialogWith_TwoButtons(Context context, String messageToShowOnAlert, final AlertDialog_OnClickInterface
            mAlertDialog_OnClickListener, String positiveButtonText, String negativeButtonText, final String strTAG, boolean isCancellable) {

        if (context == null) {
            return;
        }
        AlertDialog.Builder alertDialog_builder = new AlertDialog.Builder(context);
        alertDialog_builder.setCancelable(isCancellable);
        alertDialog_builder.setMessage(messageToShowOnAlert);

        if (positiveButtonText == null) {
            positiveButtonText = context.getString(android.R.string.ok);
        }
        final String finalPositiveButtonText = positiveButtonText;
        alertDialog_builder.setPositiveButton(positiveButtonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalPositiveButtonText, strTAG);
                        }
                    }
                });

        if (negativeButtonText == null) {
            negativeButtonText = context.getString(android.R.string.cancel);
        }
        final String finalNegativeButtonText = negativeButtonText;
        alertDialog_builder.setNegativeButton(negativeButtonText, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (mAlertDialog_OnClickListener != null && strTAG != null) {
                            mAlertDialog_OnClickListener.onAlertDialogButtonClicked(
                                    finalNegativeButtonText, strTAG);
                        }
                    }
                });

        if (!((Activity) context).isFinishing()) // show dialog only if activity is not finishing
            alertDialog_builder.show();
    }

    public static void showToast(Context context, String strToastMessage, boolean isShownLong) {
        if (isShownLong)
            Toast.makeText(context, strToastMessage, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, strToastMessage, Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog showProgressDialog(Context context, String msgOnProgressDialog, boolean isCancellable) {
        ProgressDialog progressDialog = null;
        if (context != null) {
            progressDialog = new ProgressDialog(context);

            if (msgOnProgressDialog == null)
                msgOnProgressDialog = context.getString(R.string.Loading);

            progressDialog.setMessage(msgOnProgressDialog);
            progressDialog.setCancelable(isCancellable);
            progressDialog.show();
        }
        return progressDialog;
    }

    public static void hideProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}