package com.nevostrueva.constellations;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by iiopok on 21.04.2015.
 */
public class FindVisibleConstellations {
    public static Constellation[] findVisibleConstellations(Context context, ArrayList<Integer> visibleStars, DBHelper dbHelper){
        Constellation[] parametrsVisCon;
        Constellation visCon = new Constellation();
        ArrayList<Integer> visibleConstallations = new ArrayList<>();
        ArrayList<Integer> idsConstallations = new ArrayList<>();
        ArrayList<Integer> constallationsStars = new ArrayList<>();
        Iterator<Integer> iter = visibleStars.iterator();

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false

        Cursor c;
        Boolean isConstellationVisible=true;
        while(iter.hasNext()) {
            Integer next = iter.next();
            c = db.query("con_stars_in", new String[]{"id_con"}, "id_star = ?", new String[]{next.toString()}, null, null, null);

            if (c.moveToFirst()) {

                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("id_con");

                do {
                    idsConstallations.add(Integer.valueOf(c.getString(idColIndex)));
                } while (c.moveToNext());

            } else
                Log.d("read_log", "0 rows");
            c.close();

            Iterator<Integer> iterConstellations = idsConstallations.iterator();
            while (iterConstellations.hasNext()) {
                Integer nextCon = iterConstellations.next();
                c = db.query("con_stars_in", new String[]{"id_star"}, "id_con = ?", new String[]{nextCon.toString()}, null, null, null);
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id_star");

                    do {
                        constallationsStars.add(Integer.valueOf(c.getString(idColIndex)));
                    } while (c.moveToNext());

                } else
                    Log.d("read_log", "0 rows");
                c.close();

                Iterator<Integer> iterStars = constallationsStars.iterator();
                while (iterStars.hasNext()) {
                    Integer nextStar = iterStars.next();
                    isConstellationVisible = isConstellationVisible && visibleStars.contains(nextStar);
                }
                if (isConstellationVisible) {
                    if (!visibleConstallations.contains(nextCon)){
                        visibleConstallations.add(nextCon);}
                } else {
                    isConstellationVisible = true;
                }
            }
            idsConstallations.clear();
            constallationsStars.clear();

        }
        Iterator<Integer> iterVisCon = visibleConstallations.iterator();
        parametrsVisCon = new Constellation[visibleConstallations.size()];
        while (iterVisCon.hasNext()) {
            Integer nextCon = iterVisCon.next();
            c = db.query("constellations", null, "id_con = ?", new String[]{nextCon.toString()}, null, null, null);
            if (c.moveToFirst()) {

                int nameColIndex = c.getColumnIndex("name_con");
                int imageColIndex = c.getColumnIndex("image_con");

                parametrsVisCon = new Constellation[c.getCount()];
                int i= 0;
                do {
                    visCon.conImage =  Integer.valueOf(c.getString(imageColIndex));
                    visCon.conName = c.getString(nameColIndex);
                    parametrsVisCon[i] = visCon;
                    i++;
                } while (c.moveToNext());

            } else
                Log.d("read_log", "0 rows");
            c.close();
        }
            db.close();
            Log.d("visible_con_log", visibleConstallations.toString());

        return parametrsVisCon;

    }
}
