package com.q8safemobile.base_classes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.q8safemobile.utils.AppConstants;

/**
 * Created by ahsan on 27/05/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public ProgressDialog loader;
    public BaseActivity context = this;
    public Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(Looper.getMainLooper());
    }

    public abstract void showLoader(boolean isWhiteBackground);

    public abstract void hideLoader();


    public void Log(String tag, String value) {
        if (AppConstants.onTest) Log.e(tag, value);
    }

    public void makeConnectionToast() {
        Toast.makeText(context, "Request Failed, Please Check Internet Connection !", Toast.LENGTH_SHORT).show();
    }

    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void makeSnackbar(String str) {
        try {
            Snackbar.make(findViewById(android.R.id.content), str + "", Snackbar.LENGTH_LONG).show();
        } catch (Exception e) {
            makeToastLong(str + "");
        }
    }

    public void makeConnectionSnackbar() {
        makeSnackbar("Connection Timeout !");
    }
}
