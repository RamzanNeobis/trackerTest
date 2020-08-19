package ru.startandroid.develop.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    //private ViewPager viewPager;
    //private PagerAdapter adapter;
    final Fragment fragment1 = new FragmentMap();
    final Fragment fragment2 = new FragmentInfo();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.item_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment1)
                .commit();

        getSupportFragmentManager().
                beginTransaction().
                add(R.id.main_container, fragment2).
                commit();






    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.my_location:
                            //fragment = new FragmentMap();
                            fm.beginTransaction().hide(active).show(fragment1).commit();
                            active = fragment1;
                            break;

                        case R.id.my_info:
                            //fragment = new FragmentInfo();
                            fm.beginTransaction().hide(active).show(fragment2).commit();
                            active = fragment2;
                            break;
                    }

                    return true;

                }

            };

}
