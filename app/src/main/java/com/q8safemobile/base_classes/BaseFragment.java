package com.q8safemobile.base_classes;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;

import com.q8safemobile.utils.AppConstants;
import com.q8safemobile.utils.StaticMethods;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;
    private Activity context;
    public FragmentHandlingActivity fragmentActivity;
    public Handler handler;
    private ProgressDialog dialog;
    public ProgressDialog loader;
    View parentView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        handler = new Handler(Looper.getMainLooper());
        if (context == null)
            context = ((Activity) getActivity());

        inits();
        setEvents();
        setActionBar();
    }

    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (AppConstants.sDisableFragmentAnimations) {
            Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public FragmentHandlingActivity getFragmentActivity() {
        if (context == null)
            context = (Activity) getActivity();

        if (fragmentActivity == null) {
            if (context instanceof FragmentHandlingActivity) {
                fragmentActivity = (FragmentHandlingActivity) context;
            }
        }
        return fragmentActivity;
    }

    public Activity getContext() {
        if (context == null)
            context = getActivity();

        return context;
    }

    public void hideKeyboard() {
        StaticMethods.hideSoftKeyboard(getActivity());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (context == null)
            context = (Activity) activity;
    }


    public String getFieldTexT(EditText edit) {
        try {
            return edit.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    abstract public void inits();

    abstract public void setActionBar();

    abstract public void setEvents();


    public void Log(String tag, String value) {
        if (AppConstants.onTest) Log.e(tag, value);
    }

    public void Log(String value) {
        if (AppConstants.onTest) Log.e(getClass().getSimpleName() + "", value);
    }

    public void commingSoonToast() {
        makeToast("Will be implemented in BETA");
    }

    public void makeConnectionToast() {
        makeSnackbar("Connection Timeout !");
    }

    public void makeSnackbar(String str) {
        ((BaseActivity) context).makeSnackbar(str);
    }

    public void makeToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void makeToastLong(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public String getText(String string) {
        if (string == null)
            return "";
        else return string;
    }

}
