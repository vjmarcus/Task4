package com.example.task4;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MyApp";
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLatLng;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        enableMyLocation();
        getLastKnownLocation();
    }

    private void getLastKnownLocation() {
        if (checkPermission()) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(TAG, "onSuccess: location");
                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "onMapReady: current LatLng" + location.toString());
                    addTestMarkers();
                    cameraGoToLocation();
                }
            });
        }
    }

    private void addTestMarkers() {
        for (int i = 0; i < 5; i++) {
            double lat;
            double lng;
            int random = new Random().nextInt(10);
            if (random % 2 == 0) {
                lat = currentLatLng.latitude + (new Random().nextInt(999999) * 0.0000001);
                lng = currentLatLng.longitude - (new Random().nextInt(999999) * 0.0000001);
            } else if (random % 3 == 0) {
                lat = currentLatLng.latitude - (new Random().nextInt(999999) * 0.0000001);
                lng = currentLatLng.longitude + (new Random().nextInt(999999) * 0.0000001);
            } else if (random % 5 == 0) {
                lat = currentLatLng.latitude - (new Random().nextInt(999999) * 0.0000001);
                lng = currentLatLng.longitude - (new Random().nextInt(999999) * 0.0000001);
            } else {
                lat = currentLatLng.latitude + (new Random().nextInt(999999) * 0.0000001);
                lng = currentLatLng.longitude + (new Random().nextInt(999999) * 0.0000001);
            }
            map.addMarker(new MarkerOptions().position(new LatLng(formatDouble(lat), formatDouble(lng)))
                    .title("Marker" + i));
            Log.d(TAG, "addTestMarkers: lat = " + formatDouble(lat) +  ", " + formatDouble(lng));
        }
    }

    private double formatDouble(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String result = decimalFormat.format(num);
        return Double.parseDouble(result);
    }

    private void enableMyLocation() {
        //if granted
        if (checkPermission()) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    private void cameraGoToLocation() {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10.0f));
    }

    private Boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "enableMyLocation: permission granted");
            return true;
        } else {
            Log.d(TAG, "enableMyLocation: request permission");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
    }
}