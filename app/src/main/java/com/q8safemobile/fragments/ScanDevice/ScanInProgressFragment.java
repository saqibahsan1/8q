package com.q8safemobile.fragments.ScanDevice;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;
import com.q8safemobile.fragments.ProtectYourApps.ProtectYourAppsCompletedFragment;
import com.q8safemobile.scan.DetectionsDisplay;
import com.q8safemobile.scan.ScanFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cjh.WaveProgressBarlibrary.WaveProgressBar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ScanInProgressFragment extends BaseFragment {

    @BindView(R.id.waveProgressbar)
    WaveProgressBar waveProgressbar;
    @BindView(R.id.scanningFileTextView)
    TextView scanningFile;
    Timer timer;
    TimerTask timerTask;
    private Handler mTimerHandler = new Handler();
    private volatile boolean running;
    private int totalFilesScanned  = 0;
    private int progress  = 0;
    private int totalFilesInfected = 0;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    private ArrayList<DetectionsDisplay> detections;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan_in_progress;
    }

    @Override
    public void inits() {
    }

    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(false, "", false);
    }

    @Override
    public void setEvents() {

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
        }
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            startTimer();
        detections = new ArrayList<>();
        new Scan().execute();
    }

    private void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        try {
                            progress++;
                            waveProgressbar.setProgress(progress);
                        }catch (Exception e){

                        }

                        //TODO
                    }

                });

                    if (progress == 100){
                        progress =0;
                        stopTimer();
                        try {
                        if (retrieveInt("totalFilesInfected") == 0){
                            getFragmentActivity().ReplaceFragmentWithBackstack(new ScanCompletedFragment());

                        }else {
                            getFragmentActivity().ReplaceFragmentWithBackstack(new ScanErrorFragment());
                        }
                    }catch (Exception e){}
                }
                    }


        };

        timer.schedule(timerTask, 1, 120);
    }
    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        running = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class Scan extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            scan(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
//            enableStartButton();
//            totalFilesScanned  = 0;
//            totalFilesInfected = 0;
        }

        /**
         * scan the directories
         * @param parentDir The directory to be scanned
         */
        private void scan(File parentDir)
        {
            File[] files = parentDir.listFiles();
            try
            {
                for (File file : files)
                {
                    if (file.isDirectory())
                    {
                        if(running) scan(file);
                    } else
                    {
                        updateScanningFile(file.getName());
                        ScanFile scanFile = new ScanFile(file.getAbsolutePath());
                        if(scanFile.isThreat())
                        {
                            incremetInfectedFiles();
                            updateInfectedFilesCount();
                            updateThreat(file.getName(), scanFile.getThreatName(), scanFile.getFileLocation());
                        }
                        incrementScannedFilesCount();
                        updateScannedFilesCount();
                    }
                }
            }catch (NullPointerException e){
                Log.i("NullPointerException", "@new Scan().scan()");
            }
        }
    }
    private synchronized void updateThreat(final String programName, final String threatName, final String location)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                detections.add(new DetectionsDisplay(programName, threatName, location));
                Gson gson = new Gson();
                String json = gson.toJson(detections);
                Shared("issue",json);

            }
        });
    }

    private synchronized void incrementScannedFilesCount()
    {
        totalFilesScanned++;
    }

    // used to increment the infected files
    private synchronized void incremetInfectedFiles()
    {
        totalFilesInfected++;
    }

    // Updates scanning file to TextView
    private synchronized void updateScanningFile(final String file)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    scanningFile.setText(file);
                    scanningFile.invalidate();
                }catch (Exception e){

                }
            }
        });
    }

    // Updates scanned files to TextView
    private synchronized void updateScannedFilesCount()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // filesScanned.setText(totalFilesScanned+"");
                SharedInt("totalFilesScanned",totalFilesScanned);
            }
        });
    }
    private synchronized void updateInfectedFilesCount()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
             //   filesInfected.setText(totalFilesInfected+"");
                SharedInt("totalFilesInfected",totalFilesInfected);
            }
        });
    }

}
