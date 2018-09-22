package com.q8safemobile.fragments.ProtectYourApps.Model;

import android.graphics.drawable.Drawable;

/**
 * Created by Ahsan Khan on 9/20/2018.
 */

public class ProtectAppModel {

    public Drawable getImageId() {
        return imageId;
    }

    public void setImageId(Drawable imageId) {
        this.imageId = imageId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Drawable imageId;
    public String txt;
    public boolean state;

    public ProtectAppModel(Drawable imageId, String text,boolean state) {

        this.imageId = imageId;
        this.txt=text;
        this.state = state;
    }
//    public ProtectAppModel(String title, String date, String time) {
//        this.title = title;
//        this.date = date;
//        this.time = time;
//    }


   }
