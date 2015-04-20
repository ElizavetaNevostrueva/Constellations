package com.nevostrueva.constellations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by iiopok on 19.04.2015.
 */

public class DBHelper extends SQLiteOpenHelper {

    private InputStream dbStreamStars;
    private InputStream dbStreamCon;
    private InputStream dbStreamStarsIn;

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
        try{
            dbStreamStars = context.getResources().openRawResource(R.raw.jsonstars);
            dbStreamCon = context.getResources().openRawResource(R.raw.jsonconstellations);
            dbStreamStarsIn = context.getResources().openRawResource(R.raw.jsonstarsincon);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db logs","--- onCreate database ---");
        // создаем таблицу с полями

        db.execSQL("create table constellations ("
                + "id_con integer primary key,"
                + "name_con text" + ");");

        db.execSQL("create table stars ("
                + "id_star integer primary key,"
                + "name_star text,"
                + "r_ascension_hour integer,"
                + "r_ascension_min real,"
                + "declension_hour integer,"
                + "declension_min real" + ");");

        db.execSQL("create table con_stars_in ("
                + "id_star integer,"
                + "id_con integer" + ");");

        ContentValues cv = new ContentValues();
        try{
            byte [] buffer = new byte[dbStreamStars.available()];
            while (dbStreamStars.read(buffer) != -1);
            String jsonText = new String(buffer);
            JSONObject dataJsonObj = new JSONObject(jsonText);
            JSONArray stars = dataJsonObj.getJSONArray("stars");
            for (int i=0;i<stars.length();i++)
            {
                JSONObject star = stars.getJSONObject(i);
                cv.put("id_star",star.getString("id_star"));
                cv.put("name_star",star.getString("name"));
                cv.put("r_ascension_hour",star.getString("r_ascension_hour"));
                cv.put("r_ascension_min",star.getString("r_ascension_min"));
                cv.put("declension_hour",star.getString("declension_hour"));
                cv.put("declension_min",star.getString("declension_min"));
                db.insert("stars",null,cv);
                cv.clear();
            }
            cv.clear();

            buffer = new byte[dbStreamCon.available()];
            while (dbStreamCon.read(buffer) != -1);
            jsonText = new String(buffer);
            dataJsonObj = new JSONObject(jsonText);
            JSONArray constellations = dataJsonObj.getJSONArray("constellations");
            for (int i=0;i<constellations.length();i++)
            {
                JSONObject constellation = constellations.getJSONObject(i);
                cv.put("id_con",constellation.getString("id_con"));
                cv.put("name_con",constellation.getString("name"));
                db.insert("constellations",null,cv);
                cv.clear();
            }
            cv.clear();

            buffer = new byte[dbStreamStarsIn.available()];
            while (dbStreamStarsIn.read(buffer) != -1);
            jsonText = new String(buffer);
            dataJsonObj = new JSONObject(jsonText);
            JSONArray conStarsIn = dataJsonObj.getJSONArray("con_stars_in");
            for (int i=0;i<conStarsIn.length();i++)
            {
                JSONObject conStarIn = conStarsIn.getJSONObject(i);
                cv.put("id_star",conStarIn.getString("id_star"));
                cv.put("id_con",conStarIn.getString("id_con"));
                db.insert("con_stars_in",null,cv);
                cv.clear();
            }
        }
        catch (Exception je)
        {

        }
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

