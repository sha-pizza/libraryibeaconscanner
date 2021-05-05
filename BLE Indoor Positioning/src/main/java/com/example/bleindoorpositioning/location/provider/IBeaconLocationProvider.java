package com.example.bleindoorpositioning.location.provider;

import com.example.bleindoorpositioning.ble.beacon.IBeacon;

public abstract class IBeaconLocationProvider<B extends IBeacon> extends BeaconLocationProvider<B> {

    public IBeaconLocationProvider(B beacon) {
        super(beacon);
    }

}

