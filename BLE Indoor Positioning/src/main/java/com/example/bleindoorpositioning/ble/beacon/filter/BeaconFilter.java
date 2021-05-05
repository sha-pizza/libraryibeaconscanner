package com.example.bleindoorpositioning.ble.beacon.filter;

import com.example.bleindoorpositioning.ble.beacon.Beacon;

import java.util.Collection;
import java.util.List;

public interface BeaconFilter<B extends Beacon> {

    boolean canMatch(Beacon beacon);

    boolean matches(B beacon);

    List<B> getMatches(Collection<B> beacons);

}
