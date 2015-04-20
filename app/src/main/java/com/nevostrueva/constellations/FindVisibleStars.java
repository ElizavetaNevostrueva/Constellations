package com.nevostrueva.constellations;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by iiopok on 20.04.2015.
 */
public class FindVisibleStars {

    public ArrayList<Integer> findVisibleStars(Double lon, Double lat, DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("stars", null, null, null, null, null, null);
        ArrayList<Integer> visibleStars = new ArrayList<>();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id_star");
            int nameColIndex = c.getColumnIndex("name_star");
            int raHourColIndex = c.getColumnIndex("r_ascension_hour");
            int raMinColIndex = c.getColumnIndex("r_ascension_min");
            int decHourColIndex = c.getColumnIndex("declension_hour");
            int decMinColIndex = c.getColumnIndex("declension_min");

            do {
                Double ra = Double.valueOf(Integer.valueOf(c.getString(raHourColIndex)) +
                        Integer.valueOf(c.getString(raMinColIndex)) / 60);
                Double dec = Double.valueOf(Integer.valueOf(c.getString(decHourColIndex)) +
                        Integer.valueOf(c.getString(decMinColIndex)) / 60);
                if (isStarVisible(lon, lat, ra, dec)) {
                    visibleStars.add(Integer.valueOf(c.getString(idColIndex)));
                }


                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("read_log",
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", RA hour = " + c.getString(raHourColIndex) +
                                ", RA min = " + c.getString(raMinColIndex) +
                                ", Dec hour = " + c.getString(decHourColIndex) +
                                ", Dec min = " + c.getString(decMinColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());

        } else
            Log.d("read_log", "0 rows");
        c.close();
        db.close();
        Log.d("visible_stars_log", visibleStars.toString());
        return  visibleStars;
    }


    private Boolean isStarVisible(Double lon, Double lat, Double ra, Double dec){
        Double sg = calculateStarsTime();
        Double st = sg+lon;
        Double th = st-ra;
        Double latInDegrees = Math.toDegrees(lat);
        Double decInDegrees = Math.toDegrees(dec);
        Double thInDegrees = Math.toDegrees(th);
        Double z =Math.toDegrees(Math.acos(Math.sin(latInDegrees)*Math.sin(decInDegrees)+
                Math.cos(latInDegrees)*Math.cos(decInDegrees)*Math.cos(thInDegrees)));
        Double h = 90-z;
        if (h>=0){
            return true;
        } else{
            return false;
        }
    }

    private Double calculateJulianDate(Calendar curCalendar){

        int year = curCalendar.get(Calendar.YEAR);
        int month = curCalendar.get(Calendar.MONTH);
        int day = curCalendar.get(Calendar.DATE);
        int hour = curCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = curCalendar.get(Calendar.MINUTE);

        if (month<3){
            month=+12;
            year=-1;
        }

        return Double.valueOf(year/400-year/100+year/4+365*year-
                679004+306001*((month+1)/(10000+day)));

    }

    private Double calculateStarsTime(){
        Calendar curCalendar = Calendar.getInstance();
        Double md = calculateJulianDate(curCalendar);
        Double t0 = (md-51544.5)/36525;
        Double s0  = 24110.54841+8640184.812*t0+0.093104*Math.pow(t0,2)-0.0000062*Math.pow(t0,3);
        Double nSec = Double.valueOf(3600*(curCalendar.get(Calendar.HOUR_OF_DAY)+
                curCalendar.get(Calendar.MINUTE)/60+
                curCalendar.get(Calendar.SECOND)/3600));
        Double nSecS = 366.2422/365.2422*nSec;
        return (s0+nSecS)/(15*3600);
    }

}
