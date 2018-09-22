package com.q8safemobile.fragments.ScanDevice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ScanFragment extends BaseFragment {

    @BindView(R.id.btn_scan)
    ImageView btnScan;

    private final int READ_FILE_REQUEST_CODE = 777;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan;
    }

    @Override
    public void inits() {
    }

    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(true, "", false);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        askPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_FILE_REQUEST_CODE);
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

    @OnClick(R.id.btn_scan)
    public void onClick() {
        getFragmentActivity().ReplaceFragmentWithBackstack(new ScanInProgressFragment());
    }
    private void askPermission(String permission, int requestCode)
    {
        if(ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

}
