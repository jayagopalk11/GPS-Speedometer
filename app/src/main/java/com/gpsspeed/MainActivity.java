package com.gpsspeed;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public LocationManager locationManager;
    public LocationListener locationListener;
    public TextView latLong;
    public TextView speed;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getData();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latLong = (TextView) findViewById(R.id.latLong);
        speed = (TextView) findViewById(R.id.speed);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latLong.setText("Lat:"+String.valueOf(location.getLatitude())+" Long: "+String.valueOf(location.getLongitude()));

                String result = String.valueOf(location.getSpeed()*3.6)+" KMPH";
                speed.setText(result);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent in = new Intent(Settings.System.LOCATION_PROVIDERS_ALLOWED);
                startActivity(in);
            }
        };


        //getData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i("LOGGG","build versiom");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.i("LOGGG","reqeuesting permission");
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
            else{
                getData();
            }
        } else {
            getData();
        }
    }
    public void getData(){
        Log.i("LOGGG","Get data called");
        locationManager.requestLocationUpdates("gps", 100, 1, locationListener);
    }
}


