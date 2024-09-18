package com.myaconsultancy.geodatadetails;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.myaconsultancy.geodatadetails.entity.GeoDataDetail;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GetLastLocation{

    private Context context;
    private GeoDataCaptured geoDataCaptured;

    public GetLastLocation(GeoDataCaptured geoDataCaptured) {
        this.geoDataCaptured = geoDataCaptured;
    }

    public GeoDataDetail getGeoDataDetails(Context context){
        this.context = context;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        final GeoDataDetail[] geoDataDetail = new GeoDataDetail[1];

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Aquí puedes usar Geocoder para obtener más detalles
                geoDataDetail[0] = getLocationDetails(latitude, longitude);
                geoDataCaptured.onGeoDataCaptured(geoDataDetail[0]);

            }
        });

        return geoDataDetail[0];
    }

    public GeoDataDetail getLocationDetails(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> direcciones = geocoder.getFromLocation(latitude, longitude, 1);
            if (direcciones != null && !direcciones.isEmpty()) {
//                OBTENEMOS LA DIRECCIÓN COMPLETA DE ACUERDO A LA UBICACIÓN DEL MOVIL
                GeoDataDetail data = (GeoDataDetail) direcciones.get(0);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
