package com.example.bleindoorpositioning.location;

import com.example.bleindoorpositioning.location.provider.LocationProvider;

public interface LocationListener {

    void onLocationUpdated(LocationProvider locationProvider, Location location);

}
