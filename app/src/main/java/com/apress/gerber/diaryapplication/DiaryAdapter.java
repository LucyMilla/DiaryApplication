package com.apress.gerber.diaryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lucy on 31/12/2017.
 */

public class DiaryAdapter extends ArrayAdapter <Diary> {
    public DiaryAdapter(Context context,int resource,ArrayList<Diary> diaries) {
        super(context, resource, diaries
        );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.take_diary, null);
        }
        Diary diary = getItem(position);
        if (diary != null){
            TextView title = (TextView) convertView.findViewById(R.id.list_diary_title);
            TextView date = (TextView) convertView.findViewById(R.id.list_diary_date);
            TextView content = (TextView) convertView.findViewById(R.id.list_diary_content);

            title.setText(diary.getTitle());
            date.setText(diary.getDateTimeFormatted(getContext()));
            //Instead of outputting all text with: content.setText(diary.getContent());, using a if to only output up to 50
            if(diary.getContent().length()>50){
                content.setText(diary.getContent().substring(0,50));
            }else{
                content.setText(diary.getContent());
            }



        }
        return convertView;
    }
}
