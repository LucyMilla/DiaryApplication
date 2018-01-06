package com.apress.gerber.diaryapplication;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Lucy on 29/12/2017.
 */

public class Utilities {
    public static final String FILE_EXTENSION = ".bin";

    public static boolean saveDiary(Context context, Diary diary){
        String fileName = String.valueOf(diary.getDateTime()) + FILE_EXTENSION;

        FileOutputStream fos;
        ObjectOutputStream oos;

        try{
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(diary);
            oos.close();
            fos.close();

        }catch (IOException e){
            e.printStackTrace();
            return false; //something went wrong

        }
        return true;
    }
    public static ArrayList<Diary> getAllSavedDiary(Context context){
        ArrayList<Diary> diaries = new ArrayList<>();

        File filesdir = context.getFilesDir();
        ArrayList<String> diaryfiles = new ArrayList<>();

        for (String file : filesdir.list()){
            if(file.endsWith(FILE_EXTENSION)){
                diaryfiles.add(file);
            }
        }

        FileInputStream fileis;
        ObjectInputStream objectis;

        for (int i = 0; i < diaryfiles.size(); i++){
            try{
                fileis = context.openFileInput(diaryfiles.get(i));
                objectis = new ObjectInputStream(fileis);

                diaries.add((Diary)objectis.readObject());

                fileis.close();
                objectis.close();

            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return diaries;
    }
    //Return the notes by name, if the file not found will fail
    public static Diary getDiarybyName(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        Diary diary;
        if(file.exists()){
            FileInputStream fileis;
            ObjectInputStream objectis;

            try {
                fileis = context.openFileInput(fileName);
                objectis = new ObjectInputStream(fileis);

                diary = (Diary) objectis.readObject();

                fileis.close();
                objectis.close();

            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
            return diary;
        }
        return null;
    }

    public static void deleteDiary(Context context, String fileName) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);

        if(file.exists()){
            file.delete();
        }
    }
}

