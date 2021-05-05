package com.example.bleindoorpositioning.location.provider;

import com.example.bleindoorpositioning.ble.beacon.Beacon;
import com.example.bleindoorpositioning.location.Location;

public abstract class BeaconLocationProvider<B extends Beacon> implements LocationProvider {

    protected B beacon;
    protected Location location;

    public BeaconLocationProvider(B beacon) {
        this.beacon = beacon;
    }

    protected abstract void updateLocation();

    protected boolean shouldUpdateLocation() {
        return location == null;
    }

    protected abstract boolean canUpdateLocation();

    @Override
    public Location getLocation() {
        if (shouldUpdateLocation() && canUpdateLocation()) {
            updateLocation();
        }
        return location;
    }

    public boolean hasLocation() {
        Location location = getLocation();
        return location != null && location.hasLatitudeAndLongitude();
    }

}
