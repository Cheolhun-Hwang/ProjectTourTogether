package com.hch.hooney.tourtogether.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.hch.hooney.tourtogether.R;

/**
 * Created by qewqs on 2018-01-25.
 */

public class GPS extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; //10미터 당
    private static final long MIN_TIME_UPDATES = 1000 * 60 * 1; // 1분마다

    private Context mContext;
    private boolean isGPSEnabled;       //현재 GPS 사용유무
    private boolean isNetworkEnabled;   //네트워크 사용유무
    private boolean isGetLocation;      //GPS 값

    private LocationManager manager;
    private Location location;
    private double lat;     //위도    mapY
    private double lon;     //경도    mapX

    public GPS(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if ((!isGPSEnabled) && (!isNetworkEnabled)) {
                Toast.makeText(mContext, getResources().getText(R.string.service_location_check_gps_net), Toast.LENGTH_LONG).show();
            } else {
                this.isGetLocation = true;
                if (isNetworkEnabled) {
                    manager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if(manager != null){
                        location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled){
                    if(location==null){
                        manager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                        );
                        if(manager != null){
                            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){
        if(manager!=null){
            manager.removeUpdates(GPS.this);
        }
    }

    public double getLat() {
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLon() {
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    //GPS 또는 wife 가 설정되어 있는지
    public boolean isGetLocation(){
        return isGetLocation;
    }

    public void showSettingAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle(mContext.getResources().getString(R.string.service_location_check_gps));
        alertDialog.setMessage(mContext.getResources().getString(R.string.service_location_check_gps_msg));

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
