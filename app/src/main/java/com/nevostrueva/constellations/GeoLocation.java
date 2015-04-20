package com.nevostrueva.constellations;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by User on 05.04.2015.
 */
public class GeoLocation {

    private LocationManager locationManager;
    private Double gpsLatitude = 0.0;
    private Double gpsLongitude = 0.0;
    private Double internetLatitude = 0.0;
    private Double internetLongitude = 0.0;
    private TextView gpsCoordinates;
    private TextView internetCoordinates;
    private Activity activity;
    private DBHelper dbHelper;

    public GeoLocation(Context context){
        activity = (Activity) context;
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        gpsCoordinates = (TextView) activity.findViewById(R.id.gpsCoordinates);
        internetCoordinates = (TextView) activity.findViewById(R.id.internetCoordinates);
        dbHelper=new DBHelper(context);
    }

    public Double getGpsGeoLatitude(){
        return gpsLatitude;
    }

    public Double getGpsGeoLongitude(){
        return gpsLongitude;
    }

    public Double getInternetGeoLatitude(){
        return internetLatitude;
    }

    public Double getInternetGeoLongitude(){
        return internetLongitude;
    }

    public void startListenGeoLocation(){
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
    }

    public void stopListenGeoLocation(){
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setGeoLocationParameters(location);
            FindVisibleStars fvs = new FindVisibleStars();
            //fvs.findVisibleStars(internetLongitude, internetLatitude,dbHelper);
            FindVisibleConstellations.findVisibleConstellations(
                    fvs.findVisibleStars(internetLongitude, internetLatitude,dbHelper),dbHelper);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            setGeoLocationParameters(locationManager.getLastKnownLocation(provider));
            FindVisibleStars fvs = new FindVisibleStars();
            //fvs.findVisibleStars(internetLongitude, internetLatitude,dbHelper);
            FindVisibleConstellations.findVisibleConstellations(
                    fvs.findVisibleStars(internetLongitude, internetLatitude,dbHelper),dbHelper);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
     };

    private void setGeoLocationParameters(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            gpsLatitude = location.getLatitude();
            gpsLongitude = location.getLongitude();
            gpsCoordinates.setText("GPS: "+ gpsLatitude.toString()+" "+ gpsLongitude.toString()+"\n");
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            internetLatitude = location.getLatitude();
            internetLongitude = location.getLongitude();
            internetCoordinates.setText("In: "+ internetLatitude.toString()+" "+ internetLongitude.toString());
        }
    }

}

