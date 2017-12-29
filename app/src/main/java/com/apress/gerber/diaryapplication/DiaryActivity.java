package com.apress.gerber.diaryapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class DiaryActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        mEtTitle = (EditText) findViewById(R.id.diarytitle);
        mEtContext = (EditText) findViewById(R.id.diarycontent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_diary_save:
                saveDiary();

                break;
        }
        return true;
    }
    private void saveDiary(){
        Diary diary = new Diary(System.currentTimeMillis(), mEtTitle.getText().toString()
                , mEtContext.getText().toString());

        if(Utilities.saveDiary(this, diary)) {
            Toast.makeText (getApplicationContext(), "Success!! Diary Entry Saved", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(), "Not Saved, do you have enough space?", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
