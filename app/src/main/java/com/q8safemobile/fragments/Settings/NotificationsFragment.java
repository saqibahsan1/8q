package com.q8safemobile.fragments.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class NotificationsFragment extends BaseFragment {


    @BindView(R.id.switchNewWifi)
    Switch switchNewWifi;
    @BindView(R.id.TaskKilling)
    Switch TaskKilling;
    @BindView(R.id.switchAppInstall)
    Switch switchAppInstall;
    @BindView(R.id.spanCalls)
    Switch spanCalls;
    @BindView(R.id.scanCompleteNotification)
    Switch scanCompleteNotification;
    @BindView(R.id.pushNotifications)
    Switch pushNotifications;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void inits() {
    }
    private void SharedBol(String key, boolean value){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("abc", MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    private boolean retrieveInt(String key){
        boolean count = false;
        SharedPreferences prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        count   = prefs.getBoolean(key, true);//"No name defined" is the default value.
        return count;
    }

    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(false, "", false);
    }

    @Override
    public void setEvents() {

    }
    private void CheckBol(){
        if (retrieveInt("switchNewWifi")){
            switchNewWifi.setChecked(true);
        }else {
            switchNewWifi.setChecked(false);
        }

        if (retrieveInt("TaskKilling")){
            TaskKilling.setChecked(true);
        }else {
            TaskKilling.setChecked(false);
        }
        if (retrieveInt("switchAppInstall")){
            switchAppInstall.setChecked(true);
        }else {
            switchAppInstall.setChecked(false);
        }
        if (retrieveInt("spanCalls")){
            spanCalls.setChecked(true);
        }else {
            spanCalls.setChecked(false);
        }
        if (retrieveInt("scanCompleteNotification")){
            scanCompleteNotification.setChecked(true);
        }else {
            scanCompleteNotification.setChecked(false);
        }
        if (retrieveInt("pushNotifications")){
            pushNotifications.setChecked(true);
        }else {
            pushNotifications.setChecked(false);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBol();
        switchNewWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchNewWifi",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive notification when new wifi is connected", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive notification when new wifi is connected", Toast.LENGTH_SHORT).show();

                }
            }
        });
        TaskKilling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("TaskKilling",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive notification when background task is killed ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive notification when background task is killed ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchAppInstall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchAppInstall",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive notification when app is installed or update", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive notification when app is installed or update", Toast.LENGTH_SHORT).show();

                }
            }
        });
        spanCalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("spanCalls",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive notification when spam call is received", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive notification when spam call is received", Toast.LENGTH_SHORT).show();

                }
            }
        });
        scanCompleteNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("scanCompleteNotification",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive notification when scan is completed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive notification when scan is completed", Toast.LENGTH_SHORT).show();

                }
            }
        });
        pushNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("pushNotifications",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "You will receive push notifications", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "You will not receive push notifications", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
