package com.q8safemobile.fragments.CleanJunk;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;
import com.q8safemobile.callback.IScanCallback;
import com.q8safemobile.fragments.ScanDevice.ScanCompletedFragment;
import com.q8safemobile.fragments.ScanDevice.ScanErrorFragment;
import com.q8safemobile.fragments.ScanDevice.ScanFragment;
import com.q8safemobile.model.JunkGroup;
import com.q8safemobile.model.JunkInfo;
import com.q8safemobile.task.OverallScanTask;
import com.q8safemobile.task.ProcessScanTask;
import com.q8safemobile.task.SysCacheScanTask;
import com.q8safemobile.task.CleanUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import at.grabner.circleprogress.CircleProgressView;
import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class CleanJunkFragment extends BaseFragment {

    @BindView(R.id.scanningFileTextView)
    TextView scanningFile;

    public static final int MSG_SYS_CACHE_BEGIN = 0x1001;
    public static final int MSG_SYS_CACHE_POS = 0x1002;
    public static final int MSG_SYS_CACHE_FINISH = 0x1003;

    public static final int MSG_PROCESS_BEGIN = 0x1011;
    public static final int MSG_PROCESS_POS = 0x1012;
    public static final int MSG_PROCESS_FINISH = 0x1013;

    public static final int MSG_OVERALL_BEGIN = 0x1021;
    public static final int MSG_OVERALL_POS = 0x1022;
    public static final int MSG_OVERALL_FINISH = 0x1023;
    private int progress  = 0;
    public static final int MSG_SYS_CACHE_CLEAN_FINISH = 0x1100;
    public static final int MSG_PROCESS_CLEAN_FINISH = 0x1101;
    public static final int MSG_OVERALL_CLEAN_FINISH = 0x1102;

    public static final String HANG_FLAG = "hanged";

    private Handler handler;

    private boolean mIsSysCacheScanFinish = false;
    private boolean mIsSysCacheCleanFinish = false;

    private boolean mIsProcessScanFinish = false;
    private boolean mIsProcessCleanFinish = false;

    private boolean mIsOverallScanFinish = false;
    private boolean mIsOverallCleanFinish = false;
    private Handler mTimerHandler = new Handler();
    private boolean mIsScanning = false;
    @BindView(R.id.circleView)
    CircleProgressView circleView;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    Timer timer;
    TimerTask timerTask;

    private HashMap<Integer, JunkGroup> mJunkGroups = null;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_clean_junk;
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

    }    private void stopTimer(){
        if(timer != null){
            timer.cancel();
            timer.purge();
            progress =0;
        }
    }

    private void SharedInt(String key, String value){
        editor = getActivity().getSharedPreferences("abc", MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
    private String retrieveInt(String key){
        String count ="";
        prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        if (restoredText != null) {
            count   = prefs.getString(key, null);//"No name defined" is the default value.
        }
        return count;
    }



    private void startTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run(){
                        try {
                            progress++;

                            circleView.setValueAnimated(progress);
                        }catch (Exception e){

                        }

                        //TODO
                    }

                });
                if (progress == 100){

                    stopTimer();
                    try {
                        if (retrieveInt("totalMb") == null){
                            Toast.makeText(getActivity(), "No junk was found!", Toast.LENGTH_SHORT).show();

                        }else {
                            dialog();
                        }
                    }catch (Exception e){}
                }
            }


        };

        timer.schedule(timerTask, 1, 100);
    }
    private void dialog(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    circleView.setValueAnimated(100);
                    new AlertDialog.Builder(getActivity(), R.style.alert_dialog_theme)
                            .setTitle("Clean Alert!")
                            .setMessage(retrieveInt("totalMb")+" Of junk found! Do you want to clean junk?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getActivity(), "Please wait while cleaning junk...", Toast.LENGTH_SHORT).show();
                                    clearAll();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    getFragmentActivity().ReplaceFragmentWithBackstack(new ScanFragment());

                                }
                            })
                            .show();

                }catch (Exception e){

                }

            }
        });

    }


    private void clean(){

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case MSG_SYS_CACHE_BEGIN:
                        break;

                    case MSG_SYS_CACHE_POS:
                        scanningFile.setText("" + ((JunkInfo) msg.obj).mPackageName);
                      //  mHeaderView.mSize.setText(CleanUtil.formatShortFileSize(getActivity(), getTotalSize()));
                        break;

                    case MSG_SYS_CACHE_FINISH:
                        mIsSysCacheScanFinish = true;
                        checkScanFinish();
                        break;

                    case MSG_SYS_CACHE_CLEAN_FINISH:
                        mIsSysCacheCleanFinish = true;
                        checkCleanFinish();
                        Bundle bundle = msg.getData();
                        if (bundle != null) {
                            boolean hanged = bundle.getBoolean(HANG_FLAG, false);
                            if (hanged) {
                                Toast.makeText(getActivity(), "Cache cleaning successfully！", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                        break;

                    case MSG_PROCESS_BEGIN:
                        break;

                    case MSG_PROCESS_POS:
                        try {
                            scanningFile.setText("Scanning:" + ((JunkInfo) msg.obj).mPackageName);
                        }catch (Exception e){

                        }

                        break;

                    case MSG_PROCESS_FINISH:
                        mIsProcessScanFinish = true;
                        checkScanFinish();
                        break;

                    case MSG_PROCESS_CLEAN_FINISH:
                        mIsProcessCleanFinish = true;
                        checkCleanFinish();
                        break;

                    case MSG_OVERALL_BEGIN:
                        break;

                    case MSG_OVERALL_POS:
                        scanningFile.setText("Scanning:" + ((JunkInfo) msg.obj).mPath);
                        break;

                    case MSG_OVERALL_FINISH:
                        mIsOverallScanFinish = true;
                        mIsSysCacheScanFinish = true;
                        checkScanFinish();
                        break;

                    case MSG_OVERALL_CLEAN_FINISH:
                        mIsOverallCleanFinish = true;

                        checkCleanFinish();
                        break;
                }
            }
        };
        resetState();
        if (!mIsScanning) {
            mIsScanning = true;
            startScan();
        }

    }
    private void clearAll() {
        Thread clearThread = new Thread(new Runnable() {
            @Override
            public void run() {
                JunkGroup processGroup = mJunkGroups.get(JunkGroup.GROUP_PROCESS);
                for (JunkInfo info : processGroup.mChildren) {
                    CleanUtil.killAppProcesses(info.mPackageName);
                }
                Message msg = handler.obtainMessage(CleanJunkFragment.MSG_PROCESS_CLEAN_FINISH);
                msg.sendToTarget();

                CleanUtil.freeAllAppsCache(handler);

                ArrayList<JunkInfo> junks = new ArrayList<>();
                JunkGroup group = mJunkGroups.get(JunkGroup.GROUP_APK);
                junks.addAll(group.mChildren);

                group = mJunkGroups.get(JunkGroup.GROUP_LOG);
                junks.addAll(group.mChildren);

                group = mJunkGroups.get(JunkGroup.GROUP_TMP);
                junks.addAll(group.mChildren);

                CleanUtil.freeJunkInfos(junks, handler);
                getFragmentActivity().ReplaceFragmentWithBackstack(new CleanJunkCompletedFragment());
            }
        });
        clearThread.start();
    }

    private void resetState() {
        mIsScanning = false;

        mIsSysCacheScanFinish = false;
        mIsSysCacheCleanFinish = false;

        mIsProcessScanFinish = false;
        mIsProcessCleanFinish = false;

        mJunkGroups = new HashMap<>();


        JunkGroup cacheGroup = new JunkGroup();
        cacheGroup.mName = getString(R.string.cache_clean);
        cacheGroup.mChildren = new ArrayList<>();
        mJunkGroups.put(JunkGroup.GROUP_CACHE, cacheGroup);

        JunkGroup processGroup = new JunkGroup();
        processGroup.mName = getString(R.string.process_clean);
        processGroup.mChildren = new ArrayList<>();
        mJunkGroups.put(JunkGroup.GROUP_PROCESS, processGroup);

        JunkGroup apkGroup = new JunkGroup();
        apkGroup.mName = getString(R.string.apk_clean);
        apkGroup.mChildren = new ArrayList<>();
        mJunkGroups.put(JunkGroup.GROUP_APK, apkGroup);

        JunkGroup tmpGroup = new JunkGroup();
        tmpGroup.mName = getString(R.string.tmp_clean);
        tmpGroup.mChildren = new ArrayList<>();
        mJunkGroups.put(JunkGroup.GROUP_TMP, tmpGroup);

        JunkGroup logGroup = new JunkGroup();
        logGroup.mName = getString(R.string.log_clean);
        logGroup.mChildren = new ArrayList<>();
        mJunkGroups.put(JunkGroup.GROUP_LOG, logGroup);
    }

    private void checkScanFinish() {


        if (mIsProcessScanFinish && mIsSysCacheScanFinish && mIsOverallScanFinish) {
            mIsScanning = false;

            JunkGroup cacheGroup = mJunkGroups.get(JunkGroup.GROUP_CACHE);
            ArrayList<JunkInfo> children = cacheGroup.mChildren;
            cacheGroup.mChildren = new ArrayList<>();
            for (JunkInfo info : children) {
                cacheGroup.mChildren.add(info);
                if (info.mChildren != null) {
                    cacheGroup.mChildren.addAll(info.mChildren);
                }
            }

            long size = getTotalSize();
            String totalSize = CleanUtil.formatShortFileSize(getActivity(), size);
          //  mHeaderView.mSize.setText(totalSize);
            try {
                SharedInt("totalMb",totalSize);
            }catch (Exception e){}




        }
    }

    private void checkCleanFinish() {
        if (mIsProcessCleanFinish && mIsSysCacheCleanFinish && mIsOverallCleanFinish) {
            try {
                scanningFile.setText("Cleanup complete");
            }catch (Exception e){}



            for (JunkGroup group : mJunkGroups.values()) {
                group.mSize = 0L;
                group.mChildren = null;
            }

        }
    }

    private void startScan() {

        ProcessScanTask processScanTask = new ProcessScanTask(new IScanCallback() {
            @Override
            public void onBegin() {
                Message msg = handler.obtainMessage(MSG_PROCESS_BEGIN);
                msg.sendToTarget();
            }

            @Override
            public void onProgress(JunkInfo info) {
                Message msg = handler.obtainMessage(MSG_PROCESS_POS);
                msg.obj = info;
                msg.sendToTarget();
            }

            @Override
            public void onFinish(ArrayList<JunkInfo> children) {
                JunkGroup cacheGroup = mJunkGroups.get(JunkGroup.GROUP_PROCESS);
                cacheGroup.mChildren.addAll(children);
                for (JunkInfo info : children) {
                    cacheGroup.mSize += info.mSize;
                }
                Message msg = handler.obtainMessage(MSG_PROCESS_FINISH);
                msg.sendToTarget();
            }
        });
        processScanTask.execute();

        SysCacheScanTask sysCacheScanTask = new SysCacheScanTask(new IScanCallback() {
            @Override
            public void onBegin() {
                Message msg = handler.obtainMessage(MSG_SYS_CACHE_BEGIN);
                msg.sendToTarget();
            }

            @Override
            public void onProgress(JunkInfo info) {
                Message msg = handler.obtainMessage(MSG_SYS_CACHE_POS);
                msg.obj = info;
                msg.sendToTarget();
            }

            @Override
            public void onFinish(ArrayList<JunkInfo> children) {
                JunkGroup cacheGroup = mJunkGroups.get(JunkGroup.GROUP_CACHE);
                cacheGroup.mChildren.addAll(children);
                Collections.sort(cacheGroup.mChildren);
                Collections.reverse(cacheGroup.mChildren);
                for (JunkInfo info : children) {
                    cacheGroup.mSize += info.mSize;
                }
                Message msg = handler.obtainMessage(MSG_SYS_CACHE_FINISH);
                msg.sendToTarget();
            }
        });
        sysCacheScanTask.execute();

        OverallScanTask overallScanTask = new OverallScanTask(new IScanCallback() {
            @Override
            public void onBegin() {
                Message msg = handler.obtainMessage(MSG_OVERALL_BEGIN);
                msg.sendToTarget();
            }

            @Override
            public void onProgress(JunkInfo info) {
                Message msg = handler.obtainMessage(MSG_OVERALL_POS);
                msg.obj = info;
                msg.sendToTarget();
            }

            @Override
            public void onFinish(ArrayList<JunkInfo> children) {
                for (JunkInfo info : children) {
                    String path = info.mChildren.get(0).mPath;
                    int groupFlag = 0;
                    if (path.endsWith(".apk")) {
                        groupFlag = JunkGroup.GROUP_APK;
                    } else if (path.endsWith(".log")) {
                        groupFlag = JunkGroup.GROUP_LOG;
                    } else if (path.endsWith(".tmp") || path.endsWith(".temp")) {
                        groupFlag = JunkGroup.GROUP_TMP;
                    }

                    JunkGroup cacheGroup = mJunkGroups.get(groupFlag);
                    cacheGroup.mChildren.addAll(info.mChildren);
                    cacheGroup.mSize = info.mSize;
                }

                Message msg = handler.obtainMessage(MSG_OVERALL_FINISH);
                msg.sendToTarget();
            }
        });
        overallScanTask.execute();
    }

    private long getTotalSize() {
        long size = 0L;
        for (JunkGroup group : mJunkGroups.values()) {
            size += group.mSize;
        }
        return size;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        circleView.setMaxValue(100);
        circleView.setValue(0);
        startTimer();
        clean();

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
