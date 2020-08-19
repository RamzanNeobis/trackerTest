package ru.startandroid.develop.fitnesstracker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    FusedLocationProviderClient client;

    private static final int TAG_CODE_PERMISSION_LOCATION = 1;
    GoogleMap map;
    MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mapView = view.findViewById(R.id.google_mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        assert map != null;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            client = LocationServices.getFusedLocationProviderClient(getContext());

            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                                          @Override
                                          public void onSuccess(final Location location) {
                                              if(location != null){
                                                  mapView.getMapAsync(new OnMapReadyCallback() {
                                                      @Override
                                                      public void onMapReady(GoogleMap googleMap) {
                                                          LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                                                          MarkerOptions options = new MarkerOptions().position(latLng).title("I am there");

                                                          googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                                                          googleMap.addMarker(options);
                                                      }
                                                  });
                                              }
                                          }
                                      });
                    map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);


        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    TAG_CODE_PERMISSION_LOCATION);
        }

    }
}