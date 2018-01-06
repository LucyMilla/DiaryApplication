package com.apress.gerber.diaryapplication;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Lucy on 28/12/2017.
 */

/**Serializable so when application is left, when returned data objects are still saved*/

public class Diary implements Serializable{

    private long mDateTime;
    private String mTitle;
    private String mContent;


    public Diary(long dateTime, String title, String content) {
        mDateTime = dateTime;
        mTitle = title;
        mContent = content;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public void setDateTime(long dateTime) {
        mDateTime = dateTime;
    }

    public void setTitle(String title) {
        mTitle = title;
    }



    public long getDateTime(){
        return mDateTime;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getContent(){
        return mContent;
    }


    public String getDateTimeFormatted(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(mDateTime));
    }
}
