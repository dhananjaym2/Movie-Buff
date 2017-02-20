package com.moviebuff;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.moviebuff.application.AppApplication;
import com.moviebuff.constants.AppConstants;
import com.moviebuff.interfaces.AlertDialog_OnClickInterface;
import com.moviebuff.utils.AppLog;
import com.moviebuff.utils.AppUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                AppConstants.GET_URL_MOVIES_LIST, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                AppLog.d(TAG, response.toString());
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapForHeader = new HashMap<>();
                mapForHeader.put(AppConstants.Authorization_HEADER_KEY, AppConstants.
                        BEARER_WITH_ACCESS_TOKEN_Authorization_HEADER_VALUE);
                return mapForHeader;
            }
        }; //TODO add header Authorization: Bearer AppConstants.TMDB_API_ACCESS_TOKEN

        if (AppUtil.isInternetConnectionAvailable(this)) {

            progressDialog = AppUtil.showProgressDialog(this, getString(R.string.Loading), false);

            AppLog.d(TAG, "AppConstants.GET_URL_MOVIES_LIST:" + AppConstants.GET_URL_MOVIES_LIST);

            // Adding request to request queue
            AppApplication.getInstance().addToRequestQueue(jsonObjectRequest, tag_GET_URL_MOVIES_LIST_req);
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