package com.example.bleindoorpositioning.ble.beacon;

public interface BeaconUpdateListener<B extends Beacon> {

    void onBeaconUpdated(B beacon);

}
