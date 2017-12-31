package com.apress.gerber.diaryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListViewDiarytakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewDiarytakes = (ListView) findViewById(R.id.takes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_main_create_diary:
                //start diary activity
                startActivity(new Intent(this, DiaryActivity.class));
                break;
        }
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mListViewDiarytakes.setAdapter(null);

        ArrayList<Diary> diaries = Utilities.getAllSavedDiary(this);

        if(diaries == null || diaries.size() == 0){
            Toast.makeText(getApplicationContext(), "You have No saved diary entries", Toast.LENGTH_SHORT).show();
            return;
        }else{
            DiaryAdapter na = new DiaryAdapter(this,R.layout.take_diary,diaries);
            mListViewDiarytakes.setAdapter(na);

            mListViewDiarytakes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    String fileName = ((Diary)mListViewDiarytakes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewDiaryIntent = new Intent(getApplicationContext(),DiaryActivity.class);
                    viewDiaryIntent.putExtra("DIARY_FILE", fileName);
                    startActivity(viewDiaryIntent);
                }
            });
        }
    }
}
