package com.example.bleindoorpositioning.location.provider;

import com.example.bleindoorpositioning.ble.beacon.Eddystone;

public abstract class EddystoneLocationProvider<B extends Eddystone> extends BeaconLocationProvider<B> {

    public EddystoneLocationProvider(B beacon) {
        super(beacon);
    }

}
