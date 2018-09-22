package com.q8safemobile.fragments.Settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.switchUpdateWifi)
    Switch switchUpdateWifi;

    @BindView(R.id.switchAppInstall)
    Switch switchAppInstall;
    @BindView(R.id.switchFileShield)
    Switch switchFileShield;
    @BindView(R.id.switchWebShield)
    Switch switchWebShield;
    @BindView(R.id.switchInternal)
    Switch switchInternal;
    @BindView(R.id.switchInfected)
    Switch switchInfected;
    @BindView(R.id.switchPup)
    Switch switchPup;
    @BindView(R.id.switchSmsShield)
    Switch switchSmsShield;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
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
        ((MainActivity) getActivity()).setHeader(true, "", false);
    }

    @Override
    public void setEvents() {

    }
    private void CheckBol(){
        if (retrieveInt("wifi")){
            switchUpdateWifi.setChecked(true);
        }else {
            switchUpdateWifi.setChecked(false);
        }

        if (retrieveInt("switchAppInstall")){
            switchAppInstall.setChecked(true);
        }else {
            switchAppInstall.setChecked(false);
        }
        if (retrieveInt("switchFileShield")){
            switchFileShield.setChecked(true);
        }else {
            switchFileShield.setChecked(false);
        }
        if (retrieveInt("switchWebShield")){
            switchWebShield.setChecked(true);
        }else {
            switchWebShield.setChecked(false);
        }
        if (retrieveInt("switchInternal")){
            switchInternal.setChecked(true);
        }else {
            switchInternal.setChecked(false);
        }
        if (retrieveInt("switchInfected")){
            switchInfected.setChecked(true);
        }else {
            switchInfected.setChecked(false);
        }
        if (retrieveInt("switchPup")){
            switchPup.setChecked(true);
        }else {
            switchPup.setChecked(false);
        }
        if (retrieveInt("switchSmsShield")){
            switchSmsShield.setChecked(true);
        }else {
            switchSmsShield.setChecked(false);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBol();
        switchUpdateWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("wifi",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "App will be updated only on wifi", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not be updated only on wifi", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchAppInstall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchAppInstall",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "App will check the installation method", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not check the installation method", Toast.LENGTH_SHORT).show();

                }
            }
        });

        switchFileShield.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchFileShield",isChecked);

                if (isChecked){
                    Toast.makeText(getActivity(), "App will protect your files", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not protect your files", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchWebShield.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchWebShield",isChecked);
                if (isChecked){
                    Toast.makeText(getActivity(), "App will protect your web browsing", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not protect your web browsing", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchInternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchInternal",isChecked);

                if (isChecked){
                    Toast.makeText(getActivity(), "App will update you about threats in internal storage", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will update you about threats in internal storage", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchInfected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchInfected",isChecked);

                if (isChecked){
                    Toast.makeText(getActivity(), "App will update you about threats of infected files", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not update you about threats of infected files", Toast.LENGTH_SHORT).show();

                }
            }
        });
        switchPup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchPup",isChecked);

                if (isChecked){
                    Toast.makeText(getActivity(), "Pup detection is on", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Pup detection is off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchSmsShield.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedBol("switchSmsShield",isChecked);

                if (isChecked){
                    Toast.makeText(getActivity(), "App will keep track on your sms traffic", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "App will not keep track on your sms traffic", Toast.LENGTH_SHORT).show();

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

    @OnClick({R.id.rl_notification, R.id.rl_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_notification:
                getFragmentActivity().ReplaceFragmentWithBackstack(new NotificationsFragment());
                break;
            case R.id.rl_update:
                getFragmentActivity().ReplaceFragmentWithBackstack(new UpdateCompleteFragment());
                break;
        }
    }
}
