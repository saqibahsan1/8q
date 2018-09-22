package com.q8safemobile.fragments.ScanDevice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;
import com.q8safemobile.fragments.ScanDevice.Adapter.ErrorDetaileAdapter;
import com.q8safemobile.scan.DetectionsDisplay;
import com.q8safemobile.views.TextViewCustomFont;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ScanErrorDetailFragment extends BaseFragment {

    @BindView(R.id.btn_scan_again)
    Button btnScanAgain;
    @BindView(R.id.txt_error_heading)
    TextViewCustomFont txtErrorHeading;
    @BindView(R.id.subHeading)
    TextViewCustomFont subHeading;
    @BindView(R.id.foundIssue)
    TextViewCustomFont foundIssue;
    @BindView(R.id.txt_error_detail)
    TextViewCustomFont txtErrorDetail;
    @BindView(R.id.btn_resolve)
    Button btnResolve;
    @BindView(R.id.btn_ignore)
    Button btnIgnore;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    ArrayList<DetectionsDisplay> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan_error_detail;
    }

    @Override
    public void inits() {
    }
    private void Shared(String key, String value){
        editor = getActivity().getSharedPreferences("abc", MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
    private void SharedInt(String key, int value){
        editor = getActivity().getSharedPreferences("abc", MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }
    private String retrieve(String key){
        String count =null;
        prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        if (restoredText != null) {
            count   = prefs.getString(key, "No name defined");//"No name defined" is the default value.
        }else return null;
        return count;
    }
    private int retrieveInt(String key){
        int count =0;
        prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        int restoredText = prefs.getInt(key, 0);
        if (restoredText != 0) {
            count   = prefs.getInt(key, 0);//"No name defined" is the default value.
        }
        return count;
    }

    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(false, "", false);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Gson gson = new Gson();
        try {
            String response=retrieve("issue");
            ArrayList<DetectionsDisplay> lstArrayList = gson.fromJson(response,
                    new TypeToken<List<DetectionsDisplay>>(){}.getType());
            if (retrieveInt("totalFilesInfected") >1){
                foundIssue.setText("Found " + retrieveInt("totalFilesInfected")+ " Issues");
            }else {
                subHeading.setText("Scan your Device!");
                txtErrorHeading.setText("No Ignored Issues");
                foundIssue.setText("No Issues Found");
            }

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            for (int i =0;i<lstArrayList.size();i++){
                list.add(lstArrayList.get(i));
            }
            ErrorDetaileAdapter adapter = new ErrorDetaileAdapter(getActivity(),list);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){

        }


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
    private void delete(String path){
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + path);
            } else {
                System.out.println("file not Deleted :" + path);
            }
        }
    }

    @OnClick({R.id.btn_scan_again, R.id.btn_resolve, R.id.btn_ignore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_again:
                getFragmentActivity().ReplaceFragmentWithBackstack(new ScanFragment());
                break;
            case R.id.btn_resolve:
                for (int i = 0; i<list.size();i++){
                    delete(list.get(i).getFileLoaction());
                }
                Shared("issue","");
                SharedInt("totalFilesScanned",0);
                SharedInt("totalFilesInfected",0);
                recyclerView.setVisibility(View.GONE);
                getFragmentActivity().ReplaceFragmentWithBackstack(new ScanCompletedFragment());
                break;
            case R.id.btn_ignore:
                getFragmentActivity().ReplaceFragmentWithBackstack(new ScanFragment());
                break;
        }
    }
}
