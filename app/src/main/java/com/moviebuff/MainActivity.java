package com.moviebuff;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.moviebuff.application.AppApplication;
import com.moviebuff.constants.AppConstants;
import com.moviebuff.interfaces.AlertDialog_OnClickInterface;
import com.moviebuff.utils.AppLog;
import com.moviebuff.utils.AppUtil;

public class MainActivity extends AppCompatActivity implements AlertDialog_OnClickInterface {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView recyclerView_gridOfMovies_MainActivity;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

        fetchMoviesData();
    }

    private void initializeView() {
        recyclerView_gridOfMovies_MainActivity = (RecyclerView) findViewById(R.id.recyclerView_gridOfMovies_MainActivity);
        recyclerView_gridOfMovies_MainActivity.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void fetchMoviesData() {
        // Tag used to cancel the request
        String tag_GET_URL_MOVIES_LIST_req = "string_req";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConstants.GET_URL_MOVIES_LIST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                AppLog.d(TAG, response);
                AppUtil.hideProgressDialog(progressDialog);
                // TODO parse response and show in view
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.e(TAG, "Error: " + error.getMessage());
                AppUtil.hideProgressDialog(progressDialog);
                AppUtil.showAlertDialogWith_TwoButtons(MainActivity.this, getString(R.string.WhoopsThereWasAnErrorPleaseTryAgainLater)
                        + "\n" + error.toString(), MainActivity.this, getString(R.string.Retry), getString(R.string.Exit), TAG, false);

            }
        }); //TODO add header Authorization: Bearer AppConstants.TMDB_API_ACCESS_TOKEN
        AppLog.d(TAG, "AppConstants.GET_URL_MOVIES_LIST:" + AppConstants.GET_URL_MOVIES_LIST);

        if (AppUtil.isInternetConnectionAvailable(this)) {

            progressDialog = AppUtil.showProgressDialog(this, getString(R.string.Loading), false);

            // Adding request to request queue
            AppApplication.getInstance().addToRequestQueue(strReq, tag_GET_URL_MOVIES_LIST_req);
        } else {
            AppUtil.showAlertDialogWith1Button(this, getString(R.string.PleaseConnectToAWorkingInternetConnection), this, getString(R.string.Retry), TAG, false);
        }
    }

    @Override
    public void onAlertDialogButtonClicked(String buttonText, String strTag) {
        if (strTag != null && strTag.equals(TAG)) {
            if (buttonText != null && buttonText.equals(getString(R.string.Retry))) {
                fetchMoviesData();
            }
        }
    }
}