package com.nevostrueva.constellations;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by User on 05.04.2015.
 */
public class GeoLocation {

    private LocationManager locationManager;
    private Double gpsLatitude = 0.0;
    private Double gpsLongitude = 0.0;
    private Double internetLatitude = 0.0;
    private Double internetLongitude = 0.0;

    public void getLocationManager(Context context){
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
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
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            setGeoLocationParameters(locationManager.getLastKnownLocation(provider));
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
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            internetLatitude = location.getLatitude();
            internetLongitude = location.getLongitude();
        }
    }
}

