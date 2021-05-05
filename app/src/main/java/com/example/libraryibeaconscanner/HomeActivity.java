package com.example.libraryibeaconscanner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.libraryibeaconscanner.bluetooth.BluetoothClient;
import com.example.libraryibeaconscanner.location.AndroidLocationProvider;
import com.example.libraryibeaconscanner.ui.beaconview.chart.BeaconChartFragment;
import com.example.libraryibeaconscanner.ui.beaconview.map.BeaconMapFragment;
import com.example.libraryibeaconscanner.ui.beaconview.radar.BeaconRadarFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private CoordinatorLayout coordinatorLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // setup UI
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_radar);

        // setup location
        AndroidLocationProvider.initialize(this);

        // setup bluetooth
        BluetoothClient.initialize(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                Log.w(TAG, "BeaconFilter");
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_map: {
                selectedFragment = new BeaconMapFragment();
                break;
            }
            case R.id.navigation_radar: {
                selectedFragment = new BeaconRadarFragment();
                break;
            }
            case R.id.navigation_chart: {
                selectedFragment = new BeaconChartFragment();
                break;
            }
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // observe location
        if (!AndroidLocationProvider.hasLocationPermission(this)) {
            AndroidLocationProvider.requestLocationPermission(this);
        } else if (!AndroidLocationProvider.isLocationEnabled(this)) {
            requestLocationServices();
        }
        AndroidLocationProvider.startRequestingLocationUpdates();
        AndroidLocationProvider.requestLastKnownLocation();

        // observe bluetooth
        if (!BluetoothClient.isBluetoothEnabled()) {
            requestBluetooth();
        }
        BluetoothClient.startScanning();
    }

    @Override
    protected void onPause() {
        // stop observing location
        AndroidLocationProvider.stopRequestingLocationUpdates();

        // stop observing bluetooth
        BluetoothClient.stopScanning();

        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AndroidLocationProvider.REQUEST_CODE_LOCATION_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Location permission granted");
                    AndroidLocationProvider.startRequestingLocationUpdates();
                } else {
                    Log.d(TAG, "Location permission not granted. Wut?");
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BluetoothClient.REQUEST_CODE_ENABLE_BLUETOOTH: {
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "Bluetooth enabled, starting to scan");
                    BluetoothClient.startScanning();
                } else {
                    Log.d(TAG, "Bluetooth not enabled, invoking new request");
                    BluetoothClient.requestBluetoothEnabling(this);
                }
                break;
            }
        }
    }

    private void requestLocationServices() {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout,
                R.string.error_location_disabled,
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction(R.string.action_enable, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidLocationProvider.requestLocationEnabling(HomeActivity.this);
            }
        });
        snackbar.show();
    }

    private void requestBluetooth() {
        Snackbar snackbar = Snackbar.make(
                coordinatorLayout,
                R.string.error_bluetooth_disabled,
                Snackbar.LENGTH_INDEFINITE
        );
        snackbar.setAction(R.string.action_enable, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BluetoothClient.requestBluetoothEnabling(HomeActivity.this);
            }
        });
        snackbar.show();
    }

}