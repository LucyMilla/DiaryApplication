package com.apress.gerber.diaryapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class DiaryActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private EditText mEtContent;
    private String mDiaryFileName;
    private Diary mLoadedDiary;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#663300")));

        mEtTitle = (EditText) findViewById(R.id.diarytitle);
        mEtContent = (EditText) findViewById(R.id.diarycontent);
        mDiaryFileName = getIntent().getStringExtra("DIARY_FILE");
        if (mDiaryFileName != null && !mDiaryFileName.isEmpty()) {
            mLoadedDiary = Utilities.getDiarybyName(this, mDiaryFileName);

            if (mLoadedDiary != null) {
                mEtTitle.setText(mLoadedDiary.getTitle());
                mEtContent.setText(mLoadedDiary.getContent());

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_diary_save:
                saveDiary();

            case R.id.action_diary_delete:
                deleteDiary();

                break;
        }
        return true;
    }

    //To remove complication from original creation date to last used, once view it will either be a new diary or existing
    private void saveDiary() {
        Diary diary;

        if(mEtTitle.getText().toString().trim().isEmpty() ||mEtContent.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter a Title and Content", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mLoadedDiary == null) {
            diary = new Diary(System.currentTimeMillis(), mEtTitle.getText().toString()
                    , mEtContent.getText().toString());
        } else {
            diary = new Diary(mLoadedDiary.getDateTime(), mEtTitle.getText().toString()
                    , mEtContent.getText().toString());

        }
        /*calling this, for the object wouldnt show the toast message but getApplicationContext worked. */

        if (Utilities.saveDiary(this, diary)) {
            Toast.makeText(getApplicationContext(), "Success!! Diary Entry Saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Not Saved, do you have enough space?", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteDiary() {
        if (mLoadedDiary == null) {
            finish();
        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("You are about to Delete a Diary Entry" + mEtTitle.getText().toString() + ", are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteDiary(getApplicationContext()
                                    , mLoadedDiary.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(), mEtTitle.getText().toString() + " is Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .setCancelable(false);
            dialog.show();
        }
    }
}
