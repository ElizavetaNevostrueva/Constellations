package com.nevostrueva.constellations;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CurConstellationsActivity extends ActionBarActivity {

    private GeoLocation geoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_constellations);

        Constellation con = new Constellation();
        con.conName = "Созвездия";
        con.conImage = getResources().getIdentifier("stars","drawable",this.getPackageName());
        Constellation[] firstDate = new Constellation[]{con};
        ListView listView = (ListView) this.findViewById(R.id.visible_stars);
        ConItemAdapter itemAdapter = new ConItemAdapter(this,R.layout.item_star, firstDate);
        listView.setAdapter(itemAdapter);

        geoLocation = new GeoLocation(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
        geoLocation.startListenGeoLocation();
        }
        catch (Exception e){
            Toast.makeText(this,"Включите определение местоположения!",Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        geoLocation.stopListenGeoLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cur_constellations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
