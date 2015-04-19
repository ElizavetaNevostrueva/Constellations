package com.nevostrueva.constellations;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class CurConstellationsActivity extends ActionBarActivity {

    //private LocationManager locationManager;
    private GeoLocation geoLocation;
    /*private TextView gpsCoordinates;
    private TextView internetCoordinates;*/
    /*private Double gpsLatitude = 0.0;
    private Double gpsLongitude = 0.0;
    private Double internetLatitude = 0.0;
    private Double internetLongitude = 0.0;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_constellations);

        geoLocation = new GeoLocation(this);
        /*gpsCoordinates = (TextView) findViewById(R.id.gpsCoordinates);
        internetCoordinates = (TextView) findViewById(R.id.internetCoordinates);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        geoLocation.startListenGeoLocation();
        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        geoLocation.stopListenGeoLocation();
        //locationManager.removeUpdates(locationListener);
    }

   /* private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setGeoLocationParameters(location);
*//*            if (location == null)
                return;
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                gpsCoordinates.setText("GPS lat:  "+String.valueOf(location.getLatitude())+
                        "  long:  "+String.valueOf(location.getLongitude()));

                *//**//*gpsLatitude = location.getLatitude();
                gpsLongitude = location.getLongitude();*//**//*
            } else if (location.getProvider().equals(
                    LocationManager.NETWORK_PROVIDER)) {
                internetCoordinates.setText("GPS lat:  "+String.valueOf(location.getLatitude())+
                        "  long:  "+String.valueOf(location.getLongitude()));
                //internetLatitude = location.getLatitude();
                //internetLongitude = location.getLongitude();
            }*//*
            gpsCoordinates.setText("GPS lat:  "+gpsLatitude.toString()+
                    "  long:  "+gpsLongitude.toString());
            internetCoordinates.setText("Internet lat:  "+internetLatitude.toString()+
                    "  long:  "+internetLongitude.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            setGeoLocationParameters(locationManager.getLastKnownLocation(provider));

            gpsCoordinates.setText("GPS lat:  "+gpsLatitude.toString()+
                    "  long:  "+gpsLongitude.toString());
            internetCoordinates.setText("Internet lat:  "+internetLatitude.toString()+
                    "  long:  "+internetLongitude.toString());
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };*/

    /*private void setGeoLocationParameters(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            gpsLatitude = location.getLatitude();
            gpsLongitude = location.getLongitude();
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            internetLatitude = location.getLatitude();
            internetLongitude = location.getLongitude();
        }
    };*/

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
