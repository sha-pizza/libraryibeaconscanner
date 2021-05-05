package com.example.bleindoorpositioning.ble.beacon.signal;

import com.example.bleindoorpositioning.ble.beacon.Beacon;

public interface RssiFilter {

    float filter(Beacon beacon);

}
